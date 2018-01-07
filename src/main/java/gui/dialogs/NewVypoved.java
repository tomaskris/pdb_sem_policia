package main.java.gui.dialogs;

import main.java.Entities.S_vypoved;
import main.java.gui.Vypoved;
import main.java.helper.VypovedData;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

public class NewVypoved extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBox1;
    private JButton selectFileButton;
    private JLabel fileName;
    private JPanel fileChooserPanel;
    private JFileChooser fileChooser;
    private File file;

    public NewVypoved() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setMinimumSize(new Dimension(600,500));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void selectFile() {
        this.fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(fileChooserPanel);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
            fileName.setText(file.getName());
        }
    }

    public VypovedData getVypoved(){
        setVisible(true);
        if(file != null){
            try {
                String fileExtention = FilenameUtils.getExtension(file.getName());
                return new VypovedData(getVypovedTyp(),"."+fileExtention,new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        return null;
    }

    private String getVypovedTyp() {
        switch (comboBox1.getSelectedIndex()){
            case 0:
                return "T";
            case 1:
                return "F";
            case 2:
                return "V";
            default:
                return "T";
        }
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
