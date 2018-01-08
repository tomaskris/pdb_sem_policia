
-- Dlhodobá štatistika počtu osôb vo výkone trestu (počet za rok).


create or replace type zaznam_o_vykone_za_rok is object(
  rok number,
  pocet number
);
/

create or replace type zaznamy_o_vykone_za_rok is table of zaznam_o_vykone_za_rok;
/

create or replace function stat_osob_vo_vykone
return zaznamy_o_vykone_za_rok
    as
    zaznamy zaznamy_o_vykone_za_rok;
    zaznam zaznam_o_vykone_za_rok;
    min_rok number;
    poc_osob number;
begin
  select min(to_char(odo.dat_nastupu,'YYYY')) into min_rok from s_odsudena_osoba odo;

  zaznamy := zaznamy_o_vykone_za_rok();

  for rok in min_rok..to_number(to_char(sysdate,'YYYY'))
  loop
    select count(*) into poc_osob
    from s_odsudena_osoba odo
    where to_char(rok) between to_char(odo.dat_nastupu,'YYYY') and to_char(add_months(odo.dat_nastupu, odo.dlzka_trestu * 12));

    zaznamy.extend;
    zaznamy(zaznamy.last):= zaznam_o_vykone_za_rok(rok, poc_osob);
  end loop;

  return zaznamy;
end stat_osob_vo_vykone;
/