package main.java.gui;

import main.java.Connector;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Main extends javax.swing.JDialog {
    private javax.swing.JPanel contentPane;
    private javax.swing.JButton buttonOK;

    public Main() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("SELECT * FROM OS_UDAJE");
            ResultSet result = stmnt.executeQuery();
            List<Object> output = new LinkedList<>();
            result.next();
            System.out.println(result.getString("ROD_CISLO"));
            System.out.println(result.getString("MENO"));
            System.out.println(result.getString("PRIEZVISKO"));
            System.out.println(result.getString("ULICA"));
            System.out.println(result.getString("PSC"));
            System.out.println(result.getString("OBEC"));


            System.out.println(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("sfda");

        Main dialog = new Main();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
