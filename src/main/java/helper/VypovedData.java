package main.java.helper;

import java.io.InputStream;

public class VypovedData{
    private String typ_vypovede;
    private InputStream zaznam;

    public VypovedData(String typ_vypovede, InputStream zaznam) {
        this.typ_vypovede = typ_vypovede;
        this.zaznam = zaznam;
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
}