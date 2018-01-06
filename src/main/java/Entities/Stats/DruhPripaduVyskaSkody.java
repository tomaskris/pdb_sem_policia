package main.java.Entities.Stats;

import main.java.Entities.MyDataClass;

public class DruhPripaduVyskaSkody extends MyDataClass{
    private String druh_pripadu;
    private Integer vyska_skody;

    public String getDruh_pripadu() {
        return druh_pripadu;
    }

    public void setDruh_pripadu(String druh_pripadu) {
        this.druh_pripadu = druh_pripadu;
    }

    public Integer getVyska_skody() {
        return vyska_skody;
    }

    public void setVyska_skody(Integer vyska_skody) {
        this.vyska_skody = vyska_skody;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index){
            case 0:
                return druh_pripadu;
            case 1:
                return vyska_skody;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index){
            case 0:
                return "Druh Pripadu";
            case 1:
                return "Vyska škody";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 2;
    }
}
