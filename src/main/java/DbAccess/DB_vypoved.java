package main.java.DbAccess;

import main.java.Connector;
import main.java.Entities.MyDataClass;
import main.java.Entities.S_region;
import main.java.Entities.S_vypoved;
import main.java.helper.DatabaseSelecter;
import main.java.helper.VypovedSelecter;

import java.beans.IntrospectionException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DB_vypoved implements DBAccess {
    private BigDecimal id_osoby;

    public DB_vypoved(BigDecimal id_osoby) {
        this.id_osoby = id_osoby;
    }

    @Override
    public List<MyDataClass> selectAll() {
        try {
            VypovedSelecter selector = new VypovedSelecter(S_vypoved.class,this.id_osoby);
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
        S_vypoved obj = (S_vypoved) object;
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("INSERT INTO table(select vypoved from s_osoba_pripadu where id_osoby = ?) VALUES " +
                    "T_REC_VYPOVED(?,? )");
            stmnt.setBigDecimal(1, this.id_osoby);
            stmnt.setString(2, obj.getTyp_vypovede());
            stmnt.setBlob(3, obj.getZaznam());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vytvor_vypoved(?, ?, ?); END;");
            stmnt.setBigDecimal(1, this.id_osoby);
            stmnt.setString(2, obj.getTyp_vypovede());
            stmnt.setBlob(3, obj.getZaznam());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(BigDecimal id, String typ, InputStream in) {
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("INSERT INTO table(select vypoved from s_osoba_pripadu where id_osoby = ?) VALUES (" +
                    "T_REC_VYPOVED(?, ?))");
            stmnt.setBigDecimal(1, id);
            stmnt.setString(2, typ);
            stmnt.setBinaryStream(3, in);
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MyDataClass object, MyDataClass newObject) {
//        S_region obj = (S_region) newObject;
//        try (Connection connection = Connector.getConnection()) {
//            CallableStatement stmnt = connection.prepareCall("UPDATE S_REGION SET NAZOV = ? WHERE ID_REGIONU = ?");
//            stmnt.setString(1, obj.getNazov());
//            stmnt.setBigDecimal(2, obj.getId_regionu());
//            stmnt.execute();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void delete(MyDataClass object) {
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vymaz_vypoved(?, ?); END;");
            stmnt.setBigDecimal(1, this.id_osoby);
            stmnt.setBlob(2, ((S_vypoved) object).getZaznam());
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFile(MyDataClass object){

    }

}
