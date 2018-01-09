
-- Podrobný prehľad o vyšetrovaných prípadoch.

create or replace function proc_prehlad_pripadov_xml
return CLOB as
    cursor cur_pripad is
    select pr.id_pripadu as id_p, decode(tp.nazov_typu,'C','trestny cin', 'priestupok') || ', ' || tp.druh_pripadu as tp,
      me.nazov as mv, decode(pr.dat_vykon, NULL, 'neznamy', to_char(pr.dat_vykon,'DD.MM.YYYY')) as dvy, to_char(pr.dat_zac,'DD.MM.YYYY') as zv,
      decode(pr.dat_ukon, NULL, 'neznamy', to_char(pr.dat_ukon,'DD.MM.YYYY')) as kv, decode(pr.objasneny, 'A', 'objasneny','neobjasneny') as p_obs
    from s_pripad pr join s_typ_pripadu tp on(pr.id_typ_pripadu = tp.id_typ_pripadu)
      join s_mesto me on(pr.miesto_vykon = me.psc)
    order by pr.id_pripadu;

  cursor cur_osoby_pripadu(id_p integer, typ_os char) is
    select op.rod_cislo as rc, os.meno || ' ' || os.priezvisko as meno, op.typ_osoby as tos
    from s_osoba_pripadu op join s_osoba os on(op.rod_cislo = os.rod_cislo)
    where op.id_pripadu = id_p and op.typ_osoby = typ_os
      and not exists(select 'x' from s_obzalovana_osoba obo where op.rod_cislo = obo.rod_cislo and id_p = obo.id_pripadu)
    order by op.id_osoby;
  akt_podozrivy integer;

  cursor cur_hladany(id_p integer) is
    select hlo.rod_cislo as rc, os.meno || ' ' || os.priezvisko as meno,
      hlo.dat_od as dat, decode(hlo.dat_do, NULL, 'hladany/a', to_char(hlo.dat_do,'DD.MM.YYYY')) as dat_do,
      decode(hlo.dovod, 'PST', 'podozrenie zo spachania trestneho cinu', 'PNZ', 'vydany prikaz na zatknutie',
        'VNU', 'vazen na uteku', 'svedok') as dvd
    from s_hladana_osoba hlo join s_osoba os on(hlo.rod_cislo = os.rod_cislo)
    where hlo.id_pripadu = id_p and hlo.dat_do is null
    order by hlo.id_hladanej;
  akt_hladany integer;

  cursor cur_obzalovany(id_p integer) is
    select obo.rod_cislo as rc, os.meno || ' ' || os.priezvisko as meno,
      obo.vyska_skody as skoda, obo.udelena_pokuta as pokuta
    from s_obzalovana_osoba obo join s_osoba os on(obo.rod_cislo = os.rod_cislo)
    where obo.id_pripadu = id_p
    order by obo.id_obzalovanej;
  akt_obzalovany integer;

  cursor cur_odsudeny(id_p integer) is
    select odo.rod_cislo as rc, os.meno || ' ' || os.priezvisko as meno,
      odo.dat_nastupu as nastup, odo.dlzka_trestu as dlzka, vaz.nazov as vazanie
    from s_odsudena_osoba odo join s_osoba os on(odo.rod_cislo = os.rod_cislo)
      join s_vaznica vaz on(odo.id_vaznice = vaz.id_vaznice)
    where odo.id_pripadu = id_p
    order by odo.id_odsudeneho;
  akt_odsudeny integer;
  akt_osoba integer;

    l_xmltype XMLTYPE;
    l_domdoc dbms_xmldom.DOMDocument;
    l_root_node dbms_xmldom.DOMNode;

    l_pripady_element dbms_xmldom.DOMElement;
    l_pripady_node dbms_xmldom.DOMNode;

    l_pripad_element dbms_xmldom.DOMElement;
    l_pripad_node dbms_xmldom.DOMNode;

    l_osoby_element dbms_xmldom.DOMElement;
    l_osoby_node dbms_xmldom.DOMNode;


    l_osoba_element dbms_xmldom.DOMElement;
    l_osoba_node dbms_xmldom.DOMNode;


    l_help_element dbms_xmldom.DOMElement;
    l_help_node dbms_xmldom.DOMNode;


