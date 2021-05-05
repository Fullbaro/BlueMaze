package szakdoga;

import Fullbaro.flbr;
import Parbeszed.Fizet;
import Parbeszed.Atultet;
import Parbeszed.Mennyiseg;
import Parbeszed.Uzen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import jdk.nashorn.internal.parser.TokenType;
import static szakdoga.Szakdoga.*;

public class PanelEast extends JPanel{
    
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int width = (int)screenSize.getWidth();
    int height = (int)screenSize.getHeight();
    
    public static JLabel l1=new JLabel("Asztal");
    
    static JPanel pKozep = new JPanel();
    static JPanel pLent = new JPanel();
    static JPanel pCimnek = new JPanel();
    
    JButton bFizet = new Gomb("Fizetés", zoldSzin);
    JButton bTorol = new Gomb("Töröl",kekSzin);
    JButton bReszlet = new Gomb("Részlet",kekSzin);
    JButton bUltet = new Gomb("Átültet",kekSzin);
    
    JScrollPane scKozep=new JScrollPane(pKozep);
    
    PanelEast(){
        epit();
    }
    
    void epit(){
        
        bUltet.addMouseListener(new GombFigyel());
        
        pLent.setBackground(hatterSzin);
        pLent.setLayout(new BoxLayout(pLent, BoxLayout.X_AXIS));
        pLent.add(bTorol); bTorol.setForeground(Color.white); bTorol.addMouseListener(new GombFigyel());
        pLent.add(Box.createRigidArea(new Dimension(10,10)));
        pLent.add(bUltet); bUltet.setForeground(Color.white);
        pLent.add(Box.createRigidArea(new Dimension(10,10)));
        pLent.add(bReszlet); bReszlet.setForeground(Color.white); bReszlet.addMouseListener(new GombFigyel());
        pLent.add(Box.createHorizontalGlue());
        pLent.add(bFizet); bFizet.setForeground(Color.white); bFizet.addMouseListener(new GombFigyel());
        pLent.setBorder(new EmptyBorder(25, 10, 25, 10));
        
        pKozep.setBackground(hatterSzin);
        pKozep.setLayout(new BoxLayout(pKozep, BoxLayout.Y_AXIS));
        
        pCimnek.setBackground(kekSzin);
        pCimnek.add(l1);
        pCimnek.setBorder(BorderFactory.createLineBorder(kekSzin.brighter(),5)); 
        
        l1.setForeground(Color.white);
        flbr.betumeret(l1, 30);
        l1.setBorder(new EmptyBorder(15, 30, 15, 30));
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        
        this.setLayout(new BorderLayout());
        this.add(pCimnek, BorderLayout.NORTH);
        this.setBackground(hatterSzin);
        this.setPreferredSize(new Dimension((int)(width/3.3), height));
        this.add(scKozep, BorderLayout.CENTER);
        this.add(pLent,BorderLayout.SOUTH);
        
    }
    
    public static void frissit(String s){
        l1.setText(s);
        pKozep.removeAll();
        pKozep.revalidate();
        pKozep.repaint();
        feltolt(s);
    }
    
    public static void feltolt(String asztal){
        flbr.sel=flbr.lekerdez("select sum(mennyiseg), megnevezes, mertekegyseg, sum(fizetendo) from Rendeles inner join aruk on aruk.id=rendeles.AruID inner join asztalok on Asztalok.ID= Rendeles.AsztalID  where nev like '"+asztal+"' group by  mennyiseg, megnevezes, mertekegyseg");
        for(int i=0;i<flbr.sel.size();i++){
            String[] t = flbr.sel.get(i);
            pKozep.add(new Aru(t[0], t[1], t[2], t[3]));
            pKozep.add(Box.createRigidArea(new Dimension(10,10)));
        }
    }
    
    // törzsvendéges
    
    public static void frissit2(String s){
        l1.setText(s);
        pKozep.removeAll();
        pKozep.revalidate();
        pKozep.repaint();
        feltolt2(s);
    }
    
