package szakdoga;

import Parbeszed.Modosit.TorzsModosit;
import Parbeszed.Torol.Torol;
import Parbeszed.Modosit.AruModosit;
import Parbeszed.Modosit.AsztalModosit;
import Parbeszed.Modosit.BesorolasModosit;
import Parbeszed.Uj.UjBesorolas;
import Parbeszed.Uj.UjAsztal;
import Parbeszed.Uj.UjAru;
import Fullbaro.flbr;
import Parbeszed.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public final class Ablak extends JFrame{
    
    Container cp=this.getContentPane();
    
    JPanel pHatter = new JPanel();
    
    JButton bKilep = new Gomb("Kilépés", Szakdoga.pirosSzin);
    
    JMenuBar menuSor=new JMenuBar();
    
    JMenuItem mniAl1=new JMenuItem("Áru");
    JMenuItem mniAl2=new JMenuItem("Asztal");
    JMenuItem mniAl3=new JMenuItem("Besorolas");
    JMenuItem mniAl4=new JMenuItem("Törzsvendég");
    JMenuItem mniAl5=new JMenuItem("Áru");
    JMenuItem mniAl6=new JMenuItem("Asztal");
    JMenuItem mniAl7=new JMenuItem("Besorolas");
    JMenuItem mniAl8=new JMenuItem("Törzsvendég");
    JMenuItem mniAl9=new JMenuItem("Áru");
    JMenuItem mniAl10=new JMenuItem("Asztal");
    JMenuItem mniAl11=new JMenuItem("Besorolas");
    JMenuItem mniAl12=new JMenuItem("Törzsvendég");
    
    JMenu mn1=new JMenu("Új");
    JMenu mn2=new JMenu("Beállítások");
    JMenu mn3=new JMenu("Törlés");
    JMenu mn4=new JMenu("Módosítás");
    JMenu mn5=new JMenu("Ültet");
    
    Font menuk = new Font("TimesRoman", Font.PLAIN, 15);
    Font alMenuk = new Font("TimesRoman", Font.BOLD, 12);
    
    EmptyBorder padding = new EmptyBorder(15, 30, 15, 30);
    
    Ablak(){
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setUndecorated(true);
        
        epit();
    }
    
    void epit(){
        this.setJMenuBar(menuSor);
        
        menuSor.add(Box.createRigidArea(new Dimension(20,20)));
        menuSor.add(mn1); mn1.addMouseListener(new MenuFigyel());
        menuSor.add(mn3); mn3.addMouseListener(new MenuFigyel());
        menuSor.add(mn4); mn4.addMouseListener(new MenuFigyel());
        menuSor.add(mn2); mn2.addMouseListener(new MenuFigyel());
        menuSor.add(mn5); mn5.addMouseListener(new MenuFigyel());
        menuSor.add(Box.createHorizontalGlue());
        menuSor.add(bKilep);
        
        mn1.add(mniAl1); mniAl1.addActionListener(new AlmenuFigyel()); 
        mn1.add(mniAl2); mniAl2.addActionListener(new AlmenuFigyel()); 
        mn1.add(mniAl3); mniAl3.addActionListener(new AlmenuFigyel()); 
        mn1.add(mniAl4); mniAl4.addActionListener(new AlmenuFigyel()); 
        
        mn3.add(mniAl5); mniAl5.addActionListener(new AlmenuFigyel()); 
        mn3.add(mniAl6); mniAl6.addActionListener(new AlmenuFigyel()); 
        mn3.add(mniAl7); mniAl7.addActionListener(new AlmenuFigyel()); 
        mn3.add(mniAl8); mniAl8.addActionListener(new AlmenuFigyel()); 
        
        mn4.add(mniAl9); mniAl9.addActionListener(new AlmenuFigyel()); 
        mn4.add(mniAl10); mniAl10.addActionListener(new AlmenuFigyel()); 
        mn4.add(mniAl11); mniAl11.addActionListener(new AlmenuFigyel()); 
        mn4.add(mniAl12); mniAl12.addActionListener(new AlmenuFigyel()); 
        
        mn1.setFont(menuk); mn1.setBorder(padding);
        mn2.setFont(menuk); mn2.setBorder(padding);
        mn3.setFont(menuk); mn3.setBorder(padding);
        mn4.setFont(menuk); mn4.setBorder(padding);
        mn5.setFont(menuk); mn5.setBorder(padding);
        
        mniAl1.setFont(alMenuk); mniAl1.setBorder(padding);
        mniAl2.setFont(alMenuk); mniAl2.setBorder(padding);
        mniAl3.setFont(alMenuk); mniAl3.setBorder(padding);
        mniAl4.setFont(alMenuk); mniAl4.setBorder(padding);
        mniAl5.setFont(alMenuk); mniAl5.setBorder(padding);
        mniAl6.setFont(alMenuk); mniAl6.setBorder(padding);
        mniAl7.setFont(alMenuk); mniAl7.setBorder(padding);
        mniAl8.setFont(alMenuk); mniAl8.setBorder(padding);
        mniAl9.setFont(alMenuk); mniAl9.setBorder(padding);
        mniAl10.setFont(alMenuk); mniAl10.setBorder(padding);
        mniAl11.setFont(alMenuk); mniAl11.setBorder(padding);
        mniAl12.setFont(alMenuk); mniAl12.setBorder(padding);
        
        bKilep.addMouseListener(new GombFigyel());
        bKilep.setForeground(Color.white);
        
        pHatter.setLayout(new BorderLayout());
        
        cp.setLayout(new BorderLayout());
        cp.add(pHatter, BorderLayout.CENTER);
        pHatter.add(new PanelWest(),BorderLayout.WEST);
        pHatter.add(new PanelCenter(), BorderLayout.CENTER);
        pHatter.add(new PanelEast(), BorderLayout.EAST);
    }
    
    void ujBesorolas(){
        try{
            boolean nincs=true;
            String[] adat;
            while(true){
                adat=new UjBesorolas("Adj meg új besorolást").run();
                if(adat[0].trim().length()<1)
                    new Uzen("Hiba","Nem adtál meg semmit").run();
                else
                    break;
            }
            if(!adat[0].equals("null")){

                flbr.sel=flbr.lekerdez("select megnevezes from besorolasok where torolve like 0");
                for(String[] t: flbr.sel){
                    if(t[0].toLowerCase().trim().equals(adat[0].toLowerCase().trim())){
                        new Uzen("Hiba","Ilyen besorolás már létezik").run();
                        nincs=false;
                        break;
                    }
                }
                if(nincs){
                    int id=-1;
                    if(adat[1].equals("Étel")) id=1;
                    else if(adat[1].equals("Ital")) id=2;
                    else if(adat[1].equals("Egyéb")) id=3;
                    flbr.vegrehajt("insert into besorolasok(megnevezes, tipusid,torolve) values('"+adat[0]+"',"+id+",0)");
                    PanelCenter.frissit();
                }
            }
        }catch(Exception e){System.out.println("Kilépett az ablakból");}
    }
    
    void ujTorzsvendeg(){
        boolean nincs=true;
        String adat="";
        while(true){
            adat=new Beker("Új számla nyitása","Add meg a számlatulajdonos nevét!").run();
            if(adat.trim().length()<1)
                    new Uzen("Hiba","Nem adtál meg semmit").run();
                else
                    break;
        }
        if(!adat.equals("null")){
            flbr.sel=flbr.lekerdez("select nev from torzsvendegek where torolve like 0");
                for(String[] t: flbr.sel){
                    if(t[0].toLowerCase().trim().equals(adat.toLowerCase().trim())){
                        new Uzen("Hiba","Neki már van nyitott számlája!").run();
                        nincs=false;
                        break;
                    }
                }
                if(nincs){
                    flbr.vegrehajt("insert into torzsvendegek(nev, szamlaegyenleg, torolve) values ('"+adat+"',0,0)");
                    new Uzen("Végrehajtva","A törzsvendég felvége.").run();
                    PanelWest.frissit();
                }
        }
    }
    
    class MenuFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JMenu menu=(JMenu)e.getSource();
            if(menu==mn2){
                new Beallitasok("Beállítások").run();
            }else if(menu==mn5){
                new Ultet();
            }
        }
        
    }
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JButton gomb=(JButton)e.getSource();
            if(gomb==bKilep){
                Kerdez uzi = new Kerdez("Biztonsági kérdés","Biztos hogy kilépsz?");
                if(uzi.run()==1){
                    dispose();
                    if(Szakdoga.leallitas.equals("1"))
                        flbr.cmd("shutdown /s /t 0");
                }
            }
        }
    }
    
    class AlmenuFigyel implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JMenuItem alm=(JMenuItem)e.getSource();
            if(alm==mniAl1) {
                new UjAru("Új termék felvétele").run();
                PanelCenter.frissit();
            }else if(alm==mniAl2) {
                new UjAsztal("Új asztal felvétele","").run();
            }else if(alm==mniAl3) {
                ujBesorolas();
            }else if(alm==mniAl4) {
                ujTorzsvendeg();
            }else if(alm==mniAl5) {
                new Torol("Válaszd ki a törölni kívánt árut!","select megnevezes from aruk where torolve like 0 order by megnevezes","update aruk set torolve = 1 where megnevezes like '").run();
                PanelCenter.frissit();
                PanelWest.frissit();
            }else if(alm==mniAl6) {
               new Torol("Válaszd ki a törölni kívánt asztalt!","select nev from asztalok where torolve like 0 order by nev","update asztalok set torolve = 1 where nev like '").run();
               PanelWest.frissit();
            }else if(alm==mniAl7) {
                new Torol("Válaszd ki a törölni kívánt besorolást!","select megnevezes from besorolasok where torolve like 0 order by megnevezes","update besorolasok set torolve = 1 where megnevezes like '").run();
                PanelCenter.frissit();
            }else if(alm==mniAl8) {
                new Torol("Válaszd ki a törölni kívánt törzsvendéget!","select nev from torzsvendegek where torolve like 0 order by nev","update torzsvendegek set torolve = 1 where nev like '").run();
                PanelCenter.frissit();
                PanelWest.frissit();
            }else if(alm==mniAl9) {
                new AruModosit().setVisible(true);
            }else if(alm==mniAl10) {
                new AsztalModosit().setVisible(true);
            }else if(alm==mniAl11) {
                new BesorolasModosit().setVisible(true);
            }else if(alm==mniAl12) {
                new TorzsModosit().setVisible(true);
            }
        }
    }
    
}