begin
    -- Create an empty XML document
    l_domdoc := dbms_xmldom.newDomDocument;

    -- Create a root node
    l_root_node := dbms_xmldom.makeNode(l_domdoc);


    l_pripady_element := dbms_xmldom.createElement(l_domdoc, 'Pripady' );
    l_pripady_node := dbms_xmldom.appendChild(l_root_node,dbms_xmldom.makeNode(l_pripady_element));

    for i_pripad in cur_pripad loop
        l_pripad_element := dbms_xmldom.createElement(l_domdoc, 'Pripad' );
        dbms_xmldom.setAttribute(l_pripad_element, 'ID', i_pripad.id_p);

        l_pripad_node := dbms_xmldom.appendChild(l_pripady_node,dbms_xmldom.makeNode(l_pripad_element));


        l_help_element := dbms_xmldom.createElement(l_domdoc, 'Typ' );
        l_help_node := dbms_xmldom.appendChild(l_pripad_node,dbms_xmldom.makeNode(l_help_element));

        l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
            dbms_xmldom.createTextNode(l_domdoc, i_pripad.tp )
        ));

        l_help_element := dbms_xmldom.createElement(l_domdoc, 'Datum_vykonania' );
        l_help_node := dbms_xmldom.appendChild(l_pripad_node,dbms_xmldom.makeNode(l_help_element));

        l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
            dbms_xmldom.createTextNode(l_domdoc, i_pripad.dvy )
        ));

        l_help_element := dbms_xmldom.createElement(l_domdoc, 'Zac_vysetrovania' );
        l_help_node := dbms_xmldom.appendChild(l_pripad_node,dbms_xmldom.makeNode(l_help_element));

        l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
            dbms_xmldom.createTextNode(l_domdoc, i_pripad.zv )
        ));

        l_help_element := dbms_xmldom.createElement(l_domdoc, 'Koniec_vysetrovania' );
        l_help_node := dbms_xmldom.appendChild(l_pripad_node,dbms_xmldom.makeNode(l_help_element));

        l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
            dbms_xmldom.createTextNode(l_domdoc, i_pripad.kv )
        ));

         l_help_element := dbms_xmldom.createElement(l_domdoc, 'Objasnenost' );
        l_help_node := dbms_xmldom.appendChild(l_pripad_node,dbms_xmldom.makeNode(l_help_element));

        l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
            dbms_xmldom.createTextNode(l_domdoc, i_pripad.p_obs )
        ));

        l_help_element := dbms_xmldom.createElement(l_domdoc, 'Miesto_vykonania' );
        l_help_node := dbms_xmldom.appendChild(l_pripad_node,dbms_xmldom.makeNode(l_help_element));

        l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
            dbms_xmldom.createTextNode(l_domdoc, i_pripad.mv )
        ));

        l_osoby_element := dbms_xmldom.createElement(l_domdoc, 'Podozrive_osoby' );
        l_osoby_node := dbms_xmldom.appendChild(l_pripad_node,dbms_xmldom.makeNode(l_osoby_element));

        for i_podozriva in cur_osoby_pripadu(i_pripad.id_p, 'P') loop
            l_osoba_element := dbms_xmldom.createElement(l_domdoc, 'Osoba' );
            dbms_xmldom.setAttribute(l_osoba_element, 'rod_cislo', i_podozriva.rc);
            l_osoba_node := dbms_xmldom.appendChild(l_osoby_node,dbms_xmldom.makeNode(l_osoba_element));

            l_help_element := dbms_xmldom.createElement(l_domdoc, 'Meno' );
            l_help_node := dbms_xmldom.appendChild(l_osoba_node,dbms_xmldom.makeNode(l_help_element));

            l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
                dbms_xmldom.createTextNode(l_domdoc, i_podozriva.meno )
            ));

        end loop;

        l_osoby_element := dbms_xmldom.createElement(l_domdoc, 'Hladane_osoby' );
        l_osoby_node := dbms_xmldom.appendChild(l_pripad_node,dbms_xmldom.makeNode(l_osoby_element));

        for i_hladany in cur_hladany(i_pripad.id_p) loop
            l_osoba_element := dbms_xmldom.createElement(l_domdoc, 'Osoba' );
            dbms_xmldom.setAttribute(l_osoba_element, 'rod_cislo', i_hladany.rc);
            l_osoba_node := dbms_xmldom.appendChild(l_osoby_node,dbms_xmldom.makeNode(l_osoba_element));

            l_help_element := dbms_xmldom.createElement(l_domdoc, 'Meno' );
            l_help_node := dbms_xmldom.appendChild(l_osoba_node,dbms_xmldom.makeNode(l_help_element));

            l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
                dbms_xmldom.createTextNode(l_domdoc, i_hladany.meno )
            ));

            l_help_element := dbms_xmldom.createElement(l_domdoc, 'Dovod' );
            l_help_node := dbms_xmldom.appendChild(l_osoba_node,dbms_xmldom.makeNode(l_help_element));

            l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
                dbms_xmldom.createTextNode(l_domdoc, i_hladany.dvd )
            ));

            l_help_element := dbms_xmldom.createElement(l_domdoc, 'Dat_od' );
            l_help_node := dbms_xmldom.appendChild(l_osoba_node,dbms_xmldom.makeNode(l_help_element));

            l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
                dbms_xmldom.createTextNode(l_domdoc, i_hladany.dat )
            ));

            l_help_element := dbms_xmldom.createElement(l_domdoc, 'Dat_do' );
            l_help_node := dbms_xmldom.appendChild(l_osoba_node,dbms_xmldom.makeNode(l_help_element));

            l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
                dbms_xmldom.createTextNode(l_domdoc, i_hladany.dat_do )
            ));


        end loop;

        l_osoby_element := dbms_xmldom.createElement(l_domdoc, 'Obvinene_osoby' );
        l_osoby_node := dbms_xmldom.appendChild(l_pripad_node,dbms_xmldom.makeNode(l_osoby_element));


        for i_obzalovany in cur_obzalovany(i_pripad.id_p) loop
            l_osoba_element := dbms_xmldom.createElement(l_domdoc, 'Osoba' );
            dbms_xmldom.setAttribute(l_osoba_element, 'rod_cislo', i_obzalovany.rc);
            l_osoba_node := dbms_xmldom.appendChild(l_osoby_node,dbms_xmldom.makeNode(l_osoba_element));

            l_help_element := dbms_xmldom.createElement(l_domdoc, 'Meno' );
            l_help_node := dbms_xmldom.appendChild(l_osoba_node,dbms_xmldom.makeNode(l_help_element));

            l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
                dbms_xmldom.createTextNode(l_domdoc, i_obzalovany.meno )
            ));

             l_help_element := dbms_xmldom.createElement(l_domdoc, 'Skoda' );
            l_help_node := dbms_xmldom.appendChild(l_osoba_node,dbms_xmldom.makeNode(l_help_element));

            l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
                dbms_xmldom.createTextNode(l_domdoc, i_obzalovany.skoda )
            ));

             l_help_element := dbms_xmldom.createElement(l_domdoc, 'Pokuta' );
            l_help_node := dbms_xmldom.appendChild(l_osoba_node,dbms_xmldom.makeNode(l_help_element));

            l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
                dbms_xmldom.createTextNode(l_domdoc, i_obzalovany.pokuta )
            ));

        end loop;

        l_osoby_element := dbms_xmldom.createElement(l_domdoc, 'Odsudene_osoby' );
        l_osoby_node := dbms_xmldom.appendChild(l_pripad_node,dbms_xmldom.makeNode(l_osoby_element));

        for i_odsudeny in cur_odsudeny(i_pripad.id_p) loop
            l_osoba_element := dbms_xmldom.createElement(l_domdoc, 'Osoba' );
            dbms_xmldom.setAttribute(l_osoba_element, 'rod_cislo', i_odsudeny.rc);
            l_osoba_node := dbms_xmldom.appendChild(l_osoby_node,dbms_xmldom.makeNode(l_osoba_element));

            l_help_element := dbms_xmldom.createElement(l_domdoc, 'Meno' );
            l_help_node := dbms_xmldom.appendChild(l_osoba_node,dbms_xmldom.makeNode(l_help_element));

            l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
                dbms_xmldom.createTextNode(l_domdoc, i_odsudeny.meno )
            ));

             l_help_element := dbms_xmldom.createElement(l_domdoc, 'Vazanie' );
            l_help_node := dbms_xmldom.appendChild(l_osoba_node,dbms_xmldom.makeNode(l_help_element));

            l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
                dbms_xmldom.createTextNode(l_domdoc, i_odsudeny.vazanie )
            ));

             l_help_element := dbms_xmldom.createElement(l_domdoc, 'Od' );
            l_help_node := dbms_xmldom.appendChild(l_osoba_node,dbms_xmldom.makeNode(l_help_element));

            l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
                dbms_xmldom.createTextNode(l_domdoc, i_odsudeny.nastup )
            ));

             l_help_element := dbms_xmldom.createElement(l_domdoc, 'Dlzka' );
            l_help_node := dbms_xmldom.appendChild(l_osoba_node,dbms_xmldom.makeNode(l_help_element));

            l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
                dbms_xmldom.createTextNode(l_domdoc, i_odsudeny.dlzka )
            ));


        end loop;

         l_osoby_element := dbms_xmldom.createElement(l_domdoc, 'Svedkovia' );
        l_osoby_node := dbms_xmldom.appendChild(l_pripad_node,dbms_xmldom.makeNode(l_osoby_element));


        for i_podozriva in cur_osoby_pripadu(i_pripad.id_p, 'S') loop
            l_osoba_element := dbms_xmldom.createElement(l_domdoc, 'Osoba' );
            dbms_xmldom.setAttribute(l_osoba_element, 'rod_cislo', i_podozriva.rc);
            l_osoba_node := dbms_xmldom.appendChild(l_osoby_node,dbms_xmldom.makeNode(l_osoba_element));

            l_help_element := dbms_xmldom.createElement(l_domdoc, 'Meno' );
            l_help_node := dbms_xmldom.appendChild(l_osoba_node,dbms_xmldom.makeNode(l_help_element));

            l_help_node := dbms_xmldom.appendChild(l_help_node,dbms_xmldom.makeNode(
                dbms_xmldom.createTextNode(l_domdoc, i_podozriva.meno )
            ));
        end loop;


    end loop;

    l_xmltype := dbms_xmldom.getXmlType(l_domdoc);
   dbms_xmldom.freeDocument(l_domdoc);

   return l_xmltype.getClobVal;
end;
/


select proc_prehlad_pripadov_xml from dual;