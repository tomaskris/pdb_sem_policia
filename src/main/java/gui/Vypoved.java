package main.java.gui;

import main.java.DbAccess.DB_vypoved;
import main.java.Entities.S_vypoved;
import main.java.gui.TableModels.MyTableModel;
import main.java.gui.dialogs.NewVypoved;
import main.java.helper.VypovedData;
import org.apache.commons.io.IOUtils;
import org.metawidget.swing.SwingMetawidget;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Vypoved extends JDialog {
    private JPanel contentPane;
    private JButton downloadBtn;
    private JButton vymazBtn;
    private JTable vypovede;
    private JButton pridatButton;
    private DB_vypoved dbAccess;
    private S_vypoved selectedObject;
    private SwingMetawidget metaWidget;
    private BigDecimal id_osoby;

    public Vypoved(BigDecimal id_osoby) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(downloadBtn);
        this.dbAccess = new DB_vypoved(id_osoby);
        this.id_osoby = id_osoby;
        loadData();
        setListeners();

        vypovede.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (vypovede.getSelectedRow() > -1) {
                    Object row = ((MyTableModel) vypovede.getModel()).getRow(vypovede.getSelectedRow());
                    selectedObject = (S_vypoved) row;
                }
            }
        });
    }

    private void setListeners() {
        downloadBtn.addActionListener(e -> {
            if(selectedObject != null){
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(contentPane);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();

                    Blob blob = selectedObject.getZaznam();
                    File extention = new File(file.getAbsolutePath() + getExtention());
                    boolean stav = file.renameTo(extention);

                    try {
                        InputStream in = blob.getBinaryStream();
                        OutputStream out = new FileOutputStream(extention);

                        IOUtils.copy(in, out);
                        out.close();
                        in.close();
                        System.out.println("SAVED");

                    } catch (SQLException | IOException e1) {
                        e1.printStackTrace();
                    }

                }


            }
        });

        pridatButton.addActionListener(e -> {
            NewVypoved vypovedDialog = new NewVypoved();
            VypovedData vypovedData =  vypovedDialog.getVypoved();
            if(vypovedData != null){
                dbAccess.insert(id_osoby,vypovedData.getTyp_vypovede(), vypovedData.getZaznam());
                loadData();
            }
        });

        vymazBtn.addActionListener((event) -> {
            if(selectedObject != null){
                dbAccess.delete(selectedObject);
                loadData();
            }
        });
    }

    private String getExtention() {
        switch (selectedObject.getTyp_vypovede()){
            case "T":
                return ".txt";
            case "V":
                return ".mp4";
            case "F":
                return ".jpg";
        }
        return "";
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
                        vypovede.setModel(myTableModel);
                    }
                } catch (InterruptedException | ExecutionException ex) {
                }
            }

            @Override
            protected void process(List<RuntimeException> chunks) {

            }

        }.execute();
    }

}
