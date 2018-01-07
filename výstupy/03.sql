
-- Štatistika trestných činov podľa regiónov, miest, obvodov

-- podla REGIONOV

select reg.nazov as region,
sum(case when (typ.nazov_typu = 'C') then 1 else 0 end) as poc_trest_cinov,
sum(case when (typ.nazov_typu = 'P') then 1 else 0 end) as poc_priestupkov,
count(*) as spolu
from s_region reg join s_mesto mes on(reg.id_regionu = mes.id_regionu)
join s_pripad pr on(pr.miesto_vykon = mes.psc)
join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)
group by reg.nazov;
/

-- podla MIEST

select mes.nazov as mesto,
sum(case when (typ.nazov_typu = 'C') then 1 else 0 end) as poc_trest_cinov,
sum(case when (typ.nazov_typu = 'P') then 1 else 0 end) as poc_priestupkov,
count(*) as spolu
from s_mesto mes join s_pripad pr on(pr.miesto_vykon = mes.psc)
join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)
group by mes.nazov
order by mes.nazov;
/

-- podla OBVODOV

select obv.nazov as obvod,
sum(case when (typ.nazov_typu = 'C') then 1 else 0 end) as poc_trest_cinov,
sum(case when (typ.nazov_typu = 'P') then 1 else 0 end) as poc_priestupkov,
count(*) as spolu
from s_obvod obv join s_pripad pr on(pr.id_obvodu = obv.id_obvodu)
join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)
group by obv.nazov
order by obv.nazov;