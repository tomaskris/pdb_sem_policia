package main.java.Entities.Stats;

public class ObvodTrestStat extends TrestnyCinStat{
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
                return "obvod";
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
    }}
