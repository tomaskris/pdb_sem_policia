package main.java.Entities.Stats;

import main.java.Entities.MyDataClass;

public class MzdyNaRok extends MyDataClass {
    private Integer rok;
    private Integer suma;


    public Integer getRok() {
        return rok;
    }

    public void setRok(Integer rok) {
        this.rok = rok;
    }

    public Integer getSuma() {
        return suma;
    }

    public void setSuma(Integer suma) {
        this.suma = suma;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index){
            case 0:
                return rok;
            case 1:
                return suma;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index){
            case 0:
                return "Rok";
            case 1:
                return "Celkova suma v €";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 2;
    }
}
