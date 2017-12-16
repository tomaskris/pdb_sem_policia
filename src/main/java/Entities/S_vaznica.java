package main.java.Entities;

import java.math.BigDecimal;

public class S_vaznica extends MyDataClass {
    private BigDecimal id_vaznice;
    private String psc;
    private String nazov;

    public S_vaznica() {
        this.id_vaznice = BigDecimal.ZERO;
        this.psc = "";
        this.nazov = "";
    }

    public BigDecimal getId_vaznice() {
        return id_vaznice;
    }

    public void setId_vaznice(BigDecimal id_vaznice) {
        this.id_vaznice = id_vaznice;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index) {
            case 0:
                return id_vaznice;
            case 1:
                return psc;
            case 2:
                return nazov;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index) {
            case 0:
                return "id_vaznice";
            case 1:
                return "psc";
            case 2:
                return "nazov";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 3;
    }
}
