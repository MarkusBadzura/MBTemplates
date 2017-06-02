package hilfefenster;

/**
 * Klasse zu Erstellen von Hilfetext-Objekte
 * @author Markus Badzura
 * @version 1.0
 */
public class HilfeObjekt 
{
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Variablen                                                 //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////    
    private String schlagwort;
    private String hilfelink;
    private String antwort;
    /**
     * Konstruktor zum Bilden eines Hilfetext-Objektes
     * @param schlagwort ausgelesenes Schlagwort aus XML-Datei
     * @param hilfelink ausgelesener Hilfelink aus XML-Datei
     * @param antwort ausgelesene Antwort aus XML-Datei
     * @author Markus Badzura
     * @since 1.0
     */
    HilfeObjekt(String schlagwort, String hilfelink, String antwort)
    {
        this.schlagwort = schlagwort;
        this.hilfelink = hilfelink;
        this.antwort = antwort;
    }
    /**
     * Schlagwort aus Objekt ausgeben
     * @return schlagwort String
     * @author Markus Badzura
     * @since 1.0
     */
    public String getSchlagwort()
    {
        return schlagwort;
    }
    /**
     * Hilfelink aus Objekt ausgeben
     * @return hilfelink String
     * @author Markus Badzura
     * @since 1.0
     */
    public String getHilfelink()
    {
        return hilfelink;
    }
    /**
     * Antwort aus Objekt ausgeben
     * @return antwort String
     * @author Markus Badzura
     * @since 1.0
     */
    public String getAntwort()
    {
        return antwort;
    }
}
