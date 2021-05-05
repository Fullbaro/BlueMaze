package Parbeszed.Uj;

import szakdoga.Keret;
import Fullbaro.flbr;
import szakdoga.Keret;
import Parbeszed.Uzen;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import szakdoga.Gomb;
import static szakdoga.PanelWest.frissit;
import static szakdoga.Szakdoga.*;

public class UjAsztal extends Keret{
    
    JPanel pSor1 = new JPanel();
    JPanel pSor2 = new JPanel();
    
    JButton bOk = new Gomb("OK", zoldSzin);
    
    JLabel l1 = new JLabel("Az asztal neve: ");
    JLabel l2 = new JLabel("Férőhely: ");
    
    JTextField tf1 = new JTextField(20);
    JTextField tf2 = new JTextField(10);
    
    public UjAsztal(String cim, String uzenet){
        lCim.setText(cim);
        
        bOk.addMouseListener(new GombFigyel());
        
        pSor1.setBackground(hatterSzin);
        pSor2.setBackground(hatterSzin);
        pSor1.add(l1); pSor1.add(tf1); 
        pSor2.add(l2); pSor2.add(tf2);
        pSor2.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        pKozep.setLayout(new BoxLayout(pKozep, BoxLayout.Y_AXIS));
        pKozep.setBorder(new EmptyBorder(15, 30, 15, 30));
        pKozep.add(pSor1,BorderLayout.CENTER);
        pKozep.add(pSor2, BorderLayout.CENTER);
        pKozep.add(bOk, BorderLayout.SOUTH);
        
        pack();
        this.setLocationRelativeTo(null);
    }
    
    void beker(){
        String hiba="";
        
        String nev = tf1.getText();
        String hely = tf2.getText();
        
        if(nev.length()<1 || hely.length()<1) hiba+="Minden mezőt ki kell tölteni.<br>";
        if(nev.length()>10) hiba+="Az asztal neve túl hosszú.<br>";
        if(!flbr.szamE(hely) && hely.length()>0) hiba+="Férőhelynek nem számot adtál meg.<br>";
        
        flbr.sel=flbr.lekerdez("select nev from asztalok where torolve like 0");
        for(String[] t: flbr.sel){
            if(t[0].toLowerCase().trim().equals(nev.toLowerCase().trim())) hiba+="Ilyen nevű asztal már létezik";
        }

        if(hiba.length()!=0){
            new Uzen("Hiba",hiba).setVisible(true);
        }else{
            flbr.vegrehajt("insert into asztalok(nev, ferohely, torolve) values ('"+nev+"', "+hely+",0)");
            new Uzen("Végrehajtva","Az asztal sikeresen felvége.").setVisible(true);
            frissit();
            dispose();
        }
    }
    
        
    public int run(){
        this.setVisible(true);
        return valasz;
    }
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JButton gomb=(JButton)e.getSource();
            if(gomb==bOk){
                beker();
            }
        
        }  
    }
}
