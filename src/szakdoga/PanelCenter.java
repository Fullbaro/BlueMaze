package szakdoga;

import Fullbaro.flbr;
import Parbeszed.Mennyiseg;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import static szakdoga.Szakdoga.hatterSzin;
import static szakdoga.Szakdoga.zoldSzin;
import static szakdoga.Szakdoga.kekSzin;
import static szakdoga.Szakdoga.sargaSzin;
import static szakdoga.Szakdoga.vilagosHatterSzin;

public class PanelCenter extends JPanel{
    
    JPanel pFent = new JPanel();
    JPanel pLent = new JPanel();
    JPanel pLentCimsor = new JPanel();
    JPanel pFentCim = new JPanel();
    public static JPanel pEtel = new JPanel();
    public static JPanel pItal = new JPanel();
    public static JPanel pEgyeb = new JPanel();
    static JPanel pRetegek = new JPanel();
    static JPanel pAruk = new JPanel();
    
    
    static JButton bItal = new Gomb("Italok",zoldSzin);
    static JButton bEtel = new Gomb("Ételek",zoldSzin);
    static JButton bEgyeb = new Gomb("Egyebek",zoldSzin);
    static JButton seged;
    static JButton bKivalasztott;
    
    JLabel lFentCim = new JLabel("A termékek");
    
    JScrollPane scItal=new JScrollPane(pItal);
    JScrollPane scEtel=new JScrollPane(pEtel);
    JScrollPane scEgyeb=new JScrollPane(pEgyeb);
    JScrollPane scAruk=new JScrollPane(pAruk);
    
    static CardLayout retegek = new CardLayout(); 
    
    static Vector<JButton> gombok = new Vector();
    static Vector<JLabel> aruk = new Vector();
    
    static int index=0;
    
    PanelCenter(){
        epit();
        epitFent();
        epitLent();
        feltolt();
        gombok.get(0).setBackground(sargaSzin);
        bKivalasztott=gombok.get(0);
        termekFeltolt(bKivalasztott.getText());
    }
    
    void epit(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(pFent);
        this.add(pLent); 
    }
    
    void epitFent(){
        scAruk.getVerticalScrollBar().setUnitIncrement(30);
        
        pFentCim.setLayout(new FlowLayout(FlowLayout.LEFT));
        pFentCim.add(lFentCim);
        pFentCim.setBackground(hatterSzin);
        
        pFent.setLayout(new BorderLayout());
        pFent.add(pFentCim, BorderLayout.NORTH);
        pFent.setBackground(hatterSzin);
        
        scAruk.setPreferredSize(new Dimension(0, 0));
        pAruk.setLayout(new GridLayout(0, 2, 2, 2));
        pAruk.setBackground(hatterSzin);
        pFent.add(scAruk);
    }
    
    void epitLent(){
        //pLent.setBackground(Color.blue);
        
        bItal.addMouseListener(new GombFigyel());
        bItal.setForeground(Color.white);
        bEtel.addMouseListener(new GombFigyel());
        bEtel.setForeground(Color.white);
        bEgyeb.addMouseListener(new GombFigyel());
        bEgyeb.setForeground(Color.white);
        
        pLentCimsor.setLayout(new FlowLayout(FlowLayout.LEFT));
        pLentCimsor.add(bItal);
        pLentCimsor.add(bEtel);
        pLentCimsor.add(bEgyeb);
        pLentCimsor.setBackground(hatterSzin);
        
        pLent.setLayout(new BorderLayout());
        pLent.add(pLentCimsor,BorderLayout.NORTH);
        
        pRetegek.setLayout(retegek); 
        
        
        scItal.setPreferredSize(new Dimension(0, 0));
        scEtel.setPreferredSize(new Dimension(0, 0));
        scEgyeb.setPreferredSize(new Dimension(0, 0));
        pLent.add(pRetegek, BorderLayout.CENTER);
        
        pItal.setLayout(new GridLayout(0,4,4,4));
        pItal.setBackground(hatterSzin);
        pEtel.setLayout(new GridLayout(0,4,4,4));
        pEtel.setBackground(hatterSzin);
        pEgyeb.setLayout(new GridLayout(0,4,4,4));
        pEgyeb.setBackground(hatterSzin);
        
        
        pRetegek.add(scItal,"ital");
        pRetegek.add(scEtel,"etel");
        pRetegek.add(scEgyeb,"egyeb");
        
        retegek.show(pRetegek, "etel");
        
        
    }
    
