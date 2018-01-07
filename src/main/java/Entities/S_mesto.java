package main.java.Entities;

import java.math.BigDecimal;

public class S_mesto extends MyDataClass {
  private String psc;
  private BigDecimal id_regionu;
  private String nazov;

  public S_mesto() {
    this.psc = "";
    this.id_regionu = BigDecimal.ZERO;
    this.nazov = "";
  }

  public BigDecimal getId_regionu() {
    return id_regionu;
  }

  public void setId_regionu(BigDecimal id_regionu) {
    this.id_regionu = id_regionu;
  }


  public String getPsc() {
    return psc;
  }

  public void setPsc(String psc) {
    this.psc = psc;
  }

  public String getNazov() {
    return nazov;
  }

  public void setNazov(String nazov) {
    this.nazov = nazov;
  }

  @Override
  public Object getValueAt(int index) {
    switch (index){
      case 0:
        return psc;
      case 1:
        return id_regionu;
      case 2:
        return nazov;
      default:
        return null;
    }
  }

  @Override
  public String getValueName(int index) {
    switch (index){
      case 0:
        return "psc";
      case 1:
        return "id_regionu";
      case 3:
        return "nazov";
      default:
        return null;
    }
  }

  @Override
  public int numberOfAttr() {
    return 3;
  }
}
