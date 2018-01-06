package main.java.Entities.Stats;

import main.java.Entities.MyDataClass;

public class DruhPripaduObjastnenost extends MyDataClass{
    private String druh_pripadu;
    private Integer poc_pripadov;
    private Double objastnenost;

    public String getDruh_pripadu() {
        return druh_pripadu;
    }

    public void setDruh_pripadu(String druh_pripadu) {
        this.druh_pripadu = druh_pripadu;
    }

    public Integer getPoc_pripadov() {
        return poc_pripadov;
    }

    public void setPoc_pripadov(Integer poc_pripadov) {
        this.poc_pripadov = poc_pripadov;
    }

    public Double getObjastnenost() {
        return objastnenost;
    }

    public void setObjastnenost(Double objastnenost) {
        this.objastnenost = objastnenost;
    }


    @Override
    public Object getValueAt(int index) {
        switch (index){
            case 0:
                return druh_pripadu;
            case 1:
                return poc_pripadov;
            case 2:
                return objastnenost;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index){
            case 0:
                return "Druh Pripadov";
            case 1:
                return "Pocet Pripadov";
            case 2:
                return "Objastnenost";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 3;
    }
}
