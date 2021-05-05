package Parbeszed.Modosit;

import Fullbaro.flbr;
import szakdoga.Keret;
import Parbeszed.Uzen;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import szakdoga.Gomb;
import static szakdoga.PanelWest.frissit;
import static szakdoga.Szakdoga.hatterSzin;
import static szakdoga.Szakdoga.zoldSzin;


public class AsztalModosit extends Keret{
    
    DefaultListModel modell = new DefaultListModel();
    JList lista = new JList(modell);
    JScrollPane sc = new JScrollPane(lista);

    JLabel lNev = new JLabel(". Név: ");
    JLabel lId = new JLabel();
    
    JTextField tf = new JTextField(15);
    
    JPanel pGombnak = new JPanel();
    JPanel pTarto = new JPanel();
    JPanel pSor = new JPanel();
    
    JButton bOk = new Gomb("OK",zoldSzin);
    
    public AsztalModosit(){
        lCim.setText("Asztal módosítása");
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.addListSelectionListener(new ListaFigyel());
        
        pSor.setBackground(hatterSzin);
        pSor.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSor.add(lId);
        pSor.add(lNev);
        pSor.add(tf);
        
        bOk.addMouseListener(new GombFigyel());
        pGombnak.add(bOk);
        pGombnak.setBackground(hatterSzin);
        
        pTarto.add(pSor);
        pTarto.add(pGombnak); 
        pTarto.setBackground(hatterSzin);
        pTarto.setLayout(new GridLayout(2,2,10,10));
        
        pKozep.add(sc, BorderLayout.WEST);
        pKozep.add(pTarto, BorderLayout.CENTER);
        
        listaFeltolt();
        
        pack();
        this.setLocationRelativeTo(null);
    }
 
    
    void kiir(){
        String hiba="";
        String nev = tf.getText();
        
        if(nev.length()<1) hiba+="Nem adtál meg semmit.<br>";
        if(nev.length()>10) hiba+="Az asztal neve túl hosszú.<br>";
        
        flbr.sel=flbr.lekerdez("select nev from asztalok where torolve like 0");
        for(String[] t: flbr.sel){
            if(t[0].toLowerCase().trim().equals(nev.toLowerCase().trim())) hiba+="Ilyen nevű asztal már létezik";
        }
        
        if(hiba.length()!=0){
            new Uzen("Hiba",hiba).setVisible(true);
        }else{
           flbr.vegrehajt("update asztalok set nev = '"+nev+"' where id = '"+lId.getText()+"'");
           new Uzen("Végrehajtva","Az asztal sikeresen módosítva.").setVisible(true);
           frissit();
           dispose();
        }
    }
    
    void listaFeltolt(){
        flbr.sel=flbr.lekerdez("select nev from asztalok where torolve like 0 order by nev");
        for (String[] t:flbr.sel)
            modell.addElement(t[0]);
        lista.setSelectedIndex(0);
    }
    
    
    
    class ListaFigyel implements ListSelectionListener{
        public void valueChanged(ListSelectionEvent e){
            tf.setText(lista.getSelectedValue()+"");
            flbr.sel=flbr.lekerdez("select id from asztalok where nev like '"+lista.getSelectedValue()+"'");
            lId.setText(flbr.sel.get(0)[0]);
        }
    }
    
    class GombFigyel extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            JButton gomb=(JButton)e.getSource();
            if(gomb==bOk){
                kiir();
            }
        
        }  
    }
}
