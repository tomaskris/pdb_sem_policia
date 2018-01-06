package main.java.Entities.Stats;

import main.java.Entities.MyDataClass;

public class VyskaSkodyStat extends MyDataClass{
    private Integer vyskaTrestCinov;
    private Integer vyskaPristupkov;
    private Integer vyskaSpolu;

    public Integer getVyskaTrestCinov() {
        return vyskaTrestCinov;
    }

    public void setVyskaTrestCinov(Integer vyskaTrestCinov) {
        this.vyskaTrestCinov = vyskaTrestCinov;
    }

    public Integer getVyskaPristupkov() {
        return vyskaPristupkov;
    }

    public void setVyskaPristupkov(Integer vyskaPristupkov) {
        this.vyskaPristupkov = vyskaPristupkov;
    }

    public Integer getVyskaSpolu() {
        return vyskaSpolu;
    }

    public void setVyskaSpolu(Integer vyskaSpolu) {
        this.vyskaSpolu = vyskaSpolu;
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
