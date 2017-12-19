package main.java.Entities;

import java.math.BigDecimal;
import java.util.Date;

public class S_historia_funkcii extends MyDataClass {
  private BigDecimal id_zamestnanca;
  private Date dat_od;
  private BigDecimal id_funkcie;
  private Date dat_do;

  public S_historia_funkcii() {
    this.id_zamestnanca = BigDecimal.ZERO;
    this.dat_od = new Date();
    this.id_funkcie = BigDecimal.ZERO;
    this.dat_do = new Date();

  }

  public BigDecimal getId_zamestnanca() {
    return id_zamestnanca;
  }

  public void setId_zamestnanca(BigDecimal id_zamestnanca) {
    this.id_zamestnanca = id_zamestnanca;
  }

  public Date getDat_od() {
    return dat_od;
  }

  public void setDat_od(Date dat_od) {
    this.dat_od = dat_od;
  }

  public BigDecimal getId_funkcie() {
    return id_funkcie;
  }

  public void setId_funkcie(BigDecimal id_funkcie) {
    this.id_funkcie = id_funkcie;
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
        return id_zamestnanca;
      case 1:
        return dat_od;
      case 2:
        return id_funkcie;
      case 3:
        return dat_do;
      default:
        return null;
    }
  }

  @Override
  public String getValueName(int index) {
    switch (index){
      case 0:
        return "id_zamestnanca";
      case 1:
        return "dat_od";
      case 2:
        return "id_funkcie";
      case 3:
        return "dat_do";
      default:
        return null;
    }
  }

  @Override
  public int numberOfAttr() {
    return 4;
  }
}
