package main.java.Utils;


import java.text.SimpleDateFormat;

public class Utils {
    public static java.sql.Date convertUtilToSql(java.util.Date date){
        return date == null ? null : new java.sql.Date(date.getTime());
    }

    private static String datePattern = "dd.MM.yyyy";
    public static SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
}
