package Parbeszed.Modosit;

import Fullbaro.flbr;
import szakdoga.Keret;
import Parbeszed.Uzen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import szakdoga.Gomb;
import szakdoga.PanelCenter;
import szakdoga.Szakdoga;
import static szakdoga.Szakdoga.hatterSzin;
import static szakdoga.Szakdoga.zoldSzin;

public class AruModosit extends Keret{
    
    DefaultListModel modell = new DefaultListModel();
    JList lista = new JList(modell);
    JScrollPane sc = new JScrollPane(lista);
    
    JPanel pSor1 = new JPanel();
    JPanel pSor2 = new JPanel();
    JPanel pSor3 = new JPanel();
    JPanel pSor4 = new JPanel();
    JPanel pSor5 = new JPanel();
    JPanel pTarto = new JPanel();
    JPanel pGombnak = new JPanel();
    
    JLabel lId = new JLabel("");
    JLabel lAr = new JLabel("Ár");
    JLabel lBesorol = new JLabel("Besorolás");
    JLabel lEgyseg = new JLabel("Egységár?");
    JLabel lTorolt = new JLabel("Törölve lett?");
    
    JTextField tfNev = new JTextField(23);
    JTextField tfAr = new JTextField(25);
    
    JComboBox kombo = new JComboBox();
    JScrollPane sk = new JScrollPane(kombo); 
    
    JRadioButton rb1 = new JRadioButton("Igen");
    JRadioButton rb2 = new JRadioButton("Nem");
    
    ButtonGroup bgr = new ButtonGroup();
    
    JRadioButton rb3 = new JRadioButton("Igen");
    JRadioButton rb4 = new JRadioButton("Nem");
    
    ButtonGroup bgr2 = new ButtonGroup();
    
    JButton bOk = new Gomb("OK",zoldSzin);
    
    public AruModosit(){
        lCim.setText("Áru modosítása");
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.addListSelectionListener(new ListaFigyel());
        
        feltolt();
        
        rb3.setBackground(hatterSzin);
        rb4.setBackground(hatterSzin);
        bgr2.add(rb3);
        bgr2.add(rb4);
        pSor5.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSor5.setBackground(hatterSzin);
        pSor5.add(lTorolt);
        pSor5.add(rb3);
        pSor5.add(rb4);
        
        rb1.setBackground(hatterSzin);
        rb2.setBackground(hatterSzin);
        bgr.add(rb1);
        bgr.add(rb2);
        pSor4.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSor4.setBackground(hatterSzin);
        pSor4.add(lEgyseg);
        pSor4.add(rb1);
        pSor4.add(rb2);
        
        pSor3.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSor3.setBackground(hatterSzin);
        pSor3.add(lBesorol);
        pSor3.add(kombo);
        
        pSor2.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSor2.setBackground(hatterSzin);
        pSor2.add(lAr);
        pSor2.add(tfAr);
        
        pSor1.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSor1.setBackground(hatterSzin);
        pSor1.add(lId);
        pSor1.add(tfNev);
        
        bOk.setForeground(Color.white);
        pGombnak.add(bOk);
        pGombnak.setBackground(hatterSzin);
        bOk.addMouseListener(new GombFigyel());
        
        pTarto.add(pSor1);
        pTarto.add(pSor2);
        pTarto.add(pSor3);
        pTarto.add(pSor4);
        pTarto.add(pSor5);
        pTarto.add(pGombnak);
        pTarto.setBackground(hatterSzin);
        pTarto.setLayout(new GridLayout(6,2,10,10));
        
        pKozep.add(sc, BorderLayout.WEST);
        pKozep.add(pTarto, BorderLayout.CENTER);
        
        pack();
        this.setLocationRelativeTo(null);
    }
    
    void feltolt(){ // select megnevezes, ar, besorolasid, egyseges, torolve from aruk order by megnevezes
        flbr.sel=flbr.lekerdez("select megnevezes from aruk order by megnevezes");
        for (String[] t:flbr.sel)
            modell.addElement(t[0]);
        lista.setSelectedIndex(0);

        feltolt2();
    }
    
    void feltolt2(){
        kombo.removeAllItems();
        flbr.sel=flbr.lekerdez("select besorolasid from aruk where megnevezes like '"+lista.getSelectedValue()+"'");
        flbr.sel=flbr.lekerdez("select megnevezes from besorolasok where id ="+flbr.sel.get(0)[0]);
        kombo.addItem(flbr.sel.get(0)[0]);
        flbr.sel=flbr.lekerdez("select megnevezes from besorolasok where megnevezes not like '"+flbr.sel.get(0)[0]+"' order by megnevezes");
        for (String[] t:flbr.sel)
            kombo.addItem(t[0]);
        
        
        flbr.sel=flbr.lekerdez("select id, megnevezes, ar, besorolasid, egyseges, torolve from aruk where megnevezes like '"+lista.getSelectedValue()+"'");
        lId.setText(flbr.sel.get(0)[0]+".");
        tfNev.setText(flbr.sel.get(0)[1]);
        tfAr.setText(flbr.sel.get(0)[2]);
        if(flbr.sel.get(0)[4].equals("1"))
            rb1.setSelected(true);
        else
            rb2.setSelected(true);
        
        if(flbr.sel.get(0)[5].equals("1"))
            rb3.setSelected(true);
        else
            rb4.setSelected(true);
    }
    
    void kiir(){
        String megnevezes = tfNev.getText();
        String ar = tfAr.getText();
        String besorolas = (String)kombo.getSelectedItem();
        String id=lId.getText();
        int egysegar=-1;
        if(rb1.isSelected()) egysegar = 1;
        else if(rb2.isSelected()) egysegar = 0;
        int torolt=-1;
        if(rb3.isSelected()) torolt = 1;
        else if(rb4.isSelected()) torolt = 0;
        
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
            flbr.vegrehajt("update aruk set megnevezes = '"+megnevezes+"', ar = '"+ar+"', besorolasid = (select id from Besorolasok where megnevezes like '"+besorolas+"'), egyseges = "+egysegar+", torolve = "+torolt+" where id ="+id);
            new Uzen("Végrehajtva","A termék sikeresen módosítva").setVisible(true);
            // frissit
            dispose();
        }
    }
    
    class ListaFigyel implements ListSelectionListener{
        public void valueChanged(ListSelectionEvent e){
            feltolt2();
        }
    }
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JButton gomb=(JButton)e.getSource();
            if(gomb==bOk){
                kiir();
            }
        
        }  
    }
    
}
