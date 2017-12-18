--prepisat login kris2 na svoj login
create or replace directory blob_files_police
as 'C:\TEMP\chudjak2\blob_files_police';

create or replace procedure proc_vymaz_vypoved(p_id_osoby number, zaznam blob)
 is
 nest_tab t_vypoved;
 blobLenght integer;
 res integer;
begin
 select vypoved into nest_tab from s_osoba_pripadu where id_osoby = p_id_osoby;
 blobLenght := DBMS_LOB.GETLENGTH(zaznam);
 
 for i in nest_tab.first .. nest_tab.last
  loop
   if nest_tab.exists(i) then 
    select dbms_lob.compare(zaznam, nest_tab(i).zaznam, blobLenght) into res from dual;
    if res = 0 then
      nest_tab.delete(i);
    end if;
   end if;
  end loop;
  update s_osoba_pripadu
  set vypoved=nest_tab
   where id_osoby=p_id_osoby;

end;
/


-- procedurky, ktore je potrebne vytvorit

-- proc_vymaz_pripad(id_pripadu)
-- proc_vytvor_pripad(Id_typ_pripadu, Id_obvodu, Miesto_vykon, Dat_vykon, Dat_zac, Dat_ukon)
-- proc_update_pripad(Id_typ_pripadu, Id_obvodu, Miesto_vykon, Dat_vykon, Dat_zac, Dat_ukon, id_pripadu)
 
-- proc_vytvor_region(nazov_regionu)
-- proc_update_region(nazov_regionu, id_regionu)
-- proc_vymaz_region(id_regionu)

-- proc_vytvor_hist_funkcii(Id_zamestnanca, Dat_od, id_funckie, dat_od) 
-- proc_update_hist_funkcii(Id_zamestnanca, Dat_od, id_funckie, dat_od) 
-- proc_vymaz_hist_funkcii(Id_zamestnanca, Dat_od)

-- proc_vytvor_hlad_osoba(id_pripadu, rod_cislo, dovod, dat_od, dat_do)
-- proc_update_hlad_osoba(id_pripadu, rod_cislo, dovod, dat_od, dat_do, id_hladanej)
-- proc_vymaz_hlad_osoba(id_hladanej)

-- proc_vytvor_mesto(psc, id_regionu, id_obvodu, nazov)
-- proc_update_mesto(psc, id_regionu, id_obvodu, nazov)
-- proc_delete_mesto(psc)

-- proc_vytvor_obvod(id_obvodu, psc, nazov)
-- proc_update_obvod(id_obvodu, psc, nazov)
-- proc_delete_obvod(id_obvodu)

-- proc_vytvor_obzalovana(id_pripadu, rod_cislo, vyska_skody, udelena_pokuta)
-- proc_update_obzalovana(id_pripadu, rod_cislo, vyska_skody, udelena_pokuta, id_obzalovanej)
-- proc_vymaz_obzalovana(id_obzalovanej)

-- proc_vytvor_odsudena(rod_cislo, id_pripadu, id_veznice, dlzka_trestu, dat_nastupu)
-- proc_update_odsudena(rod_cislo, id_pripadu, id_veznice, dlzka_trestu, dat_nastupu, id_odsudeneho)
-- proc_delete_odsudena(id_odsudeneho)

--
--
--

--
--
--