package Parbeszed.Uj;

import Fullbaro.flbr;
import szakdoga.Keret;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import szakdoga.Gomb;
import szakdoga.Szakdoga;
import static szakdoga.Szakdoga.*;

public class UjBesorolas extends Keret{
    
    String[] vissza= new String[2];
    
    JPanel pLent = new JPanel();
    JPanel pSor1 = new JPanel();
    JPanel pSor2 = new JPanel();
    JPanel pSorTarto = new JPanel();
    
    JLabel l1 = new JLabel("Megnevezés: ");
    JLabel l2 = new JLabel("Válassz kategóiát: ");
    
    JComboBox kombo;
    
    Vector<String> besorolasok = new Vector();
    
    JTextField tf1 = new JTextField(10);
    
    JButton bOk = new Gomb("OK",zoldSzin);
    
    public UjBesorolas(String cim){
        feltolt();
        
        bOk.setForeground(Color.white);
        bOk.addMouseListener(new GombFigyel());
        
        lCim.setText(cim);
        
        pLent.setBackground(hatterSzin);
        pLent.add(bOk);
        pLent.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        tf1.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        
        kombo = new JComboBox(besorolasok);
        JScrollPane sk = new JScrollPane(kombo); 
        sk.setPreferredSize(new Dimension(100,30));
        
        pSor1.add(l1); pSor1.add(tf1); pSor1.setBackground(hatterSzin);
        pSor2.add(l2); pSor2.add(sk); pSor2.setBackground(hatterSzin);
        
        pSorTarto.setLayout(new BoxLayout(pSorTarto, BoxLayout.Y_AXIS));
        pSorTarto.add(pSor1);
        pSorTarto.add(pSor2);
        
        pKozep.add(pSorTarto, BorderLayout.CENTER);
        pKozep.add(pLent, BorderLayout.SOUTH);
        
        pack();
        this.setLocationRelativeTo(null);
    }
    
    void feltolt(){
        flbr.sel=flbr.lekerdez("select megnevezes from tipusok");
        for(String[] t: flbr.sel)
            besorolasok.add(t[0]);
    }
    
    public String[] run(){
        this.setVisible(true);
        return vissza;
    }
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JButton gomb=(JButton)e.getSource();
            if(gomb==bOk){
                vissza[0]=tf1.getText();
                vissza[1]=(String)kombo.getSelectedItem();
                dispose();
            }
        }
        
    }
    
}
