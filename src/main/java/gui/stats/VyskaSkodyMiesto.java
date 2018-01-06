package main.java.gui.stats;

import main.java.DbAccess.DB_Stats;
import main.java.gui.TableModels.MyTableModel;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VyskaSkodyMiesto extends JDialog implements LoadDataForTable {
    private JPanel contentPane;
    private JComboBox comboBox1;
    private JTable my_table;
    private JButton buttonOK;
    private DB_Stats dbAccess;

    public VyskaSkodyMiesto() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.dbAccess = new DB_Stats();
    }


    @Override
    public void loadData() {
        new SwingWorker<List, RuntimeException>() {
            @Override
            protected List doInBackground() throws Exception {
                switch (getMiesto()) {
                    case REGION:
                        return dbAccess.getStatsVyskaSkodyRegion();
                    case MESTO:
                        return dbAccess.getStatsVyskaSkodyMesto();
                    case DRUH_PRIPADU:
                        return dbAccess.getStatsVyskaSkodyDruhPripadu();
                    case POHLAVIE:
                        return dbAccess.getStatsVyskaSkodyPohlavie();
                    case VEK_OBVINENEHO:
                        return dbAccess.getStatsVyskaSkodyVekObvineneho();
                    default:
                        return dbAccess.getStatsVyskaSkodyRegion();
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
        DRUH_PRIPADU,
        POHLAVIE,
        VEK_OBVINENEHO
    }

    private MIESTO getMiesto() {
        switch (comboBox1.getSelectedIndex()) {
            case 0:
                return MIESTO.REGION;
            case 1:
                return MIESTO.MESTO;
            case 2:
                return MIESTO.DRUH_PRIPADU;
            case 3:
                return MIESTO.POHLAVIE;
            case 4:
                return MIESTO.VEK_OBVINENEHO;
            default:
                return MIESTO.REGION;
        }
    }

    @Override
    public JPanel getPanel() {
        return contentPane;
    }

    @Override
    public String toString() {
        return "Vyska skody podla miesta.";
    }
}
