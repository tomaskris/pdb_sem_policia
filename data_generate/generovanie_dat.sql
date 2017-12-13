--GENEROVANIE DAT

--generovanie dat pre tabulku S_BIOM_UDAJE
--vytvorenie sekvencie
create sequence sekv_id_biom_udaje
increment by 1 start with 1;
--trigger pre inkrementovanie ID pre tabulku s_region
create or replace trigger trig_biom_udaje_inc_id
 before insert on s_biom_udaje
 referencing new as novy
 for each row
begin
 select sekv_id_biom_udaje.nextval into :novy.id_biom_udaju from dual;
end;
/

--funkcia mi vrati vysku osoby
create or replace function get_vyska_osoby
 return number
is
begin
 --nahodne vygeneruje cislo od 150 do 200
 return ROUND(dbms_random.value(150, 200));
end;
/

--funkcia mi vrati typ osoby
create or replace function get_typ_osoby(id number)
 return char
is
begin
 case 
  when id = 1 then return 'VC';
  when id = 2 then return 'CH';
  when id = 3 then return 'ST';
  when id = 4 then return 'TU';
  when id = 5 then return 'VT';
  else null;
 end case;
end;
/

--funkcia mi vrati farbu vlasov
create or replace function get_vlasy(id number)
 return char
is
begin
 case 
  when id = 1 then return 'BL';
  when id = 2 then return 'RY';
  when id = 3 then return 'HN';
  when id = 4 then return 'CR';
  when id = 5 then return 'CE';
  else null;
 end case;
end;
/

--funkcia mi vrati farbu oci
create or replace function get_oci(id number)
 return char
is
begin
 case 
  when id = 1 then return 'H';
  when id = 2 then return 'M';
  when id = 3 then return 'S';
  when id = 4 then return 'Z';
  when id = 5 then return 'C';
  else null;
 end case;
end;
/

create or replace procedure proc_insert_tab_bio_udaje
 is
 vyska number;
 typ char(2);
 vlasy char(2);
 oci char(1);
begin
--vsetky kombinacie hodnot
--cyklus typ osoby
 for i in 1..5 loop
  typ := get_typ_osoby(i);
  --cyklus vlasy
  for j in 1..5 loop
   vlasy := get_vlasy(j);
   --cyklus oci
   for k in 1..5 loop
    oci := get_oci(k);
    --nahodna vyska
    vyska := get_vyska_osoby;
    --vysledny insert
    insert into s_biom_udaje(vyska, typ_postavy, farba_vlasov, farba_oci) values(vyska, typ, vlasy, oci);
   end loop;
  end loop;
  
 end loop;
end;
/

execute proc_insert_tab_bio_udaje;
--select * from s_biom_udaje;

commit;

---------------------------------------------------------------------------------------

--generovanie dat pre tabulku S_OSOBA

--vytvorim si pomocnu tabulku pre nahodny vyber psc
create table pom_tab_psc 
as select psc, row_number() over (order by psc) cislo_riadka
from s_mesto;

create or replace function get_psc(riadok integer)
 return Char
 is
 p_psc Char(5);
begin
 select psc into p_psc from pom_tab_psc where cislo_riadka = riadok;
 return p_psc;
end;
/

create or replace procedure proc_insert_tab_osoba
 is 
 cursor cur_osoby is (select rod_cislo as rc, meno, priezvisko
                        from priklad_db2.os_udaje
                        where meno is not null and priezvisko is not null);
 biom integer;
 size_tab_biom integer;
 p_psc Char(5);
 size_tab_pom_psc integer;
 
begin
 --ulozim si velkost tabulky pom_tab_psc
 select count(*) into size_tab_pom_psc from pom_tab_psc;

 --ulozim si velkost tabulky s_biom_udaje
 select count(*) into size_tab_biom from s_biom_udaje;
 
 for i in cur_osoby loop
  --vygenerujem si nahodne id_biom_udaju
  biom := ROUND(dbms_random.value(1, size_tab_biom));
  --vygenerujem si nahodne psc
  p_psc := get_psc(ROUND(dbms_random.value(1, size_tab_pom_psc)));
  --insertnem osobu
  insert into s_osoba values(i.rc, biom, p_psc, i.meno, i.priezvisko);
 end loop;
