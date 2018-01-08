-- Ročné náklady na mzdy pracovníkov PZ

create or replace type zaznam_o_mzdach_za_rok is object(
  rok number,
  suma number
);
/

create or replace type zaznamy_o_mzdach_za_rok is table of zaznam_o_mzdach_za_rok;
/




create or replace function proc_rocne_naklady_spolu
return zaznamy_o_mzdach_za_rok as
  zaznamy zaznamy_o_mzdach_za_rok;
  zaznam zaznam_o_mzdach_za_rok;
  min_rok number;
  naklady number;
begin
 select min(to_char(zam.dat_od,'YYYY')) into min_rok from s_zamestnanec zam;

 zaznamy := zaznamy_o_mzdach_za_rok();
 
 for rok in min_rok..to_number(to_char(sysdate,'YYYY')) 
  loop
    select round(sum(case when (rok > to_char(hf.dat_od,'YYYY') and rok < to_char(nvl(hf.dat_do,sysdate),'YYYY')) 
                    then (tf.plat * 12) 
                else (case when (to_char(hf.dat_od,'YYYY') = rok and (to_char(nvl(hf.dat_do,sysdate),'YYYY') = rok))
                    then (tf.plat * months_between(nvl(hf.dat_do,sysdate), hf.dat_od))
                else (case when (to_char(hf.dat_od,'YYYY') = rok)
                    then (tf.plat * months_between(to_date('31.12.'||rok), hf.dat_od))
                else (case when (to_char(nvl(hf.dat_do,sysdate),'YYYY') = rok)
                    then (tf.plat * months_between(nvl(hf.dat_do,sysdate), to_date('01.01.'||rok))) 
                else 0 end) end) end) end),0) as roc_nakl
    into naklady
    from s_historia_funkcii hf join s_typ_funkcie tf on(hf.id_funkcie = tf.id_funkcie)
    join s_zamestnanec za on (za.id_zamestnanca = hf.id_zamestnanca)
    join s_obvod ob on (za.id_obvodu = ob.id_obvodu);
    
    zaznamy.extend;
    zaznamy(zaznamy.last):= zaznam_o_mzdach_za_rok(rok, naklady);
  end loop;
    
    return zaznamy;
end;
/

select * from table(proc_rocne_naklady_spolu());


select * from s_osoba_pripadu
where vypoved is not null
order by id_osoby;

select * from s_osoba_pripadu;

select * from table(select vypoved from s_osoba_pripadu os
where os.id_osoby = 1);

update s_osoba_pripadu
set vypoved = null;


