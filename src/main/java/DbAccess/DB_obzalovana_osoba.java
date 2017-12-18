package main.java.DbAccess;

import main.java.Connector;
import main.java.Entities.MyDataClass;
import main.java.Entities.S_obzalovana_osoba;
import main.java.Entities.S_region;
import main.java.helper.DatabaseSelecter;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DB_obzalovana_osoba implements DBAccess {


    @Override
    public List<MyDataClass> selectAll() {
        try {
            DatabaseSelecter selector = new DatabaseSelecter(S_obzalovana_osoba.class);
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
        S_obzalovana_osoba obj = (S_obzalovana_osoba) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vytvor_obzalovana(?, ?, ?, ?); END;");
            stmnt.setBigDecimal(1, obj.getId_pripadu());
            stmnt.setString(2, obj.getRod_cislo());
            stmnt.setBigDecimal(3, obj.getVyska_skody());
            stmnt.setBigDecimal(4, obj.getUdelena_pokuta());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MyDataClass object, MyDataClass newObject) {
        S_obzalovana_osoba obj = (S_obzalovana_osoba) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_update_obzalovana(?, ?, ?, ?, ?); END;");
            stmnt.setBigDecimal(1, obj.getId_pripadu());
            stmnt.setString(2, obj.getRod_cislo());
            stmnt.setBigDecimal(3, obj.getVyska_skody());
            stmnt.setBigDecimal(4, obj.getUdelena_pokuta());
            stmnt.setBigDecimal(5, obj.getId_obzalovanej());

            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(MyDataClass object) {
        S_obzalovana_osoba obj = (S_obzalovana_osoba) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vymaz_obzalovana(?); END;");
            stmnt.setBigDecimal(1, obj.getId_obzalovanej());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
