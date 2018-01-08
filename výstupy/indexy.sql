
create index os_pripadu_prip_typ on s_osoba_pripadu(id_pripadu, typ_osoby);
create index os_hladan_prip_datdo on s_hladana_osoba(id_pripadu, dat_do);
create index os_obzal_id_prip on s_obzalovana_osoba(id_pripadu);
create index os_obzal_rod_prip on s_obzalovana_osoba(rod_cislo, id_pripadu);
create index os_odsud_id_prip on s_odsudena_osoba(id_pripadu);
create index os_meno_priezv on s_osoba(rod_cislo, meno, priezvisko);