package main.java.gui.stats;

import main.java.DbAccess.DB_Stats;
import main.java.Utils.Utils;
import main.java.gui.TableModels.MyTableModel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ObjastnenostRegion extends JDialog implements LoadDataForTable {
    private JPanel contentPane;
    private JTable my_table;
    private JCheckBox obdobie;
    private JPanel my_panel;
    private JPanel panel;
    private JButton buttonOK;
    private DB_Stats dbAccess;
    private JDatePicker dat_od;
    private JDatePicker dat_do;


    public ObjastnenostRegion() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.dbAccess = new DB_Stats();


//        JDatePanel panel = new JDatePanel();
        dat_od = new JDatePicker();
        dat_od.setEnabled(false);
        dat_do = new JDatePicker();
        dat_do.setEnabled(false);
        panel.add(new JLabel("datum od: "));
        panel.add(dat_od);
        panel.add(new JLabel("datum do: "));
        panel.add(dat_do);

        obdobie.addActionListener((e) -> {
            boolean enabled = obdobie.isSelected();
            dat_od.setEnabled(enabled);
            dat_do.setEnabled(enabled);
        });

    }

    @Override
    public void loadData() {
        new SwingWorker<List, RuntimeException>() {
            @Override
            protected List doInBackground() throws Exception {
                boolean zaObdobie = obdobie.isSelected();
                if(zaObdobie){
                    Date datum_od = Utils.dateFormatter.parse(dat_od.getFormattedTextField().getText());
                    Date datum_do = Utils.dateFormatter.parse(dat_do.getFormattedTextField().getText());
                    return dbAccess.getStatsObjastnenostRegion(datum_od, datum_do);
                }else {
                    return dbAccess.getStatsObjastnenostRegion();
                }
            }

            @Override
            protected void done() {
                try {
                    if (get() != null) {
                        MyTableModel myTableModel = new MyTableModel(get());
                        my_table.setModel(myTableModel);

                    } else
                        System.out.println("data is null");

                } catch (InterruptedException | ExecutionException ex) {
                }
            }

            @Override
            protected void process(List<RuntimeException> chunks) {

            }

        }.execute();
    }

    @Override
    public JPanel getPanel() {
        return contentPane;
    }

    @Override
    public String toString() {
        return "Objastnenost podla Regionov";
    }
}
