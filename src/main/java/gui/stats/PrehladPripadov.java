package main.java.gui.stats;

import main.java.DbAccess.DB_Stats;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PrehladPripadov extends JDialog implements LoadDataForTable {
    private JPanel contentPane;
    private JComboBox typ;
    private JLabel status;
    private DB_Stats dbAccess;

    public PrehladPripadov() {
        setContentPane(contentPane);
        setModal(true);
        this.dbAccess = new DB_Stats();

        typ.addActionListener( (r) -> {
            status.setText("Selected: "+selectedValue());
        });

    }

    @Override
    public void loadData() {
       if(selectedValue().equals("PDF")){
           getFile(true);
       }else {
           getFile(false);
       }
    }

    private void getFile(boolean pdf) {
        String cesta = null;
        File extention = null;
        String ext = pdf ? ".pdf" : ".xml";

        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(contentPane);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            extention = new File(file.getAbsolutePath() + ext);
            cesta = extention.getAbsolutePath();
        }

        String finalCesta = cesta;
        String fileName = extention.getName();
        SwingWorker sw = new SwingWorker<Boolean, RuntimeException>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                if(pdf){
                    return dbAccess.getPripadyData(finalCesta);
                }else {
                    return dbAccess.getPripadyDataXML(finalCesta);
                }
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        System.out.println("True");
                        status.setText(fileName+" was saved.");
                    } else
                        System.out.println("Download failed.");

                } catch (InterruptedException | ExecutionException ex) {
                }
            }

            @Override
            protected void process(List<RuntimeException> chunks) {

            }
        };

        if(cesta != null){
            sw.execute();
        }

    }





    private String selectedValue(){
        return (String) typ.getSelectedItem();
    }

    @Override
    public JPanel getPanel() {
        return contentPane;
    }

    @Override
    public String toString() {
        return "Prehľad prípadov.";
    }

}
