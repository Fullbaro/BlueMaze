package szakdoga;

import Fullbaro.flbr;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import szakdoga.Gomb;
import static szakdoga.PanelWest.frissit;
import static szakdoga.Szakdoga.*;

public class Keret extends JDialog{

    public int valasz=-1, xHely, yHely;
    
    public JPanel pHatter = new JPanel();
    public JPanel pFent = new JPanel();
    public JPanel pKozep = new JPanel();
    
    public JLabel lCim = new JLabel();
    public JLabel lX = new JLabel("X");
    
    public Keret(){
        this.setUndecorated(true);
        this.setModal(true);
        //this.pKozep=tartalom;
        epit();
        this.setLocationRelativeTo(null);
    }
    
    void epit(){
        lCim.setForeground(Color.WHITE);
        
        lX.setOpaque(false);
        lX.setBackground(pirosSzin);
        flbr.betumeret(lX, 18);
        lX.setForeground(Color.white);
        lX.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){  
                dispose();
            }  
        }); 
        
        pFent.setLayout(new BoxLayout(pFent, BoxLayout.X_AXIS));
        pFent.setBackground(hatterSzin.darker());
        pFent.add(lCim);
        pFent.add(Box.createHorizontalGlue());
        pFent.add(lX);
        pFent.add(Box.createRigidArea(new Dimension(5,5)));
        pFent.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent me){
              xHely = me.getX();
              yHely = me.getY();
            }
        });
        pFent.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent me){
                getAbak().setLocation(x()+me.getX()-xHely,y()+me.getY()-yHely);
            }
          });
        
        pHatter.setLayout(new BorderLayout());
        pHatter.setBackground(hatterSzin);
        pHatter.add(pFent, BorderLayout.NORTH);
        pHatter.setBorder(BorderFactory.createLineBorder(hatterSzin.darker(),3)); 
        pHatter.add(pKozep, BorderLayout.CENTER);
        
        pKozep.setLayout(new BorderLayout());
        pKozep.setBorder(new EmptyBorder(15, 30, 15, 30));
        pKozep.setBackground(hatterSzin);
        
        this.add(pHatter);
    }
    
    public JDialog getAbak(){
        return this;
    }
    
    public int x() {
        return this.getX();
    }

    public int y() {
        return this.getY();
    }
    
}
