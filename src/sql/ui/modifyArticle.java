package sql.ui;

import LoginTest.LoginPan;
import org.hibernate.Query;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sql.model.FamilleArticle;
import ui.DefaultGridBagConstraints;


import ca.odell.glazedlists.*;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import gestion.ui.articlePan;
import static gestion.ui.articlePan.update;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.text.JTextComponent;
import sql.model.Article;
import sql.model.Fournisseur;
import sql.model.mySession;

/**
 *
 * @author Oussama
 */
public final class modifyArticle extends JPanel {

    private JList list;
    private JFrame parent;
    private Article modify;
    JComboBox comboBox1;
    JComboBox comboBox2;
    JComboBox comboBox;

    public modifyArticle(final JFrame parent, Article modifyMe) throws HeadlessException, IOException {
        this.modify = modifyMe;
        this.addViews();
        this.parent = parent;
    }

    public void addViews() throws HeadlessException, IOException {
        try {
            UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        this.setLayout(new GridBagLayout());
        final GridBagConstraints c = new DefaultGridBagConstraints();

        // Titre
        final JLabel title = new JLabel("Modifier un article", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = c.gridy = 0;
        c.gridwidth = GridBagConstraints.REMAINDER; // seul composant de sa colonne, il est donc le dernier
        c.weightx = 0;
        c.gridheight = 1; // valeur par défaut - peut s'étendre sur une seule ligne.
        c.anchor = GridBagConstraints.PAGE_START; // ou BASELINE_LEADING mais pas WEST.
        c.insets = new Insets(10, 15, 10, 0); // Marge à gauche de 15 et marge au dessus de 10.
        this.add(title, c);

        // Nom
        final JLabel labelNom = new JLabel("Nom", SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.BASELINE_LEADING; // pas LINE_START ni WEST !!
        c.insets = new Insets(10, 15, 0, 0);
        this.add(labelNom, c);

        final JTextField textNom = new JTextField(modify.getNom());
        /* passons au composant suivant: le champ de saisie. */
        c.gridx = 1; /* une position horizontalement à droite de l'étiquette */
        c.gridy = 1; /* sur la même ligne que l'étiquette */
        c.gridwidth = GridBagConstraints.REMAINDER; /* il est le dernier composant de sa ligne. */
        c.gridheight = 1; /* une seule cellule verticalement suffit */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(0, 15, 0, 15);
        this.add(textNom, c);

        // Informations supp
        final JLabel labelInfo = new JLabel("Informations supp.", SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.BASELINE_LEADING; // pas LINE_START ni WEST !!
        c.insets = new Insets(10, 15, 0, 0);
        this.add(labelInfo, c);

        final JTextField InfoNom = new JTextField(modify.getInfos());
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = GridBagConstraints.REMAINDER; /* il est le dernier composant de sa ligne. */
        c.gridheight = 1; /* une seule cellule verticalement suffit */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(0, 15, 0, 15);
        this.add(InfoNom, c);

        // Informations supp
        final JLabel labelMarque = new JLabel("Marque", SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy ++;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.BASELINE_LEADING; // pas LINE_START ni WEST !!
        c.insets = new Insets(10, 15, 0, 0);
        this.add(labelMarque, c);

        final JTextField marqueNom = new JTextField(modify.getMarque());
        c.gridx = 1;
        c.gridwidth = GridBagConstraints.REMAINDER; /* il est le dernier composant de sa ligne. */
        c.gridheight = 1; /* une seule cellule verticalement suffit */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(0, 15, 0, 15);
        this.add(marqueNom, c);
        
        /* autoComplete Combobox */
        // Famille
        final JLabel labelFamille = new JLabel("Famille ", SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.BASELINE_LEADING; // pas LINE_START ni WEST !!
        c.insets = new Insets(10, 15, 0, 0);
        this.add(labelFamille, c);

        final List<FamilleArticle> sousFamilleList = LoginPan.sousFamilletList;
        final JTextField textPere = new JTextField(modify.getFamilleArticle().getNomFamille(), 15);
        comboBox1 = new JComboBox();
        comboBox1.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXX");
        Object[] familles = sousFamilleList.toArray();
        AutoCompleteSupport.install(comboBox1, GlazedLists.eventListOf(familles));
        c.gridx = 1; /* une position horizontalement à droite de l'étiquette */
        c.gridheight = 1; /* une seule cellule verticalement suffit */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(0, 15, 0, 0);
        DefaultGridBagConstraints.lockMinimumSize(textPere);
        this.add(comboBox1, c);

        /* Icons Box Panel*/
        ElementBoxPanel box1 = new ElementBoxPanel("famille");
        c.gridx = 3; /* une position horizontalement à droite de l'étiquette */
        c.gridwidth = GridBagConstraints.REMAINDER; /* il est le dernier composant de sa ligne. */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(10, 0, 0, 15);
        this.add(box1, c);

        // Fournisseur
        final JLabel labelFrounisseur = new JLabel("Fournisseur ", SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.BASELINE_LEADING; // pas LINE_START ni WEST !!
        c.insets = new Insets(10, 15, 0, 0);
        this.add(labelFrounisseur, c);

        final List<Fournisseur> fournisseurList = LoginPan.fournisseurList;
        final JTextField textFournit = new JTextField(modify.getFournisseur().getNomFournisseur(),15);
        comboBox2 = new JComboBox();
        comboBox2.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXX");
        Object[] fournisseurs = fournisseurList.toArray();
        AutoCompleteSupport.install(comboBox2, GlazedLists.eventListOf(fournisseurs));
        c.gridx = 1; /* une position horizontalement à droite de l'étiquette */
        c.gridheight = 1; /* une seule cellule verticalement suffit */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(0, 15, 0, 0);
        DefaultGridBagConstraints.lockMinimumSize(textFournit);
        this.add(comboBox2, c);

        /* Icons Box Panel*/
        ElementBoxPanel box2 = new ElementBoxPanel("fournisseur");
        c.gridx = 3; /* une position horizontalement à droite de l'étiquette */
        c.gridwidth = GridBagConstraints.REMAINDER; /* il est le dernier composant de sa ligne. */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(10, 0, 0, 15);
        this.add(box2, c);

        c.gridy++;
        c.gridx = 0;
        c.gridwidth = GridBagConstraints.REMAINDER; // seul composant de sa colonne, il est donc le dernier
        c.weightx = 0;
        c.gridheight = 1; // valeur par défaut - peut s'étendre sur une seule ligne.
        c.anchor = GridBagConstraints.PAGE_START; // ou BASELINE_LEADING mais pas WEST.
        c.insets = new Insets(15, 15, 0, 15);
        this.add(new JSeparator(SwingConstants.HORIZONTAL), c);

        // Prix d'Achat
        final JLabel labelPrixAchat = new JLabel("Prix d'achat ", SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.BASELINE_LEADING; // pas LINE_START ni WEST !!
        c.insets = new Insets(10, 15, 0, 0);
        this.add(labelPrixAchat, c);

        final JTextField prixAchat = new JTextField(String.valueOf(modify.getPAchat()));
        c.gridx = 1;
        c.gridwidth = GridBagConstraints.REMAINDER; /* il est le dernier composant de sa ligne. */
        c.gridheight = 1; /* une seule cellule verticalement suffit */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(0, 15, 0, 15);
        this.add(prixAchat, c);

        // Prix de vente
        final JLabel labelPrixVente = new JLabel("Prix de Vente ", SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.BASELINE_LEADING; // pas LINE_START ni WEST !!
        c.insets = new Insets(10, 15, 0, 0);
        this.add(labelPrixVente, c);

        final JTextField prixVente = new JTextField(String.valueOf(modify.getPVente()));
        c.gridx = 1;
        c.gridwidth = GridBagConstraints.REMAINDER; /* il est le dernier composant de sa ligne. */
        c.gridheight = 1; /* une seule cellule verticalement suffit */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(0, 15, 0, 15);
        this.add(prixVente, c);

        // Prix de vente
        final JLabel labelQteMin = new JLabel("Qté de stock minimale ", SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.BASELINE_LEADING; // pas LINE_START ni WEST !!
        c.insets = new Insets(10, 15, 0, 0);
        this.add(labelQteMin, c);

        final JTextField QteMin = new JTextField(String.valueOf(modify.getQteMin()));
        c.gridx = 1;
        c.gridwidth = GridBagConstraints.REMAINDER; /* il est le dernier composant de sa ligne. */
        c.gridheight = 1; /* une seule cellule verticalement suffit */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(0, 15, 0, 15);
        this.add(QteMin, c);

        // Prix de vente
        final JLabel labelQte = new JLabel("Qté en stock ", SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.BASELINE_LEADING; // pas LINE_START ni WEST !!
        c.insets = new Insets(10, 15, 0, 0);
        this.add(labelQte, c);

        final JTextField QteStock = new JTextField(String.valueOf(modify.getQteStock()));
        c.gridx = 1;
        c.gridwidth = GridBagConstraints.REMAINDER; /* il est le dernier composant de sa ligne. */
        c.gridheight = 1; /* une seule cellule verticalement suffit */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(0, 15, 0, 15);
        this.add(QteStock, c);

        /* bouton AJOUTER. */
        c.gridy++; /* nouvelle ligne */
        c.gridx = 1;
        c.gridwidth = GridBagConstraints.RELATIVE; // le bouton est l'avant dernier composant de sa ligne.
        c.fill = GridBagConstraints.NONE;
        JButton okButton = new JButton("Ajouter");
        JButton cancelButton = new JButton("Annuler");
        okButton.setPreferredSize(new Dimension(70, 23));
        okButton.setMinimumSize(new Dimension(70, 23));
        c.weightx = 1.;
        c.anchor = GridBagConstraints.BASELINE_TRAILING; // Pas LINE_END, ni EAST.
        c.insets = new Insets(15, 0, 0, 0);
        this.add(okButton, c);
        okButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Article ar = new Article();
                ar.setNom(textNom.getText());
                ar.setMarque(marqueNom.getText());
                ar.setFamilleArticle((FamilleArticle)comboBox1.getSelectedItem());
                ar.setFournisseur((Fournisseur)comboBox2.getSelectedItem());
                ar.setInfos(InfoNom.getText());
                ar.setLibelle(textNom.getText()+" "+marqueNom.getText()+" "+InfoNom.getText());
                ar.setPAchat(Float.parseFloat(prixAchat.getText()));
                ar.setPVente(Float.parseFloat(prixVente.getText()));
                ar.setQteMin(Integer.parseInt(QteMin.getText()));
                ar.setQteStock(Integer.parseInt(QteStock.getText()));
                mySession.session.beginTransaction();
                mySession.session.saveOrUpdate(ar);
                mySession.session.getTransaction().commit();
//                try {
//                    articlePan.update();
//                } catch (HeadlessException | IOException ex) {
//                    Logger.getLogger(AddFamille.class.getName()).log(Level.SEVERE, null, ex);
//                }
                parent.dispose();
            }
        });

        /* notre dernier bouton. */
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 3;
        c.weightx = 0.; /* remettons le poids à zéro. */
        c.insets = new Insets(0, 0, 15, 15);
        this.add(cancelButton, c);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parent.dispose();
            }
        });
    }
}
