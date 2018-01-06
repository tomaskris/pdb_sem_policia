package main.java.DbAccess;

import main.java.Connector;
import main.java.Entities.MyDataClass;
import main.java.Entities.S_zamestnanec;
import main.java.Utils.Utils;
import main.java.helper.DatabaseSelecter;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DB_zamestnanec implements DBAccess {


    @Override
    public List<MyDataClass> selectAll() {
        try {
            DatabaseSelecter selector = new DatabaseSelecter(S_zamestnanec.class);
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
        S_zamestnanec obj = (S_zamestnanec) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vytvor_zamestnanec( ?, ?, ?, ?); END;");
            stmnt.setString(1, obj.getRod_cislo());
            stmnt.setBigDecimal(2, obj.getId_obvodu());
            stmnt.setDate(3, Utils.convertUtilToSql(obj.getDat_od()));
            stmnt.setDate(4, Utils.convertUtilToSql(obj.getDat_do()));
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MyDataClass object, MyDataClass newObject) {
        S_zamestnanec obj = (S_zamestnanec) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_update_zamestnanec( ?, ?, ?, ?, ?); END;");
            stmnt.setString(1, obj.getRod_cislo());
            stmnt.setBigDecimal(2, obj.getId_obvodu());
            stmnt.setDate(3, Utils.convertUtilToSql(obj.getDat_od()));
            stmnt.setDate(4, Utils.convertUtilToSql(obj.getDat_do()));
            stmnt.setBigDecimal(5, obj.getId_zamestnanca());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(MyDataClass object) {
        S_zamestnanec obj = (S_zamestnanec) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vymaz_zamestnanec(?); END;");
            stmnt.setBigDecimal(1, obj.getId_zamestnanca());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
