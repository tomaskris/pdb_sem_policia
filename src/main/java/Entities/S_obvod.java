package main.java.Entities;

import java.math.BigDecimal;

public class S_obvod extends MyDataClass {
  private BigDecimal id_obvodu;
  private String psc;
  private String nazov;

  public S_obvod() {
    this.id_obvodu = BigDecimal.ZERO;
    this.psc = "";
    this.nazov = "";
  }

  public BigDecimal getId_obvodu() {
    return id_obvodu;
  }

  public void setId_obvodu(BigDecimal id_obvodu) {
    this.id_obvodu = id_obvodu;
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
        return id_obvodu;
      case 1:
        return psc;
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
        return "id_obvodu";
      case 1:
        return "psc";
      case 2:
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
