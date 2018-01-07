package main.java.gui;

import main.java.gui.stats.*;

import javax.swing.*;
import java.awt.*;

public class Stats extends JDialog {
    private JPanel contentPane;
    private JButton load;
    private JPanel stat;
    private JComboBox comboBox1;
    private LoadDataForTable currentStat;


    public Stats() {
        setContentPane(contentPane);
        setModal(true);
        /*TrestneCinyMiesto statt = new TrestneCinyMiesto();
        currentStat = statt;
        stat.add(statt.getPanel());*/

        LoadDataForTable[] stats = new LoadDataForTable[9];
        stats[0] = new TrestneCinyMiesto();
        stats[1] = new VyskaSkodyMiesto();
        stats[2] = new ObjastnenostNajcastejsi();
        stats[3] = new ObjastnenostRegion();
        stats[4] = new Amnestia();
        stats[5] = new VazniPocasRokov();
        stats[6] = new MzdyPocasRokov();
        stats[7] = new PrehladPripadov();
        stats[8] = new NajpocetnejsieDruhPripadu();




        for (int i = 0; i < stats.length; i++) {
            stat.add(stats[i].getPanel(),stats[i].toString());
        }
//        stat.add(stats[0].getPanel());




        comboBox1.setModel(new DefaultComboBoxModel(
                stats
        ));

        comboBox1.addActionListener((e)->{
            System.out.println(e);
            System.out.println(getSelectedItem().toString());
            CardLayout cl = (CardLayout) stat.getLayout();
            cl.show(stat, getSelectedItem().toString());
        });

        load.addActionListener((e) ->{
            getSelectedItem().loadData();
//            currentStat.loadData();
        });


    }



    public JPanel getPanel(){
        return contentPane;
    }

    public LoadDataForTable getSelectedItem(){
        return ( (LoadDataForTable) comboBox1.getSelectedItem());
    }
}
