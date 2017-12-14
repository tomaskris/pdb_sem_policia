package main.java.Entities;

import java.math.BigDecimal;

public class S_typ_funkcie extends MyDataClass {
    private BigDecimal id_funkcie;
    private String nazov;
    private BigDecimal plat;

    public BigDecimal getId_funkcie() {
        return id_funkcie;
    }

    public void setId_funkcie(BigDecimal id_funkcie) {
        this.id_funkcie = id_funkcie;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public BigDecimal getPlat() {
        return plat;
    }

    public void setPlat(BigDecimal plat) {
        this.plat = plat;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index) {
            case 0:
                return id_funkcie;
            case 1:
                return nazov;
            case 2:
                return plat;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index) {
            case 0:
                return "id_funkcie";
            case 1:
                return "nazov";
            case 2:
                return "plat";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 3;
    }
}
