package main.java;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class Connector {

    public static Connection getConnection() {
        try {
//            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String url = "jdbc:oracle:thin:@asterix.fri.uniza.sk:1521/orclpdb.fri.uniza.sk";
            String username = "xxx";
            String password = "xxx";
            Class.forName("oracle.jdbc.OracleDriver");

            Connection connection = DriverManager.getConnection( url, username, password);
            System.out.println("Connected");
            return connection;

        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return null;

        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver missing!");
            e.printStackTrace();
            return null;
        }
    }

}
