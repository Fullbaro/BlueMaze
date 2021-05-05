
package Parbeszed;

import szakdoga.Keret;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import szakdoga.Gomb;
import szakdoga.Szakdoga;
import static szakdoga.Szakdoga.*;

public class Mennyiseg extends Keret{
    
    String[] vissza = new String[2];
    
    int ar;
    
    JPanel pTarto = new JPanel();
    
    Gomb b1 = new Gomb("2 cl",kekSzin);
    Gomb b2 = new Gomb("4 cl",kekSzin);
    Gomb b3 = new Gomb("1 dl",kekSzin);
    Gomb b4 = new Gomb("2 dl",kekSzin);
    Gomb b5 = new Gomb("3 dl",kekSzin);
    Gomb b6 = new Gomb("4 dl",kekSzin);
    Gomb b7 = new Gomb("5 dl",kekSzin);
    Gomb b8 = new Gomb("1 L",kekSzin);
    
    public Mennyiseg(String cim, String ar){
        lCim.setText(cim);
        this.ar=Integer.parseInt(ar);
        pKozep.add(pTarto,BorderLayout.CENTER);
        pTarto.setBackground(hatterSzin);
        
        pTarto.setLayout(new GridLayout(0,2,2,2));
        pTarto.add(b1); b1.setForeground(Color.white); b1.addMouseListener(new GombFigyel());
        pTarto.add(b2); b2.setForeground(Color.white); b2.addMouseListener(new GombFigyel());
        pTarto.add(b3); b3.setForeground(Color.white); b3.addMouseListener(new GombFigyel());
        pTarto.add(b4); b4.setForeground(Color.white); b4.addMouseListener(new GombFigyel());
        pTarto.add(b5); b5.setForeground(Color.white); b5.addMouseListener(new GombFigyel());
        pTarto.add(b6); b6.setForeground(Color.white); b6.addMouseListener(new GombFigyel());
        pTarto.add(b7); b7.setForeground(Color.white); b7.addMouseListener(new GombFigyel());
        pTarto.add(b8); b8.setForeground(Color.white); b8.addMouseListener(new GombFigyel());
        
        pack();
        this.setLocationRelativeTo(null);
    }
    
    public String[] run(){
        this.setVisible(true);
        return vissza;
    }
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JButton gomb=(JButton)e.getSource();
            vissza[1] = gomb.getText();
            if(gomb==b1){
                vissza[0] = ((int)(ar*0.02))+"";
                dispose();
            }else if(gomb==b2){
                vissza[0] = ((int)(ar*0.04))+"";
                dispose();
            }else if(gomb==b3){
                vissza[0] = ((int)(ar*0.1))+""; 
                dispose();
            }else if(gomb==b4){
                vissza[0] = ((int)(ar*0.2))+"";
                dispose();
            }else if(gomb==b5){
                vissza[0] = ((int)(ar*0.3))+"";
                dispose();
            }else if(gomb==b6){
                vissza[0] = ((int)(ar*0.4))+""; 
                dispose();
            }else if(gomb==b7){
                vissza[0] = ((int)(ar*0.5))+"";
                dispose();
            }else if(gomb==b8){
                vissza[0] = ar+"";
                dispose();
            }
        
        }  
    }
    
}
