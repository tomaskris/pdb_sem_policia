package main.java.Entities;

import java.math.BigDecimal;
import java.sql.Blob;

public class S_vypoved extends MyDataClass {
    private BigDecimal id_vypovede;
    private BigDecimal id_osoby;
    private String typ_vypovede;
    private Blob zaznam;

    public BigDecimal getId_vypovede() {
        return id_vypovede;
    }

    public void setId_vypovede(BigDecimal id_vypovede) {
        this.id_vypovede = id_vypovede;
    }

    public BigDecimal getId_osoby() {
        return id_osoby;
    }

    public void setId_osoby(BigDecimal id_osoby) {
        this.id_osoby = id_osoby;
    }

    public String getTyp_vypovede() {
        return typ_vypovede;
    }

    public void setTyp_vypovede(String typ_vypovede) {
        this.typ_vypovede = typ_vypovede;
    }

    public Blob getZaznam() {
        return zaznam;
    }

    public void setZaznam(Blob zaznam) {
        this.zaznam = zaznam;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index) {
            case 0:
                return id_vypovede;
            case 1:
                return id_osoby;
            case 2:
                return typ_vypovede;
            case 3:
                return zaznam;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index) {
            case 0:
                return "id_vypovede";
            case 1:
                return "id_osoby";
            case 2:
                return "typ_vypovede";
            case 3:
                return "zaznam";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 4;
    }
}
