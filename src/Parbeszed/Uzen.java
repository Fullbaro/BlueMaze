package Parbeszed;

import szakdoga.Keret;
import Fullbaro.flbr;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import szakdoga.*;
import static szakdoga.Szakdoga.*;

public class Uzen extends Keret{
    
    JLabel lUzen = new JLabel();
    
    JPanel pLent = new JPanel();
    
    JButton bOk = new Gomb("OK",zoldSzin);
    
    public Uzen(String cim, String felirat){
        lUzen.setText("<html>"+felirat);
        lUzen.setBorder(new EmptyBorder(30, 50, 30, 50));
        flbr.betumeret(lUzen, 18);
        
        lCim.setText(cim);

        pLent.add(bOk);
        pLent.setBackground(hatterSzin);
        
        bOk.setForeground(Color.white);
        bOk.addMouseListener(new GombFigyel());
        
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
            if(gomb==bOk){
                dispose();
            }
        
        }  
    }
}