end;
/

execute proc_insert_tab_osoba;
--select * from s_osoba;


commit;

--------------------------------------------------------------------------------------

--generovanie dat pre tabulku S_ZAMESTNANEC

--vytvorenie sekvencie
create sequence sekv_id_zamestnanec
increment by 1 start with 1;
--trigger pre inkrementovanie ID pre tabulku s_region
create or replace trigger trig_zamestnanec_inc_id
 before insert on s_zamestnanec
 referencing new as novy
 for each row
begin
 select sekv_id_zamestnanec.nextval into :novy.id_zamestnanca from dual;
end;
/


--trigger, ktory skontroluje ci vkladana osoba ma viac ako 21 rokov k sucastnemu datumu, ak nie tak nevlozi
--a zaroven ci tato osoba nepracuje niekde inde
create or replace trigger trig_zamestnanec_prijimam
 before insert on s_zamestnanec
 referencing new as novy
 for each row
declare 
 vek integer;
 pocet integer;
begin
 --ulozim si vek osoby
 select trunc(months_between(sysdate, to_date((substr(rod_cislo, 5,2) || '.' || decode(substr(rod_cislo, 3,1),6,1,5,0,substr(rod_cislo, 3,1)) || substr(rod_cislo, 4,1) || '.19' || substr(rod_cislo, 1,2)), 'DD.MM.YYYY'))/12)
 into vek
 from s_osoba
 where rod_cislo = :novy.rod_cislo;
 
 --ak ma osoba viac ako 21 rokov, tak sa moze vkladat
 if(vek > 21) then
  --zistim ci sa osoba uz existuje v tabulke s_zamestnanec 
  select count(rod_cislo) into pocet from s_zamestnanec where rod_cislo = :novy.rod_cislo;
  if(pocet >= 1) then
   --tak musim este skontrolovat, ci uz skoncil v tom prvom zamestnani
   pocet := 0;
   select count(*) into pocet from s_zamestnanec
   where rod_cislo = :novy.rod_cislo and dat_od <= sysdate and (dat_do is null or dat_do > sysdate);
   --ak je ta osoba uz niekde zamestnana
   if(pocet > 0) then
    --vypise chybu ze nebolo mozne vlozit
    RAISE_APPLICATION_ERROR(-20000,'Osoba este niekde pracuje, takze nemoze byt vlozena.');
   end if;
  end if;
  else
   --inak vypise chybu ze nebolo mozne vlozit
   RAISE_APPLICATION_ERROR(-20000,'Vek osoby musi byt aspon 21 rokov, takze nemoze byt vlozena.');
 end if;
end;
/


--funkcia pre generovanie ci je osoba prijata podobne ako trigger 
create or replace function is_osoba_prijata(rc s_osoba.rod_cislo%type)
 return integer
is
 vek integer;
 pocet integer;
begin
 --ulozim si vek osoby
 select trunc(months_between(sysdate, to_date((substr(rod_cislo, 5,2) || '.' || decode(substr(rod_cislo, 3,1),6,1,5,0,substr(rod_cislo, 3,1)) || substr(rod_cislo, 4,1) || '.19' || substr(rod_cislo, 1,2)), 'DD.MM.YYYY'))/12)
 into vek
 from s_osoba
 where rod_cislo = rc;
 
 --ak ma osoba aspon 21 rokov, tak sa moze vkladat
 if(vek >= 21) then
  --zistim ci sa osoba uz existuje v tabulke s_zamestnanec 
  select count(rod_cislo) into pocet from s_zamestnanec where rod_cislo = rc;
  --ak existuje v tabulke
  if(pocet >= 1) then
   --tak musim este skontrolovat, ci uz skoncil v tom prvom zamestnani
   pocet := 0;
   select count(*) into pocet from s_zamestnanec
   where rod_cislo = rc and dat_od <= sysdate and (dat_do is null or dat_do > sysdate);
   --ak je ta osoba uz niekde zamestnana
   if(pocet > 0) then
    return 0;
   else 
    return 1;
   end if;
  else
   return 1;
  end if;
 else
  --inak vypise chybu ze nebolo mozne vlozit
  return 0;
 end if;
