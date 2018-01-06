package main.java.Entities.Stats;

public class VekObvinenehoVyskaSkody extends VyskaSkodyStat {
    private String vek_obvineneho;

    public String getVek_obvineneho() {
        return vek_obvineneho;
    }

    public void setVek_obvineneho(String vek_obvineneho) {
        this.vek_obvineneho = vek_obvineneho;
    }

    @Override
    public Object getValueAt(int index) {
        switch (index){
            case 0:
                return vek_obvineneho;
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
                return "Vek Obvineneho";
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
