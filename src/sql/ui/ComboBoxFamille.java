package sql.ui;

import LoginTest.LoginPan;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import java.util.List;
import javax.swing.JComboBox;
import sql.model.FamilleArticle;

/**
 *
 * @author Oussama
 */
public class ComboBoxFamille extends JComboBox{
    //static FamilleArticle[] elements;
    static Object[] elements;
    static EventList<Object> list = GlazedLists.eventListOf();
    
    public ComboBoxFamille() {
        this.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXX");
        elements = LoginPan.FamilletList.toArray();
        list.removeAll(GlazedLists.eventListOf(elements));
        list.addAll(GlazedLists.eventListOf(elements));
        AutoCompleteSupport.install(this, list);
    }
    
    public static void updateListe(){
        list.removeAll(GlazedLists.eventListOf(elements));
        elements = LoginPan.FamilletList.toArray();
        list.addAll(GlazedLists.eventListOf(elements));
    }
}
