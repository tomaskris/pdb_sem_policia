package main.java.DbAccess;

import main.java.Entities.*;

import java.util.HashMap;

public class AccessList {
    private static HashMap<Class,DBAccess> map;

    public static HashMap<Class,DBAccess> getMap(){
        if(map == null){
            map = new HashMap<>();
            map.put(S_biom_udaje.class, new DB_biom_udaje());
            map.put(S_historia_funkcii.class, new DB_historia_funkcii());
            map.put(S_hladana_osoba.class, new DB_hladana_osoba());
            map.put(S_mesto.class, new DB_mesto());
            map.put(S_obvod.class, new DB_obvod());
            map.put(S_obzalovana_osoba.class, new DB_obzalovana_osoba());
            map.put(S_odsudena_osoba.class, new DB_odsudena_osoba());
            map.put(S_osoba.class, new DB_osoba());
            map.put(S_osoba_pripadu.class, new DB_osoba_pripadu());
            map.put(S_pripad.class, new DB_pripad());
            map.put(S_region.class, new DB_region());
            map.put(S_typ_funkcie.class, new DB_typ_funkcie());
            map.put(S_vaznica.class, new DB_vaznica());
            map.put(S_zamestnanec.class, new DB_zamestnanec());
        }
        return map;
    }

}