    public static void feltolt(){
        gombok.clear();
        flbr.sel=flbr.lekerdez("select distinct megnevezes from besorolasok where tipusid=1 and torolve like 0");
        for(String[] t: flbr.sel){
            seged = new Gomb("", kekSzin);
            seged.setForeground(Color.white);
            seged.addMouseListener(new GombFigyel());
            seged.setText(t[0]);
            pEtel.add(seged);
            gombok.add(seged);
            seged.setName(""+index);
            index++;
        }
        flbr.sel=flbr.lekerdez("select distinct megnevezes from besorolasok where tipusid=2 and torolve like 0");
        for(String[] t: flbr.sel){
            seged = new Gomb("", kekSzin);
            seged.setForeground(Color.white);
            seged.addMouseListener(new GombFigyel());
            seged.setText(t[0]);
            pItal.add(seged);
            gombok.add(seged);
            seged.setName(""+index);
            index++;
        }
        flbr.sel=flbr.lekerdez("select distinct megnevezes from besorolasok where tipusid=3 and torolve like 0");
        for(String[] t: flbr.sel){
            seged = new Gomb("", kekSzin);
            seged.setForeground(Color.white);
            seged.addMouseListener(new GombFigyel());
            seged.setText(t[0]);
            pEgyeb.add(seged);
            gombok.add(seged);
            seged.setName(""+index);
            index++;
        }
        index=0;
        
        
    }
    
    static void termekFeltolt(String besorolas){
        aruk.clear();
        int index=0;
        pAruk.removeAll();
        pAruk.revalidate();
        pAruk.repaint();
        flbr.sel=flbr.lekerdez("select aruk.megnevezes from aruk inner join besorolasok on besorolasok.id=aruk.besorolasid where Besorolasok.Megnevezes like '"+besorolas+"' and aruk.torolve like 0");
        for(String[] t: flbr.sel){
            JLabel aktualis = new JLabel(t[0],SwingConstants.CENTER);
            flbr.betumeret(aktualis, 18);
            aktualis.setOpaque(true);
            aktualis.setBackground(vilagosHatterSzin);
            aktualis.setBorder(new EmptyBorder(15,15,15,15));
            aktualis.addMouseListener(new LabelFigyel());
            pAruk.add(aktualis);
            aruk.add(aktualis);
            aktualis.setName(""+index);
            index++;
        }
    }
    
    public static void frissit(){
        pItal.removeAll(); pEtel.removeAll(); pEgyeb.removeAll();
        pItal.revalidate(); pEtel.revalidate(); pEgyeb.revalidate();
        pItal.repaint(); pEtel.repaint(); pEgyeb.repaint();
        feltolt();
        gombok.get(0).setBackground(sargaSzin);
        bKivalasztott=gombok.get(0);
        termekFeltolt(bKivalasztott.getText());
    }
    
    static void szinTorol(){
        for(JButton b:gombok)
            if(b.getBackground()==sargaSzin)
                b.setBackground(kekSzin);
    }
    
