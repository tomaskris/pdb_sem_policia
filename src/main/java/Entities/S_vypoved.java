package main.java.Entities;

import java.math.BigDecimal;
import java.sql.Blob;

public class S_vypoved extends MyDataClass {
    private BigDecimal id_vypovede;
    private String typ_vypovede;
    private String typ_suboru;
    private Blob zaznam;

    public S_vypoved() {
        this.typ_vypovede = "";
        this.zaznam = null;
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

    public String getTyp_suboru() {
        return typ_suboru;
    }

    public void setTyp_suboru(String typ_suboru) {
        this.typ_suboru = typ_suboru;
    }

    public BigDecimal getId_vypovede() {
        return id_vypovede;
    }

    public void setId_vypovede(BigDecimal id_vypovede) {
        this.id_vypovede = id_vypovede;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index) {
            case 0:
                return id_vypovede;
            case 1:
                return typ_vypovede;
            case 2:
                return typ_suboru;
            case 3:
                return zaznam == null ? "" : "Zaznam";
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index) {
            case 0:
                return "ID";
            case 1:
                return "typ vypovede";
            case 2:
                return "typ suboru";
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
