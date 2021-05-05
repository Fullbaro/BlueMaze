package szakdoga;

import Fullbaro.flbr;
import Parbeszed.Lekerdez;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Szakdoga {


    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int width = (int)screenSize.getWidth();
    public static final int height = (int)screenSize.getHeight();
    
    public static final Color hatterSzin= new Color(98,157,213);
    public static final Color kekSzin = new Color(11, 10, 216);
    public static final Color zoldSzin = new Color(20, 139, 23);
    public static final Color vilagosHatterSzin = new Color(185,214,212);
    public static final Color pirosSzin = new Color(226, 0, 7);
    public static final Color sargaSzin = new Color(247,197,1);
    
    public static int arfolyam;
    public static String inditas;
    public static String leallitas;
    public static String logo;
    
    
    Szakdoga(){
        //atmasol();
        //flbr.kapcsolodik(System.getenv("appdata")+"//BlueMaze//adatbazis.db");
        flbr.kapcsolodik("assets/adatbazis.db");
        betolt();
        new Ablak().setVisible(true);
        exit();
    }
    
    void exit(){
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                //rendez();
                flbr.kapcsolatBont();
            }
        }, "Shutdown-thread")); 
    }
    
    void rendez(){
        flbr.vegrehajt("create table seged as select aruid, asztalid, torzsid, sum(mennyiseg), sum(fizetendo), mertekegyseg from rendeles group by  aruid, asztalid, torzsid, mertekegyseg");
        flbr.vegrehajt("delete from rendeles");
        flbr.vegrehajt("insert into rendeles  select * from seged ");
        flbr.vegrehajt("drop table seged");
    }
    
    void atmasol(){
        String adatMappa = System.getenv("appdata");
        File f = new File(adatMappa+"//BlueMaze");
        if(!f.exists())
            f.mkdirs();
        f=new File(adatMappa+"//BlueMaze//adatbazis.db");
        if(!f.exists()){
            try{
                Files.copy(Paths.get("assets/adatbazis.db"), Paths.get(f.getAbsolutePath()));
            }catch(Exception e){e.printStackTrace();}
        }
    }
    
    void betolt(){
        flbr.sel=flbr.lekerdez("select * from beallitasok");
        arfolyam = Integer.parseInt(flbr.sel.get(0)[0]);
        inditas = flbr.sel.get(0)[1];
        leallitas = flbr.sel.get(0)[2];
        logo = flbr.sel.get(0)[3];
    }
    
    public static void main(String[] args) {
        //flbr.kapcsolodik("assets/adatbazis.db");
        //new Lekerdez();
        new Szakdoga();
    }
    
}
