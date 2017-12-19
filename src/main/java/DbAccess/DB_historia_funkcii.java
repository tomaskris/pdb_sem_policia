package main.java.DbAccess;

import main.java.Connector;
import main.java.Entities.MyDataClass;
import main.java.Entities.S_historia_funkcii;
import main.java.Entities.S_pripad;
import main.java.Entities.S_region;
import main.java.Utils;
import main.java.helper.DatabaseSelecter;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DB_historia_funkcii implements DBAccess {


    @Override
    public List<MyDataClass> selectAll() {
        try {
            DatabaseSelecter selector = new DatabaseSelecter(S_historia_funkcii.class);
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
        S_historia_funkcii obj = (S_historia_funkcii) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vytvor_hist_funkcii(?, ?, ?, ?); END;");
            stmnt.setBigDecimal(1, obj.getId_zamestnanca());
            stmnt.setDate(2, Utils.convertUtilToSql(obj.getDat_od()));
            stmnt.setBigDecimal(3, obj.getId_funkcie());
            stmnt.setDate(4, Utils.convertUtilToSql(obj.getDat_do()));
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MyDataClass object, MyDataClass newObject) {
        S_historia_funkcii obj = (S_historia_funkcii) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_update_hist_funkcii(?, ?, ?, ?); END;");
            stmnt.setBigDecimal(1, obj.getId_zamestnanca());
            stmnt.setDate(2, Utils.convertUtilToSql(obj.getDat_od()));
            stmnt.setBigDecimal(3, obj.getId_funkcie());
            stmnt.setDate(4, Utils.convertUtilToSql(obj.getDat_do()));
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(MyDataClass object) {
        S_historia_funkcii obj = (S_historia_funkcii) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vymaz_hist_funkcii(?, ?); END;");
            stmnt.setBigDecimal(1, obj.getId_zamestnanca());
            stmnt.setDate(2, Utils.convertUtilToSql(obj.getDat_od()));
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
