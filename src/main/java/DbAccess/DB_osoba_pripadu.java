package main.java.DbAccess;

import main.java.Connector;
import main.java.Entities.MyDataClass;
import main.java.Entities.S_osoba_pripadu;
import main.java.Entities.S_region;
import main.java.helper.DatabaseSelecter;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DB_osoba_pripadu implements DBAccess {


    @Override
    public List<MyDataClass> selectAll() {
        try {
            DatabaseSelecter selector = new DatabaseSelecter(S_osoba_pripadu.class);
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
        S_osoba_pripadu obj = (S_osoba_pripadu) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vytvor_osoba_pripadu(?, ?, ?); END;");
            stmnt.setBigDecimal(1, obj.getId_pripadu());
            stmnt.setString(2, obj.getRod_cislo());
            stmnt.setString(3, obj.getTyp_osoby());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MyDataClass object, MyDataClass newObject) {
        S_osoba_pripadu obj = (S_osoba_pripadu) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_update_osoba_pripadu(?, ?, ?, ?); END;");
            stmnt.setBigDecimal(1, obj.getId_pripadu());
            stmnt.setString(2, obj.getRod_cislo());
            stmnt.setString(3, obj.getTyp_osoby());
            stmnt.setBigDecimal(4, obj.getId_osoby());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(MyDataClass object) {
        S_osoba_pripadu obj = (S_osoba_pripadu) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vymaz_osoba_pripadu(?); END;");
            stmnt.setBigDecimal(1, obj.getId_osoby());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
