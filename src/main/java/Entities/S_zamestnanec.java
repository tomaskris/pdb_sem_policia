package main.java.Entities;

import java.math.BigDecimal;
import java.util.Date;

public class S_zamestnanec extends MyDataClass {
    private BigDecimal id_zamestnanca;
    private String rod_cislo;
    private BigDecimal id_obvodu;
    private Date dat_od;
    private Date dat_do;

    public BigDecimal getId_zamestnanca() {
        return id_zamestnanca;
    }

    public void setId_zamestnanca(BigDecimal id_zamestnanca) {
        this.id_zamestnanca = id_zamestnanca;
    }

    public BigDecimal getId_obvodu() {
        return id_obvodu;
    }

    public void setId_obvodu(BigDecimal id_obvodu) {
        this.id_obvodu = id_obvodu;
    }

    public String getRod_cislo() {
        return rod_cislo;
    }

    public void setRod_cislo(String rod_cislo) {
        this.rod_cislo = rod_cislo;
    }

    public Date getDat_od() {
        return dat_od;
    }

    public void setDat_od(Date dat_od) {
        this.dat_od = dat_od;
    }

    public Date getDat_do() {
        return dat_do;
    }

    public void setDat_do(Date dat_do) {
        this.dat_do = dat_do;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index) {
            case 0:
                return id_zamestnanca;
            case 1:
                return rod_cislo;
            case 2:
                return id_obvodu;
            case 3:
                return dat_od;
            case 4:
                return dat_do;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index) {
            case 0:
                return "id_zamestnanca";
            case 1:
                return "rod_cislo";
            case 2:
                return "id_obvodu";
            case 3:
                return "dat_od";
            case 4:
                return "dat_do";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 5;
    }
}
