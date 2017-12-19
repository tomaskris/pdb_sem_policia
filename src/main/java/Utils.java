package main.java;



public class Utils {
    public static java.sql.Date convertUtilToSql(java.util.Date date){
        return date == null ? null : new java.sql.Date(date.getTime());
    }
}
