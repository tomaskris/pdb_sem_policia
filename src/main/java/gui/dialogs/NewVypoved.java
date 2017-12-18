package main.java.gui.dialogs;

import main.java.Entities.S_vypoved;
import main.java.gui.Vypoved;
import main.java.helper.VypovedData;
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
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return acceptFile(f);
            }

            @Override
            public String getDescription() {
                return null;
            }
        });
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
                return new VypovedData(getVypovedTyp(),new FileInputStream(file));
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


    public final static String jpg = "jpg";
    public final static String mp4 = "mp4";
    public final static String txt = "txt";

    public boolean acceptFile(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals(jpg) ||
                    extension.equals(mp4) ||
                    extension.equals(txt)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
