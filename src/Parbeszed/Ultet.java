package Parbeszed;

import Fullbaro.flbr;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import szakdoga.Gomb;
import szakdoga.Keret;
import szakdoga.PanelWest;
import szakdoga.Szakdoga;
import static szakdoga.Szakdoga.kekSzin;

public class Ultet extends Keret{
    
    JPanel pCimnek = new JPanel();
    JPanel pTarto = new JPanel();
    
    JLabel l1 = new JLabel("Potenciális szabad asztalok");
    
    Gomb aktualis;
    
    JScrollPane sc = new JScrollPane(pTarto);
    
    int fo;
    
    public Ultet(){
        kerdez();
        epit();
        this.setPreferredSize(new Dimension(400,800));
        pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    void kerdez(){
        while(true){
            String hiba="";
            String adat=new Beker("Vendég ültetése", "Add meg hánya szeretnének leülni").run();
            
            System.out.println(adat);
            if(adat.equals("null")) dispose();
            if(adat.length()<1) hiba+="Nem adtál meg semmit!<br>";
            if(!flbr.szamE(adat)) hiba+="Nem számot adtál meg!<br>";

            if(hiba.length()<1){
                fo=Integer.parseInt(adat);
                break;
            }else
                new Uzen("Hiba", hiba).run();
        }
    }
    
    void epit(){
        lCim.setText("Vendég leültetése");
        
        flbr.betumeret(l1, 20);
        l1.setForeground(Color.white);
        
        pTarto.setBackground(Szakdoga.hatterSzin);
        pTarto.setLayout(new GridLayout(0,2,2,2));
        
        pCimnek.add(l1);
        pCimnek.setBackground(Szakdoga.hatterSzin);
        
        pKozep.add(pCimnek, BorderLayout.NORTH);
        pKozep.add(pTarto, BorderLayout.CENTER);
        
        feltolt();
    }
    
    void feltolt(){
        flbr.sel=flbr.lekerdez("Select nev from asztalok where torolve like 0 and id not in (select asztalid from Rendeles) and ferohely > "+fo+" order by ferohely");
        for(String[] t:flbr.sel){
            aktualis = new Gomb(t[0],kekSzin);
            aktualis.setForeground(Color.white);
            aktualis.addMouseListener(new GombFigyel());
            pTarto.add(aktualis);
        }     
    }
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            Gomb gomb=(Gomb)e.getSource();
            PanelWest.kivalaszt(gomb.getText());
            dispose();
        }
    }
    
}
