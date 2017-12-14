package main.java.Entities;

import java.math.BigDecimal;

public class S_region extends MyDataClass {
    private BigDecimal id_regionu;
    private String nazov;

    public BigDecimal getId_regionu() {
        return id_regionu;
    }

    public void setId_regionu(BigDecimal id_regionu) {
        this.id_regionu = id_regionu;
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
                return id_regionu;
            case 1:
                return nazov;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index) {
            case 0:
                return "id_regionu";
            case 1:
                return "nazov";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 2;
    }
}
