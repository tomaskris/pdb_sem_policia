package main.java.Entities;

import java.math.BigDecimal;
import java.util.Date;

public class S_hladana_osoba extends MyDataClass {
  private BigDecimal id_hladanej;
  private BigDecimal id_pripadu;
  private String rod_cislo;
  private String dovod;
  private Date dat_od;
  private Date dat_do;

  public S_hladana_osoba() {
    this.id_hladanej = BigDecimal.ZERO;
    this.id_pripadu = BigDecimal.ZERO;
    this.rod_cislo = "";
    this.dovod = "";
    this.dat_od = new Date();
    this.dat_do = new Date();
  }

  public BigDecimal getId_hladanej() {
    return id_hladanej;
  }

  public void setId_hladanej(BigDecimal id_hladanej) {
    this.id_hladanej = id_hladanej;
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

  public String getDovod() {
    return dovod;
  }

  public void setDovod(String dovod) {
    this.dovod = dovod;
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
    switch (index){
      case 0:
        return id_hladanej;
      case 1:
        return id_pripadu;
      case 2:
        return rod_cislo;
      case 3:
        return dovod;
      case 4:
        return dat_od;
      case 5:
        return dat_do;
      default:
        return null;
    }
  }

  @Override
  public String getValueName(int index) {
    switch (index){
      case 0:
        return "id_hladanej";
      case 1:
        return "id_pripadu";
      case 2:
        return "rod_cislo";
      case 3:
        return "dovod";
      case 4:
        return "dat_od";
      case 5:
        return "dat_do";
      default:
        return null;
    }
  }

  @Override
  public int numberOfAttr() {
    return 6;
  }
}
