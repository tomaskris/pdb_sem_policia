package main.java.Entities;

import java.math.BigDecimal;
import java.sql.Blob;

public class S_vypoved extends MyDataClass {
    private String typ_vypovede;
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

    @Override
    public Object getValueAt(int index) {
        switch (index) {
            case 0:
                return typ_vypovede;
            case 1:
                return zaznam;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index) {
            case 0:
                return "typ_vypovede";
            case 1:
                return "zaznam";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 2;
    }
}
