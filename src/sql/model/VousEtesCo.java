package sql.model;

import com.jgoodies.forms.factories.Borders.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import sql.model.User;

/**
 *
 * @author Oussama
 */
public class VousEtesCo extends JPanel{
    
    private String connecte = "Vous êtes connectés en tant que : ";
    public static User utilisateur;
    private JLabel label;
    
    public VousEtesCo() {
           label = new JLabel(connecte + utilisateur.getUserName(),SwingConstants.LEFT);
           //label = new JLabel(connecte);
           this.setBackground(new java.awt.Color(255, 255, 255));
           this.setLayout(new BorderLayout());
           this.add("West",label);
           this.setBorder(new javax.swing.border.EmptyBorder(5, 5, 5, 5));
    }

    public User getUtilisateur() {
        return utilisateur;
    }

    public static void setUtilisateur(User utilisateur) {
        VousEtesCo.utilisateur = utilisateur;
    }
    
    
}
