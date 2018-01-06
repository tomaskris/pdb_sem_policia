package main.java.DbAccess;

import main.java.Connector;
import main.java.Entities.MyDataClass;
import main.java.Entities.S_pripad;
import main.java.Utils.Utils;
import main.java.helper.DatabaseSelecter;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DB_pripad implements DBAccess {


    @Override
    public List<MyDataClass> selectAll() {
        try {
            DatabaseSelecter selector = new DatabaseSelecter(S_pripad.class);
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
        S_pripad obj = (S_pripad) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vytvor_pripad(?, ?, ?, ?, ?, ?, ?); END;");
            stmnt.setBigDecimal(1, obj.getId_typ_pripadu());
            stmnt.setBigDecimal(2, obj.getId_obvodu());
            stmnt.setString(3, obj.getMiesto_vykon());
            stmnt.setDate(4, Utils.convertUtilToSql(obj.getDat_vykon()));
            stmnt.setString(5, obj.getObjasneny());
            stmnt.setDate(6, Utils.convertUtilToSql(obj.getDat_zac()));
            stmnt.setDate(7, Utils.convertUtilToSql(obj.getDat_ukon()));
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MyDataClass object, MyDataClass newObject) {
        S_pripad obj = (S_pripad) newObject;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall(
                    "BEGIN proc_update_pripad(?, ?, ?, ?, ?, ?, ?, ?); END;"
            );

            stmnt.setBigDecimal(1, obj.getId_typ_pripadu());
            stmnt.setBigDecimal(2, obj.getId_obvodu());
            stmnt.setString(3, obj.getMiesto_vykon());
            stmnt.setDate(4, Utils.convertUtilToSql(obj.getDat_vykon()));
            stmnt.setString(5, obj.getObjasneny());
            stmnt.setDate(6, Utils.convertUtilToSql(obj.getDat_zac()));
            stmnt.setDate(7, Utils.convertUtilToSql(obj.getDat_ukon()));
            stmnt.setBigDecimal(8, obj.getId_pripadu());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(MyDataClass object) {
        S_pripad obj = (S_pripad) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vymaz_pripad(?); END;");
            stmnt.setBigDecimal(1, obj.getId_pripadu());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
