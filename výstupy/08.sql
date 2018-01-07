-- Zoznam osôb u ktorých sa môže bližšie posudzovať nárok na amnestiu udelenú prezidentom
-- pri príležitosti 20. výročia založenia nemenovaného štátu (kritéria si vymyslite).
-- kriteria: tie osoby, ktore nespachali zavazny trest cin, medzi vybranymi a zaroven
--                     ktorych dlzka trestu je menej ako 10 rokov a zaroven
--                     ktore si uz odsedeli 75% z celkovej dlzky trestu

select odo.rod_cislo, os.meno || ' ' || os.priezvisko as meno, odo.dlzka_trestu, odo.dat_nastupu,
 trunc(months_between(sysdate,odo.dat_nastupu)/12) as poc_rok_vo_vazani,
 decode(tp.nazov_typu,'C','trestny cin','priestupok') as trest_cin, tp.druh_pripadu
from s_osoba os join s_odsudena_osoba odo on(os.rod_cislo = odo.rod_cislo)
join s_pripad pr on(odo.id_pripadu = pr.id_pripadu)
join s_typ_pripadu tp on(pr.id_typ_pripadu = tp.id_typ_pripadu)
where (months_between(sysdate,add_months(odo.dat_nastupu, odo.dlzka_trestu*12)) < 0)
and (round(trunc(months_between(sysdate, odo.dat_nastupu)/12)/odo.dlzka_trestu*100,2) >= 75)
and (tp.druh_pripadu in ('usmrtenie','ublíženie na zdraví', 'podplácanie'))
and (odo.dlzka_trestu < 10);
/