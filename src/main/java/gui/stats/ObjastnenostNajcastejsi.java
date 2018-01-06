package main.java.gui.stats;

import main.java.DbAccess.DB_Stats;
import main.java.gui.TableModels.MyTableModel;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ObjastnenostNajcastejsi extends JDialog implements LoadDataForTable {
    private JPanel contentPane;
    private JTable my_table;
    private JSlider pocet;
    private JLabel lblPocet;
    private DB_Stats dbAccess;

    public ObjastnenostNajcastejsi() {
        setContentPane(contentPane);
        setModal(true);
        this.dbAccess = new DB_Stats();

        pocet.setValue(1);
        lblPocet.setText("Počet trestných činov: "+pocet.getValue());
        pocet.addChangeListener((e) ->{
            lblPocet.setText("Počet trestných činov: "+pocet.getValue());
        });
    }

    @Override
    public void loadData() {
        new SwingWorker<List, RuntimeException>() {
            @Override
            protected List doInBackground() throws Exception {
                return dbAccess.getStatsObjastnenostDruhPripadu(pocet.getValue());
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
        return "Objasnenost najčastejšich druhov trestných činov.";
    }
}