end;
/


--funkcia mi vrati nahodnu osobu
create or replace function get_random_osoba
 return Char
is
 rc s_osoba.rod_cislo%type; 
begin
 select rod_cislo into rc from
 ( select rod_cislo from s_osoba
  where length(trim(rod_cislo)) = 11 
   and (decode(substr(rod_cislo, 3,1),6,1,5,0,substr(rod_cislo, 3,1)) || substr(rod_cislo, 4,1) between 1 and 12)
   --tu mi to neviem z akeho dovodu neslo ani takto (substr(rod_cislo, 5,2) between 1 and 28), tak som to urobil inak
   and (substr(rod_cislo, 5,1) in (0, 1, 2))
  order by dbms_random.value )
 where rownum = 1;
 return rc;
end;
/

--funkcia mi vrati datum narodenia osoby
create or replace function get_datum_narodenia(rc s_osoba.rod_cislo%type)
 return date
is
 datum date;
begin
 select to_date((substr(rod_cislo, 5,2) || '.' || decode(substr(rod_cislo, 3,1),6,1,5,0,substr(rod_cislo, 3,1)) || substr(rod_cislo, 4,1) || '.19' || substr(rod_cislo, 1,2)), 'DD.MM.YYYY')
 into datum
 from s_osoba
 where rod_cislo = rc;
 
 return datum;
end;
/

--funkcia mi generuje datum medzi dvoma datumami
create or replace function get_nahodny_datum(dat_od date, dat_do date)
 return date
is
 datum date;
begin
 select to_date(trunc(dbms_random.value(to_char(dat_od, 'J'),to_char(dat_do, 'J'))),'J') 
 into datum
 from DUAL;
 
 return datum;
end;
/

create or replace procedure proc_insert_zamestnanci_obvodov
 is
 cursor cur_obvody is (select id_obvodu from s_obvod);
 poc_zamest integer;
begin
 --pre kazdy obvod
 for i in cur_obvody loop
  --generujem nahodny pocet UKONCENYCH zamestnancov od 40 do 60 osob
  poc_zamest := ROUND(dbms_random.value(40, 60));
  proc_insert_tab_zamestnanec(i.id_obvodu, poc_zamest, 1);

  --generujem nahodny pocet AKTUALNE PRACUJUCICH zamestnancov od 10 do 20 osob
  poc_zamest := ROUND(dbms_random.value(10, 20));
  proc_insert_tab_zamestnanec(i.id_obvodu, poc_zamest, 0);
 end loop;
end;
/

create or replace procedure proc_insert_tab_zamestnanec(id_obv integer, poc_zamest integer, is_ukon_sluzba integer)
 is
 rc s_osoba.rod_cislo%type;
 dat_nar date;
 poc_prac_rokov integer;
 dat_zac date;
 dat_ukon date;
 size_tab_funkcie integer;
 cur_id_zamest integer;
