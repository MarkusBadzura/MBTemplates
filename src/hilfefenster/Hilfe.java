package hilfefenster;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;

/**
 * Hilfefenster
 * @author Markus Badzura
 * @version 1.0
 */
public class Hilfe extends JFrame implements ComponentListener, ListSelectionListener
        ,ChangeListener, ActionListener
{
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration ImageIcon - Objekt und Bildschirmgröße                    //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////
    private final Dimension SCREENSIZE = java.awt.Toolkit.getDefaultToolkit().getScreenSize ();
    private final HilfeReadXml RHX = new HilfeReadXml();
    HilfeObjekt rht_temp;
    private List l_hilfetext = new ArrayList();
    private List l_suchhilfe = new ArrayList();
    private String[] htext;
    private HTMLEditorKit eKit = new HTMLEditorKit();
    boolean aktiv = false;
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Hilfefenster Bedienelemente                               //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////
    private JSplitPane jsp;
    private JScrollPane jsp_tabContent, jsp_right, jsp_tabSearch;
    private JList jl_hilfelink, jl_suchergebnis;
    private DefaultListModel dlm_suche = new DefaultListModel();
    private JTextPane jtp_antwort;
    private JTextField jtf_search;
    private JTabbedPane jtp_hilfe;
    private JButton jbt_suche;
    /**
     * Template für Hilffunktion
     * Öffnet ein Hilfefenster mit den Tabs Inhalt und Suchen
     * @param fenstertitel String Titel, welchen das Fenster haben soll
     * @param iconpfad String Pfad des Icons
     * @param xmlpfad String Pfad zur help.xml
     * @author Markus Badzura
     * @version 1.0
     */
    public void setHilfe(String fenstertitel, URL pfad, String xmlpfad)
    {
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) 
        {
            System.err.println("Setting Look and Feel Failed");
        }
        // Pfadangabe zum Image
        // Erstellen ImageIcon-Objekt
        ImageIcon ICON = new ImageIcon(pfad);
        this.setTitle(fenstertitel);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(SCREENSIZE.width/3,SCREENSIZE.height);
        this.setLocation((SCREENSIZE.width/3)*2+15,0);
        this.setLayout(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                exit();
            }
        });          
        this.addComponentListener(this);
        this.setIconImage(ICON.getImage());
        RHX.hilfeReadXml(xmlpfad);
        getHilfeText();
        setHilfeLink();
        setSplitPane();        
        setVisible(true);   
        aktiv = true;
    }
    /**
     * Prüfung, ob Hilfefenster bereits aktiv ist. Wenn aktiv, dann wird
     * das Hilfefenster in den Vordergrund geholt
     * @return aktiv Bool-Wert
     * @author Markus Badzura
     * @since 1.0
     */
    public boolean isAktiv()
    {
        if (aktiv)
        {
            this.setState(JFrame.NORMAL);   
        }
        return aktiv;
    }
     /**
     * Abfragedialog beim Beenden des Programmes, inclusive des Schließens
     * über ALT + F4 und dem Schließbutton über die Titelleiste.
     * @author Markus Badzura
     * @since 1.0
     */
    private void exit()
    {
        this.dispose();
    }
    /**
     * Liste mit den verfügbaren Hilfetupel lladen
     * @author Markus Badzura
     * @since 1.0.005
     */
    private void getHilfeText()
    {
        l_hilfetext = RHX.gehHilfethemen();
    }
    /**
     * Vorhandene Fragestellungen für die Hilfe auflisten
     * im Tab Inhalt
     * @author Markus Badzura
     * @since 1.0
     */
    private void setHilfeLink()
    {
        htext = new String[l_hilfetext.size()];
        for (int i = 0;i<l_hilfetext.size();i++)
        {
            rht_temp = (HilfeObjekt)l_hilfetext.get(i);
            htext[i] = rht_temp.getHilfelink();
        }
    }
    /**
     * Setzen des geteilten Hilfefensters
     * links 2 Tabs und rechts die Antworten
     * @author Markus Badzura
     * @since 1.0
     */
    private void setSplitPane()
    {
        jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        jtp_hilfe = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
        jtp_hilfe.addChangeListener(this);
        jsp_tabContent = new JScrollPane();
        jsp_right = new JScrollPane();
        jsp_tabSearch = new JScrollPane();
        jsp_tabSearch.setLayout(null);
        jtf_search = new JTextField();
        jbt_suche = new JButton("Suche starten");
        jbt_suche.addActionListener(this);
        jl_suchergebnis = new JList(dlm_suche);
        jl_suchergebnis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jl_suchergebnis.addListSelectionListener(this);
        jsp_tabSearch.add(jtf_search);
        jsp_tabSearch.add(jbt_suche);
        jsp_tabSearch.add(jl_suchergebnis);
        jl_hilfelink = new JList(htext);
        jl_hilfelink.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jl_hilfelink.addListSelectionListener(this);
        jtp_antwort = new JTextPane();
        jtp_antwort.setEditorKit(eKit);
        jsp_tabContent.setViewportView(jl_hilfelink);
        jsp_right.setViewportView(jtp_antwort);
        setSizeComponent();
        jtp_hilfe.add("Inhalt",jsp_tabContent);
        jtp_hilfe.add("Suche",jsp_tabSearch);
        jsp.setLeftComponent(jtp_hilfe);
        jsp.setRightComponent(jsp_right);
        this.add(jsp);
    }
    /**
     * Größe der Komponenten neu setzen (Bei Initialisierung und nach
     * Veränderung der Fenstergröße)
     * @author Markus Badzura
     * @since 1.0
     */
    private void setSizeComponent()
    {
        jsp.setSize(this.getSize());
        jsp.setDividerLocation(0.4);
        jsp.setDividerSize(0);
        jtf_search.setBounds(5,5,(int)(this.getWidth()*0.38),20);
        jbt_suche.setBounds(5,30,(int)(this.getWidth()*0.38),20);
        jl_suchergebnis.setBounds(5,50,(int)(this.getWidth()*0.38),this.getHeight());             
    }
    /**
     * Befüllen der Liste mit Hilfethemen, dessen Schlagwort in der Hilfe-
     * anfrage des Benutzers vorkommt.
     * @author Markus Badzura
     * @since 1.0
     */
    private void fillSuche()
    {
        String temp = jtf_search.getText().trim();
        l_suchhilfe.clear();
        dlm_suche.removeAllElements();
        int index = 0;
        if ("".equals(temp))
        {
            JOptionPane.showConfirmDialog(null, "Es fehlt ein oder mehrere Schlagwörter, nach denen gesucht werden soll?",
                "Fehlende Eingabe", JOptionPane.OK_OPTION);
            jtf_search.requestFocus();
        }
        else
        {
            for (int i = 0; i < l_hilfetext.size();i++)
            {
                rht_temp = (HilfeObjekt)l_hilfetext.get(i);
                if (temp.toLowerCase().contains(rht_temp.getSchlagwort().toLowerCase()))
                {
                    dlm_suche.add(index, rht_temp.getHilfelink());
                    l_suchhilfe.add(rht_temp);
                    ++index;
                }
            }    
        }
    }
    /**
     * Setzen der Antwort im rechten Teil des JSplit nach Auswahl des Hilfe-
     * themas in der Listenansicht
     * @param auswahl int Index der Objektarraylist zum Auslesen
     * @param tab int Inhalt- oder Suchen-Tab 
     * @author Markus Badzura
     * @since 1.0
     */
    private void setAntwort(int auswahl, int tab)
    {
        if (tab == 1)
            rht_temp = (HilfeObjekt)l_hilfetext.get(auswahl);
        else
            rht_temp = (HilfeObjekt) l_suchhilfe.get(auswahl);
        jtp_antwort.setText(rht_temp.getAntwort());
    }
    /**
     * Componentlister um auf Änderung der Größe des Hilfefensters zu reagieren
     * @param e Auslösende Component
     * @author Markus Badzura
     * @since 1.0
     */
    @Override
    public void componentResized(ComponentEvent e) 
    {
        setSizeComponent();
    }
    /**
     * Componentlistener um auf Änderung der Position des Hilfefensters zu reagieren
     * @param e Auslösende Component
     * @author Markus Badzura
     * @version 1.0
     */
    @Override
    public void componentMoved(ComponentEvent e) 
    {
        // Keine Aktionen
    }
    /**
     * Componentlistener um beim Aufzeigen Zeigen des Hilfefensters zu reagieren
     * @param e Auslösende Component
     * @author Markus Badzura
     * @since 1.0
     */
    @Override
    public void componentShown(ComponentEvent e) 
    {
        // Keine Aktionen
    }
    /**
     * Componentlistener, um auf ausblenden des Hilfefensters zu reagieren
     * @param e Auszulösende Component
     * @author Markus Badzura
     * @since 1.0
     */
    @Override
    public void componentHidden(ComponentEvent e) 
    {
        // Keine Aktionen
    }
    /**
     * ListSelectionListener. Reagiert auf Listenauswahl
     * @param e Auslösende Liste
     * @author Markus Badzura
     * @since 1.0
     */
    @Override
    public void valueChanged(ListSelectionEvent e) 
    {
        if (e.getSource() == jl_hilfelink)
        {
            if (jl_hilfelink.getSelectedIndex() >= 0)
                setAntwort(jl_hilfelink.getSelectedIndex(),1);
        }
        if (e.getSource() == jl_suchergebnis)
        {
            if (jl_suchergebnis.getSelectedIndex() >= 0)
                setAntwort(jl_suchergebnis.getSelectedIndex(),2);
        }
    }
    /**
     * ChangeListener, um auf Wechsel der Tabs zu reagieren
     * @param e auslösendes JTabbedPane
     * @author Markus Badzura
     * @since 1.0
     */
    @Override
    public void stateChanged(ChangeEvent e) 
    {
        jtf_search.setText("");
        jtp_antwort.setText("<br>");
        jl_suchergebnis.removeAll();
        jl_hilfelink.clearSelection();
        dlm_suche.removeAllElements();
    }
    /**
     * ActionListener, um auf Buttonklicks reagieren zu können
     * @param e Auslösender Button
     * @author Markus Badzura
     * @since 1.0
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == jbt_suche)
        {
            fillSuche();
        }
    }
}