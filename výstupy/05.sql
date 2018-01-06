
-- Štatistické vyhodnotenie spôsobenej škody podľa rôznych kritérií

-- podla REGIONU

select reg.nazov as region,
 sum(case when (typ.nazov_typu = 'C') then obo.vyska_skody else 0 end) as vyska_skody_trest_cinov,
 sum(case when (typ.nazov_typu = 'P') then obo.vyska_skody else 0 end) as vyska_skody_priestupkov,
 sum(case when (obo.vyska_skody is not null) then obo.vyska_skody else 0 end) as vyska_skody_spolu
from s_region reg join s_mesto mes on(reg.id_regionu = mes.id_regionu)
join s_pripad pr on(pr.miesto_vykon = mes.psc)
join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)
join s_obzalovana_osoba obo on(obo.id_pripadu = pr.id_pripadu)
group by reg.nazov
order by vyska_skody_spolu desc;
/


-- podla MESTA

select mes.nazov as mesto,
 sum(case when (typ.nazov_typu = 'C') then obo.vyska_skody else 0 end) as vyska_skody_trest_cinov,
 sum(case when (typ.nazov_typu = 'P') then obo.vyska_skody else 0 end) as vyska_skody_priestupkov,
 sum(case when (obo.vyska_skody is not null) then obo.vyska_skody else 0 end) as vyska_skody_spolu
from s_mesto mes join s_pripad pr on(pr.miesto_vykon = mes.psc)
join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)
join s_obzalovana_osoba obo on(obo.id_pripadu = pr.id_pripadu)
group by mes.nazov
order by vyska_skody_spolu desc;
/

-- podla DRUHU PRIPADU

select typ.druh_pripadu as druh_pripadu,
 sum(case when (obo.vyska_skody is not null) then obo.vyska_skody else 0 end) as vyska_skody_spolu
from s_pripad pr right join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)
join s_obzalovana_osoba obo on(obo.id_pripadu = pr.id_pripadu)
group by typ.druh_pripadu
order by vyska_skody_spolu desc;
/

-- podla VEKU OBZALOVANEHO

select case when (months_between(sysdate, to_date(substr(obo.rod_cislo,5,2) || '.' ||
decode(substr(obo.rod_cislo,3,1),6,1,5,0,substr(obo.rod_cislo,3,1)) ||
substr(obo.rod_cislo,4,1) || '.19' || substr(obo.rod_cislo,1,2),'DD.MM.YYYY'))/12 < 14) then 'malolety'
else (case when (months_between(sysdate, to_date(substr(obo.rod_cislo,5,2) || '.' ||
decode(substr(obo.rod_cislo,3,1),6,1,5,0,substr(obo.rod_cislo,3,1)) ||
substr(obo.rod_cislo,4,1) || '.19' || substr(obo.rod_cislo,1,2),'DD.MM.YYYY'))/12 between 14 and 18) then 'mladistvy'
else (case when (months_between(sysdate, to_date(substr(obo.rod_cislo,5,2) || '.' ||
decode(substr(obo.rod_cislo,3,1),6,1,5,0,substr(obo.rod_cislo,3,1)) ||
substr(obo.rod_cislo,4,1) || '.19' || substr(obo.rod_cislo,1,2),'DD.MM.YYYY'))/12 between 19 and 60) then 'dospely' else 'osoby pokrocileho vek' end) end) end as vek_obvineneho,
sum(case when (typ.nazov_typu = 'C') then obo.vyska_skody else 0 end) as vyska_skody_trest_cinov,
sum(case when (typ.nazov_typu = 'P') then obo.vyska_skody else 0 end) as vyska_skody_priestupkov,
sum(case when (obo.vyska_skody is not null) then obo.vyska_skody else 0 end) as vyska_skody_spolu
from s_pripad pr right join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)
join s_obzalovana_osoba obo on(obo.id_pripadu = pr.id_pripadu)
group by case when (months_between(sysdate, to_date(substr(obo.rod_cislo,5,2) || '.' ||
decode(substr(obo.rod_cislo,3,1),6,1,5,0,substr(obo.rod_cislo,3,1)) ||
substr(obo.rod_cislo,4,1) || '.19' || substr(obo.rod_cislo,1,2),'DD.MM.YYYY'))/12 < 14) then 'malolety'
else (case when (months_between(sysdate, to_date(substr(obo.rod_cislo,5,2) || '.' ||
decode(substr(obo.rod_cislo,3,1),6,1,5,0,substr(obo.rod_cislo,3,1)) ||
substr(obo.rod_cislo,4,1) || '.19' || substr(obo.rod_cislo,1,2),'DD.MM.YYYY'))/12 between 14 and 18) then 'mladistvy'
else (case when (months_between(sysdate, to_date(substr(obo.rod_cislo,5,2) || '.' ||
decode(substr(obo.rod_cislo,3,1),6,1,5,0,substr(obo.rod_cislo,3,1)) ||
substr(obo.rod_cislo,4,1) || '.19' || substr(obo.rod_cislo,1,2),'DD.MM.YYYY'))/12 between 19 and 60) then 'dospely' else 'osoby pokrocileho vek' end) end) end
order by vyska_skody_spolu desc;
/

-- podla POHLAVIA

select decode(substr(obo.rod_cislo,3,1), 6, 'zena', 5, 'zena', 'muz') as pohlavie_obvineneho,
sum(case when (typ.nazov_typu = 'C') then obo.vyska_skody else 0 end) as vyska_skody_trest_cinov,
sum(case when (typ.nazov_typu = 'P') then obo.vyska_skody else 0 end) as vyska_skody_priestupkov,
sum(case when (obo.vyska_skody is not null) then obo.vyska_skody else 0 end) as vyska_skody_spolu
from s_pripad pr right join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)
join s_obzalovana_osoba obo on(obo.id_pripadu = pr.id_pripadu)
group by decode(substr(obo.rod_cislo,3,1), 6, 'zena', 5, 'zena', 'muz')
order by vyska_skody_spolu desc;
/