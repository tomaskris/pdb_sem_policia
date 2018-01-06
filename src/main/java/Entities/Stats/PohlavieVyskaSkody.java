package main.java.Entities.Stats;

public class PohlavieVyskaSkody extends VyskaSkodyStat {
    private String pohlavie;

    public String getPohlavie() {
        return pohlavie;
    }

    public void setPohlavie(String pohlavie) {
        this.pohlavie = pohlavie;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index){
            case 0:
                return pohlavie;
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
                return "pohlavie";
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
