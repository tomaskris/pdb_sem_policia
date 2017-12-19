package main.java.DbAccess;

import main.java.Entities.MyDataClass;

import java.util.List;

public interface DBAccess{
    List<MyDataClass> selectAll();
    void insert(MyDataClass object);
    void update(MyDataClass object, MyDataClass newObject);
    void delete(MyDataClass object);
}
