--INSERTY


insert into s_typ_funkcie values(1, 'Komisar Rex',20);
/

desc s_osoba_pripadu;

select * from s_osoba_pripadu ;

select * from table(select vypoved from s_osoba_pripadu where id_osoby = 952);
/

desc T_REC_VYPOVED;






create sequence sekv_typ_funkcie
increment by 1 start with 6;
--drop sequence sekv_id_obvod;
--trigger pre inkrementovanie ID pre tabulku s_region
create or replace trigger trig_typ_funkcie_inc_id
 before insert on s_typ_funkcie
 referencing new as novy
 for each row
begin
 select sekv_typ_funkcie.nextval into :novy.id_funkcie from dual;
end;
/

--vytvorenie sekvencie
create sequence sekv_region
increment by 1 start with 3;
--drop sequence sekv_id_obvod;
--trigger pre inkrementovanie ID pre tabulku s_region
create or replace trigger trig_region_inc_id
 before insert on s_region
 referencing new as novy
 for each row
begin
 select sekv_region.nextval into :novy.id_regionu from dual;
end;
/

--insert regionov (u nas budu regiony iba zo Slovenska a to aj tak iba zopar)
insert into s_region values(1, 'Orava');
insert into s_region values(2, 'Liptov');
--delete from s_region;
--insert miest pre jednotlive regiony
--nastavenie odlozitelnej kontroly pre session
alter session set constraints = deferred;

--insert obvodov

--vytvorenie sekvencie
create sequence sekv_id_obvod
increment by 1 start with 1;
--drop sequence sekv_id_obvod;
--trigger pre inkrementovanie ID pre tabulku s_region
create or replace trigger trig_obvod_inc_id
 before insert on s_obvod
 referencing new as novy
 for each row
begin
 select sekv_id_obvod.nextval into :novy.id_obvodu from dual;
end;
/

--insert obvodov a miest

--pre region orava
--obvod OO PZ Dolnı Kubín
insert into s_obvod(psc, nazov) values('02601', 'OO PZ Dolnı Kubín');
insert into s_mesto values('02601', 1, 1, 'Dolnı Kubín');
insert into s_mesto values('02753', 1, 1, 'Istebné');
insert into s_mesto values('02751', 1, 1, 'Kra¾ovany');
insert into s_mesto values('02755', 1, 1, 'Krivá');
insert into s_mesto values('02741', 1, 1, 'Oravskı Podzámok');
insert into s_mesto values('02754', 1, 1, 'Velièná');
insert into s_mesto values('02705', 1, 1, 'Zázrivá');
insert into s_mesto values('02721', 1, 1, 'aškov');

--obvod OO PZ Námestovo
insert into s_obvod(psc, nazov) values('02901', 'OO PZ Námestovo');
insert into s_mesto values('02942', 1, 2, 'Bobrov');
insert into s_mesto values('02952', 1, 2, 'Hruštín');
insert into s_mesto values('02322', 1, 2, 'Klin');
insert into s_mesto values('02951', 1, 2, 'Lokca');
insert into s_mesto values('02901', 1, 2, 'Námestovo'); --
insert into s_mesto values('02964', 1, 2, 'Oravská Jasenica');
insert into s_mesto values('02947', 1, 2, 'Oravská Polhora');
insert into s_mesto values('02962', 1, 2, 'Oravské Veselé');
insert into s_mesto values('02944', 1, 2, 'Rabèa');
insert into s_mesto values('02945', 1, 2, 'Rabèice');
insert into s_mesto values('02946', 1, 2, 'Sihelné');
insert into s_mesto values('02943', 1, 2, 'Zubrohlava');

--obvod OO PZ Zákamenné
insert into s_obvod(psc, nazov) values('02956', 'OO PZ Zákamenné');
insert into s_mesto values('02956', 1, 5, 'Zákamenné');
insert into s_mesto values('02953', 1, 5, 'Breza');
insert into s_mesto values('02954', 1, 5, 'Krušetnica');
insert into s_mesto values('02963', 1, 5, 'Mútne');
insert into s_mesto values('02955', 1, 5, 'Novo');
insert into s_mesto values('02957', 1, 5, 'Oravská Lesná');

--obvod OO PZ Tvrdošín
insert into s_obvod(psc, nazov) values('02744', 'OO PZ Tvrdošín');
insert into s_mesto values('02743', 1, 3, 'Niná');
insert into s_mesto values('02742', 1, 3, 'Podbiel');
insert into s_mesto values('02744', 1, 3, 'Tvrdošín');
insert into s_mesto values('02732', 1, 3, 'Zuberec');

--obvod OO PZ Trstená
insert into s_obvod(psc, nazov) values('02801', 'OO PZ Trstená');
insert into s_mesto values('02713', 1, 4, 'Hladovka');
insert into s_mesto values('02712', 1, 4, 'Liesek');
insert into s_mesto values('02801', 1, 4, 'Trstená');

--pre region liptov
--obvod OO PZ Ruomberok I
insert into s_obvod(psc, nazov) values('03401', 'OO PZ Ruomberok I');
insert into s_mesto values('03401', 2, 6, 'Ruomberok');
insert into s_mesto values('03495', 2, 6, 'Likavka');

