package main.java.Entities;

import org.metawidget.inspector.annotation.UiHidden;

import java.math.BigDecimal;

public class S_biom_udaje extends MyDataClass {
  private BigDecimal id_biom_udaju;
  private BigDecimal vyska;
  private String typ_postavy;
  private String farba_vlasov;
  private String farba_oci;

  public S_biom_udaje() {
    this.id_biom_udaju = BigDecimal.ONE;
    this.vyska = BigDecimal.ONE;
    this.typ_postavy = "";
    this.farba_vlasov = "";
    this.farba_oci = "";
  }

  @UiHidden
  public BigDecimal getId_biom_udaju() {
    return id_biom_udaju;
  }

  public void setId_biom_udaju(BigDecimal id_biom_udaju) {
    this.id_biom_udaju = id_biom_udaju;
  }

  public BigDecimal getVyska() {
    return vyska;
  }

  public void setVyska(BigDecimal vyska) {
    this.vyska = vyska;
  }

  public String getTyp_postavy() {
    return typ_postavy;
  }

  public void setTyp_postavy(String typ_postavy) {
    this.typ_postavy = typ_postavy;
  }

  public String getFarba_vlasov() {
    return farba_vlasov;
  }

  public void setFarba_vlasov(String farba_vlasov) {
    this.farba_vlasov = farba_vlasov;
  }

  public String getFarba_oci() {
    return farba_oci;
  }

  public void setFarba_oci(String farba_oci) {
    this.farba_oci = farba_oci;
  }

  @Override
  public Object getValueAt(int index) {
    switch (index){
      case 0:
        return id_biom_udaju;
      case 1:
        return vyska;
      case 2:
        return typ_postavy;
      case 3:
        return farba_vlasov;
      case 4:
        return farba_oci;
      default:
        return null;
    }
  }

  @Override
  public String getValueName(int index) {
    switch (index){
      case 0:
        return "id_biom_udaju";
      case 1:
        return "vyska";
      case 2:
        return "typ_postavy";
      case 3:
        return "farba_vlasov";
      case 4:
        return "farba_oci";
      default:
        return null;
    }
  }

  @Override
  public int numberOfAttr() {
    return 5;
  }
}
