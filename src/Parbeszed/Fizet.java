package Parbeszed;

import Fullbaro.flbr;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import static javax.swing.BorderFactory.createEmptyBorder;
import szakdoga.Keret;
import szakdoga.PanelEast;
import szakdoga.PanelWest;
import szakdoga.Szakdoga;


public class Fizet extends Keret{
    
    JPanel pTarto = new JPanel();
    JPanel pSor1 = new JPanel();
    JPanel pSor2 = new JPanel();
    JPanel pSor3 = new JPanel();
    JPanel pSor4 = new JPanel();
    JPanel pSor5 = new JPanel();
    
    JLabel l1 = new JLabel("Számlaszám: ");
    JLabel l2 = new JLabel("Vendég: ");
    JLabel l3 = new JLabel("Dátum: ");
    JLabel l4 = new JLabel("Összesen: ");
    JLabel l5 = new JLabel("EUR: ");
    JLabel lLogo = new JLabel();
    JLabel lSzamlasz = new JLabel();
    JLabel lVendeg = new JLabel();
    JLabel lDatum = new JLabel();
    JLabel lOssz = new JLabel();
    JLabel lOssz2 = new JLabel();
    
    JScrollPane sc = new JScrollPane(pSor4);
    
    boolean ures=true;
    
    int ossz;
    
    public Fizet(){
        epit();   
        pack();
    }
    
    void epit(){
        lCim.setText("NYUGTA ÖSSZEFOGLALÓ");
        
        //Szakdoga.logo="C:/Users/14-122/Desktop/Képkivágás.png";
        lLogo.setIcon(new ImageIcon(Szakdoga.logo));
        lLogo.setPreferredSize(new Dimension(300,100));
        
        pSor1.setBackground(Color.white);
        pSor1.add(lLogo);
        
        pSor2.setBackground(Color.white);
        pSor2.setLayout(new FlowLayout(FlowLayout.LEFT));
        pSor2.add(l1);
        pSor2.add(lSzamlasz);
        pSor2.add(l3);
        pSor2.add(lDatum);
        
        pSor3.setBackground(Color.white);
        pSor3.setLayout(new FlowLayout(FlowLayout.LEFT));
        pSor3.add(l2);
        pSor3.add(lVendeg);
        
        pSor4.setBackground(Color.white);
        pSor4.setLayout(new BoxLayout(pSor4, BoxLayout.Y_AXIS));
        
        pSor5.setBackground(Color.white);
        pSor5.setLayout(new FlowLayout(FlowLayout.LEFT));
        pSor5.add(l4); 
        pSor5.add(lOssz); flbr.betumeret(lOssz, 20);
        pSor5.add(l5);
        pSor5.add(lOssz2); flbr.betumeret(lOssz2, 20);
        
        pTarto.setBackground(Color.white);
        pTarto.setLayout(new BoxLayout(pTarto, BoxLayout.Y_AXIS));
        pTarto.add(pSor1);
        pTarto.add(pSor2);
        pTarto.add(pSor3);
        pTarto.add(sc);
        pTarto.add(pSor5);
        
        pKozep.setBackground(Color.white);
        pKozep.add(pTarto, BorderLayout.NORTH); sc.setBorder(createEmptyBorder());
    }
    
    void betolt(){
        ossz=0;
        boolean torzs=false;
            flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev like '"+PanelEast.l1.getText()+"'");
            for(String[] t:flbr.sel)
                torzs=true;
        try{
            if(torzs){
                flbr.sel=flbr.lekerdez("select sum(mennyiseg), megnevezes, mertekegyseg, sum(fizetendo) from Rendeles inner join aruk on aruk.id=rendeles.AruID inner join Torzsvendegek on Torzsvendegek.ID=Rendeles.TorzsID where nev like '"+PanelEast.l1.getText()+"' group by  mennyiseg, megnevezes, mertekegyseg, fizetendo");
                lVendeg.setText(PanelEast.l1.getText());
                for(String[] t:flbr.sel){
                    JLabel seged = new JLabel(t[0]+"    "+t[1]+" "+t[2]+"   "+t[3]+" Ft");
                    ossz+=Integer.parseInt(t[3]);
                    pSor4.add(seged);
                }
                flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev like '"+PanelEast.l1.getText()+"'");
                flbr.vegrehajt("delete from rendeles where torzsid like '"+flbr.sel.get(0)[0]+"'");
            }else{
                flbr.sel=flbr.lekerdez("select sum(mennyiseg), megnevezes, mertekegyseg, sum(fizetendo) from Rendeles inner join aruk on aruk.id=rendeles.AruID inner join asztalok on Asztalok.ID= Rendeles.AsztalID  where nev like '"+PanelEast.l1.getText()+"' group by  mennyiseg, megnevezes, mertekegyseg, fizetendo");
                lVendeg.setText(PanelEast.l1.getText());
                for(String[] t:flbr.sel){
                    JLabel seged = new JLabel(t[0]+"    "+t[1]+" "+t[2]+"   "+t[3]+" Ft");
                    ossz+=Integer.parseInt(t[3]);
                    pSor4.add(seged);
                }
                flbr.sel=flbr.lekerdez("select id from asztalok where nev like '"+PanelEast.l1.getText()+"'");
                flbr.vegrehajt("delete from rendeles where asztalid like '"+flbr.sel.get(0)[0]+"'");
            }
            flbr.sel=flbr.lekerdez("select max(szamlaszam), datum from szamlak");
            lSzamlasz.setText(flbr.sel.get(0)[0]);
            lDatum.setText(flbr.sel.get(0)[1]);

            lOssz.setText(ossz+" Ft");
            lOssz2.setText(flbr.kerekit(((double)ossz/Szakdoga.arfolyam), 2)+" €");
            
        }catch(Exception e){new Uzen("Hiba", "Ennél az asztalnál nincs rendelés").run(); ures=false;}   
        pack();
        this.setLocationRelativeTo(null);
    }
    
    public int run(){
        betolt();
        
        if(ures)
            this.setVisible(true);
        else dispose();
        PanelWest.kivalaszt(PanelEast.l1.getText());
        return valasz;
    }
    
}
