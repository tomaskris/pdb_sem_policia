package main.java.helper;


import main.java.Connector;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Class that creates a list of <T>s filled with values from the corresponding
 * database-table.
 *
 * @author Tino for http://www.java-blog.com
 *
 * @param <T>
 */
public class VypovedSelecter<T>{

    private BigDecimal id_osoby;
    private String query;
    private Class<T> type;


    public VypovedSelecter(Class<T> type, BigDecimal id){
        this.type = type;
        this.id_osoby = id;
        this.query = createQuery();
    }


    protected String createQuery() {

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");
        sb.append(getColumns(false));
        sb.append(" FROM ");

		/* We assume the table-name exactly matches the simpleName of T */
        sb.append("table(select vypoved from s_osoba_pripadu where id_osoby = ");
        sb.append(this.id_osoby);
        sb.append(" )");
        //sb.append(" ORDER BY "+ type.getDeclaredFields()[0].getName() +" ASC");
        return sb.toString();
    }

    /**
     * Creates a list of <T>s filled with values from the corresponding
     * database-table
     *
     * @return List of <T>s filled with values from the corresponding
     *         database-table
     *
     * @throws SQLException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    public List<T> selectObjects() throws SQLException,
            SecurityException, IllegalArgumentException,
            InstantiationException, IllegalAccessException,
            IntrospectionException, InvocationTargetException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = Connector.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            return createObjects(resultSet);

        } finally {
//            DatabaseResourceCloser.close(resultSet, statement,
//                    connection);
        }
    }

    /**
     *
     * Creates a list of <T>s filled with values from the provided ResultSet
     *
     * @param resultSet
     *            ResultSet that contains the result of the
     *            database-select-query
     *
     * @return List of <T>s filled with values from the provided ResultSet
     *
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    private List<T> createObjects(ResultSet resultSet)
            throws SecurityException, IllegalArgumentException,
            SQLException, InstantiationException,
            IllegalAccessException, IntrospectionException,
            InvocationTargetException {

        List<T> list = new ArrayList<T>();

        while (resultSet.next()) {

            T instance = type.newInstance();

            for (Field field : type.getDeclaredFields()) {

				/* We assume the table-column-names exactly match the variable-names of T */
                Object value = resultSet.getObject(field.getName());

                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
                        field.getName(), type);

                Method method = propertyDescriptor.getWriteMethod();
                method.invoke(instance, value);
            }

            list.add(instance);
        }
        return list;
    }

    /**
     *
     * Creates a comma-separated-String with the names of the variables in this
     * class
     *
     * @param usePlaceHolders
     *            true, if PreparedStatement-placeholders ('?') should be used
     *            instead of the names of the variables
     * @return
     */
    protected String getColumns(boolean usePlaceHolders) {
        StringBuilder sb = new StringBuilder();

        boolean first = true;
		/* Iterate the column-names */
        for (Field f : type.getDeclaredFields()) {
            if (first)
                first = false;
            else
                sb.append(", ");

            if (usePlaceHolders)
                sb.append("?");
            else
                sb.append(f.getName());
        }

        return sb.toString();
    }
}
