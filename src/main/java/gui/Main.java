package main.java.gui;


import main.java.gui.TableModels.MyTableModel;
import main.java.helper.DatabaseSelecter;

import javax.swing.*;
import java.awt.*;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main extends javax.swing.JDialog {
    private javax.swing.JPanel contentPane;
    private javax.swing.JButton buttonOK;
    private JTabbedPane tabbedPane1;
    private JTable myTable;
    private JPanel Mesta;
    private JPanel tables;

    public Main() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setPreferredSize(new Dimension(800,640));

        buttonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                onOK();
            }
        });
    }




    private void onOK() {
        // add your code here
        dispose();
    }

    public static void main(String[] args) {

        Main dialog = new Main();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        tables = new Tables().getPanel();
    }
}
