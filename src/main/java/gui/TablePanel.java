package main.java.gui;

import main.java.DbAccess.AccessList;
import main.java.DbAccess.BackgroundDB;
import main.java.DbAccess.DBAccess;
import main.java.Entities.MyDataClass;
import main.java.WidgetConfig;
import main.java.gui.TableModels.MyTableModel;
import main.java.helper.DatabaseSelecter;
import org.metawidget.swing.SwingMetawidget;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.beans.IntrospectionException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TablePanel extends JDialog {
    private JPanel contentPane;
    private JTable my_table;
    private JButton insertRowButton;
    private JButton deleteRowButton;
    private JButton loadDataButton;
    private JPanel insertPanel;
    private JButton updateButton;
    private JButton buttonOK;

    private Class dataClass;
    private MyDataClass instance;
    private DBAccess dbAccess;
    private SwingMetawidget metaWidget;
    private MyDataClass selectedObject;

    public TablePanel(Class dataClass) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.dataClass = dataClass;
        this.dbAccess = AccessList.getMap().get(this.dataClass);
        createFields();
        my_table.getTableHeader().setReorderingAllowed(false);

        initListeners();

    }

    protected MyTableModel getTableModel() {
        return ((MyTableModel) my_table.getModel());
    }

    private void createFields() {
        try {
            Constructor construct = dataClass.getConstructor();
            instance = (MyDataClass) construct.newInstance();

            GridBagConstraints constrains = new GridBagConstraints();
            constrains.weightx = 1.0;
            constrains.weighty = 1.0;
            constrains.fill = GridBagConstraints.HORIZONTAL;

            my_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event) {
                    if (my_table.getSelectedRow() > -1) {
                        Object row = ((MyTableModel) my_table.getModel()).getRow(my_table.getSelectedRow());
                        selectedObject = (MyDataClass) row;
                        metaWidget.setToInspect(row);
                    }
                }
            });

            this.metaWidget = new SwingMetawidget();
            WidgetConfig.setCommonSettings(this.metaWidget);
            this.metaWidget.setToInspect(instance);
            this.insertPanel.add(metaWidget, constrains);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    private void initListeners() {

        insertRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                BackgroundDB.BackgroundDBAccess(() -> {
                    dbAccess.insert(metaWidget.getToInspect());
                }, () -> {
                    loadData();
                });
            }
        });

        deleteRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                BackgroundDB.BackgroundDBAccess(() -> {
                            dbAccess.delete(metaWidget.getToInspect());
                        },
                        () -> {
                            loadData();
                        }
                );
            }
        });

        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                BackgroundDB.BackgroundDBAccess(() -> {
                            dbAccess.update(selectedObject,metaWidget.getToInspect());
                        },
                        () -> {
                            loadData();
                        }
                );
            }
        });

        loadDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                loadData();
            }
        });


    }


    private void loadData() {
        new SwingWorker<List, RuntimeException>() {
            @Override
            protected List doInBackground() throws Exception {
                return dbAccess.selectAll();
            }

            @Override
            protected void done() {
                try {
                    if (get() != null) {
                        MyTableModel myTableModel = new MyTableModel(get());
                        my_table.setModel(myTableModel);
                    } else {
                        System.out.println("data is null");
                    }
                } catch (InterruptedException | ExecutionException ex) {
                }
            }

            @Override
            protected void process(List<RuntimeException> chunks) {

            }

        }.execute();
    }


    public JPanel getPanel() {
        return contentPane;
    }
}
