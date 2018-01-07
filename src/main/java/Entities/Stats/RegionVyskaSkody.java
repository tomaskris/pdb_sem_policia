package main.java.Entities.Stats;

public class RegionVyskaSkody extends VyskaSkodyStat {
    private String region;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index){
            case 0:
                return region;
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
