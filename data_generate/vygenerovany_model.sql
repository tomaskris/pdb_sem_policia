--DROPNUTIE TABULIEK

Drop table s_obzalovana_osoba;
Drop table s_historia_funkcii;
Drop table s_vypoved;
Drop table s_osoba_pripadu;
Drop table s_odsudena_osoba;
Drop table s_hladana_osoba;
Drop table s_typ_funkcie;
Drop table s_zamestnanec;
Drop table s_osoba;
drop type t_vypoved;
drop type t_rec_vypoved;
Drop table s_pripad;
Drop table s_biom_udaje;
Drop table s_vaznica;
Drop table s_typ_pripadu;
Drop table s_obvod cascade constraints;
Drop table s_mesto cascade constraints;
Drop table s_region cascade constraints;


--drop vsetky seqvencie

drop sequence sekv_id_biom_udaje;
drop sequence sekv_id_zamestnanec;
drop sequence sekv_id_pripad;
drop sequence sekv_id_osoba_pripadu;
drop sequence sekv_id_vypovede;
drop sequence sekv_id_hladanej;
drop sequence sekv_id_odsudeneho;
drop sequence sekv_id_obzalovaneho;
drop sequence sekv_id_obvod;
drop table pom_tab_psc;


--VYTVORENIE TABULIEK

Create table s_osoba (
	rod_cislo Char (11) NOT NULL ,
	id_biom_udaju Integer NOT NULL ,
	psc Char (5) NOT NULL ,
	meno Varchar2 (20) NOT NULL ,
	priezvisko Varchar2 (30) NOT NULL ,
primary key (rod_cislo) 
) 
/

Create table s_obvod (
	id_obvodu Integer NOT NULL ,
	psc Char (5) NOT NULL ,
	nazov Varchar2 (30) NOT NULL ,
primary key (id_obvodu) 
) 
/

Create table s_mesto (
	psc Char (5) NOT NULL ,
	id_regionu Integer NOT NULL ,
	nazov Varchar2 (30) NOT NULL ,
primary key (psc) 
) 
/

Create table s_region (
	id_regionu Integer NOT NULL ,
	nazov Varchar2 (30) NOT NULL ,
primary key (id_regionu) 
) 
/

Create table s_zamestnanec (
	id_zamestnanca Integer NOT NULL ,
	rod_cislo Char (11) NOT NULL ,
	id_obvodu Integer NOT NULL ,
	dat_od Date NOT NULL ,
	dat_do Date,
primary key (id_zamestnanca) 
) 
/

Create table s_typ_funkcie (
	id_funkcie Integer NOT NULL ,
	nazov Varchar2 (30) NOT NULL ,
	plat Integer NOT NULL ,
primary key (id_funkcie) 
) 
/

Create table s_hladana_osoba (
	id_hladanej Integer NOT NULL ,
	id_pripadu Integer NOT NULL ,
	rod_cislo Char (11) NOT NULL ,
	dovod Char (3) NOT NULL  Check (dovod in ('PST','PNZ','VNU','SVD') ) ,
	dat_od Date NOT NULL ,
	dat_do Date,
primary key (id_hladanej) 
) 
/

Create table s_odsudena_osoba (
	id_odsudeneho Integer NOT NULL ,
	rod_cislo Char (11) NOT NULL ,
	id_pripadu Integer NOT NULL ,
	id_vaznice Integer NOT NULL ,
	dlzka_trestu Integer NOT NULL ,
	dat_nastupu Date NOT NULL ,
primary key (id_odsudeneho) 
) 
/

create or replace type t_rec_vypoved as object (
  id_vypovede Integer,
  typ_vypovede Char(1),
  zaznam Blob,
  typ_suboru varchar(20)
);
/
create or replace type t_vypoved is table of t_rec_vypoved
/

Create table s_osoba_pripadu (
	id_osoby Integer NOT NULL ,
	id_pripadu Integer NOT NULL ,
	rod_cislo Char (11) NOT NULL ,
	typ_osoby Char (1) NOT NULL  Check (typ_osoby in ('S','P') ) ,
    vypoved t_vypoved ,
primary key (id_osoby) 
)
nested table vypoved store as tab_vypoved
/

Create table s_vypoved (
	id_vypovede Integer NOT NULL ,
	id_osoby Integer NOT NULL ,
	typ_vypovede Char (1) NOT NULL ,
	zaznam Blob NOT NULL ,
primary key (id_vypovede) 
) 
/

Create table s_historia_funkcii (
	id_zamestnanca Integer NOT NULL ,
	dat_od Date NOT NULL ,
	id_funkcie Integer NOT NULL ,
	dat_do Date ,
primary key (id_zamestnanca,dat_od) 
) 
/

