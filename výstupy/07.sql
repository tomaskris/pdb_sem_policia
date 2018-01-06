-- Miera objasnenosti najčastejšie sa vyskytujúceho druhu trestných činov

select druh_pripadu, poc_pripadov, objasnenost from (
  select druh_pripadu, poc_pripadov, objasnenost, row_number() over (order by objasnenost desc) as rn
  from
  (
    select typ.druh_pripadu as druh_pripadu, count(*) as poc_pripadov,
    round(
      sum(case when (pr.objasneny = 'A') then 1 else 0 end)
      /
      (select count(*) from s_pripad)*100
    ,2) as objasnenost
    from s_pripad pr join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)
    group by typ.druh_pripadu)
  )
where rn < 5;