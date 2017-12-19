package main.java.gui;

import main.java.Entities.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Tables extends JDialog {
    private JPanel contentPane;
    private JTabbedPane tabPanel;
    private JButton buttonOK;
    private List<Class> tables;

    public Tables() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        tables = new ArrayList<>();

        /*
        tables.add(S_biom_udaje.class);

        tables.add(S_historia_funkcii.class);
        tables.add(S_hladana_osoba.class);
        tables.add(S_mesto.class);
        tables.add(S_obvod.class);
        tables.add(S_obzalovana_osoba.class);
        tables.add(S_odsudena_osoba.class);
        tables.add(S_osoba.class);
*/
        tables.add(S_pripad.class);

/*
        tables.add(S_osoba_pripadu.class);
        tables.add(S_region.class);
        tables.add(S_typ_funkcie.class);

        tables.add(S_typ_pripadu.class);
        tables.add(S_vaznica.class);
        tables.add(S_zamestnanec.class);
        */



        int index = 0;
        for (Class dataClass : tables){
            tabPanel.add(new TablePanel(dataClass).getPanel());
            tabPanel.setTitleAt(index,dataClass.getSimpleName());
            index++;
        }
    }

    public JPanel getPanel(){
        return contentPane;
    }
}