Create table s_biom_udaje (
	id_biom_udaju Integer NOT NULL ,
	vyska Integer NOT NULL ,
	typ_postavy Char (2) NOT NULL  Check (typ_postavy in ('VC','CH','ST','TU','VT') ) ,
	farba_vlasov Char (2) NOT NULL  Check (farba_vlasov in ('BL','RY','HN','CR','CE') ) ,
	farba_oci Char (1) NOT NULL  Check (farba_oci in ('H','M','S','Z','C') ) ,
primary key (id_biom_udaju) 
) 
/

Create table s_pripad (
	id_pripadu Integer NOT NULL ,
	id_typ_pripadu Integer NOT NULL ,
	id_obvodu Integer NOT NULL ,
	miesto_vykon Char (5) NOT NULL ,
	dat_vykon Date ,
	objasneny Char (1) NOT NULL  Check (objasneny in ('A','N') ) ,
	dat_zac Date NOT NULL ,
	dat_ukon Date,
primary key (id_pripadu) 
) 
/

Create table s_vaznica (
	id_vaznice Integer NOT NULL ,
	psc Char (5) NOT NULL ,
	nazov Varchar2 (100) NOT NULL ,
primary key (id_vaznice) 
) 
/

Create table s_typ_pripadu (
	id_typ_pripadu Integer NOT NULL ,
	nazov_typu Char (1) NOT NULL  Check (nazov_typu in ('C','P') ) ,
	druh_pripadu Varchar2 (100) NOT NULL ,
primary key (id_typ_pripadu) 
) 
/
--alter table s_typ_pripadu
--modify druh_pripadu Varchar2 (100);


Create table s_obzalovana_osoba (
	id_obzalovanej Integer NOT NULL ,
	id_pripadu Integer NOT NULL ,
	rod_cislo Char (11) NOT NULL ,
	vyska_skody Integer,
	udelena_pokuta Integer,
primary key (id_obzalovanej) 
) 
/


-- Create Foreign keys section

Alter table s_zamestnanec add  foreign key (rod_cislo) references s_osoba (rod_cislo); 
/

Alter table s_osoba_pripadu add  foreign key (rod_cislo) references s_osoba (rod_cislo); 
/

Alter table s_odsudena_osoba add  foreign key (rod_cislo) references s_osoba (rod_cislo); 
/

Alter table s_hladana_osoba add  foreign key (rod_cislo) references s_osoba (rod_cislo); 
/

Alter table s_obzalovana_osoba add  foreign key (rod_cislo) references s_osoba (rod_cislo); 
/

Alter table s_zamestnanec add  foreign key (id_obvodu) references s_obvod (id_obvodu); 
/

Alter table s_pripad add  foreign key (id_obvodu) references s_obvod (id_obvodu); 
/

Alter table s_obvod add  ( constraint tab_obvod_fk foreign key (psc) references s_mesto (psc) deferrable );  
/

Alter table s_osoba add  foreign key (psc) references s_mesto (psc); 
/

Alter table s_pripad add  foreign key (miesto_vykon) references s_mesto (psc); 
/

Alter table s_vaznica add  foreign key (psc) references s_mesto (psc); 
/

Alter table s_mesto add  foreign key (id_regionu) references s_region (id_regionu); 
/

Alter table s_historia_funkcii add  foreign key (id_zamestnanca) references s_zamestnanec (id_zamestnanca); 
/

Alter table s_historia_funkcii add  foreign key (id_funkcie) references s_typ_funkcie (id_funkcie); 
/

Alter table s_vypoved add  foreign key (id_osoby) references s_osoba_pripadu (id_osoby); 
/

Alter table s_osoba add  foreign key (id_biom_udaju) references s_biom_udaje (id_biom_udaju); 
/

Alter table s_osoba_pripadu add  foreign key (id_pripadu) references s_pripad (id_pripadu); 
/

Alter table s_odsudena_osoba add  foreign key (id_pripadu) references s_pripad (id_pripadu); 
/

Alter table s_hladana_osoba add  foreign key (id_pripadu) references s_pripad (id_pripadu);
/

Alter table s_obzalovana_osoba add  foreign key (id_pripadu) references s_pripad (id_pripadu); 
/

Alter table s_odsudena_osoba add  foreign key (id_vaznice) references s_vaznica (id_vaznice); 
/

Alter table s_pripad add  foreign key (id_typ_pripadu) references s_typ_pripadu (id_typ_pripadu); 
/

commit;