    static class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JButton gomb=(JButton)e.getSource();
            if(gomb==bItal){
                retegek.show(pRetegek, "ital");
            }else if(gomb==bEtel){
                retegek.show(pRetegek, "etel");
            }else if(gomb==bEgyeb){
                retegek.show(pRetegek, "egyeb");
            }else{
                szinTorol();
                int hely = Integer.parseInt(gomb.getName());
                gombok.get(hely).setBackground(sargaSzin);
                termekFeltolt(gombok.get(hely).getText());
                bKivalasztott=gombok.get(hely);
            }
        }
    }
    
    static class LabelFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JLabel label = (JLabel)e.getSource();
            boolean torzsvendeg=true;
            boolean megse=true;
            String fizetendo="";
            String[] bekert = new String[2]; bekert[1]="";
            if(!PanelWest.bKivalasztott.getName().contains("T")) torzsvendeg=false;  // ha törzsvendég
                flbr.sel=flbr.lekerdez("select egyseges from Aruk where megnevezes like '"+label.getText()+"'");
                if(flbr.sel.get(0)[0].equals("1")){ // egyeges a termek
                    flbr.sel=flbr.lekerdez("select ar from aruk where megnevezes like '"+label.getText()+"'");
                    bekert= new Mennyiseg("", flbr.sel.get(0)[0]).run();
                    if(bekert[0]==null)
                        megse=false;
                    else
                        fizetendo=bekert[0];   
                }else{
                    flbr.sel=flbr.lekerdez("select ar from aruk where megnevezes like '"+label.getText()+"'");
                    fizetendo=flbr.sel.get(0)[0];
                }
                if(megse){ // ha nem léptek ki a bekérőablakból
                    flbr.sel=flbr.lekerdez("select id from aruk where megnevezes='"+label.getText()+"'"); // ha html van bennek akkor elöbb kivenni
                    String aruId=flbr.sel.get(0)[0]; 
                    if(torzsvendeg){// ha törzsvendég
                        flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev='"+PanelWest.bKivalasztott.getText()+"'");
                        String torzsId=flbr.sel.get(0)[0];
                        flbr.vegrehajt("insert into rendeles(aruid, torzsId, fizetendo, mertekegyseg, mennyiseg) values ('"+aruId+"','"+torzsId+"', '"+fizetendo+"', '"+bekert[1]+"',1)");
                        PanelEast.frissit2(PanelWest.bKivalasztott.getText()); // törzs
                    }else{ // ha nem törzsvendég
                        flbr.sel=flbr.lekerdez("select id from asztalok where nev='"+PanelWest.bKivalasztott.getText()+"'");
                        String asztalId=flbr.sel.get(0)[0];
                        flbr.vegrehajt("insert into rendeles(aruid, asztalid, fizetendo, mertekegyseg, mennyiseg) values ('"+aruId+"','"+asztalId+"', '"+fizetendo+"', '"+bekert[1]+"',1)");
                        PanelEast.frissit(PanelWest.bKivalasztott.getText());
                    }
                }
        }
        
        public void mousePressed(MouseEvent e) {
            JLabel label = (JLabel)e.getSource();
            label.setBackground(vilagosHatterSzin.darker());
        }
        
        public void mouseReleased(MouseEvent e) {
            JLabel label = (JLabel)e.getSource();
            label.setBackground(vilagosHatterSzin);
        }
    }
}



/*

                        flbr.sel=flbr.lekerdez("select id, aruid, asztalid, torzsid, mennyiseg, mertekegyseg from rendeles where aruid like '"+aruId+"' and torzsid like '"+torzsId+"' and mertekegyseg like '"+bekert[1]+"'");
                        if(flbr.sel.size()>0){
                            flbr.vegrehajt("UPDATE Rendeles SET mennyiseg=((select mennyiseg from Rendeles where id="+flbr.sel.get(0)[0]+")+1), fizetendo=fizetendo+"+fizetendo+" WHERE id="+flbr.sel.get(0)[0]);
                        }else





flbr.sel=flbr.lekerdez("select id, aruid, asztalid, torzsid, mennyiseg, mertekegyseg from rendeles where aruid like '"+aruId+"' and asztalid like '"+asztalId+"' and mertekegyseg like '"+bekert[1]+"'");
                        if(flbr.sel.size()>0){
                            flbr.vegrehajt("UPDATE Rendeles SET mennyiseg=((select mennyiseg from Rendeles where id="+flbr.sel.get(0)[0]+")+1), fizetendo=fizetendo+"+fizetendo+" WHERE id="+flbr.sel.get(0)[0]);
                        }else


*/