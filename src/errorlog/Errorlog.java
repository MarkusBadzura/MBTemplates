package errorlog;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Errorlog
 * Erzeugt einen Errorlog und schreib Fehlermeldungen, die übergeben werden
 * in diese Log-Datei
 * @author Markus Badzura
 * @version 1.0
 */
public class Errorlog 
{
    /**************************************************************************
    * Variablendeklaration                                                    *
    ***************************************************************************/      
    private String file;
    
    /**
     * Erzeugen der Errorlog - Datei
     * @param turnus String Gibt den Turnus der Errorlog-Datei an
     * "t" = Speichern in täglicher Datei, ansonsten monatlich
     * @param pfad Pfad zum Ordner, in welchem gespeichert werden soll.
     * @author Markus Badzura
     */
    public Errorlog(String turnus, String pfad)
    {
        LocalDate d = LocalDate.now();
        if ("t".equals(turnus))
            file = pfad + "/" + d.getYear()+("_") +d.getMonth()+"_"+d.getDayOfMonth() +"_errlog.txt";
        else
            file = pfad + "/" + d.getYear()+("_") +d.getMonth() +"_errlog.txt";
    }
    /**
     * Error-Log eintrag schreiben
     * @param inhalt Fehlerbeschreibung/Information
     * @param art Fehler = "ERROR", Information = INFORM
     * @author Markus Badzura
     * since 1.0
     */
    public void schreibe(String inhalt, String art)
    {
        try 
        {
            FileWriter writer = new FileWriter(file,true);
            LocalTime t = LocalTime.now();
            String zeit = t.getHour()+":"+t.getMinute();
            writer.write(zeit+"\t"+art+"\t"+inhalt+"\r\n");
            writer.close();
        } 
        catch (IOException e) 
        {
        }        
    }    
}
