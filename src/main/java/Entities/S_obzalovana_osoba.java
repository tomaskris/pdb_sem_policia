package main.java.Entities;

import java.math.BigDecimal;

public class S_obzalovana_osoba extends MyDataClass {
  private BigDecimal id_obzalovanej;
  private BigDecimal id_pripadu;
  private String rod_cislo;
  private BigDecimal vyska_skody;
  private BigDecimal udelena_pokuta;

  public BigDecimal getId_pripadu() {
    return id_pripadu;
  }

  public void setId_pripadu(BigDecimal id_pripadu) {
    this.id_pripadu = id_pripadu;
  }

  public BigDecimal getId_obzalovanej() {
    return id_obzalovanej;
  }

  public void setId_obzalovanej(BigDecimal id_obzalovanej) {
    this.id_obzalovanej = id_obzalovanej;
  }

  public String getRod_cislo() {
    return rod_cislo;
  }

  public void setRod_cislo(String rod_cislo) {
    this.rod_cislo = rod_cislo;
  }

  public BigDecimal getVyska_skody() {
    return vyska_skody;
  }

  public void setVyska_skody(BigDecimal vyska_skody) {
    this.vyska_skody = vyska_skody;
  }

  public BigDecimal getUdelena_pokuta() {
    return udelena_pokuta;
  }

  public void setUdelena_pokuta(BigDecimal udelena_pokuta) {
    this.udelena_pokuta = udelena_pokuta;
  }

  @Override
  public Object getValueAt(int index) {
    switch (index){
      case 0:
        return id_obzalovanej;
      case 1:
        return id_pripadu;
      case 2:
        return rod_cislo;
      case 3:
        return vyska_skody;
      case 4:
        return udelena_pokuta;
      default:
        return null;
    }
  }

  @Override
  public String getValueName(int index) {
    switch (index){
      case 0:
        return "id_obzalovanej";
      case 1:
        return "id_pripadu";
      case 2:
        return "rod_cislo";
      case 3:
        return "vyska_skody";
      case 4:
        return "udelena_pokuta";
      default:
        return null;
    }
  }

  @Override
  public int numberOfAttr() {
    return 5;
  }
}
