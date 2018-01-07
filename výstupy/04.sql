
-- Štatistika (počty) trestných činov rôznych druhov, zoradená od najčastejšie sa vyskytujúceho druhu po najzriedkavejšie.

select typ.druh_pripadu as druh_pripadu, count(*) as poc_pripadov
from s_pripad pr right join s_typ_pripadu typ on(typ.id_typ_pripadu = pr.id_typ_pripadu)
group by typ.druh_pripadu
order by poc_pripadov desc;
/