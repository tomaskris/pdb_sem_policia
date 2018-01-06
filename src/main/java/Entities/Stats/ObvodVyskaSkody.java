package main.java.Entities.Stats;

public class ObvodVyskaSkody extends VyskaSkodyStat {
    private String obvod;

    public String getObvod() {
        return obvod;
    }

    public void setObvod(String obvod) {
        this.obvod = obvod;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index){
            case 0:
                return obvod;
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
                return "region";
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
