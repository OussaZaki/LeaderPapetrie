package sql.ui;

import gestion.ui.tabbed;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import ui.DefaultGridBagConstraints;

/**
 *
 * @author Oussama
 */
public class ElementBoxPanel extends JPanel implements ActionListener {

    private static ImageIcon iconList = new ImageIcon("/assets/BoxPanel/liste.png");
    private static ImageIcon iconModif = new ImageIcon("/assets/BoxPanel/pen.png");
    private static ImageIcon iconAdd = new ImageIcon("/assets/BoxPanel/add.png");
    private Boolean canModif = false;
    // true if viewButton can currently modify the selection
    private Boolean isModif = null;

    private static void initIcons() {
        iconModif = new ImageIcon("/assets/BoxPanel/pen.png");
        iconList = new ImageIcon("/assets/BoxPanel/liste.png");
        iconAdd = new ImageIcon("/assets/BoxPanel/add.png");
    }

    public final void setCanModify(boolean canModif) {
        if (this.canModif == null || this.canModif != canModif) {
            this.canModif = canModif;
            this.updateViewBtn();
        }
    }

    private final void setViewBtn(boolean modif) {
        if (this.isModif == null || this.isModif != modif) {
            this.isModif = modif;
            //this.viewFrame = null;
            if (this.isModif) {
                this.viewButton.setToolTipText("Modifier");
                this.viewButton.setIcon(getModifIcon());
            }
            // each time its icon change, otherwise (at least on Mac) the opacity is wrong
        }
    }

    private static Icon getModifIcon() {
        return iconModif;
    }

    private static Icon getAddIcon() {
        return iconAdd;
    }

    private static Icon getListIcon() {
        return iconList;
    }
    private final JButton viewButton = new JButton();
    private final JButton listButton = new JButton();
    private final JButton addButton = new JButton();
    private boolean minimal = false;

    public ElementBoxPanel(String elements) {

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0;
        c.weightx = 1;
        //initIcons();
        if (!this.minimal) {
            c.weightx = 0;
            c.gridx++;

            this.viewButton.setPreferredSize(new Dimension(24, 16));
            //this.setCanModify(true);
            this.viewButton.setIcon(new ImageIcon("src\\assets\\BoxPanel\\pen.png"));
            IListButton.initButton(this.viewButton);
            this.viewButton.setToolTipText("Modifier " + elements);
            DefaultGridBagConstraints.lockMinimumSize(this.viewButton);
            this.viewButton.setEnabled(false);
            add(this.viewButton, c);
            c.gridx++;

            // Add button
            this.addButton.setPreferredSize(new Dimension(24, 16));
            this.addButton.setIcon(new ImageIcon("src\\assets\\BoxPanel\\add.png"));
            IListButton.initButton(this.addButton);
            this.addButton.setToolTipText("Créer " + elements);
            DefaultGridBagConstraints.lockMinimumSize(this.addButton);
            add(this.addButton, c);
            addButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    addActionPerformed(evt);
                }
            });
            c.gridx++;

            this.listButton.setPreferredSize(new Dimension(24, 16));
            this.listButton.setIcon(new ImageIcon("src\\assets\\BoxPanel\\liste.png"));
            IListButton.initButton(this.listButton);
            this.listButton.setToolTipText("Lister les " + elements);
            DefaultGridBagConstraints.lockMinimumSize(this.listButton);
            listButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    listActionPerformed(evt);
                }
            });
            c.gridx++;
            add(this.listButton, c);


//            this.addButton.addActionListener(this);
//            this.viewButton.addActionListener(this);
//            this.listButton.addActionListener(this);
        }
    }

    private void updateViewBtn() {
        final boolean modif, enabled;
//        if (getEnabled() == ComboMode.DISABLED || this.getSelectedId() < SQLRow.MIN_VALID_ID) {
//            // disabled
//            modif = this.canModif;
//            enabled = false;
//        } else if (this.canModif && getEnabled() == ComboMode.EDITABLE) {
//            // modification enabled
//            modif = true;
//            enabled = true;
//        } else {
        // view enabled
        modif = false;
        enabled = true;
//        }
        this.setViewBtn(modif);
        this.viewButton.setEnabled(enabled);
    }

    private void valueChanged() {
        updateViewBtn();
//        if (this.viewFrame != null) {
//            // changer la frame du détail
//            this.viewFrame.selectionId(this.getSelectedId());
//        }
//        if (this.listFrame != null) {
//            this.listFrame.getPanel().getListe().selectID(this.getSelectedId());
//        }
    }

    public void setInfoIconVisible(boolean b) {
        this.viewButton.setVisible(b);
    }

    public void setListIconVisible(boolean b) {
        this.listButton.setVisible(b);
    }

    public void setAddIconVisible(boolean b) {
        this.addButton.setVisible(b);
    }

    public void setButtonsVisible(boolean b) {
        this.setInfoIconVisible(b);
        this.setListIconVisible(b);
        this.setAddIconVisible(b);
    }

    public final boolean isUpdating() {
        return this.isUpdating();
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            AddFrame frm = new AddFrame("Ajouter Famille", 1);
        } catch (HeadlessException | IOException ex) {
            Logger.getLogger(tabbed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            AddFrame frm = new AddFrame("Ajouter Famille", 1);
        } catch (HeadlessException | IOException ex) {
            Logger.getLogger(tabbed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listActionPerformed(java.awt.event.ActionEvent evt) {
            
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