begin
 --ulozim si velkost tabulky s_typ_funkcie
 select count(*) into size_tab_funkcie from s_typ_funkcie;
 
 for j in 1..poc_zamest loop
   --vyberiem nahodnu osobu
   rc := get_random_osoba;
   --ak je taka osoba prijata, tak ju mozem zobrat
   if (is_osoba_prijata(rc) = 1) then
    --zistim si datum narodenia osoby
    dat_nar := get_datum_narodenia(rc);
    --zistim si pocet rokov, kolko by mohol pracovat od svojich 21 narodenin
    select (trunc(months_between(sysdate, dat_nar)/12) - 21 ) 
    into poc_prac_rokov from dual;
    --ulozim si datum odkedy zacal pracovat
    dat_zac := to_date(add_months(sysdate,-poc_prac_rokov*12));
    --vygenerujem si datum ukoncenia pracovnej cinnosti
    dat_ukon := get_nahodny_datum(dat_zac, sysdate);
    --insertnem ho do tabulky s_zamestnanec
    if (is_ukon_sluzba = 1) then
     insert into s_zamestnanec(rod_cislo, id_obvodu, dat_od, dat_do) values(rc, id_obv, dat_zac, dat_ukon);
    else
     insert into s_zamestnanec(rod_cislo, id_obvodu, dat_od, dat_do) values(rc, id_obv, dat_zac, null);
    end if;
    --ulozim si id_zamestnanca
    select sekv_id_zamestnanec.currval into cur_id_zamest from dual;
    --insertnem jeho funkcie do tabulky s_historia_funkcii cez proceduru
    proc_insert_historia_funkcii(cur_id_zamest, dat_zac, dat_ukon, size_tab_funkcie);
   end if;
  end loop;
end;
/

create or replace function is_unique_date_historia(dat date)
 return number
is
 poc integer; 
begin
 select count(*) into poc from s_historia_funkcii 
 where dat_od = dat;
 
 if (poc = 0) then
  return 0;
 else 
  return 1;
 end if;
end;
/

create or replace procedure proc_insert_historia_funkcii(id_zamest integer, dat_zac date, dat_ukon date, poc_funkcii integer)
 is
 poc_zmen_funkcii integer;
 dat_zac_funkcie date;
 dat_ukon_funkcie date;
 rand_funkcia integer;
begin
   --vygenerujem si kolkokrat zmenil svoju funkciu
   poc_zmen_funkcii := round(dbms_random.value(0,3));
   --ak zmenil funkciu
   if (poc_zmen_funkcii > 0) then
    dat_zac_funkcie := dat_zac;
    dat_ukon_funkcie := dat_zac;
    for k in 1..poc_zmen_funkcii loop
     --vygenerujem si nahodny datum zaciatku prace v danej funkcii
     dat_zac_funkcie := get_nahodny_datum(dat_ukon_funkcie, dat_ukon);
     if(is_unique_date_historia(dat_zac_funkcie) = 0) then
      --ak to je uz posledna zmena funkcie
      if(k = poc_zmen_funkcii) then
       dat_ukon_funkcie := dat_ukon;
      else 
       --vygenerujem si nahodny datum ukoncenia funkcie
       dat_ukon_funkcie := get_nahodny_datum(dat_zac_funkcie, dat_ukon);
      end if; 
      --vygenerujem nahodne id_funkcie
      rand_funkcia := round(dbms_random.value(1, poc_funkcii));
      --insertnem do historie
      insert into s_historia_funkcii values(id_zamest, dat_zac_funkcie, rand_funkcia, dat_ukon_funkcie);
     end if;
    end loop;
   end if;
end;
/

execute proc_insert_zamestnanci_obvodov;

--select * from s_zamestnanec order by id_zamestnanca;
--select * from s_historia_funkcii;

commit;

----------------------------------------------------------------------------------------------------

--generovanie PRIPADOV

--vytvorenie sekvencie
create sequence sekv_id_pripad
increment by 1 start with 1;
--drop sequence sekv_id_pripad;
--drop trigger trig_zamestnanec_inc_id;
--trigger pre inkrementovanie ID pre tabulku s_region
create or replace trigger trig_pripad_inc_id
 before insert on s_pripad
 referencing new as novy
 for each row
begin
 select sekv_id_pripad.nextval into :novy.id_pripadu from dual;
end;
/

--vytvorenie sekvencie
create sequence sekv_id_osoba_pripadu
increment by 1 start with 1;
--drop trigger trig_zamestnanec_inc_id;
--trigger pre inkrementovanie ID pre tabulku s_region
create or replace trigger trig_osoba_pripadu_inc_id
 before insert on s_osoba_pripadu
 referencing new as novy
 for each row
