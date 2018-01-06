package main.java.Entities.Stats;

public class MestoVyskaSkody extends VyskaSkodyStat {
    private String mesto;

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index){
            case 0:
                return mesto;
            case 1:
                return getVyskaTrestCinov();
            case 2:
                return getVyskaPristupkov();
            case 3:
                return getVyskaSpolu();
            default:
                return null;
        }
    }

    @Override
    public String getValueName(int index) {
        switch (index){
            case 0:
                return "mesto";
            case 1:
                return "Vyska škody tr. činov";
            case 2:
                return "Vyska škody priestupkov";
            case 3:
                return "spolu";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 4;
    }
}
