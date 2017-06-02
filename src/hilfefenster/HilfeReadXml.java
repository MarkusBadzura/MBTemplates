package hilfefenster;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Hilfefenster Auslesen des Hilfetextes aus XML-Datei
 * benötigt JDom in den Libraries
 * @author Markus Badzura
 * @version 1.0
 */
public class HilfeReadXml 
{
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Variablen                                                 //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////    
    private String schlagwort, hilfelink, antwort;
    private List rht;
    /**
     * Hilfethemen aus XML auslesen und in Hilfethema wandeln.
     * Speichern in List
     * @param xmlpfad Pfadangabe zur help.xml
     * @author Markus Badzura
     * @since 1.0
     */
    public void hilfeReadXml(String xmlpfad)
    {
	SAXBuilder builder = new SAXBuilder();
	File xmlFile = new File(xmlpfad);
        try 
        {
            rht = new ArrayList();
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("Hilfetext");         
            for (int i = 0; i < list.size(); i++) 
            {    
                Element node = (Element) list.get(i);
                schlagwort = node.getChild("Schlagwort").getText();
                hilfelink = node.getChild("Hilfelink").getText();
                antwort = node.getChild("Antwort").getText();	
                HilfeObjekt temp = new HilfeObjekt(schlagwort, hilfelink, antwort);
                rht.add(temp);
            }
        } 
        catch (JDOMException | IOException e) 
        {
            System.out.println(e);
        }
    } 
    /**
     * Hilfethemen übergeben
     * @return rht List&lt;HilfeObjekt&gt;
     * @author Markus Badzura
     * @since 1.0
     */
    public List<HilfeObjekt> gehHilfethemen()
    {
        return rht;
    }
}
