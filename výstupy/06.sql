
-- Miera objasnenosti trestných činov v jednotlivých regiónoch, za určité obdobie

select reg.nazov as region,
  round(sum(case when (typ.nazov_typu = 'C' and pr.objasneny = 'A') then 1 else 0 end)/(select count(*)
    from s_pripad join s_typ_pripadu using(id_typ_pripadu) where nazov_typu = 'C' and dat_zac between  ? and ?)*100,2) as objasnenost_trest_cinov,
  round(sum(case when (typ.nazov_typu = 'P' and pr.objasneny = 'A') then 1 else 0 end)/(select count(*)
    from s_pripad join s_typ_pripadu using(id_typ_pripadu) where nazov_typu = 'P' and dat_zac between  ? and ?)*100,2) as objasnenost_priestupkov,
  round(sum(case when (pr.objasneny = 'A') then 1 else 0 end)/(select count(*) from s_pripad where dat_zac between  ? and ?)*100,2) as objasnenost_spolu
  from s_region reg join s_mesto mes on(reg.id_regionu = mes.id_regionu)
    join s_pripad pr on(pr.miesto_vykon = mes.psc)
    join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)
    where pr.dat_zac between ? and ?
    group by reg.nazov
    order by objasnenost_spolu desc;
    /