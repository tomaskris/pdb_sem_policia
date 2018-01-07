package main.java.DbAccess;


import main.java.Connector;
import main.java.Entities.Stats.*;
import main.java.Utils.Utils;
import main.java.helper.CreatePdfHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceInputStream;
import org.apache.commons.io.input.ReaderInputStream;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DB_Stats{

    public boolean getPripadyData(String destination) {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select proc_prehlad_pripadov_xml from dual");
            ResultSet result = stmnt.executeQuery();
            List<RegionTrestStat> data = new LinkedList<>();
            while (result.next()) {

                Clob xmlClob = result.getClob(1);
                Reader r = xmlClob.getCharacterStream();

                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser saxParser = spf.newSAXParser();

                ReaderInputStream ris = new ReaderInputStream(r, StandardCharsets.UTF_8);
                saxParser.parse(ris,new CreatePdfHandler(destination));

            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getPripadyDataXML(String destination) {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select proc_prehlad_pripadov_xml from dual");
            ResultSet result = stmnt.executeQuery();
            List<RegionTrestStat> data = new LinkedList<>();
            while (result.next()) {

                Clob xmlClob = result.getClob(1);
                Reader reader = xmlClob.getCharacterStream();
                String o = IOUtils.toString(reader);
                File subor = new File(destination);
                if (subor != null) {
                    try (BufferedWriter w = new BufferedWriter(new FileWriter(subor))) {
                        IOUtils.write(o, w);
                    }
                }else {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<RegionTrestStat> getStatsTrestneCinyRegion() {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select reg.nazov as region,\n" +
                    "       sum(case when (typ.nazov_typu = 'C') then 1 else 0 end) as poc_trest_cinov,\n" +
                    "       sum(case when (typ.nazov_typu = 'P') then 1 else 0 end) as poc_priestupkov,\n" +
                    "       count(*) as spolu\n" +
                    "from s_region reg join s_mesto mes on(reg.id_regionu = mes.id_regionu)\n" +
                    "  join s_pripad pr on(pr.miesto_vykon = mes.psc)\n" +
                    "  join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)\n" +
                    "group by reg.nazov");
            ResultSet result = stmnt.executeQuery();
            List<RegionTrestStat> data = new LinkedList<>();
            while (result.next()) {
                RegionTrestStat row = new RegionTrestStat();
                row.setRegion(result.getString("region"));
                row.setPocetTrestCinov(result.getInt("poc_trest_cinov"));
                row.setPocetPriestupkov(result.getInt("poc_priestupkov"));
                row.setSpolu(result.getInt("spolu"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MestoTrestStat> getStatsTrestneCinyMesto() {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select mes.nazov as mesto, \n" +
                    "    sum(case when (typ.nazov_typu = 'C') then 1 else 0 end) as poc_trest_cinov,\n" +
                    "    sum(case when (typ.nazov_typu = 'P') then 1 else 0 end) as poc_priestupkov,\n" +
                    "    count(*) as spolu\n" +
                    "from s_mesto mes join s_pripad pr on(pr.miesto_vykon = mes.psc)\n" +
                    "  join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)\n" +
                    "group by mes.nazov\n" +
                    "order by mes.nazov");
            ResultSet result = stmnt.executeQuery();
            List<MestoTrestStat> data = new LinkedList<>();
            while (result.next()) {
                MestoTrestStat row = new MestoTrestStat();
                row.setMesto(result.getString("mesto"));
                row.setPocetTrestCinov(result.getInt("poc_trest_cinov"));
                row.setPocetPriestupkov(result.getInt("poc_priestupkov"));
                row.setSpolu(result.getInt("spolu"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public List<ObvodTrestStat> getStatsTrestneCinyObvod() {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select obv.nazov as obvod, \n" +
                    "    sum(case when (typ.nazov_typu = 'C') then 1 else 0 end) as poc_trest_cinov,\n" +
                    "    sum(case when (typ.nazov_typu = 'P') then 1 else 0 end) as poc_priestupkov,\n" +
                    "    count(*) as spolu\n" +
                    "from s_obvod obv join s_pripad pr on(pr.id_obvodu = obv.id_obvodu)\n" +
                    "  join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)\n" +
                    "group by obv.nazov\n" +
                    "order by obv.nazov");
            ResultSet result = stmnt.executeQuery();
            List<ObvodTrestStat> data = new LinkedList<>();
            while (result.next()) {
                ObvodTrestStat row = new ObvodTrestStat();
                row.setObvod(result.getString("obvod"));
                row.setPocetTrestCinov(result.getInt("poc_trest_cinov"));
                row.setPocetPriestupkov(result.getInt("poc_priestupkov"));
                row.setSpolu(result.getInt("spolu"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<DruhPripaduPocet> getStatsDruhPripadu() {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select typ.druh_pripadu as druh_pripadu, count(*) as poc_pripadov\n" +
                    "from s_pripad pr right join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)\n" +
                    "group by typ.druh_pripadu\n" +
                    "order by poc_pripadov desc");
            ResultSet result = stmnt.executeQuery();
            List<DruhPripaduPocet> data = new LinkedList<>();
            while (result.next()) {
                DruhPripaduPocet row = new DruhPripaduPocet();
                row.setDruh_pripadu(result.getString("druh_pripadu"));
                row.setPoc_pripadov(result.getInt("poc_pripadov"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<RegionVyskaSkody> getStatsVyskaSkodyRegion() {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select reg.nazov as region, \n" +
                    "    sum(case when (typ.nazov_typu = 'C') then obo.vyska_skody else 0 end) as vyska_skody_trest_cinov,\n" +
                    "    sum(case when (typ.nazov_typu = 'P') then obo.vyska_skody else 0 end) as vyska_skody_priestupkov,\n" +
                    "    sum(case when (obo.vyska_skody is not null) then obo.vyska_skody else 0 end) as vyska_skody_spolu\n" +
                    "from s_region reg join s_mesto mes on(reg.id_regionu = mes.id_regionu)\n" +
                    "  join s_pripad pr on(pr.miesto_vykon = mes.psc)\n" +
                    "  join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)\n" +
                    "  join s_obzalovana_osoba obo on(obo.id_pripadu = pr.id_pripadu)\n" +
                    "group by reg.nazov\n" +
                    "order by vyska_skody_spolu desc");
            ResultSet result = stmnt.executeQuery();
            List<RegionVyskaSkody> data = new LinkedList<>();
            while (result.next()) {
                RegionVyskaSkody row = new RegionVyskaSkody();
                row.setRegion(result.getString("region"));
                row.setVyskaTrestCinov(result.getInt("vyska_skody_trest_cinov"));
                row.setVyskaPristupkov(result.getInt("vyska_skody_priestupkov"));
                row.setVyskaSpolu(result.getInt("vyska_skody_spolu"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MestoVyskaSkody> getStatsVyskaSkodyMesto() {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select mes.nazov as mesto, \n" +
                    "    sum(case when (typ.nazov_typu = 'C') then obo.vyska_skody else 0 end) as vyska_skody_trest_cinov,\n" +
                    "    sum(case when (typ.nazov_typu = 'P') then obo.vyska_skody else 0 end) as vyska_skody_priestupkov,\n" +
                    "    sum(case when (obo.vyska_skody is not null) then obo.vyska_skody else 0 end) as vyska_skody_spolu\n" +
                    "from s_mesto mes join s_pripad pr on(pr.miesto_vykon = mes.psc)\n" +
                    "  join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)\n" +
                    "  join s_obzalovana_osoba obo on(obo.id_pripadu = pr.id_pripadu)\n" +
                    "group by mes.nazov\n" +
                    "order by vyska_skody_spolu desc");
            ResultSet result = stmnt.executeQuery();
            List<MestoVyskaSkody> data = new LinkedList<>();
            while (result.next()) {
                MestoVyskaSkody row = new MestoVyskaSkody();
                row.setMesto(result.getString("mesto"));
                row.setVyskaTrestCinov(result.getInt("vyska_skody_trest_cinov"));
                row.setVyskaPristupkov(result.getInt("vyska_skody_priestupkov"));
                row.setVyskaSpolu(result.getInt("vyska_skody_spolu"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DruhPripaduVyskaSkody> getStatsVyskaSkodyDruhPripadu() {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select typ.druh_pripadu as druh_pripadu, \n" +
                    "    sum(case when (obo.vyska_skody is not null) then obo.vyska_skody else 0 end) as vyska_skody_spolu\n" +
                    "from s_pripad pr right join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)\n" +
                    "    join s_obzalovana_osoba obo on(obo.id_pripadu = pr.id_pripadu)\n" +
                    "group by typ.druh_pripadu\n" +
                    "order by vyska_skody_spolu desc");
            ResultSet result = stmnt.executeQuery();
            List<DruhPripaduVyskaSkody> data = new LinkedList<>();
            while (result.next()) {
                DruhPripaduVyskaSkody row = new DruhPripaduVyskaSkody();
                row.setDruh_pripadu(result.getString("druh_pripadu"));
                row.setVyska_skody(result.getInt("vyska_skody_spolu"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<VekObvinenehoVyskaSkody> getStatsVyskaSkodyVekObvineneho() {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select case when (months_between(sysdate, to_date(substr(obo.rod_cislo,5,2) || '.' || \n" +
                    "    decode(substr(obo.rod_cislo,3,1),6,1,5,0,substr(obo.rod_cislo,3,1)) || \n" +
                    "    substr(obo.rod_cislo,4,1) || '.19' || substr(obo.rod_cislo,1,2),'DD.MM.YYYY'))/12 < 14) then 'malolety' \n" +
                    "    else (case when (months_between(sysdate, to_date(substr(obo.rod_cislo,5,2) || '.' || \n" +
                    "        decode(substr(obo.rod_cislo,3,1),6,1,5,0,substr(obo.rod_cislo,3,1)) || \n" +
                    "        substr(obo.rod_cislo,4,1) || '.19' || substr(obo.rod_cislo,1,2),'DD.MM.YYYY'))/12 between 14 and 18) then 'mladistvy'\n" +
                    "        else (case when (months_between(sysdate, to_date(substr(obo.rod_cislo,5,2) || '.' || \n" +
                    "        decode(substr(obo.rod_cislo,3,1),6,1,5,0,substr(obo.rod_cislo,3,1)) || \n" +
                    "        substr(obo.rod_cislo,4,1) || '.19' || substr(obo.rod_cislo,1,2),'DD.MM.YYYY'))/12 between 19 and 60) then 'dospely' else 'osoby pokrocileho vek' end) end) end as vek_obvineneho, \n" +
                    "    sum(case when (typ.nazov_typu = 'C') then obo.vyska_skody else 0 end) as vyska_skody_trest_cinov,\n" +
                    "    sum(case when (typ.nazov_typu = 'P') then obo.vyska_skody else 0 end) as vyska_skody_priestupkov,\n" +
                    "    sum(case when (obo.vyska_skody is not null) then obo.vyska_skody else 0 end) as vyska_skody_spolu\n" +
                    "from s_pripad pr right join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)\n" +
                    "    join s_obzalovana_osoba obo on(obo.id_pripadu = pr.id_pripadu)\n" +
                    "group by case when (months_between(sysdate, to_date(substr(obo.rod_cislo,5,2) || '.' || \n" +
                    "    decode(substr(obo.rod_cislo,3,1),6,1,5,0,substr(obo.rod_cislo,3,1)) || \n" +
                    "    substr(obo.rod_cislo,4,1) || '.19' || substr(obo.rod_cislo,1,2),'DD.MM.YYYY'))/12 < 14) then 'malolety' \n" +
                    "    else (case when (months_between(sysdate, to_date(substr(obo.rod_cislo,5,2) || '.' || \n" +
                    "        decode(substr(obo.rod_cislo,3,1),6,1,5,0,substr(obo.rod_cislo,3,1)) || \n" +
                    "        substr(obo.rod_cislo,4,1) || '.19' || substr(obo.rod_cislo,1,2),'DD.MM.YYYY'))/12 between 14 and 18) then 'mladistvy'\n" +
                    "        else (case when (months_between(sysdate, to_date(substr(obo.rod_cislo,5,2) || '.' || \n" +
                    "        decode(substr(obo.rod_cislo,3,1),6,1,5,0,substr(obo.rod_cislo,3,1)) || \n" +
                    "        substr(obo.rod_cislo,4,1) || '.19' || substr(obo.rod_cislo,1,2),'DD.MM.YYYY'))/12 between 19 and 60) then 'dospely' else 'osoby pokrocileho vek' end) end) end\n" +
                    "order by vyska_skody_spolu desc");
            ResultSet result = stmnt.executeQuery();
            List<VekObvinenehoVyskaSkody> data = new LinkedList<>();
            while (result.next()) {
                VekObvinenehoVyskaSkody row = new VekObvinenehoVyskaSkody();
                row.setVek_obvineneho(result.getString("vek_obvineneho"));
                row.setVyskaTrestCinov(result.getInt("vyska_skody_trest_cinov"));
                row.setVyskaPristupkov(result.getInt("vyska_skody_priestupkov"));
                row.setVyskaSpolu(result.getInt("vyska_skody_spolu"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<PohlavieVyskaSkody> getStatsVyskaSkodyPohlavie() {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select decode(substr(obo.rod_cislo,3,1), 6, 'zena', 5, 'zena', 'muz') as pohlavie_obvineneho, \n" +
                    "    sum(case when (typ.nazov_typu = 'C') then obo.vyska_skody else 0 end) as vyska_skody_trest_cinov,\n" +
                    "    sum(case when (typ.nazov_typu = 'P') then obo.vyska_skody else 0 end) as vyska_skody_priestupkov,\n" +
                    "    sum(case when (obo.vyska_skody is not null) then obo.vyska_skody else 0 end) as vyska_skody_spolu\n" +
                    "from s_pripad pr right join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)\n" +
                    "    join s_obzalovana_osoba obo on(obo.id_pripadu = pr.id_pripadu)\n" +
                    "group by decode(substr(obo.rod_cislo,3,1), 6, 'zena', 5, 'zena', 'muz')\n" +
                    "order by vyska_skody_spolu desc");
            ResultSet result = stmnt.executeQuery();
            List<PohlavieVyskaSkody> data = new LinkedList<>();
            while (result.next()) {
                PohlavieVyskaSkody row = new PohlavieVyskaSkody();
                row.setPohlavie(result.getString("pohlavie_obvineneho"));
                row.setVyskaTrestCinov(result.getInt("vyska_skody_trest_cinov"));
                row.setVyskaPristupkov(result.getInt("vyska_skody_priestupkov"));
                row.setVyskaSpolu(result.getInt("vyska_skody_spolu"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<RegionObjastnenost> getStatsObjastnenostRegion() {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select reg.nazov as region, \n" +
                    "    round(sum(case when (typ.nazov_typu = 'C' and pr.objasneny = 'A') then 1 else 0 end)/(select count(*) \n" +
                    "        from s_pripad join s_typ_pripadu using(id_typ_pripadu) where nazov_typu = 'C')*100,2) as objasnenost_trest_cinov,\n" +
                    "    round(sum(case when (typ.nazov_typu = 'P' and pr.objasneny = 'A') then 1 else 0 end)/(select count(*) \n" +
                    "        from s_pripad join s_typ_pripadu using(id_typ_pripadu) where nazov_typu = 'P')*100,2) as objasnenost_priestupkov,\n" +
                    "    round(sum(case when (pr.objasneny = 'A') then 1 else 0 end)/(select count(*) from s_pripad)*100,2) as objasnenost_spolu\n" +
                    "from s_region reg join s_mesto mes on(reg.id_regionu = mes.id_regionu)\n" +
                    "  join s_pripad pr on(pr.miesto_vykon = mes.psc)\n" +
                    "  join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)\n" +
                    "group by reg.nazov\n" +
                    "order by objasnenost_spolu desc");
            ResultSet result = stmnt.executeQuery();
            List<RegionObjastnenost> data = new LinkedList<>();
            while (result.next()) {
                RegionObjastnenost row = new RegionObjastnenost();
                row.setRegion(result.getString("region"));
                row.setTrestne_ciny(result.getDouble("objasnenost_trest_cinov"));
                row.setPriestupky(result.getDouble("objasnenost_priestupkov"));
                row.setSpolu(result.getDouble("objasnenost_spolu"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<RegionObjastnenost> getStatsObjastnenostRegion(Date dat_od, Date dat_do ){
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select reg.nazov as region, \n" +
                    "      round(sum(case when (typ.nazov_typu = 'C' and pr.objasneny = 'A') then 1 else 0 end)/(select count(*) \n" +
                    "          from s_pripad join s_typ_pripadu using(id_typ_pripadu) where nazov_typu = 'C' and dat_zac between ? and ?)*100,2) as objasnenost_trest_cinov,\n" +
                    "      round(sum(case when (typ.nazov_typu = 'P' and pr.objasneny = 'A') then 1 else 0 end)/(select count(*) \n" +
                    "          from s_pripad join s_typ_pripadu using(id_typ_pripadu) where nazov_typu = 'P' and dat_zac between ? and ?)*100,2) as objasnenost_priestupkov,\n" +
                    "      round(sum(case when (pr.objasneny = 'A') then 1 else 0 end)/(select count(*) from s_pripad where dat_zac between ? and ?)*100,2) as objasnenost_spolu\n" +
                    "  from s_region reg join s_mesto mes on(reg.id_regionu = mes.id_regionu)\n" +
                    "    join s_pripad pr on(pr.miesto_vykon = mes.psc)\n" +
                    "    join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)\n" +
                    "  where pr.dat_zac between ? and ?\n" +
                    "  group by reg.nazov\n" +
                    "  order by objasnenost_spolu desc");
            stmnt.setDate(1, Utils.convertUtilToSql(dat_od));
            stmnt.setDate(2, Utils.convertUtilToSql(dat_do));
            stmnt.setDate(3, Utils.convertUtilToSql(dat_od));
            stmnt.setDate(4, Utils.convertUtilToSql(dat_do));
            stmnt.setDate(5, Utils.convertUtilToSql(dat_od));
            stmnt.setDate(6, Utils.convertUtilToSql(dat_do));
            stmnt.setDate(7, Utils.convertUtilToSql(dat_od));
            stmnt.setDate(8, Utils.convertUtilToSql(dat_do));


            ResultSet result = stmnt.executeQuery();
            List<RegionObjastnenost> data = new LinkedList<>();
            while (result.next()) {
                RegionObjastnenost row = new RegionObjastnenost();
                row.setRegion(result.getString("region"));
                row.setTrestne_ciny(result.getDouble("objasnenost_trest_cinov"));
                row.setPriestupky(result.getDouble("objasnenost_priestupkov"));
                row.setSpolu(result.getDouble("objasnenost_spolu"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public List<DruhPripaduObjastnenost> getStatsObjastnenostDruhPripadu(Integer pocet) {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("select druh_pripadu, poc_pripadov, objasnenost from (\n" +
                    "    select druh_pripadu, poc_pripadov, objasnenost, row_number() over (order by objasnenost desc) as rn\n" +
                    "    from(\n" +
                    "        select typ.druh_pripadu as druh_pripadu, count(*) as poc_pripadov, \n" +
                    "            round(sum(case when (pr.objasneny = 'A') then 1 else 0 end)/(select count(*) from s_pripad)*100,2) as objasnenost\n" +
                    "        from s_pripad pr join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)\n" +
                    "        group by typ.druh_pripadu)\n" +
                    "    )\n" +
                    "where rn < ?");
            stmnt.setInt(1,pocet + 1);
            ResultSet result = stmnt.executeQuery();
            List<DruhPripaduObjastnenost> data = new LinkedList<>();
            while (result.next()) {
                DruhPripaduObjastnenost row = new DruhPripaduObjastnenost();
                row.setDruh_pripadu(result.getString("druh_pripadu"));
                row.setPoc_pripadov(result.getInt("poc_pripadov"));
                row.setObjastnenost(result.getDouble("objasnenost"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<AmnestiaStat> getStatsAmnestia() {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("    select odo.rod_cislo, os.meno || ' ' || os.priezvisko as meno, odo.dlzka_trestu, odo.dat_nastupu, \n" +
                    "        trunc(months_between(sysdate,odo.dat_nastupu)/12) as poc_rok_vo_vazani,\n" +
                    "        decode(tp.nazov_typu,'C','trestny cin','priestupok') as trest_cin, tp.druh_pripadu\n" +
                    "    from s_osoba os join s_odsudena_osoba odo on(os.rod_cislo = odo.rod_cislo)\n" +
                    "        join s_pripad pr on(odo.id_pripadu = pr.id_pripadu)\n" +
                    "        join s_typ_pripadu tp on(pr.id_typ_pripadu = tp.id_typ_pripadu)\n" +
                    "    where (months_between(sysdate,add_months(odo.dat_nastupu, odo.dlzka_trestu*12)) < 0)\n" +
                    "        and (round(trunc(months_between(sysdate, odo.dat_nastupu)/12)/odo.dlzka_trestu*100,2) >= 75)\n" +
                    "        and (tp.druh_pripadu in ('usmrtenie','ublíženie na zdraví', 'podplácanie'))\n" +
                    "        and (odo.dlzka_trestu < 10)");
            ResultSet result = stmnt.executeQuery();
            List<AmnestiaStat> data = new LinkedList<>();
            while (result.next()) {
                AmnestiaStat row = new AmnestiaStat();
                row.setRod_cislo(result.getString("rod_cislo"));
                row.setMeno(result.getString("meno"));
                row.setDlzka_trestu(result.getInt("dlzka_trestu"));
                row.setDat_nastupu(result.getDate("dat_nastupu"));
                row.setRoky_vo_vazeni(result.getInt("poc_rok_vo_vazani"));
                row.setTrestny_cin(result.getString("trest_cin"));
                row.setDruh_pripadu(result.getString("druh_pripadu"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PocetVaznovNaRok> getStatsOsobyVoVykone() {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("SELECT * FROM TABLE(stat_osob_vo_vykone())");
            ResultSet result = stmnt.executeQuery();
            List<PocetVaznovNaRok> data = new LinkedList<>();
            while (result.next()) {
                PocetVaznovNaRok row = new PocetVaznovNaRok();
                row.setRok(result.getInt("rok"));
                row.setPocet(result.getInt("pocet"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<MzdyNaRok> getStatsMzdyRoky() {
        try (Connection conn = Connector.getConnection();) {
            CallableStatement stmnt = conn.prepareCall("SELECT * FROM TABLE(proc_rocne_naklady_spolu())");
            ResultSet result = stmnt.executeQuery();
            List<MzdyNaRok> data = new LinkedList<>();
            while (result.next()) {
                MzdyNaRok row = new MzdyNaRok();
                row.setRok(result.getInt("rok"));
                row.setSuma(result.getInt("suma"));
                data.add(row);
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
