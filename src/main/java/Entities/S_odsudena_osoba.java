package main.java.Entities;

import java.math.BigDecimal;
import java.util.Date;

public class S_odsudena_osoba extends MyDataClass {
  private BigDecimal id_odsudeneho;
  private String rod_cislo;
  private BigDecimal id_pripadu;
  private BigDecimal id_vaznice;
  private BigDecimal dlzka_trestu;
  private Date dat_nastupu;

  public S_odsudena_osoba() {
    this.id_odsudeneho = BigDecimal.ZERO;
    this.rod_cislo = "";
    this.id_pripadu = BigDecimal.ZERO;
    this.id_vaznice = BigDecimal.ZERO;
    this.dlzka_trestu = BigDecimal.ZERO;
    this.dat_nastupu = new Date();
  }

  public BigDecimal getId_odsudeneho() {
    return id_odsudeneho;
  }

  public void setId_odsudeneho(BigDecimal id_odsudeneho) {
    this.id_odsudeneho = id_odsudeneho;
  }

  public BigDecimal getId_pripadu() {
    return id_pripadu;
  }

  public void setId_pripadu(BigDecimal id_pripadu) {
    this.id_pripadu = id_pripadu;
  }

  public BigDecimal getId_vaznice() {
    return id_vaznice;
  }

  public void setId_vaznice(BigDecimal id_vaznice) {
    this.id_vaznice = id_vaznice;
  }

  public String getRod_cislo() {
    return rod_cislo;
  }

  public void setRod_cislo(String rod_cislo) {
    this.rod_cislo = rod_cislo;
  }

  public BigDecimal getDlzka_trestu() {
    return dlzka_trestu;
  }

  public void setDlzka_trestu(BigDecimal dlzka_trestu) {
    this.dlzka_trestu = dlzka_trestu;
  }

  public Date getDat_nastupu() {
    return dat_nastupu;
  }

  public void setDat_nastupu(Date dat_nastupu) {
    this.dat_nastupu = dat_nastupu;
  }

  @Override
  public Object getValueAt(int index) {
    switch (index){
      case 0:
        return id_odsudeneho;
      case 1:
        return rod_cislo;
      case 2:
        return id_pripadu;
      case 3:
        return id_vaznice;
      case 4:
        return dlzka_trestu;
      case 5:
        return dat_nastupu;
      default:
        return null;
    }
  }

  @Override
  public String getValueName(int index) {
    switch (index){
      case 0:
        return "id_odsudeneho";
      case 1:
        return "rod_cislo";
      case 2:
        return "id_pripadu";
      case 3:
        return "id_vaznice";
      case 4:
        return "dlzka_trestu";
      case 5:
        return "dat_nastupu";
      default:
        return null;
    }
  }

  @Override
  public int numberOfAttr() {
    return 6;
  }
}