begin
 select sekv_id_osoba_pripadu.nextval into :novy.id_osoby from dual;
end;
/

--vytvorenie sekvencie
create sequence sekv_id_vypovede
increment by 1 start with 1;
--drop trigger trig_zamestnanec_inc_id;
--trigger pre inkrementovanie ID pre tabulku s_region
create or replace trigger trig_vypoved_inc_id
 before insert on s_vypoved
 referencing new as novy
 for each row
begin
 select sekv_id_vypovede.nextval into :novy.id_vypovede from dual;
end;
/

--vytvorenie sekvencie
create sequence sekv_id_hladanej
increment by 1 start with 1;
--drop trigger trig_zamestnanec_inc_id;
--trigger pre inkrementovanie ID pre tabulku s_region
create or replace trigger trig_hladana_inc_id
 before insert on s_hladana_osoba
 referencing new as novy
 for each row
begin
 select sekv_id_hladanej.nextval into :novy.id_hladanej from dual;
end;
/

--vytvorenie sekvencie
create sequence sekv_id_odsudeneho
increment by 1 start with 1;
--drop trigger trig_zamestnanec_inc_id;
--trigger pre inkrementovanie ID pre tabulku s_region
create or replace trigger trig_odsudena_inc_id
 before insert on s_odsudena_osoba
 referencing new as novy
 for each row
begin
 select sekv_id_odsudeneho.nextval into :novy.id_odsudeneho from dual;
end;
/

--vytvorenie sekvencie
create sequence sekv_id_obzalovaneho
increment by 1 start with 1;
--drop trigger trig_obzalovany_inc_id;
--trigger pre inkrementovanie ID pre tabulku s_region
create or replace trigger trig_obzalovany_inc_id
 before insert on s_obzalovana_osoba
 referencing new as novy
 for each row
begin
 select sekv_id_obzalovaneho.nextval into :novy.id_obzalovanej from dual;
end;
/

create or replace procedure proc_insert_vypoved(osoba integer)
 is
 typ Char(1);
 rec blob;
begin
 case round(dbms_random.value(1,3))
  when 1 then
   typ := 'T';
  when 2 then
   typ := 'F';
  else
   typ := 'V';
 end case;
 
 insert into s_vypoved(id_osoby, typ_vypovede, zaznam) values(osoba, typ, rec);
end;
/

create or replace procedure proc_insert_odsudeny(pripad integer, rc s_osoba.rod_cislo%type, dat_zac_prip date, dat_ukon_hlad date)
 is
 dat_nastupu date;
 size_tab_vaznice integer;
 vaznica integer;
 dlzka_trest integer;
begin
 --ulozim si velkost tabulky s_vaznica
 select count(*) into size_tab_vaznice from s_vaznica;
 --ulozim si id vaznice pre vazna
 vaznica := round(dbms_random.value(1,size_tab_vaznice));
 --vygenerujem nahodne dlzku trestu od priblizne 1 az po 80 rokov 
 dlzka_trest := round(dbms_random.value(1,80));
 --ak bolo ukoncene hladanie, tak datum nastupu bude medzi, datumom 
 if(dat_ukon_hlad is not null) then
  dat_nastupu := get_nahodny_datum(dat_ukon_hlad, (dat_ukon_hlad + round(dbms_random.value(0,50))));
 else
  dat_nastupu := get_nahodny_datum(dat_zac_prip, (dat_zac_prip + round(dbms_random.value(0,50))));
 end if;
 
 insert into s_odsudena_osoba(rod_cislo, id_pripadu, id_vaznice, dlzka_trestu, dat_nastupu)
 values(rc, pripad, vaznica, dlzka_trest, dat_nastupu);
end;
/

--tato funkcia mi vlozi hladaneho a vrati mi datum ukoncenia hladaneho 
create or replace function fun_insert_hladany(pripad integer, rc s_osoba.rod_cislo%type, dovod Char, dat_zac_prip date, dat_ukon_prip date)
 return date 
is
 dat_ukon_hlad date;