    public static void feltolt2(String torzs){
        flbr.sel=flbr.lekerdez("select sum(mennyiseg), megnevezes, mertekegyseg, sum(fizetendo) from Rendeles inner join aruk on aruk.id=rendeles.AruID inner join Torzsvendegek on Torzsvendegek.ID=Rendeles.TorzsID where nev like '"+torzs+"' group by  mennyiseg, megnevezes, mertekegyseg");
        for(int i=0;i<flbr.sel.size();i++){
            String[] t = flbr.sel.get(i);
            Aru egy = new Aru(t[0], t[1], t[2], t[3]);
            pKozep.add(egy);
            pKozep.add(Box.createRigidArea(new Dimension(10,10)));
        }
    }

    
    void kiir(){
        
        if(!l1.getText().equals("")){
            Date most = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String vendeg=l1.getText();

            boolean torzs=false;
            flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev like '"+vendeg+"'");
            for(String[] t:flbr.sel)
                torzs=true;

            if(torzs){
                flbr.sel=flbr.lekerdez("select sum(mennyiseg), megnevezes, mertekegyseg, sum(fizetendo) from Rendeles inner join aruk on aruk.id=rendeles.AruID inner join Torzsvendegek on Torzsvendegek.ID=Rendeles.TorzsID where nev like '"+vendeg+"' group by  mennyiseg, megnevezes, mertekegyseg");
                for(String[] t:flbr.sel){
                    flbr.vegrehajt("insert into Szamlareszlet(vendeg, mennyiseg, arumegnevezes, fizetendo, mertekegyseg) values('"+vendeg+"', '"+t[0]+"', '"+t[1]+"', '"+t[3]+"', '"+t[2]+"')");
                    flbr.vegrehajt("insert into Szamlak(datum, reszletid) values('"+sdf.format(most)+"' ,(select max(id) from szamlareszlet))");
                }
            }else{
                flbr.sel=flbr.lekerdez("select sum(mennyiseg), megnevezes, mertekegyseg, sum(fizetendo) from Rendeles inner join aruk on aruk.id=rendeles.AruID inner join asztalok on Asztalok.ID= Rendeles.AsztalID  where nev like '"+vendeg+"' group by  mennyiseg, megnevezes, mertekegyseg");
                for(String[] t:flbr.sel){
                    flbr.vegrehajt("insert into Szamlareszlet(vendeg, mennyiseg, arumegnevezes, fizetendo, mertekegyseg) values('"+vendeg+"', '"+t[0]+"', '"+t[1]+"', '"+t[3]+"', '"+t[2]+"')");
                    flbr.vegrehajt("insert into Szamlak(datum, reszletid) values('"+sdf.format(most)+"' ,(select max(id) from szamlareszlet))");
                }
            }
            new Fizet().run();
            PanelEast.frissit2(vendeg);
        }else{
            //hiba
        }
    }
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JButton gomb=(JButton)e.getSource();
            if(gomb==bUltet){
                new Atultet(PanelWest.bKivalasztott.getText()+" átültetése",PanelWest.bKivalasztott.getText()).run();
            }else if(gomb==bFizet){
                kiir();
            }else if(gomb==bTorol){
                if(PanelWest.bKivalasztott.getName().contains("T")){
                    flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev like '"+PanelWest.bKivalasztott.getText()+"'");
                    flbr.vegrehajt("delete from rendeles where torzsid like "+flbr.sel.get(0)[0]);
                    frissit2(PanelWest.bKivalasztott.getText());
                }else{
                    flbr.sel=flbr.lekerdez("select id from asztalok where nev like '"+PanelWest.bKivalasztott.getText()+"'");
                    flbr.vegrehajt("delete from rendeles where asztalid like "+flbr.sel.get(0)[0]);
                    frissit(PanelWest.bKivalasztott.getText());
                }
            }else if(gomb==bReszlet){
                 new Uzen("Figyelmeztetés", "Ez a funkció fejlesztés alatt áll!").run();
            }
        }
    }
    
    
static class Aru extends JPanel{
        
    JLabel lMennyiseg = new JLabel();
    JLabel lNev = new JLabel();
    JLabel lMertek = new JLabel();
    JLabel lAr = new JLabel();
    JLabel lPlusz = new JLabel(" + ");
    JLabel lMinusz = new JLabel(" - ");
    JLabel lFt = new JLabel(" Ft");
    
