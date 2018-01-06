package main.java.gui.stats;

import main.java.DbAccess.DB_Stats;
import main.java.gui.TableModels.MyTableModel;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TrestneCinyMiesto extends JDialog implements LoadDataForTable {
    private JPanel contentPane;
    private JTable my_table;
    private JComboBox comboBox1;
    private DB_Stats dbAccess;
    private MIESTO miesto;

    public TrestneCinyMiesto() {
        setContentPane(contentPane);
        setModal(true);
        this.dbAccess = new DB_Stats();
        miesto = MIESTO.REGION;

        comboBox1.addActionListener((event) ->{
            System.out.println("event");
        });
    }


    public void loadData() {
        new SwingWorker<List, RuntimeException>() {
            @Override
            protected List doInBackground() throws Exception {
                switch (getMiesto()) {
                    case REGION:
                        return dbAccess.getStatsTrestneCinyRegion();
                    case MESTO:
                        return dbAccess.getStatsTrestneCinyMesto();
                    case OBVOD:
                        return dbAccess.getStatsTrestneCinyObvod();
                    default:
                        return dbAccess.getStatsTrestneCinyRegion();
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



    enum MIESTO {
        REGION,
        MESTO,
        OBVOD
    }

    private MIESTO getMiesto(){
        switch (comboBox1.getSelectedIndex()){
            case 0:
                return MIESTO.REGION;
            case 1:
                return MIESTO.MESTO;
            case 2:
                return MIESTO.OBVOD;
            default:
                return MIESTO.REGION;
        }
    }

    public JPanel getPanel() {
        return contentPane;
    }

    @Override
    public String toString() {
        return "Trestne ciny podla miesta.";
    }
}
