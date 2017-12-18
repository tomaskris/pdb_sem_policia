package main.java.DbAccess;

import main.java.Connector;
import main.java.Entities.MyDataClass;
import main.java.Entities.S_osoba;
import main.java.Entities.S_region;
import main.java.helper.DatabaseSelecter;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DB_osoba implements DBAccess {


    @Override
    public List<MyDataClass> selectAll() {
        try {
            DatabaseSelecter selector = new DatabaseSelecter(S_osoba.class);
            try {
                return selector.selectObjects();
            } catch (SQLException | InstantiationException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insert(MyDataClass object) {
        S_osoba obj = (S_osoba) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vytvor_osoba(?, ?, ?, ?, ?); END;");
            stmnt.setString(1, obj.getRod_cislo());
            stmnt.setBigDecimal(2, obj.getId_biom_udaju());
            stmnt.setString(3, obj.getPsc());
            stmnt.setString(4, obj.getMeno());
            stmnt.setString(5, obj.getPriezvisko());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MyDataClass object, MyDataClass newObject) {
        S_osoba obj = (S_osoba) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_update_osoba(?, ?, ?, ?, ?); END;");
            stmnt.setString(1, obj.getRod_cislo());
            stmnt.setBigDecimal(2, obj.getId_biom_udaju());
            stmnt.setString(3, obj.getPsc());
            stmnt.setString(4, obj.getMeno());
            stmnt.setString(5, obj.getPriezvisko());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(MyDataClass object) {
        S_osoba obj = (S_osoba) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_delete_osoba(?); END;");
            stmnt.setString(1, obj.getRod_cislo());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