begin
 --ak bol ukonceny pripad, tak vygenerujem nahodny datum ukoncenia hladania
 if (dat_ukon_prip is not null) then
  dat_ukon_hlad := get_nahodny_datum(dat_zac_prip, dat_ukon_prip);
 end if;
 
 insert into s_hladana_osoba(id_pripadu, rod_cislo, dovod, dat_od, dat_do) values(pripad, rc, dovod, dat_zac_prip, dat_ukon_hlad);
 
 return dat_ukon_hlad;
end;
/

create or replace procedure proc_rand_trest_cin(pripad integer, dat_zac_prip date, dat_ukon_prip date) 
 is
 poc_osob_pripadu integer;
 typ Char(1);
 skoda integer;
 pokuta integer;
 rc s_osoba.rod_cislo%type;
 poc_vypovedi integer;
 dat_ukon_hlad date;
begin
 --nahodne vygenerujem pocet osob pripadu
 poc_osob_pripadu := round(dbms_random.value(1,5));
 for i in 1..poc_osob_pripadu loop
  --nahodne vygenerujem osobu_pripadu
  rc := get_random_osoba;
  typ := 'P';
  --insertnem osobu pripadu
  insert into s_osoba_pripadu(id_pripadu, rod_cislo, typ_osoby) values(pripad, rc, typ);
  --nahodne zistim ci tato podozriva osoba bola obzalovana (1-ano, 0-nie)
  if(round(dbms_random.value(0,1)) = 1) then
   --obzalovana osoba sposobila nahodnu skodu
   skoda := round(dbms_random.value(10, 2000));
   --pokuta, ktoru musi obzalovana osoba zaplatit
   pokuta := round(dbms_random.value(skoda, (skoda * 2)));
   --insertnem obzalovanu osobu
   insert into s_obzalovana_osoba(id_pripadu, rod_cislo, vyska_skody, udelena_pokuta) values(pripad, rc, skoda, pokuta);
   --nahodne zistim ci tato obzalovana osoba je hladana (1-ano, 0-nie)
   if(round(dbms_random.value(0,1)) = 1) then
    --hladana osoba na ktory je vydany prikaz na zatknutie
    dat_ukon_hlad := fun_insert_hladany(pripad, rc, 'PNZ', dat_zac_prip, dat_ukon_prip);
    --ak hladana osoba bola najdena
    if (dat_ukon_hlad is not null) then
     --tak hodim osobu do lochu
     proc_insert_odsudeny(pripad, rc, dat_zac_prip, dat_ukon_hlad);
    end if;
   else
    --ak obzalovana osoba nie je hladana, tak hned sa supne do lochu
   proc_insert_odsudeny(pripad, rc, dat_zac_prip, dat_ukon_hlad);
   end if; 
  else 
   --ak podozriva osoba nie je obzalovana
   --nahodne zistim ci tato podozriva osoba je hladana (1-ano, 0-nie)
   if(round(dbms_random.value(0,1)) = 1) then
    --hladana osoba na ktorej je podozrenie zo spachania trestneho cinu
    dat_ukon_hlad := fun_insert_hladany(pripad, rc, 'PST', dat_zac_prip, dat_ukon_prip);
   end if;
  end if;
 end loop;
 
 --nahodne vygenerujem pocet svedkov trestneho cinu
 poc_osob_pripadu := round(dbms_random.value(0,3));
 for i in 1..poc_osob_pripadu loop
  --nahodne vygenerujem osobu_pripadu
  rc := get_random_osoba;
  typ := 'S';
    --nahodne si vygenerujem pocet vypovedi
  poc_vypovedi := round(dbms_random.value(0,1));
  if(poc_vypovedi = 1) then
    --insertnem osobu pripadu aj s nahodnou vypovedou
    insert into s_osoba_pripadu(id_pripadu, rod_cislo, typ_osoby, vypoved) values(pripad, rc, typ, t_vypoved(get_rand_t_rec_vypoved));
  else 
    --insertnem osobu pripadu bez vypovede
    insert into s_osoba_pripadu(id_pripadu, rod_cislo, typ_osoby) values(pripad, rc, typ);
  end if;
 end loop;
