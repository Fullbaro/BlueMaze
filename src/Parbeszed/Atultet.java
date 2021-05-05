package Parbeszed;

import Fullbaro.flbr;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.*;
import szakdoga.Gomb;
import szakdoga.Keret;
import szakdoga.PanelWest;
import szakdoga.Szakdoga;
import static szakdoga.Szakdoga.kekSzin;
import static szakdoga.Szakdoga.pirosSzin;
import static szakdoga.Szakdoga.sargaSzin;

public class Atultet extends Keret{
    
    String vissza, asztalErrol;
    
    JPanel pGombok = new JPanel();
    
    JScrollPane sc = new JScrollPane(pGombok);
    
    Gomb aktualis;
    
    Vector<Gomb> gombok = new Vector();
    
    public Atultet(String cim, String asztal){ 
        lCim.setText(cim);
        
        this.asztalErrol=asztal;
        
        pGombok.setLayout(new GridLayout(0,2,2,2));
        pGombok.setBackground(Szakdoga.hatterSzin);
        
        pKozep.add(sc, BorderLayout.CENTER);
        
        feltolt();
        szinez();
        this.setPreferredSize(new Dimension(400,800));
        pack();
        this.setLocationRelativeTo(null);
    }
    
    public String run(){
        this.setVisible(true);
        return vissza;
    }
    
    void feltolt(){
        gombok.clear();
        int index=0;
        flbr.sel=flbr.lekerdez("Select nev from asztalok where torolve like 0 order by nev");
        for(String[] t:flbr.sel){
            aktualis = new Gomb(t[0],kekSzin);
            aktualis.setForeground(Color.white);
            aktualis.addMouseListener(new GombFigyel());
            pGombok.add(aktualis);
            aktualis.setName(""+index); index++;
            gombok.add(aktualis);
        }
        
        flbr.sel=flbr.lekerdez("Select nev from torzsvendegek  where torolve like 0 order by nev");
        for(String[] t:flbr.sel){
            aktualis = new Gomb(t[0],kekSzin);
            aktualis.setForeground(Color.white);
            aktualis.addMouseListener(new GombFigyel());
            aktualis.setName("T"+index); index++;
            pGombok.add(aktualis);
            gombok.add(aktualis);
        }
        
    }
    
    void szinez(){
        for(JButton b:gombok){
            flbr.sel=flbr.lekerdez("select mennyiseg, megnevezes, ar from Rendeles inner join aruk on aruk.id=rendeles.AruID inner join asztalok on Asztalok.ID= Rendeles.AsztalID where nev like '"+b.getText()+"'");
            if(flbr.sel.size()>0)
                b.setBackground(pirosSzin);
        }
    }
    
    void ultet(){
        boolean torzsRol=false;
        flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev like '"+asztalErrol+"'");
        for(String[] t:flbr.sel)
            torzsRol=true;
        
        boolean torzsRe=false;
        flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev like '"+vissza+"'");
        for(String[] t:flbr.sel)
            torzsRe=true;
        
        if(torzsRe){
            if(torzsRol){// torzsről törzsre
                flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev like '"+asztalErrol+"'");
                String idEzt = flbr.sel.get(0)[0];
                flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev like '"+vissza+"'");
                String idErre= flbr.sel.get(0)[0];
                flbr.sel=flbr.lekerdez("select * from rendeles where torzsid="+idEzt);
                //for(String[] t:flbr.sel) System.out.println(t[0]+" "+t[1]+" "+t[2]+" "+t[3]+" "+t[4]+" "+t[5]);
                flbr.vegrehajt("update rendeles set torzsid = '"+idErre+"' where torzsid = '"+idEzt+"'");
            }else{// asztalról törzsre
                flbr.sel=flbr.lekerdez("select id from asztalok where nev like '"+asztalErrol+"'");
                String idEzt = flbr.sel.get(0)[0];
                flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev like '"+vissza+"'");
                String idErre= flbr.sel.get(0)[0];
                flbr.sel=flbr.lekerdez("select * from rendeles where asztalid="+idEzt);
                //for(String[] t:flbr.sel) System.out.println(t[0]+" "+t[1]+" "+t[2]+" "+t[3]+" "+t[4]+" "+t[5]);
                flbr.vegrehajt("update rendeles set torzsid = '"+idErre+"', asztalid = null where asztalid = '"+idEzt+"'");
            }
        }else{
            if(torzsRol){ // törzsről asztalra
                flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev like '"+asztalErrol+"'");
                String idEzt = flbr.sel.get(0)[0];
                flbr.sel=flbr.lekerdez("select id from asztalok where nev like '"+vissza+"'");
                String idErre= flbr.sel.get(0)[0];
                flbr.sel=flbr.lekerdez("select * from rendeles where torzsid="+idEzt);
                //for(String[] t:flbr.sel) System.out.println(t[0]+" "+t[1]+" "+t[2]+" "+t[3]+" "+t[4]+" "+t[5]);
                flbr.vegrehajt("update rendeles set asztalid = '"+idErre+"', torzsid= null where torzsid = '"+idEzt+"'");
            }else{// asztalról asztalra
                flbr.sel=flbr.lekerdez("select id from asztalok where nev like '"+asztalErrol+"'");
                String idEzt = flbr.sel.get(0)[0];
                flbr.sel=flbr.lekerdez("select id from asztalok where nev like '"+vissza+"'");
                String idErre= flbr.sel.get(0)[0];
                flbr.sel=flbr.lekerdez("select * from rendeles where asztalid="+idEzt);
                //for(String[] t:flbr.sel) System.out.println(t[0]+" "+t[1]+" "+t[2]+" "+t[3]+" "+t[4]+" "+t[5]);
                flbr.vegrehajt("update rendeles set asztalid = '"+idErre+"' where asztalid = '"+idEzt+"'");
            }
        }
    }
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            Gomb gomb=(Gomb)e.getSource();
            vissza=gomb.getText();
            ultet();
            PanelWest.kivalaszt(gomb.getText()); // nem jókor hívom szerintem
            dispose();
        }
    }
    
}

/*


flbr.sel=flbr.lekerdez("select id from asztalok where nev like '"+asztalErrol+"'");
            String idEzt = flbr.sel.get(0)[0];
            flbr.sel=flbr.lekerdez("select * from rendeles ");// where

*/