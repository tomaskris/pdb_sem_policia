
-- Zoznam trestných činov a priestupkov za určité obdobie rozdelený podľa druhu trestného činu.


-- 2) Zoznam trestn�ch �inov a priestupkov za ur�it� obdobie rozdelen� pod�a druhu trestn�ho �inu.
create or replace procedure proc_trest_ciny_za_obd(od date, do date) is
 cursor cur_ciny is
  select tp.id_typ_pripadu as id_typ, decode(tp.nazov_typu, 'C','trestny cin','priestupok') as n_typ, tp.druh_pripadu as d_typ
  from s_typ_pripadu tp;

 cursor cur_prip(druh s_typ_pripadu.druh_pripadu%type) is
  select me.nazov as obvod, decode(pr.dat_vykon, NULL, 'neznamy', to_char(pr.dat_vykon,'DD.MM.YYYY')) as dvy, pr.dat_zac as zv,
   decode(pr.dat_ukon, NULL, 'neznamy', to_char(pr.dat_ukon,'DD.MM.YYYY')) as kv, decode(pr.objasneny, 'A', 'objasneny','neobjasneny') as p_obs
  from s_pripad pr join s_typ_pripadu tp on(pr.id_typ_pripadu = tp.id_typ_pripadu)
   join s_mesto me on(pr.miesto_vykon = me.psc)
  where (pr.dat_zac between od and do)
   and tp.druh_pripadu = druh;

 v_cin cur_ciny%ROWTYPE;
 v_pripad cur_prip%ROWTYPE;
 akt_prip integer;
begin
 open cur_ciny;
  loop
   fetch cur_ciny into v_cin;
   exit when cur_ciny%notfound;
   akt_prip := 1;
   open cur_prip(v_cin.d_typ);
    loop
     fetch cur_prip into v_pripad;
     exit when cur_prip%notfound;
     if(akt_prip = 1) then
      dbms_output.put_line('Typ pripadu: ' || v_cin.n_typ || ', ' || (v_cin.d_typ));
      dbms_output.put_line('.   ' || rpad('Pc.',4) || rpad('Miesto vykon',20) || rpad('Dat vykon',12) || rpad('Objasnenost',13)
                           || rpad('Zac vyset', 12) || rpad('Kon vyset', 12));
     end if;
     dbms_output.put_line('.   ' || rpad(akt_prip,4) || rpad(v_pripad.obvod,20) || rpad(v_pripad.dvy,12) || rpad(v_pripad.p_obs,13)
                          || rpad(v_pripad.zv, 12) || rpad(v_pripad.kv, 12));
     akt_prip := akt_prip + 1;
    end loop;
   close cur_prip;
  end loop;
 close cur_ciny;
end;
/

execute proc_trest_ciny_za_obd(to_date('01.01.2002'), to_date('01.01.2003'));