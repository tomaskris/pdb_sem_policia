package main.java.Entities;

import java.math.BigDecimal;
import java.util.Date;

public class S_pripad extends MyDataClass {
    private BigDecimal id_pripadu;
    private BigDecimal id_typ_pripadu;
    private BigDecimal id_obvodu;
    private String miesto_vykon;
    private Date dat_vykon;
    private String objasneny;
    private Date dat_zac;
    private Date dat_ukon;

    public S_pripad() {
        this.id_pripadu = BigDecimal.ZERO;
        this.id_typ_pripadu = BigDecimal.ZERO;
        this.id_obvodu = BigDecimal.ZERO;
        this.miesto_vykon = "";
        this.dat_vykon = new Date();
        this.objasneny = "";
        this.dat_zac = new Date();
        this.dat_ukon = new Date();
    }

    public BigDecimal getId_pripadu() {
        return id_pripadu;
    }

    public void setId_pripadu(BigDecimal id_pripadu) {
        this.id_pripadu = id_pripadu;
    }

    public BigDecimal getId_typ_pripadu() {
        return id_typ_pripadu;
    }

    public void setId_typ_pripadu(BigDecimal id_typ_pripadu) {
        this.id_typ_pripadu = id_typ_pripadu;
    }

    public BigDecimal getId_obvodu() {
        return id_obvodu;
    }

    public void setId_obvodu(BigDecimal id_obvodu) {
        this.id_obvodu = id_obvodu;
    }

    public String getMiesto_vykon() {
        return miesto_vykon;
    }

    public void setMiesto_vykon(String miesto_vykon) {
        this.miesto_vykon = miesto_vykon;
    }

    public Date getDat_vykon() {
        return dat_vykon;
    }

    public void setDat_vykon(Date dat_vykon) {
        this.dat_vykon = dat_vykon;
    }

    public String getObjasneny() {
        return objasneny;
    }

    public void setObjasneny(String objasneny) {
        this.objasneny = objasneny;
    }

    public Date getDat_zac() {
        return dat_zac;
    }

    public void setDat_zac(Date dat_zac) {
        this.dat_zac = dat_zac;
    }

    public Date getDat_ukon() {
        return dat_ukon;
    }

    public void setDat_ukon(Date dat_ukon) {
        this.dat_ukon = dat_ukon;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index) {
            case 0:
                return id_pripadu;
            case 1:
                return id_typ_pripadu;
            case 2:
                return id_obvodu;
            case 3:
                return miesto_vykon;
            case 4:
                return dat_vykon;
            case 5:
                return objasneny;
            case 6:
                return dat_zac;
            case 7:
                return dat_ukon;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index) {
            case 0:
                return "id_pripadu";
            case 1:
                return "id_typ_pripadu";
            case 2:
                return "id_obvodu";
            case 3:
                return "miesto_vykon";
            case 4:
                return "dat_vykon";
            case 5:
                return "objasneny";
            case 6:
                return "dat_zac";
            case 7:
                return "dat_ukon";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 8;
    }
}
