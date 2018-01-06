package main.java.Entities.Stats;

import main.java.Entities.MyDataClass;

import java.math.BigInteger;

public class TrestnyCinStat extends MyDataClass {
    private Integer pocetTrestCinov;
    private Integer pocetPriestupkov;
    private Integer spolu;

    public Integer getPocetTrestCinov() {
        return pocetTrestCinov;
    }

    public void setPocetTrestCinov(Integer pocetTrestCinov) {
        this.pocetTrestCinov = pocetTrestCinov;
    }

    public Integer getPocetPriestupkov() {
        return pocetPriestupkov;
    }

    public void setPocetPriestupkov(Integer pocetPriestupkov) {
        this.pocetPriestupkov = pocetPriestupkov;
    }

    public Integer getSpolu() {
        return spolu;
    }

    public void setSpolu(Integer spolu) {
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
        return 3;
    }
}

