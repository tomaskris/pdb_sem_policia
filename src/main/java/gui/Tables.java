package main.java.gui;

import main.java.Entities.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Tables extends JDialog {
    private JPanel contentPane;
    private JTabbedPane tabPanel;
    private JPanel pripadyPnl;
    private JPanel osobyPnl;
    private JTabbedPane pripady;
    private JTabbedPane osoby;
    private JTabbedPane policia;
    private JPanel policiaPNL;

    public Tables() {
        setContentPane(contentPane);
        setModal(true);




        osoby.add(new TablePanel(S_osoba.class).getPanel());
        osoby.setTitleAt(0,"Osoby");
        osoby.add(new TablePanel(S_osoba_pripadu.class).getPanel());
        osoby.setTitleAt(1,"Osoby pripadu");
        osoby.add(new TablePanel(S_obzalovana_osoba.class).getPanel());
        osoby.setTitleAt(2,"Obzalovane osoby");
        osoby.add(new TablePanel(S_odsudena_osoba.class).getPanel());
        osoby.setTitleAt(3,"Odsudene osoby");
        osoby.add(new TablePanel(S_hladana_osoba.class).getPanel());
        osoby.setTitleAt(4,"Hladane osoby");
        osoby.add(new TablePanel(S_biom_udaje.class).getPanel());
        osoby.setTitleAt(5,"Biometricke udaje");

        pripady.add(new TablePanel(S_pripad.class).getPanel());
        pripady.setTitleAt(0,"Pripady");
        pripady.add(new TablePanel(S_typ_pripadu.class).getPanel());
        pripady.setTitleAt(1,"Typy Pripadov");

        policia.add(new TablePanel(S_zamestnanec.class).getPanel());
        policia.setTitleAt(0,"Zamestnaneci");
        policia.add(new TablePanel(S_typ_funkcie.class).getPanel());
        policia.setTitleAt(1,"Typy funckii");
        policia.add(new TablePanel(S_historia_funkcii.class).getPanel());
        policia.setTitleAt(2,"Historia funkcii");
        policia.add(new TablePanel(S_vaznica.class).getPanel());
        policia.setTitleAt(3,"Väznice");
        policia.add(new TablePanel(S_obvod.class).getPanel());
        policia.setTitleAt(4,"Policajné obvody");
        policia.add(new TablePanel(S_mesto.class).getPanel());
        policia.setTitleAt(5,"Mesta");
        policia.add(new TablePanel(S_region.class).getPanel());
        policia.setTitleAt(6,"Regiony");



    }

    public JPanel getPanel(){
        return contentPane;
    }

    public JPanel getPanelPolicia(){
        return policiaPNL;
    }

    public JPanel getPanelPripady(){
        return pripadyPnl;
    }

    public JPanel getPanelOsoby(){
        return osobyPnl;
    }



}