end;
/

--funkcia ti vrati nahodny record pre blob (konkretny fotku, video alebo text)
create or replace function get_rand_t_rec_vypoved
 return t_rec_vypoved
is
 typ integer;
 polozka integer;
 vypoved t_rec_vypoved;
begin
 typ := round(dbms_random.value(1,3));
 polozka := round(dbms_random.value(1,3));
 case typ
  when 1 then 
   case polozka 
    when 1 then
     vypoved := t_rec_vypoved('F',get_local_binary_data('BLOB_FILES_POLICE','fotka1.jpg'));
    when 2 then
     vypoved := t_rec_vypoved('F',get_local_binary_data('BLOB_FILES_POLICE','fotka2.jpg'));
    else 
     vypoved := t_rec_vypoved('F',get_local_binary_data('BLOB_FILES_POLICE','fotka3.jpg'));
   end case;
  when 2 then
   case polozka 
    when 1 then
     vypoved := t_rec_vypoved('V',get_local_binary_data('BLOB_FILES_POLICE','video1.mp4'));
    when 2 then
     vypoved := t_rec_vypoved('V',get_local_binary_data('BLOB_FILES_POLICE','video2.mp4'));
    else 
     vypoved := t_rec_vypoved('V',get_local_binary_data('BLOB_FILES_POLICE','video3.mp4'));
   end case;
  else 
   case polozka 
    when 1 then
     vypoved := t_rec_vypoved('T',get_local_binary_data('BLOB_FILES_POLICE','text1.txt'));
    when 2 then
     vypoved := t_rec_vypoved('T',get_local_binary_data('BLOB_FILES_POLICE','text2.txt'));
    else 
     vypoved := t_rec_vypoved('T',get_local_binary_data('BLOB_FILES_POLICE','text3.txt'));
   end case;
 end case;
 
 return vypoved;
end;
/

create or replace procedure proc_rand_priestupok(pripad integer)
 is
 poc_osob_pripadu integer;
 typ Char(1);
 skoda integer;
 pokuta integer;
 rc s_osoba.rod_cislo%type;
 poc_vypovedi integer;
begin
 --nahodne vygenerujem pocet obvinenych z priestupku
 poc_osob_pripadu := round(dbms_random.value(1,3));
 for i in 1..poc_osob_pripadu loop
  --nahodne vygenerujem osobu_pripadu
  rc := get_random_osoba;
  --obzalovana osoba sposobila nahodnu skodu
  skoda := round(dbms_random.value(10, 2000));
  --pokuta, ktoru musi obzalovana osoba zaplatit
  pokuta := round(dbms_random.value(skoda, (skoda * 2)));
  --insertnem obzalovanu osobu
  insert into s_obzalovana_osoba(id_pripadu, rod_cislo, vyska_skody, udelena_pokuta) values(pripad, rc, skoda, pokuta);
 end loop;
 
 --nahodne vygenerujem pocet svedkov
 poc_osob_pripadu := round(dbms_random.value(0,3));
 for i in 1..poc_osob_pripadu loop
  --nahodne vygenerujem osobu_pripadu
  rc := get_random_osoba;
  typ := 'S';
    --nahodne si vygenerujem pocet vypovedi
  poc_vypovedi := round(dbms_random.value(0,1));
  if(poc_vypovedi = 1) then
    --insertnem osobu pripadu aj s nahodnou vypovedou
    insert into s_osoba_pripadu(id_pripadu, rod_cislo, typ_osoby, vypoved) values(pripad, rc, typ, t_vypoved(get_rand_t_rec_vypoved));
  else 
    --insertnem osobu pripadu bez vypovede
    insert into s_osoba_pripadu(id_pripadu, rod_cislo, typ_osoby) values(pripad, rc, typ);
  end if;
 end loop;
end;
/

