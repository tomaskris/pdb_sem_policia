package main.java.Entities.Stats;

public class RegionTrestStat extends TrestnyCinStat{
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
                return getPocetTrestCinov();
            case 2:
                return getPocetPriestupkov();
            case 3:
                return getSpolu();
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
                return "Pocet trestnych cinov";
            case 2:
                return "Pocet Priestipkov";
            case 3:
                return "Spolu";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 4;
    }
}
