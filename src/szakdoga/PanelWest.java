package szakdoga;

import Fullbaro.flbr;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.*;
import static szakdoga.Szakdoga.hatterSzin;
import static szakdoga.Szakdoga.kekSzin;
import static szakdoga.Szakdoga.pirosSzin;
import static szakdoga.Szakdoga.sargaSzin;


public class PanelWest extends JPanel{

    static JPanel pAsztalok = new JPanel();
    static JPanel pTorzsvendek = new JPanel();
    
    JScrollPane scAlap=new JScrollPane(pAsztalok);
    JScrollPane scTorzs=new JScrollPane(pTorzsvendek);
    
    JTabbedPane tp = new JTabbedPane();
    
    public static Gomb aktualis;
    public static Gomb bKivalasztott;
    
    static Vector<Gomb> gombok = new Vector();
    
    PanelWest(){
        epit(); 
        feltolt();
        szinTorol();
        tp.setPreferredSize(new Dimension(305,0));
        gombok.get(0).setBackground(sargaSzin);
        bKivalasztott=gombok.get(0);
        PanelEast.frissit(bKivalasztott.getText());
    }
    
    void epit(){
        this.setLayout(new BorderLayout());
        this.setBackground(hatterSzin);
        
        pAsztalok.setLayout(new GridLayout(0, 2,2,2));
        pAsztalok.setBackground(hatterSzin);

        tp.setFont(new Font("TimesRoman", Font.BOLD, 16));
        this.add(tp, BorderLayout.CENTER);
        scAlap.setVerticalScrollBarPolicy(  ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
        scAlap.getVerticalScrollBar().setUnitIncrement(30);
        tp.addTab("Asztalok", scAlap);
        
        tp.addTab("Törzsvendégek", scTorzs);
        pTorzsvendek.setBackground(hatterSzin);
        pTorzsvendek.setLayout(new BoxLayout(pTorzsvendek, BoxLayout.Y_AXIS));
    }
    
    public static void feltolt(){
        gombok.clear();
        int index=0;
        flbr.sel=flbr.lekerdez("Select nev from asztalok where torolve like 0 order by nev");
        for(String[] t:flbr.sel){
            aktualis = new Gomb(t[0],kekSzin);
            aktualis.setForeground(Color.white);
            aktualis.addMouseListener(new GombFigyel());
            aktualis.setName(""+index); index++;
            pAsztalok.add(aktualis);
            gombok.add(aktualis);
        }
        
        flbr.sel=flbr.lekerdez("Select nev from torzsvendegek  where torolve like 0 order by nev");
        for(String[] t:flbr.sel){
            aktualis = new Gomb(t[0],kekSzin);
            aktualis.setForeground(Color.white);
            aktualis.addMouseListener(new GombFigyel());
            aktualis.setName("T"+index); index++;
            aktualis.setPreferredSize(new Dimension(305,0));
            pTorzsvendek.add(aktualis);
            gombok.add(aktualis);
        }
    }
    
    static void szinTorol(){
        for(JButton b:gombok){
            if(b.getBackground()==sargaSzin)
                b.setBackground(kekSzin);
            flbr.sel=flbr.lekerdez("select mennyiseg, megnevezes, ar from Rendeles inner join aruk on aruk.id=rendeles.AruID inner join asztalok on Asztalok.ID= Rendeles.AsztalID where nev like '"+b.getText()+"'");
            if(flbr.sel.size()>0)
                b.setBackground(pirosSzin);
        }
    }
    
    public static void kivalaszt(String nev){
        szinTorol();
        for(Gomb b: gombok){
            if(b.getText().equals(nev)){
                b.setBackground(sargaSzin);
                bKivalasztott=b;
                
                boolean torzs=false;
                flbr.sel=flbr.lekerdez("select id from torzsvendegek where nev like '"+b.getText()+"'");
                for(String[] t:flbr.sel)
                    torzs=true;
                
                
                if(torzs)
                    PanelEast.frissit2(b.getText());
                else
                    PanelEast.frissit(b.getText());
            }
        }
    }
    
    static class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            Gomb gomb=(Gomb)e.getSource();
            szinTorol();
            try{
                int hely = Integer.parseInt(gomb.getName());
                gombok.get(hely).setBackground(sargaSzin);
                bKivalasztott=gomb;
                PanelEast.frissit(gomb.getText());
            }catch(Exception a){
                int hely = Integer.parseInt(gomb.getName().replace("T",""));
                gombok.get(hely).setBackground(sargaSzin);
                bKivalasztott=gomb;
                PanelEast.frissit2(gomb.getText());
            }
            
        }
        
    }

    public static void frissit() {
        pAsztalok.removeAll(); pTorzsvendek.removeAll();
        pAsztalok.revalidate(); pTorzsvendek.revalidate();
        pAsztalok.repaint(); pTorzsvendek.repaint();
        feltolt();
        szinTorol();
        gombok.get(0).setBackground(sargaSzin);
        bKivalasztott=gombok.get(0);
        PanelEast.frissit(bKivalasztott.getText());
    }
    
    
}