--procedura pre insert do tabulky s_pripad
create or replace procedure proc_insert_pripady is
 dat_pripadu date;
 poc_pripadov integer;
 size_tab_obvod integer;
 rand_obvod integer;
 rand_miesto Char(5);
 size_tab_pom_psc integer;
 obvod_pripadu integer;
 dat_zac_prip date;
 dat_ukon_prip date;
 objasneny Char(1);
 typ_pripadu integer;
 cin Char(1);
 size_tab_typ_pripad integer;
 cur_id_pripad integer;
begin
 for prip in 1..1000 loop
 --PRE JEDEN PRIPAD V DANY DATUM 
 --pocet riadov tabulky s_obvod
 select count(*) into size_tab_obvod from s_obvod;
 --ulozim si velkost tabulky pom_tab_psc
 select count(*) into size_tab_pom_psc from pom_tab_psc;
 --ulozim si velkost tabulky s_typ_pripadu
 select count(*) into size_tab_typ_pripad from s_typ_pripadu;
 --nie vzdy sa vie, kedy sa pripad stal.. ak nasli mrtve telo vo vode, tak nevedno kedy sa stal ten pripad
 if (round(dbms_random.value(0, 1)) = 1) then
  --ak sa vie, kedy sa stal pripad
  --pripady maximalne 30 rokov dozadu
  dat_pripadu := get_nahodny_datum(add_months(sysdate, (-12*30)), sysdate);
  --vygenerujem si kedy sa pripad zacal riesit
  dat_zac_prip := get_nahodny_datum(dat_pripadu, (dat_pripadu + 10));
 else 
  dat_pripadu := null;
  dat_zac_prip := get_nahodny_datum(add_months(sysdate, (-12*30)), sysdate);
 end if;
 --pocet pripadov v takom datume od 1 po 2
 poc_pripadov := round(dbms_random.value(1, 2));
 --nahodne vygenerovany obvod
 rand_obvod := round(dbms_random.value(1, size_tab_obvod));
 --nahodne vygenerovane miesto
 rand_miesto := get_psc(ROUND(dbms_random.value(1, size_tab_pom_psc)));
 --zistim si id_obvodu podla obce, na zaklade ktore viem pod ktory PZ patri
 select id_obvodu into obvod_pripadu from s_mesto where psc = rand_miesto;
 --nahodne vygenerujem ci bol pripad objasneny
 if(round(dbms_random.value(0, 1)) = 1) then
  objasneny := 'A';
  --ak bol pripad objasneny tam mu nastavim dattum ukoncenia pripadu
  dat_ukon_prip := get_nahodny_datum(dat_zac_prip, sysdate);
 else 
  objasneny := 'N';
  dat_ukon_prip := null;
 end if;
 
 --vygenerujem tolko pripadov v ten isty den
 for i in 1..poc_pripadov loop
  --vygenerujem si typ_pripadu
  typ_pripadu := round(dbms_random.value(1, size_tab_typ_pripad));
  --insertnem do tabulky s_pripad
  insert into s_pripad(id_typ_pripadu, id_obvodu, miesto_vykon, dat_vykon, objasneny, dat_zac, dat_ukon)
  values(typ_pripadu, rand_obvod, rand_miesto, dat_pripadu, objasneny, dat_zac_prip, dat_ukon_prip);

  --ulozim si id_pripadu
  select sekv_id_pripad.currval into cur_id_pripad from dual;
  --zistim ci bol vygenerovany trestny cin alebo priestupok
  select nazov_typu into cin from s_typ_pripadu where id_typ_pripadu = typ_pripadu;
  --ak sa jedna o trestny cin, tak vygenerujem trestny cin, inak priestupok
  if (cin = 'C') then
   proc_rand_trest_cin(cur_id_pripad, dat_zac_prip, dat_ukon_prip);
  else 
   proc_rand_priestupok(cur_id_pripad);
  end if;
 end loop;
 end loop;
end;
/

execute proc_insert_pripady;

--odstranim pomocnu tabulku
drop table pom_tab_psc;

commit;