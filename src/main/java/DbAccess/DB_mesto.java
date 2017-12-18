package main.java.DbAccess;

import main.java.Connector;
import main.java.Entities.MyDataClass;
import main.java.Entities.S_hladana_osoba;
import main.java.Entities.S_mesto;
import main.java.Entities.S_region;
import main.java.helper.DatabaseSelecter;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DB_mesto implements DBAccess {


    @Override
    public List<MyDataClass> selectAll() {
        try {
            DatabaseSelecter selector = new DatabaseSelecter(S_mesto.class);
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
        S_mesto obj = (S_mesto) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vytvor_mesto(?, ?, ?, ?); END;");
            stmnt.setString(1, obj.getPsc());
            stmnt.setBigDecimal(2, obj.getId_regionu());
            stmnt.setBigDecimal(3, obj.getId_obvodu());
            stmnt.setString(4, obj.getNazov());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MyDataClass object, MyDataClass newObject) {
        S_mesto obj = (S_mesto) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_update_mesto(?, ?, ?, ?); END;");
            stmnt.setString(1, obj.getPsc());
            stmnt.setBigDecimal(2, obj.getId_regionu());
            stmnt.setBigDecimal(3, obj.getId_obvodu());
            stmnt.setString(4, obj.getNazov());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(MyDataClass object) {
        S_mesto obj = (S_mesto) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_delete_mesto(?); END;");
            stmnt.setString(1, obj.getPsc());

            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
