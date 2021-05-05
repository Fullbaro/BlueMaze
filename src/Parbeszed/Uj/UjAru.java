package Parbeszed.Uj;

import szakdoga.Keret;
import Fullbaro.flbr;
import szakdoga.Keret;
import Parbeszed.Uzen;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import szakdoga.Gomb;
import szakdoga.PanelCenter;
import static szakdoga.PanelCenter.*;
import static szakdoga.Szakdoga.*;

public class UjAru extends Keret{
    
    JPanel pSor1 = new JPanel();
    JPanel pSor2 = new JPanel();
    JPanel pSor3 = new JPanel();
    JPanel pSor4 = new JPanel();
    JPanel pLent = new JPanel();
    
    JButton bOk = new Gomb("OK", zoldSzin);
    JButton bUj = new Gomb("Új", kekSzin);
    
    JLabel l1 = new JLabel("Megnevezés: ");
    JLabel l2 = new JLabel("Ár: ");
    JLabel l3 = new JLabel("Besorolás: ");
    JLabel l4 = new JLabel("Egységár? ");
    
    JTextField tf1 = new JTextField(20);
    JTextField tf2 = new JTextField(10);
    
    Vector<String> besorolasok = new Vector();
    
    JRadioButton rb1 = new JRadioButton("Igen");
    JRadioButton rb2 = new JRadioButton("Nem");
    
    ButtonGroup bgr = new ButtonGroup();
    
    JComboBox kombo;
    
    public UjAru(String cim){
        lCim.setText("Új áru felvétele");
        
        bOk.setForeground(Color.white);
        bOk.addMouseListener(new GombFigyel());
        bUj.setForeground(Color.white);
        bUj.addMouseListener(new GombFigyel());
        
        feltolt();
        kombo = new JComboBox(besorolasok);
        JScrollPane sk = new JScrollPane(kombo); 
        
        rb1.setBackground(hatterSzin);
        rb2.setBackground(hatterSzin);
        bgr.add(rb1);
        bgr.add(rb2); 
        rb2.setSelected(true);
        
        pSor1.setBackground(hatterSzin);
        pSor2.setBackground(hatterSzin);
        pSor3.setBackground(hatterSzin);
        pSor4.setBackground(hatterSzin);
        pSor1.add(l1); pSor1.add(tf1); 
        pSor2.add(l2); pSor2.add(tf2);
        pSor3.add(l3); pSor3.add(sk); pSor3.add(bUj);
        pSor4.add(l4); pSor4.add(rb1); pSor4.add(rb2);
        
        pLent.add(bOk);
        pLent.setBackground(hatterSzin);

        pKozep.setLayout(new BoxLayout(pKozep, BoxLayout.Y_AXIS));
        pKozep.setBorder(new EmptyBorder(15, 30, 15, 30));
        pKozep.add(pSor1,BorderLayout.CENTER);
        pKozep.add(pSor2,BorderLayout.CENTER);
        pKozep.add(pSor3,BorderLayout.CENTER);
        pKozep.add(pSor4,BorderLayout.CENTER);
        pHatter.add(pLent,BorderLayout.SOUTH);
        
        pack();
        this.setLocationRelativeTo(null);
    }
    
    void feltolt(){
        flbr.sel=flbr.lekerdez("select megnevezes from besorolasok");
        for(String[] t: flbr.sel)
            besorolasok.add(t[0]);
    }
    
    public int run(){
        this.setVisible(true);
        return valasz;
    }
    
    void beker(){
        String megnevezes = tf1.getText();
        String ar = tf2.getText();
        String besorolas = (String)kombo.getSelectedItem();
        int egysegar=-1;
        if(rb1.isSelected()) egysegar = 1;
        else if(rb2.isSelected()) egysegar = 0;
        
        String hiba="";
        
        if(megnevezes.length()<1 || ar.length()<1) hiba+="Minden mezőt ki kell tölteni.<br>";
        if(!flbr.szamE(ar) && ar.length()>0) hiba+="Árnak nem számot adtál meg.<br>";
        
        flbr.sel=flbr.lekerdez("select megnevezes from aruk where torolve like 0");
        for(String[] t: flbr.sel){
            if(t[0].toLowerCase().trim().equals(megnevezes.toLowerCase().trim())) hiba+="Ilyen nevű termék már létezik";
        }
        
        if(hiba.length()!=0){
            new Uzen("Hiba",hiba).setVisible(true);
        }else{
            flbr.vegrehajt("insert into aruk(megnevezes, ar, besorolasid, egyseges, torolve) values('"+megnevezes+"',"+ar+",(select id from Besorolasok where megnevezes like '"+besorolas+"'),"+egysegar+",0)");
            new Uzen("Végrehajtva","A termék sikeresen felvéve.").setVisible(true);
            // frissit
            dispose();
        }
    }
    
    void uj(){
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

                flbr.sel=flbr.lekerdez("select megnevezes from besorolasok");
                for(String[] t: flbr.sel){
                    if(t[0].toLowerCase().trim().equals(adat[0].toLowerCase().trim())){
                        new Uzen("Hiba","Ilyen besorolás már létezik").run();
                        nincs=false;
                        break;
                    }
                }
                if(nincs){
                    kombo.addItem(adat[0]);
                    int id=-1;
                    if(adat[1].equals("Étel")) id=1;
                    else if(adat[1].equals("Ital")) id=2;
                    else if(adat[1].equals("Egyéb")) id=3;
                    flbr.vegrehajt("insert into besorolasok(megnevezes, tipusid) values('"+adat[0]+"',"+id+")");
                    PanelCenter.frissit();
                }
            }
        }catch(Exception e){System.out.println("Kilépett az ablakból");}
    }
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JButton gomb=(JButton)e.getSource();
            if(gomb==bOk){
                beker();
            }else if(gomb==bUj){
                uj();
            }
        
        }  
    }
    
}
