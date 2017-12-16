package main.java.Entities;

import java.math.BigDecimal;

public class S_osoba_pripadu extends MyDataClass {
    private BigDecimal id_osoby;
    private BigDecimal id_pripadu;
    private String rod_cislo;
    private String typ_osoby;
    private Object vypoved;

    public S_osoba_pripadu() {
        this.id_osoby = BigDecimal.ZERO;
        this.id_pripadu = BigDecimal.ZERO;
        this.rod_cislo = "";
        this.typ_osoby = "";
        this.vypoved = new Object();
    }

    public BigDecimal getId_osoby() {
        return id_osoby;
    }

    public void setId_osoby(BigDecimal id_osoby) {
        this.id_osoby = id_osoby;
    }

    public BigDecimal getId_pripadu() {
        return id_pripadu;
    }

    public void setId_pripadu(BigDecimal id_pripadu) {
        this.id_pripadu = id_pripadu;
    }

    public String getRod_cislo() {
        return rod_cislo;
    }

    public void setRod_cislo(String rod_cislo) {
        this.rod_cislo = rod_cislo;
    }

    public String getTyp_osoby() {
        return typ_osoby;
    }

    public void setTyp_osoby(String typ_osoby) {
        this.typ_osoby = typ_osoby;
    }

    public Object getVypoved() {
        return vypoved;
    }

    public void setVypoved(Object vypoved) {
        this.vypoved = vypoved;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index) {
            case 0:
                return id_osoby;
            case 1:
                return id_pripadu;
            case 2:
                return rod_cislo;
            case 3:
                return typ_osoby;
            case 4:
                return vypoved;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index) {
            case 0:
                return "id_osoby";
            case 1:
                return "id_pripadu";
            case 2:
                return "rod_cislo";
            case 3:
                return "typ_osoby";
            case 4:
                return "vypoved";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 5;
    }
}
