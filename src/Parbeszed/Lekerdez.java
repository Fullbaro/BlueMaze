package Parbeszed;

import Fullbaro.flbr;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import szakdoga.Gomb;
import szakdoga.Keret;
import szakdoga.PanelWest;
import szakdoga.Szakdoga;

public class Lekerdez extends Keret{
    
    JPanel pSor = new JPanel();
    
    JTextArea ta = new JTextArea();
    
    JTextField tf = new JTextField();
    
    Gomb b1 = new Gomb("Mehet", Szakdoga.zoldSzin);
    
    public Lekerdez(){
        epit();
        this.setPreferredSize(new Dimension(700,500));
        pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    void epit(){
        lCim.setText("SQLITE lekérdezés végrehajtása");
        
        pSor.setLayout(new BoxLayout(pSor, BoxLayout.X_AXIS));
        pSor.add(tf);
        pSor.add(b1); b1.setForeground(Color.white);
        b1.addMouseListener(new GombFigyel());
        pSor.setBorder(new EmptyBorder(0, 0, 20, 0));
        pSor.setBackground(Szakdoga.hatterSzin);
        
        pKozep.add(pSor, BorderLayout.NORTH);
        pKozep.add(ta, BorderLayout.CENTER);
    }
    
    void lekerdez(){
        ta.setText("");
        flbr.sel=flbr.lekerdez(tf.getText());
        for(String[] t: flbr.sel){
            String akt ="";
            for(String s:t)
                akt+=s+" ";
            
            ta.append(akt+"\n");
        }
    }
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            Gomb gomb=(Gomb)e.getSource();
            lekerdez();
        }
    }
    
}
