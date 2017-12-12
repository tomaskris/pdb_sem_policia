package main.java;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    public static Connection getConnection() {
        try {
//            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String url = "jdbc:oracle:thin:@asterix.fri.uniza.sk:1521/orclpdb.fri.uniza.sk";
            String username = "xxx";
            String password = "xxx";
            throw new Error("Treba tu odkomentovat riadok a vyplnit prihlasovacie udaje");


            Class.forName("oracle.jdbc.OracleDriver");


            Connection connection = DriverManager.getConnection( url, username, password);
            System.out.println("You madee it, take control your database now!");
            return connection;

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;

        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return null;
        }
    }

}
