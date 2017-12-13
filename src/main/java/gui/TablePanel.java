package main.java.gui;

import main.java.gui.TableModels.MyTableModel;
import main.java.helper.DatabaseSelecter;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TablePanel extends JDialog {
    private JPanel contentPane;
    private JTable my_table;
    private JButton buttonOK;
    private Class dataClass;

    public TablePanel(Class dataClass) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.dataClass = dataClass;
        my_table.getTableHeader().setReorderingAllowed(false);
        load();
    }

    private void load() {
        new SwingWorker<List, RuntimeException>() {
            @Override
            protected List doInBackground() throws Exception {
                try {
                    DatabaseSelecter selector = new DatabaseSelecter(dataClass);
                    try {
                        return selector.selectObjects();
                    } catch (SQLException | InstantiationException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        return null;
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void done() {
                try {

                    if (get() != null) {
                        MyTableModel myTableModel = new MyTableModel(get());
                        my_table.setModel(myTableModel);
                    }else{
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


    public JPanel getPanel(){
        return contentPane;
    }
}
