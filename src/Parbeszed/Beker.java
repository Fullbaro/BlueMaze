package Parbeszed;

import szakdoga.Keret;
import Fullbaro.flbr;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import szakdoga.Gomb;
import static szakdoga.Szakdoga.*;

public class Beker extends Keret{
    
    String vissza="null";
    
    JPanel pTarto = new JPanel();
    JPanel pLent = new JPanel();
    JPanel pSor1 = new JPanel();
    JPanel pSor2 = new JPanel();
    
    JLabel l1 = new JLabel();
    
    JTextField tf1 = new JTextField(20);
    
    JButton bOk = new Gomb("OK", zoldSzin);
    
    public Beker(String cim, String uzenet){
        lCim.setText(cim);
        l1.setText(uzenet);
        flbr.betumeret(l1, 18);
        l1.setBorder(new EmptyBorder(15, 30, 15, 30));
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        
        bOk.addMouseListener(new GombFigyel());
        
        pLent.setBackground(hatterSzin);
        pLent.add(bOk);
        
        pTarto.setBackground(hatterSzin);
        pTarto.setLayout(new BoxLayout(pTarto, BoxLayout.Y_AXIS));
        pSor1.add(l1); pSor1.setBackground(hatterSzin);
        pSor2.add(tf1); pSor2.setBackground(hatterSzin);
        pSor2.setBorder(new EmptyBorder(15, 30, 15, 30));
        pTarto.add(pSor1); pTarto.add(pSor2);
        
        pKozep.add(pTarto, BorderLayout.CENTER);
        pKozep.add(pLent, BorderLayout.SOUTH);
        
        pack();
        this.setLocationRelativeTo(null);
    }
    
    public String run(){
        this.setVisible(true);
        return vissza;
    }
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JButton gomb=(JButton)e.getSource();
            if(gomb==bOk){
                vissza=tf1.getText();
                dispose();
            }
        
        }  
    }
    
}
