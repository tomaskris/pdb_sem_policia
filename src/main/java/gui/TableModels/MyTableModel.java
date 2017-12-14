package main.java.gui.TableModels;

import main.java.Entities.MyDataClass;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MyTableModel<T extends MyDataClass> extends AbstractTableModel {
    private List<T> objects;

    public MyTableModel() {
    }

    public MyTableModel(List<T> objects) {
        this.objects = objects;
    }

    @Override
    public int getRowCount() {
        return objects.size();
    }

    @Override
    public int getColumnCount() {
        if(objects.size() > 0)
            return objects.get(0).numberOfAttr();
        else
            return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(objects.size() > rowIndex){
            MyDataClass mesto = objects.get(rowIndex);
            return mesto.getValueAt(columnIndex);
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        if(objects.size() > 0)
            return objects.get(0).getValueName(column);
        else
            return "UNKNOWN";
    }
}
