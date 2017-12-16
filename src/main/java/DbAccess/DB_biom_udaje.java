package main.java.DbAccess;

import main.java.Connector;
import main.java.Entities.MyDataClass;
import main.java.Entities.S_biom_udaje;
import main.java.Entities.S_region;
import main.java.helper.DatabaseSelecter;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DB_biom_udaje implements DBAccess {


    @Override
    public List<MyDataClass> selectAll() {
        try {
            DatabaseSelecter selector = new DatabaseSelecter(S_biom_udaje.class);
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
        S_biom_udaje obj = (S_biom_udaje) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("INSERT INTO S_BIOM_UDAJE VALUES (1, ?, ?, ?, ?)");
            stmnt.setBigDecimal(1, obj.getVyska());
            stmnt.setString(2, obj.getTyp_postavy());
            stmnt.setString(3, obj.getFarba_vlasov());
            stmnt.setString(4, obj.getFarba_oci());
            stmnt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MyDataClass object, MyDataClass newObject) {
        S_biom_udaje obj = (S_biom_udaje) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("UPDATE S_BIOM_UDAJE SET VYSKA = ?," +
                    " TYP_POSTAVY = ?, " +
                    "FARBA_VLASOV = ?, " +
                    "FARBA_OCI = ? WHERE ID_BIOM_UDAJU = ?");
            stmnt.setBigDecimal(1, obj.getVyska());
            stmnt.setString(2, obj.getTyp_postavy());
            stmnt.setString(3, obj.getFarba_vlasov());
            stmnt.setString(4, obj.getFarba_oci());
            stmnt.setBigDecimal(5, obj.getId_biom_udaju());

            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(MyDataClass object) {
        S_biom_udaje obj = (S_biom_udaje) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("DELETE FROM S_BIOM_UDAJE WHERE ID_BIOM_UDAJU = ?");
            stmnt.setBigDecimal(1,obj.getId_biom_udaju());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
