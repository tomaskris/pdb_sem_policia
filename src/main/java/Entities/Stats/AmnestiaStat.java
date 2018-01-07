package main.java.Entities.Stats;

import main.java.Entities.MyDataClass;

import java.util.Date;

public class AmnestiaStat extends MyDataClass{
    private String rod_cislo;
    private String meno;
    private Integer dlzka_trestu;
    private Date dat_nastupu;
    private Integer roky_vo_vazeni;
    private String trestny_cin;
    private String druh_pripadu;

    public String getRod_cislo() {
        return rod_cislo;
    }

    public void setRod_cislo(String rod_cislo) {
        this.rod_cislo = rod_cislo;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public Integer getDlzka_trestu() {
        return dlzka_trestu;
    }

    public void setDlzka_trestu(Integer dlzka_trestu) {
        this.dlzka_trestu = dlzka_trestu;
    }

    public Date getDat_nastupu() {
        return dat_nastupu;
    }

    public void setDat_nastupu(Date dat_nastupu) {
        this.dat_nastupu = dat_nastupu;
    }

    public Integer getRoky_vo_vazeni() {
        return roky_vo_vazeni;
    }

    public void setRoky_vo_vazeni(Integer roky_vo_vazeni) {
        this.roky_vo_vazeni = roky_vo_vazeni;
    }

    public String getTrestny_cin() {
        return trestny_cin;
    }

    public void setTrestny_cin(String trestny_cin) {
        this.trestny_cin = trestny_cin;
    }

    public String getDruh_pripadu() {
        return druh_pripadu;
    }

    public void setDruh_pripadu(String druh_pripadu) {
        this.druh_pripadu = druh_pripadu;
    }


    @Override
    public Object getValueAt(int index) {
        switch (index){
            case 0:
                return rod_cislo;
            case 1:
                return meno;
            case 2:
                return dlzka_trestu;
            case 3:
                return dat_nastupu;
            case 4:
                return roky_vo_vazeni;
            case 5:
                return trestny_cin;
            case 6:
                return druh_pripadu;
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index){
            case 0:
                return "Rodné čislo";
            case 1:
                return "Meno";
            case 2:
                return "Dlzka trestu";
            case 3:
                return "Nastup";
            case 4:
                return "Roky vo vez.";
            case 5:
                return "Tr. čin";
            case 6:
                return "Druh Pripadu";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 7;
    }
}
