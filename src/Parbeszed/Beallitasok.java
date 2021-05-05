package Parbeszed;

import szakdoga.Keret;
import Fullbaro.flbr;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import szakdoga.Gomb;
import szakdoga.Szakdoga;
import static szakdoga.Szakdoga.hatterSzin;

public class Beallitasok extends Keret{
    
    JPanel pAlap = new JPanel();
    JPanel pSor1 = new JPanel();
    JPanel pSor2 = new JPanel();
    JPanel pSor3 = new JPanel();
    JPanel pSor4 = new JPanel();
    JPanel pSor5 = new JPanel();
    JPanel pSor6 = new JPanel();
    
    JLabel l1 = new JLabel("Ezuró árfolyam: ");
    JLabel l2 = new JLabel("Ft");
    JLabel l3 = new JLabel("Program indítása a windowsal");
    JLabel l4 = new JLabel("Windows leállítása a program bezárásával");
    JLabel l5 = new JLabel("Logo: ");
    
    JCheckBox cb1 = new JCheckBox();
    JCheckBox cb2 = new JCheckBox();
    
    JTextField tf1 = new JTextField(10);
    JTextField tf2 = new JTextField(15);
    
    JButton bOk = new Gomb("OK", Szakdoga.zoldSzin);
    JButton bTalloz = new Gomb("Tallózás", Szakdoga.zoldSzin);
    JButton bLekerdez = new Gomb("SQLITE lekérdezés", Szakdoga.zoldSzin);
    
    public Beallitasok(String cim){
        lCim.setText(cim);
        
        pSor1.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSor1.setBackground(hatterSzin);
        pSor1.add(l1);
        pSor1.add(tf1);
        pSor1.add(l2);
        
        pSor2.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSor2.setBackground(hatterSzin);
        pSor2.add(l3);
        pSor2.add(cb1); cb1.setBackground(hatterSzin);
        
        pSor3.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSor3.setBackground(hatterSzin);
        pSor3.add(l4);
        pSor3.add(cb2); cb2.setBackground(hatterSzin);
        
        pSor4.setBackground(hatterSzin);
        pSor4.add(bOk);
        bOk.setForeground(Color.white);
        bOk.addMouseListener(new GombFigyel());
        
        pSor5.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSor5.setBackground(hatterSzin);
        pSor5.add(l5);
        pSor5.add(tf2);
        pSor5.add(bTalloz);
        
        pSor6.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSor6.setBackground(hatterSzin);
        pSor6.add(bLekerdez);
        bLekerdez.addMouseListener(new GombFigyel());
        
        pAlap.setLayout(new BoxLayout(pAlap, BoxLayout.Y_AXIS));
        pAlap.add(pSor1);
        pAlap.add(pSor2);
        pAlap.add(pSor3);
        pAlap.add(pSor5);
        pAlap.add(pSor6);
        pAlap.add(pSor4);
        
        bTalloz.addMouseListener(new GombFigyel()); bTalloz.setForeground(Color.white);
        
        
        pKozep.add(pAlap, BorderLayout.CENTER);
        
        feltolt();
        
        pack();
        this.setLocationRelativeTo(null);
    }
    
    public int run(){
        this.setVisible(true);
        return valasz;
    }
    
    void feltolt(){
        flbr.sel=flbr.lekerdez("select * from beallitasok");
        tf1.setText(flbr.sel.get(0)[0]);
        if(flbr.sel.get(0)[1].equals("1"))
            cb1.setSelected(true);
        if(flbr.sel.get(0)[2].equals("1"))
            cb2.setSelected(true);
        tf2.setText(flbr.sel.get(0)[3]);
    }
    
    void kiir(){
        String hiba="";
        String adat = tf1.getText();
        String indul="";
        String leall="";
        String logo="";
        
        logo=tf2.getText();
        
        if(cb1.isSelected()) indul="1";
        else indul="0";
        if(cb2.isSelected()) leall="1";
        else leall="0";
        
        if(adat.length()<1) hiba+="Nem adtál meg árfolyamot.<br>";
        if(!flbr.szamE(adat)) hiba+="Nem számot adtál meg.";
        
        if(hiba.length()!=0){
            new Uzen("Hiba",hiba).setVisible(true);
        }else{
            Szakdoga.arfolyam=Integer.parseInt(adat);
            Szakdoga.inditas=indul;
            Szakdoga.leallitas=leall;
            flbr.vegrehajt("update beallitasok set arfolyam="+adat+", inditas='"+indul+"', leallitas='"+leall+"', logo='"+logo+"'");
            dispose();
        }
    }
    
    void talloz(){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Válaszd ki a logót!");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setAcceptAllFileFilterUsed(false);
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            Szakdoga.logo=chooser.getSelectedFile().getAbsolutePath();
            tf2.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JButton gomb=(JButton)e.getSource();
            if(gomb==bOk){
                kiir();
            }else if(gomb==bTalloz){
                talloz();
            }else if(gomb==bLekerdez){
                new Lekerdez();
            }
        
        }  
    }
    
}
