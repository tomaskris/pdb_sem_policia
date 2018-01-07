package main.java.helper;

import java.io.InputStream;

public class VypovedData{
    private String typ_vypovede;
    private String typ_suboru;
    private InputStream zaznam;

    public VypovedData(String typ_vypovede,String typ_suboru, InputStream zaznam) {
        this.typ_vypovede = typ_vypovede;
        this.zaznam = zaznam;
        this.typ_suboru = typ_suboru;
    }

    public String getTyp_vypovede() {
        return typ_vypovede;
    }

    public void setTyp_vypovede(String typ_vypovede) {
        this.typ_vypovede = typ_vypovede;
    }

    public InputStream getZaznam() {
        return zaznam;
    }

    public void setZaznam(InputStream zaznam) {
        this.zaznam = zaznam;
    }

    public String getTyp_suboru() {
        return typ_suboru;
    }

    public void setTyp_suboru(String typ_suboru) {
        this.typ_suboru = typ_suboru;
    }
}