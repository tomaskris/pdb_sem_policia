package main.java.gui.stats;

import main.java.DbAccess.DB_Stats;
import main.java.gui.TableModels.MyTableModel;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MzdyPocasRokov extends JDialog implements LoadDataForTable  {
    private JPanel contentPane;
    private JTable my_table;
    private DB_Stats dbAccess;

    public MzdyPocasRokov() {
        setContentPane(contentPane);
        setModal(true);
        this.dbAccess = new DB_Stats();
    }

    @Override
    public void loadData() {
        new SwingWorker<List, RuntimeException>() {
            @Override
            protected List doInBackground() throws Exception {
                return dbAccess.getStatsMzdyRoky();
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
        return "Mzdy";
    }
}
