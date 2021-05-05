package Parbeszed;

import szakdoga.Keret;
import Fullbaro.flbr;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import szakdoga.Gomb;
import static szakdoga.Szakdoga.*;

public class Kerdez extends Keret{
    
    int valasz=-1;
    
    JLabel lUzen = new JLabel();
    
    JPanel pLent = new JPanel();
    
    JButton bIgen = new Gomb("Igen",zoldSzin);
    JButton bNem = new Gomb("Nem",pirosSzin);
    
    public Kerdez(String cim, String uzenet){
        lCim.setText(cim);
        
        pLent.setLayout(new FlowLayout(FlowLayout.CENTER));
        pLent.add(bIgen);
        pLent.add(bNem);
        pLent.setBackground(hatterSzin);
        
        lUzen.setHorizontalAlignment(SwingConstants.CENTER);
        lUzen.setText(uzenet);
        flbr.betumeret(lUzen, 18);
        lUzen.setBorder(new EmptyBorder(15, 30, 15, 30));
        
        bIgen.addMouseListener(new GombFigyel());
        bNem.addMouseListener(new GombFigyel());
        
        pKozep.setLayout(new BorderLayout());
        pKozep.add(lUzen, BorderLayout.CENTER);
        pKozep.add(pLent, BorderLayout.SOUTH);
        
        pack();
        this.setLocationRelativeTo(null);
    }
    
    public int run(){
        this.setVisible(true);
        return valasz;
    }
    
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JButton gomb=(JButton)e.getSource();
            if(gomb==bIgen){
                valasz=1;
                dispose();
            }else if(gomb==bNem){
                valasz=2;
                dispose();
            }
        }
        
    }
}
