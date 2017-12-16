package main.java.DbAccess;

import main.java.Connector;
import main.java.Entities.MyDataClass;
import main.java.Entities.S_region;
import main.java.Entities.S_typ_funkcie;
import main.java.helper.DatabaseSelecter;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DB_typ_funkcie implements DBAccess {


    @Override
    public List<MyDataClass> selectAll() {
        try {
            DatabaseSelecter selector = new DatabaseSelecter(S_typ_funkcie.class);
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
        S_typ_funkcie obj = (S_typ_funkcie) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("INSERT INTO S_TYP_FUNKCIE VALUES (1, ?, ?)");
            stmnt.setString(1, obj.getNazov());
            stmnt.setBigDecimal(2, obj.getPlat());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MyDataClass object, MyDataClass newObject) {
        S_typ_funkcie obj = (S_typ_funkcie) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("UPDATE S_TYP_FUNKCIE SET " +
                    "NAZOV = ?, " +
                    "PLAT = ?" +
                    " WHERE ID_FUNKCIE = ?");
            stmnt.setString(1, obj.getNazov());
            stmnt.setBigDecimal(2, obj.getPlat());
            stmnt.setBigDecimal(3, obj.getId_funkcie());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(MyDataClass object) {
        S_typ_funkcie obj = (S_typ_funkcie) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("DELETE FROM S_TYP_FUNKCIE WHERE ID_FUNKCIE = ?");
            stmnt.setBigDecimal(1,obj.getId_funkcie());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
