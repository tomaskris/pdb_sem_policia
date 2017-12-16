package main.java.Entities;

import main.java.DbAccess.DBAccess;

public abstract class MyDataClass {

    public abstract Object getValueAt(int index);
    public abstract String getValueName(int index);
    public abstract int numberOfAttr();

}
