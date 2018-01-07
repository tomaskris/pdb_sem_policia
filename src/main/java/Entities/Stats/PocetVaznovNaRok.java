package main.java.Entities.Stats;

import main.java.Entities.MyDataClass;

public class PocetVaznovNaRok extends MyDataClass {
    private Integer rok;
    private Integer pocet;


    public Integer getRok() {
        return rok;
    }

    public void setRok(Integer rok) {
        this.rok = rok;
    }

    public Integer getPocet() {
        return pocet;
    }

    public void setPocet(Integer pocet) {
        this.pocet = pocet;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index){
            case 0:
                return rok;
            case 1:
                return pocet;
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
                return "Pocet vaznov vo vykone";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 2;
    }
}
