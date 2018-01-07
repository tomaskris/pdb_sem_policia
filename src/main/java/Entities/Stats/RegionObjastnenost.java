package main.java.Entities.Stats;

public class RegionObjastnenost extends ObjastnenostStat{
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
                return getTrestne_ciny();
            case 2:
                return getPriestupky();
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
                return "Objastnenost tr. činov";
            case 2:
                return "Objastnenost priestupkov";
            case 3:
                return "Objastnenost spolu";
            default:
                return null;
        }
    }

    @Override
    public int numberOfAttr() {
        return 4;
    }
}
