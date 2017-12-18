package main.java.DbAccess;

import main.java.Connector;
import main.java.Entities.MyDataClass;
import main.java.Entities.S_odsudena_osoba;
import main.java.Entities.S_region;
import main.java.Utils;
import main.java.helper.DatabaseSelecter;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DB_odsudena_osoba implements DBAccess {


    @Override
    public List<MyDataClass> selectAll() {
        try {
            DatabaseSelecter selector = new DatabaseSelecter(S_odsudena_osoba.class);
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
        S_odsudena_osoba obj = (S_odsudena_osoba) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vytvor_odsudena(?, ?, ?, ?, ?); END;");
            stmnt.setString(1, obj.getRod_cislo());
            stmnt.setBigDecimal(2, obj.getId_pripadu());
            stmnt.setBigDecimal(3, obj.getId_vaznice());
            stmnt.setBigDecimal(4, obj.getDlzka_trestu());
            stmnt.setDate(5, Utils.convertUtilToSql(obj.getDat_nastupu()));
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MyDataClass object, MyDataClass newObject) {
        S_odsudena_osoba obj = (S_odsudena_osoba) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_update_odsudena(?, ?, ?, ?, ?, ?); END;");
            stmnt.setString(1, obj.getRod_cislo());
            stmnt.setBigDecimal(2, obj.getId_pripadu());
            stmnt.setBigDecimal(3, obj.getId_vaznice());
            stmnt.setBigDecimal(4, obj.getDlzka_trestu());
            stmnt.setDate(5, Utils.convertUtilToSql(obj.getDat_nastupu()));
            stmnt.setBigDecimal(6, obj.getId_odsudeneho());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(MyDataClass object) {
        S_odsudena_osoba obj = (S_odsudena_osoba) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_delete_odsudena(?); END;");
            stmnt.setBigDecimal(1, obj.getId_odsudeneho());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
