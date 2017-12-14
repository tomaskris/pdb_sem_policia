package main.java.Entities;

import java.math.BigDecimal;

public class S_osoba extends MyDataClass {
    private String rod_cislo;
    private BigDecimal id_biom_udaju;
    private String psc;
    private String meno;
    private String priezvisko;

    public BigDecimal getId_biom_udaju() {
        return id_biom_udaju;
    }

    public void setId_biom_udaju(BigDecimal id_biom_udaju) {
        this.id_biom_udaju = id_biom_udaju;
    }

    public String getRod_cislo() {
        return rod_cislo;
    }

    public void setRod_cislo(String rod_cislo) {
        this.rod_cislo = rod_cislo;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index) {
            case 0:
                return rod_cislo;
            case 1:
                return id_biom_udaju;
            case 2:
                return psc;
            case 3:
                return meno;
            case 4:
                return priezvisko;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index) {
            case 0:
                return "rod_cislo";
            case 1:
                return "id_biom_udaju";
            case 2:
                return "psc";
            case 3:
                return "meno";
            case 4:
                return "priezvisko";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 5;
    }
}
