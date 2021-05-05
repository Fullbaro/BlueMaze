package Parbeszed.Torol;

import Fullbaro.flbr;
import szakdoga.Keret;
import Parbeszed.Uzen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import szakdoga.Gomb;
import szakdoga.Szakdoga;
import static szakdoga.Szakdoga.hatterSzin;
import static szakdoga.Szakdoga.pirosSzin;
import static szakdoga.Szakdoga.zoldSzin;

public class Torol extends Keret{
        
    JLabel l1 = new JLabel();
    
    Gomb bOk = new Gomb("OK",zoldSzin);
    Gomb bMegse = new Gomb("Mégse",pirosSzin);
    
    JPanel pLent = new JPanel();
    JPanel pTarto = new JPanel();
    
    JComboBox kombo;
    
    String select, update;
    
    Vector<String> besorolasok = new Vector();
    
    public Torol(String uzenet,String select, String update){
        lCim.setText("Áru törlése");
        l1.setText(uzenet);
        this.select=select;
        this.update=update;
        
        feltolt();
        kombo = new JComboBox(besorolasok);
        JScrollPane sk = new JScrollPane(kombo); 
        
        pTarto.setBackground(hatterSzin);
        pTarto.setLayout(new BoxLayout(pTarto, BoxLayout.Y_AXIS));
        pTarto.add(l1); flbr.betumeret(l1, 20);
        l1.setBorder(new EmptyBorder(20, 20, 20, 20));
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        pTarto.setBorder(new EmptyBorder(20, 20, 20, 20));
        pTarto.add(sk);
        
        bOk.setForeground(Color.white); bMegse.setForeground(Color.white);
        bOk.addMouseListener(new GombFigyel());
        bMegse.addMouseListener(new GombFigyel());
        
        pLent.setBackground(hatterSzin);
        pLent.add(bOk); pLent.add(bMegse);
        
        pKozep.add(pTarto, BorderLayout.CENTER);
        pKozep.add(pLent, BorderLayout.SOUTH);
        
        pack();
        this.setLocationRelativeTo(null);
    }
    
    void feltolt(){
        flbr.sel=flbr.lekerdez(select);
        for(String[] t: flbr.sel)
            besorolasok.add(t[0]);
    }
    
    public int run(){
        this.setVisible(true);
        return valasz;
    }
    
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JButton gomb=(JButton)e.getSource();
            if(gomb==bOk){
                flbr.vegrehajt(update+kombo.getSelectedItem()+"'");
                new Uzen("Végrehajtva","Sikeresen törölve.").run();
                dispose();
            }else if(gomb==bMegse){
                dispose();
            }
        
        }  
    }
    
}
