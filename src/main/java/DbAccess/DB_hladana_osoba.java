package main.java.DbAccess;

import main.java.Connector;
import main.java.Entities.MyDataClass;
import main.java.Entities.S_historia_funkcii;
import main.java.Entities.S_hladana_osoba;
import main.java.Entities.S_region;
import main.java.Utils;
import main.java.helper.DatabaseSelecter;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DB_hladana_osoba implements DBAccess {


    @Override
    public List<MyDataClass> selectAll() {
        try {
            DatabaseSelecter selector = new DatabaseSelecter(S_hladana_osoba.class);
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
        S_hladana_osoba obj = (S_hladana_osoba) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vytvor_hlad_osoba(?, ?, ?, ?, ?); END;");
            stmnt.setBigDecimal(1, obj.getId_pripadu());
            stmnt.setString(2, obj.getRod_cislo());
            stmnt.setString(3, obj.getDovod());
            stmnt.setDate(4, Utils.convertUtilToSql(obj.getDat_od()));
            stmnt.setDate(5, Utils.convertUtilToSql(obj.getDat_do()));
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MyDataClass object, MyDataClass newObject) {
        S_hladana_osoba obj = (S_hladana_osoba) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_update_hlad_osoba(?, ?, ?, ?, ?, ?); END;");
            stmnt.setBigDecimal(1, obj.getId_pripadu());
            stmnt.setString(2, obj.getRod_cislo());
            stmnt.setString(3, obj.getDovod());
            stmnt.setDate(4, Utils.convertUtilToSql(obj.getDat_od()));
            stmnt.setDate(5, Utils.convertUtilToSql(obj.getDat_do()));
            stmnt.setBigDecimal(6,obj.getId_hladanej());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(MyDataClass object) {
        S_hladana_osoba obj = (S_hladana_osoba) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vymaz_hlad_osoba(?); END;");
            stmnt.setBigDecimal(1,obj.getId_hladanej());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
