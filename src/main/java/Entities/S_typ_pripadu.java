package main.java.Entities;

import java.math.BigDecimal;

public class S_typ_pripadu extends MyDataClass {
    private BigDecimal id_typ_pripadu;
    private String nazov_typu;
    private String druh_pripadu;

    public S_typ_pripadu() {
        this.id_typ_pripadu = BigDecimal.ZERO;
        this.nazov_typu = "";
        this.druh_pripadu = "";
    }

    public BigDecimal getId_typ_pripadu() {
        return id_typ_pripadu;
    }

    public void setId_typ_pripadu(BigDecimal id_typ_pripadu) {
        this.id_typ_pripadu = id_typ_pripadu;
    }

    public String getNazov_typu() {
        return nazov_typu;
    }

    public void setNazov_typu(String nazov_typu) {
        this.nazov_typu = nazov_typu;
    }

    public String getDruh_pripadu() {
        return druh_pripadu;
    }

    public void setDruh_pripadu(String druh_pripadu) {
        this.druh_pripadu = druh_pripadu;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index) {
            case 0:
                return id_typ_pripadu;
            case 1:
                return nazov_typu;
            case 2:
                return druh_pripadu;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index) {
            case 0:
                return "id_typ_pripadu";
            case 1:
                return "nazov_typu";
            case 2:
                return "druh_pripadu";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 3;
    }
}