--obvod OO PZ Ruomberok II
insert into s_obvod(psc, nazov) values('03401', 'OO PZ Ruomberok II');
insert into s_mesto values('03491', 2, 7, '¼ubochòa');
insert into s_mesto values('03492', 2, 7, 'Stankovany');
insert into s_mesto values('03496', 2, 7, 'Valaská Dubová');

--obvod OO PZ Liptovská Osada
insert into s_obvod(psc, nazov) values('03473', 'OO PZ Liptovská Osada');
insert into s_mesto values('03472', 2, 8, 'Liptovská Lúna');
insert into s_mesto values('03473', 2, 8, 'Liptovská Osada'); 
insert into s_mesto values('03474', 2, 8, 'Liptovské Revúce');

--obvod OO PZ Liptovská Teplá
insert into s_obvod(psc, nazov) values('03483', 'OO PZ Liptovská Teplá');
insert into s_mesto values('03483', 2, 9, 'Liptovská Teplá');
insert into s_mesto values('03484', 2, 9, 'Liptovské Sliaèe');
insert into s_mesto values('03481', 2, 9, 'Lisková');
insert into s_mesto values('03482', 2, 9, 'Lúèky');
insert into s_mesto values('03471', 2, 9, 'Ludrová');

--obvod OO PZ Liptovskı Mikuláš
insert into s_obvod(psc, nazov) values('03101', 'OO PZ Liptovskı Mikuláš');
insert into s_mesto values('03101', 2, 10, 'Liptovskı Mikuláš');
insert into s_mesto values('03221', 2, 10, 'Bobrovec');
insert into s_mesto values('03212', 2, 10, 'Dúbrava');
insert into s_mesto values('03203', 2, 10, 'Liptovskı Ján');
insert into s_mesto values('03204', 2, 10, 'Liptovskı Ondrej');
insert into s_mesto values('03214', 2, 10, '¼ube¾a');
insert into s_mesto values('03215', 2, 10, 'Partizánska ¼upèa');
insert into s_mesto values('03205', 2, 10, 'Smreèany');
insert into s_mesto values('03211', 2, 10, 'Svätı Krí');
insert into s_mesto values('03202', 2, 10, 'Závaná Poruba');

--obvod OO PZ Liptovská Sielnica
insert into s_obvod(psc, nazov) values('03223', 'OO PZ Liptovská Sielnica');
insert into s_mesto values('03223', 2, 11, 'Liptovská Sielnica');
insert into s_mesto values('03213', 2, 11, 'Vlachy');
insert into s_mesto values('03231', 2, 11, 'Hybe');
insert into s_mesto values('03233', 2, 11, 'Krá¾ova Lehota');
insert into s_mesto values('03244', 2, 11, 'Liptovská Kokava');

--obvod OO PZ Liptovskı Hrádok
insert into s_obvod(psc, nazov) values('03301', 'OO PZ Liptovskı Hrádok');
insert into s_mesto values('03301', 2, 12, 'Liptovskı Hrádok');
insert into s_mesto values('03242', 2, 12, 'Pribylina');
insert into s_mesto values('03261', 2, 12, 'Vaec');
insert into s_mesto values('03232', 2, 12, 'Vıchodná');

commit;

--opatovne zapnutie okamzitej kontroly integritnych obmedzeni
alter session set constraints = immediate;
--delete from s_obvod;
--select * from s_obvod;
--select * from s_mesto;

--insert vaznic
insert into s_vaznica values (1, '02601','Ústav na vıkon väzby Dolnı Kubín');
insert into s_vaznica values (2, '03401','Ústav na vıkon trestu odòatia slobody Ruomberok');
insert into s_vaznica values (3, '03101','Ústav na vıkon väzby Liptovskı Mikuláš');

--insert typ funkcii
--dopravni policajt, mestsky policajt, kriminalny policajt, policajny psovod, pyrotechnik
insert into s_typ_funkcie values (1, 'dopravni policajt', 1200);
insert into s_typ_funkcie values (2, 'mestsky policajt', 750);
insert into s_typ_funkcie values (3, 'kriminalny policajt', 1500);
insert into s_typ_funkcie values (4, 'policajny psovod', 600);
insert into s_typ_funkcie values (5, 'pyrotechnik', 1350);


--insert typ pripadov
insert into s_typ_pripadu values (1, 'C', 'vrada');
insert into s_typ_pripadu values (2, 'C', 'usmrtenie');
insert into s_typ_pripadu values (3, 'C', 'ublíenie na zdraví');
insert into s_typ_pripadu values (4, 'C', 'vydieranie');
insert into s_typ_pripadu values (5, 'C', 'znásilnenie');
insert into s_typ_pripadu values (6, 'C', 'tıranie');
insert into s_typ_pripadu values (7, 'C', 'podplácanie');
insert into s_typ_pripadu values (8, 'C', 'pytliactvo');
insert into s_typ_pripadu values (9, 'P', 'jazda pod vplyvom alkoholu');
insert into s_typ_pripadu values (10, 'P', 'sfalšovanie dokumentov');
insert into s_typ_pripadu values (11, 'P', 'rušenie noèného k¾udu');
insert into s_typ_pripadu values (12, 'P', 'nepravdivá svedecká vıpoveï');
insert into s_typ_pripadu values (13, 'P', 'prekroèenie rıchlosti');
insert into s_typ_pripadu values (14, 'P', 'predaj alkoholickıch nápojov osobám mladším ako 18 rokov');

commit;