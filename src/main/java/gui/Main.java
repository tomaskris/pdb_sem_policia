package main.java.gui;


import main.java.DbAccess.DBAccess;
import main.java.DbAccess.DB_Stats;
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
    private Tables tables;
    private JPanel stats;

    public Main() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setPreferredSize(new Dimension(800,640));
        tables = new Tables();
        tabbedPane1.add("Policia",tables.getPanelPolicia());
        tabbedPane1.add("Pripady", tables.getPanelPripady());
        tabbedPane1.add("Osoby",tables.getPanelOsoby());

    }




    private void onOK() {
        // add your code here
        DB_Stats access = new DB_Stats();
        access.getPripadyData("C:\\Users\\Chudj\\Desktop\\TEST\\text.pdf");
    }

    public static void main(String[] args) {

        Main dialog = new Main();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        stats = new Stats().getPanel();
    }
}
