set serveroutput on;

-- 1) Podrobný preh¾ad o vyšetrovaných prípadoch.
create or replace procedure proc_prehlad_pripadov is 
  cursor cur_pripad is 
    select pr.id_pripadu as id_p, decode(tp.nazov_typu,'C','trestny cin', 'priestupok') || ', ' || tp.druh_pripadu as tp,
      me.nazov as mv, decode(pr.dat_vykon, NULL, 'neznamy', to_char(pr.dat_vykon,'DD.MM.YYYY')) as dvy, pr.dat_zac as zv
    from s_pripad pr join s_typ_pripadu tp on(pr.id_typ_pripadu = tp.id_typ_pripadu)
      join s_mesto me on(pr.miesto_vykon = me.psc)
    where pr.id_pripadu between 1 and 20--pr.objasneny = 'N' and pr.id_pripadu in (1, 2,3, 4)
    order by pr.id_pripadu;
  
  cursor cur_osoby_pripadu(id_p integer, typ_os char) is
    select op.rod_cislo as rc, os.meno || ' ' || os.priezvisko as meno, op.typ_osoby as tos
    from s_osoba_pripadu op join s_osoba os on(op.rod_cislo = os.rod_cislo)
    where op.id_pripadu = id_p and op.typ_osoby = typ_os 
      and not exists(select 'x' from s_obzalovana_osoba obo where op.rod_cislo = obo.rod_cislo and id_p = obo.id_pripadu)
    order by op.id_osoby;
  akt_podozrivy integer;
  
  cursor cur_hladany(id_p integer) is
    select hlo.rod_cislo as rc, os.meno || ' ' || os.priezvisko as meno,
      hlo.dat_od as dat, decode(hlo.dat_do, NULL, 'hladany/a', to_char(hlo.dat_do,'DD.MM.YYYY')) as dat_do, 
      decode(hlo.dovod, 'PST', 'podozrenie zo spachania trestneho cinu', 'PNZ', 'vydany prikaz na zatknutie', 
        'VNU', 'vazen na uteku', 'svedok') as dvd
    from s_hladana_osoba hlo join s_osoba os on(hlo.rod_cislo = os.rod_cislo)
    where hlo.id_pripadu = id_p and hlo.dat_do is null
    order by hlo.id_hladanej;
  akt_hladany integer;
  
  cursor cur_obzalovany(id_p integer) is
    select obo.rod_cislo as rc, os.meno || ' ' || os.priezvisko as meno,
      obo.vyska_skody as skoda, obo.udelena_pokuta as pokuta
    from s_obzalovana_osoba obo join s_osoba os on(obo.rod_cislo = os.rod_cislo)
    where obo.id_pripadu = id_p
    order by obo.id_obzalovanej;
  akt_obzalovany integer;
  
  cursor cur_odsudeny(id_p integer) is
    select odo.rod_cislo as rc, os.meno || ' ' || os.priezvisko as meno,
      odo.dat_nastupu as nastup, odo.dlzka_trestu as dlzka, vaz.nazov as vazanie 
    from s_odsudena_osoba odo join s_osoba os on(odo.rod_cislo = os.rod_cislo)
      join s_vaznica vaz on(odo.id_vaznice = vaz.id_vaznice)
    where odo.id_pripadu = id_p
    order by odo.id_odsudeneho;
  akt_odsudeny integer;
  akt_osoba integer;
begin
  for i_pripad in cur_pripad loop
    dbms_output.put_line('ID Pripadu: ' || i_pripad.id_p);
    dbms_output.put_line('.   ' || rpad('Typ pripadu: ',13) || rpad(i_pripad.tp,100));
    dbms_output.put_line('.   ' || rpad('Datum vykonania: ',17) || rpad(i_pripad.dvy,15)
                           || rpad('Datum zaciatku vysetrovania: ',29) || rpad(i_pripad.zv,15));
    dbms_output.put_line('.   ' || rpad('Miesto vykonania: ',18) || rpad(i_pripad.mv,15));
    
    akt_osoba := 1;
    for i_podozriva in cur_osoby_pripadu(i_pripad.id_p, 'P') loop
      if (akt_osoba = 1) then
        dbms_output.put_line('.   ' || rpad('Podozrive osoby:',16));
      end if;
      dbms_output.put_line('.      ' || rpad(akt_osoba,5)
                            || rpad(i_podozriva.meno,35) || rpad(i_podozriva.rc,15));
      akt_osoba := akt_osoba + 1;
    end loop;
    
    akt_osoba := 1;
    for i_hladany in cur_hladany(i_pripad.id_p) loop
      if (akt_osoba = 1) then
        dbms_output.put_line('.   ' || rpad('Hladane osoby:',16));
      end if;
      dbms_output.put_line('.      ' || rpad(akt_osoba,5)
                            || rpad(i_hladany.meno,35) || rpad(i_hladany.rc,15));
      dbms_output.put_line('.           ' || rpad('dovod: '||i_hladany.dvd,50) || rpad('od: ' ||i_hladany.dat,15) || rpad(i_hladany.dat_do,10));
      akt_osoba := akt_osoba + 1;
    end loop;
    
    akt_osoba := 1;
    for i_obzalovany in cur_obzalovany(i_pripad.id_p) loop
      if (akt_osoba = 1) then
        dbms_output.put_line('.   ' || rpad('Obvinene osoby:',17)); 
      end if;
      dbms_output.put_line('.      ' || rpad(akt_osoba,5)
                            || rpad(i_obzalovany.meno,35) || rpad(i_obzalovany.rc,15));
      dbms_output.put_line('.           ' || rpad('skoda: '||i_obzalovany.skoda||'e',17) || rpad('pokuta: '||i_obzalovany.pokuta||'e',17));
      akt_osoba := akt_osoba + 1;
    end loop;
    
    akt_osoba := 1;
    for i_odsudeny in cur_odsudeny(i_pripad.id_p) loop
      if (akt_osoba = 1) then
        dbms_output.put_line('.   ' || rpad('Odsudene osoby:',17)); 
      end if;
      dbms_output.put_line('.      ' || rpad(akt_osoba,5)
                            || rpad(i_odsudeny.meno,35) || rpad(i_odsudeny.rc,15));
      dbms_output.put_line('.           ' || rpad(i_odsudeny.vazanie,50) || rpad('od: '||i_odsudeny.nastup,15) 
                            || rpad('dlzka trestu: '||i_odsudeny.dlzka ||' r', 20));
      akt_osoba := akt_osoba + 1;
    end loop;
    
    for i_podozriva in cur_osoby_pripadu(i_pripad.id_p, 'S') loop
      if (akt_osoba = 1) then
        dbms_output.put_line('.   ' || rpad('Svedkovia:',10));
      end if;
      dbms_output.put_line('.      ' || rpad(akt_osoba,5)
                            || rpad(i_podozriva.meno,35) || rpad(i_podozriva.rc,15));
      akt_osoba := akt_osoba + 1;
    end loop;
  end loop;
end;
/

execute proc_prehlad_pripadov;