    Aru(String mennyiseg, String nev, String mertek, String ar){
        this.setBackground(vilagosHatterSzin);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(lMennyiseg); flbr.betumeret(lMennyiseg, 20); 
        lMennyiseg.setBorder(new EmptyBorder(25, 10, 25, 5));
        
        this.add(lPlusz); flbr.betumeret(lPlusz, 20); lPlusz.setBorder(BorderFactory.createLineBorder(zoldSzin,3));  //lPlusz.setBorder(new EmptyBorder(25, 5, 25, 5));
        this.add(lMinusz); flbr.betumeret(lMinusz, 20); lMinusz.setBorder(BorderFactory.createLineBorder(pirosSzin,3));   //lMinusz.setBorder(new EmptyBorder(25, 5, 25, 20)); 
        lPlusz.setOpaque(true); lPlusz.setBackground(zoldSzin);
        lMinusz.setOpaque(true); lMinusz.setBackground(pirosSzin);
        
        this.add(lNev); flbr.betumeret(lNev, 20);  lNev.setBorder(new EmptyBorder(0, 10, 0, 0)); 
        this.add(Box.createRigidArea(new Dimension(10,10)));
        this.add(lMertek); flbr.betumeret(lMertek, 20);
        this.add(Box.createHorizontalGlue());
        this.add(lAr); flbr.betumeret(lAr, 20);
        this.add(lFt); flbr.betumeret(lFt, 20);
        lFt.setBorder(new EmptyBorder(0, 0, 0, 20));
        
        
        lMennyiseg.setText(mennyiseg);
        lNev.setText(nev);
        lMertek.setText(mertek);
        lAr.setText(ar);
        
        lPlusz.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e) {
                novel();
                lMennyiseg.setText((Integer.parseInt(lMennyiseg.getText())+1)+"");
            }  
        }); 
        
        lMinusz.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e) {
                csokkent();
            }  
        }); 
    }
    
    void novel(){
                flbr.sel=flbr.lekerdez("select id from aruk where megnevezes='"+lNev.getText()+"'"); // ha html van bennek akkor elöbb kivenni
                String aruId=flbr.sel.get(0)[0]; 
                flbr.sel=flbr.lekerdez("select ar from aruk where id = "+aruId+"");
                String fizetendo=(Integer.parseInt(lAr.getText())/Integer.parseInt(lMennyiseg.getText()))+""; 
                if(PanelWest.bKivalasztott.getName().contains("T")){// ha törzsvendég
                    flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev='"+PanelWest.bKivalasztott.getText()+"'");
                    String torzsId=flbr.sel.get(0)[0];
                    flbr.vegrehajt("insert into rendeles(aruid, torzsId, fizetendo, mertekegyseg, mennyiseg) values ('"+aruId+"','"+torzsId+"', '"+fizetendo+"', '"+lMertek.getText()+"',1)");
                    PanelEast.frissit2(PanelWest.bKivalasztott.getText()); // törzs
                }else{ // ha nem törzsvendég
                    flbr.sel=flbr.lekerdez("select id from asztalok where nev='"+PanelWest.bKivalasztott.getText()+"'");
                    String asztalId=flbr.sel.get(0)[0];
                    flbr.vegrehajt("insert into rendeles(aruid, asztalid, fizetendo, mertekegyseg, mennyiseg) values ('"+aruId+"','"+asztalId+"', '"+fizetendo+"', '"+lMertek.getText()+"',1)");
                    PanelEast.frissit(PanelWest.bKivalasztott.getText());
                    
                }
    }
    
    void csokkent(){
        flbr.sel=flbr.lekerdez("select id from aruk where megnevezes='"+lNev.getText()+"'"); // ha html van bennek akkor elöbb kivenni
        String aruId=flbr.sel.get(0)[0]; 
        
        if(PanelWest.bKivalasztott.getName().contains("T")){// ha törzsvendég
            flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev='"+PanelWest.bKivalasztott.getText()+"'");
            String torzsId=flbr.sel.get(0)[0];
            
            flbr.vegrehajt("delete from rendeles where rowid = (select rowid from rendeles where aruid = "+aruId+" and torzsid = "+torzsId+" and mertekegyseg like '"+lMertek.getText()+"' limit 1)");
            frissit2(PanelWest.bKivalasztott.getText());
        }else{
            flbr.sel=flbr.lekerdez("select id from asztalok where nev='"+PanelWest.bKivalasztott.getText()+"'");
            String asztalId=flbr.sel.get(0)[0];
            
            flbr.vegrehajt("delete from rendeles where rowid = (select rowid from rendeles where aruid = "+aruId+" and asztalid = "+asztalId+" and mertekegyseg like '"+lMertek.getText()+"' limit 1)");
            frissit(PanelWest.bKivalasztott.getText());
        }
    }
}

    
}
