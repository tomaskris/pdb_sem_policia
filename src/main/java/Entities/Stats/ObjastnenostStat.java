package main.java.Entities.Stats;

import main.java.Entities.MyDataClass;

public class ObjastnenostStat extends MyDataClass {
    private double trestne_ciny;
    private double priestupky;
    private double spolu;

    public double getTrestne_ciny() {
        return trestne_ciny;
    }

    public void setTrestne_ciny(double trestne_ciny) {
        this.trestne_ciny = trestne_ciny;
    }

    public double getPriestupky() {
        return priestupky;
    }

    public void setPriestupky(double priestupky) {
        this.priestupky = priestupky;
    }

    public double getSpolu() {
        return spolu;
    }

    public void setSpolu(double spolu) {
        this.spolu = spolu;
    }


    @Override
    public Object getValueAt(int index) {
        return null;
    }

    @Override
    public String getValueName(int index) {
        return null;
    }

    @Override
    public int numberOfAttr() {
        return 0;
    }
}
