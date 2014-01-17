package sql.ui;

import LoginTest.LoginPan;
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
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import sql.model.mySession;

/**
 *
 * @author Oussama
 */
public final class AddFamille extends JPanel {

    private JList list;
    private JFrame parent;
    ComboBoxFamille comboBox;

    public AddFamille(final JFrame parent) throws HeadlessException, IOException {
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
        final JLabel title = new JLabel("Ajouter une famille", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = c.gridy = 0;
        c.gridwidth = GridBagConstraints.REMAINDER; // seul composant de sa colonne, il est donc le dernier
        c.weightx = 0;
        c.gridheight = 1; // valeur par défaut - peut s'étendre sur une seule ligne.
        c.anchor = GridBagConstraints.PAGE_START; // ou BASELINE_LEADING mais pas WEST.
        c.insets = new Insets(10, 15, 10, 0); // Marge à gauche de 15 et marge au dessus de 10.
        this.add(title, c);

        // Label Nom famille
        final JLabel labelNom = new JLabel("Famille", SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 1;
        /* une seule cellule sera disponible pour ce composant. */
        c.gridwidth = 1;
        c.gridheight = 1;
        /* Maintenant, nous voyons sur notre interface que le composant n'est pas le seul sur sa ligne.
         * Un champ de saisie le suit. Pour aligner correctement les étiquettes et les champs de saisie,
         * la ligne d'écriture nous facilite le travail. Nous allons l'utiliser ici. */
        c.anchor = GridBagConstraints.BASELINE_LEADING; // pas LINE_START ni WEST !!
        /* Une petite marge autour du composant. Attention à toujours indiquer les mêmes marges à gauche, sinon les
         * composants ne sont plus alignés. */
        c.insets = new Insets(10, 15, 0, 0);
        this.add(labelNom, c);

        // TestField Nom Famille
        final JTextField textNom = new JTextField(15);
        /* passons au composant suivant: le champ de saisie. */
        c.gridx = 1; /* une position horizontalement à droite de l'étiquette */
        c.gridy = 1; /* sur la même ligne que l'étiquette */
        c.gridwidth = GridBagConstraints.REMAINDER; /* il est le dernier composant de sa ligne. */
        c.gridheight = 1; /* une seule cellule verticalement suffit */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(0, 15, 0, 15);
        this.add(textNom, c);

        // Famille père
        final JLabel labelFamille = new JLabel("sous-famille de", SwingConstants.RIGHT);
        c.gridx = 0;
        c.gridy = 2;
        /* une seule cellule sera disponible pour ce composant. */
        c.gridwidth = 1;
        c.gridheight = 1;
        /* Maintenant, nous voyons sur notre interface que le composant n'est pas le seul sur sa ligne.
         * Un champ de saisie le suit. Pour aligner correctement les étiquettes et les champs de saisie,
         * la ligne d'écriture nous facilite le travail. Nous allons l'utiliser ici. */
        c.anchor = GridBagConstraints.BASELINE_LEADING; // pas LINE_START ni WEST !!
        c.insets = new Insets(10, 15, 0, 0);
        this.add(labelFamille, c);

        /* autoComplete Combobox */

        final JTextField textPere = new JTextField(15);
        comboBox = new ComboBoxFamille();
//        comboBox.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXX");
//        Object[] elements = resultList.toArray();
//        AutoCompleteSupport.install(comboBox, GlazedLists.eventListOf(elements));

        c.gridx = 1; /* une position horizontalement à droite de l'étiquette */
        c.gridy = 2; /* sur la même ligne que l'étiquette */
        c.gridheight = 1; /* une seule cellule verticalement suffit */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(0, 15, 0, 0);
        DefaultGridBagConstraints.lockMinimumSize(textPere);
        this.add(comboBox, c);

        /* Icons Box Panel*/
        ElementBoxPanel box = new ElementBoxPanel("Famille");
        c.gridx = 3; /* une position horizontalement à droite de l'étiquette */
        c.gridy = 2; /* sur la même ligne que l'étiquette */
        c.gridwidth = GridBagConstraints.REMAINDER; /* il est le dernier composant de sa ligne. */
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
        c.insets = new Insets(10, 0, 0, 15);
        this.add(box, c);

        /* bouton AJOUTER. */
        c.gridy = 3; /* nouvelle ligne */
        c.gridx = 1;
        c.gridwidth = GridBagConstraints.RELATIVE; // le bouton est l'avant dernier composant de sa ligne.
        c.fill = GridBagConstraints.NONE;
        JButton okButton = new JButton("Ajouter");
        JButton cancelButton = new JButton("Annuler");
        okButton.setPreferredSize(new Dimension(70, 23));
        okButton.setMinimumSize(new Dimension(70, 23));
        c.weightx = 1.;
        c.anchor = GridBagConstraints.BASELINE_TRAILING; // Pas LINE_END, ni EAST.
        c.insets = new Insets(10, 0, 0, 0);
        this.add(okButton, c);
        okButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane d = new JOptionPane();
                if (!textNom.getText().trim().isEmpty()) {
                    FamilleArticle fam = new FamilleArticle();
                    fam.setNomFamille(textNom.getText());
                    fam.setFamilleArticle((FamilleArticle) comboBox.getSelectedItem());
                    mySession.session.beginTransaction();
                    mySession.session.saveOrUpdate(fam);
                    mySession.session.getTransaction().commit();
                    String show = "la Famille " + fam + " a bien été ajoutée";
                    d.showMessageDialog(new JFrame(), show, "Ajout", JOptionPane.INFORMATION_MESSAGE);
                    ComboBoxFamille.updateListe();
                    ComboBoxSousFamille.updateListe();
                    try {
                        articlePan.update();
                    } catch (HeadlessException | IOException ex) {
                        System.out.println("Problème d'Update");
                    }
                    parent.dispose();
                } else {
                    d.showMessageDialog(new JFrame(), "Attention ! le champs famille est vide", "Modification", JOptionPane.ERROR_MESSAGE);
                }
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
