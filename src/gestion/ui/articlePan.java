package gestion.ui;

import LoginTest.LoginPan;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import org.hibernate.Query;
import sql.model.FamilleArticle;
import sql.model.mySession;
import sql.ui.AddFrame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.ExpandVetoException;
import sql.model.Article;
import sql.ui.ComboBoxFamille;
import sql.ui.ComboBoxSousFamille;

/**
 *
 * @author Oussama
 */
public class articlePan extends JPanel implements TreeExpansionListener, TreeWillExpandListener {

    private static javax.swing.JTree familleTree;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable resultTable;
    static DefaultMutableTreeNode root = new DefaultMutableTreeNode("Familles");
    static DefaultTreeModel treeModel;
    static ModelArticle model;

    public articlePan() {
        familleTree = new javax.swing.JTree();
        try {
            model = new ModelArticle();
            update();
            updateTable();
            init();
        } catch (HeadlessException | IOException ex) {
            Logger.getLogger(articlePan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setToModel() {
        familleTree.setModel(treeModel);
    }

    public static void update() throws HeadlessException, IOException {
        root.removeFromParent();
        root.removeAllChildren();
        LoginPan.runQueryFamille();
        treeModel = new DefaultTreeModel(root);
        for (FamilleArticle fam : LoginPan.FamilletList) {
            mySession.session.refresh(fam);
            DefaultMutableTreeNode curFam = new DefaultMutableTreeNode(fam);
            root.add(curFam);
            for (FamilleArticle fa : fam.getFamilleArticles()) {
                if(fa.getFamilleArticle().equals(fam))
                    curFam.add(new DefaultMutableTreeNode(fa));
            }
        }
        familleTree.setModel(treeModel);
        ComboBoxFamille.updateListe();
        ComboBoxSousFamille.updateListe();
    }
    
    public static void updateTable() throws HeadlessException, IOException {
        int rowCount=model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            model.removeRow(0);
        }
        model.removeTableModelListener(null);
        LoginPan.runQueryArticle();
        for (Article article : LoginPan.ArticleList) {
            model.addRow(new Object[]{article, article.getFamilleArticle(), article.getPAchat(), article.getPVente(), article.getQteStock(), article.getQteMin()});
        }
    }
    
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = tabbed.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            AddFrame frm = new AddFrame("Ajouter Famille", 1);
        } catch (HeadlessException | IOException ex) {
            Logger.getLogger(tabbed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String toModify = familleTree.getLastSelectedPathComponent().toString();
            AddFrame frm = new AddFrame("Modifier Famille", 2, toModify);
        } catch (HeadlessException | IOException ex) {
            Logger.getLogger(articlePan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        String ToDelete = familleTree.getLastSelectedPathComponent().toString();
        String hql = "delete from FamilleArticle where nomFamille = :name";
        mySession.setSession();
        Query query = mySession.session.createQuery(hql);
        query.setString("name", ToDelete);
        int rowCount = query.executeUpdate();
        mySession.session.getTransaction().commit();
        if (rowCount != 0) {
            try {
                String show = "la Famille " + ToDelete + " a bien été supprimé";
                JOptionPane d = new JOptionPane();
                d.showMessageDialog(new JFrame(), show,
                        "Famille supprimée", JOptionPane.INFORMATION_MESSAGE);
                update();
            } catch (HeadlessException | IOException ex) {
                Logger.getLogger(articlePan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            //String toModify = familleTree.getLastSelectedPathComponent().toString();
            AddFrame frm = new AddFrame("Ajouter Article", 3);
        } catch (HeadlessException | IOException ex) {
            Logger.getLogger(articlePan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Not yet");
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Not yet");
    }

    private void init() {
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeModel = new DefaultTreeModel(root);

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        this.setBackground(new java.awt.Color(255, 255, 255));
        this.setPreferredSize(new java.awt.Dimension(810, 509));

        jSplitPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jSplitPane1.setAutoscrolls(true);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        familleTree.setModel(treeModel);
        familleTree.setEditable(true);
        ImageIcon leafIcon = createImageIcon("/assets/BoxPanel/famille.png");
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(leafIcon);
        renderer.setOpenIcon(leafIcon);
        renderer.addMouseListener(null);
        familleTree.setCellRenderer(renderer);
        familleTree.addTreeWillExpandListener(this);
        familleTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                jButton2.setEnabled(false);
                jButton3.setEnabled(false);
                String comp = familleTree.getLastSelectedPathComponent() != null
                        ? familleTree.getLastSelectedPathComponent().toString() : null;
                if (comp != null) {
                    if (!comp.equals("Familles")) {
                        boolean test = false;
                        for (FamilleArticle fam : LoginPan.FamilletList) {
                            if(!fam.getFamilleArticles().isEmpty())
                                if (fam.getNomFamille().equals(familleTree.getLastSelectedPathComponent().toString())) {
                                test = true;
                            }
                        }
                        if (!test) {
                            jButton2.setEnabled(true);
                            jButton3.setEnabled(true);
                        }
                    }
                }
            }
        });

        jScrollPane1.setViewportView(familleTree);

        jButton1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jButton1.setText("Ajouter");
        jButton1.setMargin(new java.awt.Insets(3, 5, 3, 5));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jButton2.setText("Modifier");
        jButton2.setMargin(new java.awt.Insets(3, 5, 3, 5));
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jButton3.setText("Supprimer");
        jButton3.setMargin(new java.awt.Insets(3, 5, 3, 5));
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(jScrollPane1));
        jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton1)
                .addComponent(jButton2)
                .addComponent(jButton3))
                .addGap(5, 5, 5)));

        jSplitPane1.setLeftComponent(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setPreferredSize(new java.awt.Dimension(810, 507));

        jScrollPane2.setAutoscrolls(true);

        JScrollPane jsc = new JScrollPane(resultTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultTable.setModel(model);
        resultTable.setRowHeight(20);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(130);
        resultTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        resultTable.setDragEnabled(true);
        resultTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(resultTable);
        resultTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Liste des articles");

        jButton4.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jButton4.setText("Ajouter");
        jButton4.setMargin(new java.awt.Insets(3, 5, 3, 5));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jButton5.setText("Modifier");
        jButton5.setMargin(new java.awt.Insets(3, 5, 3, 5));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jButton6.setText("Supprimer");
        jButton6.setMargin(new java.awt.Insets(3, 5, 3, 5));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Rechercher");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(394, Short.MAX_VALUE)));
        jPanel10Layout.setVerticalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(100, 220, 280)
                .addComponent(jLabel3))
                .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(50, Short.MAX_VALUE)));
        jPanel9Layout.setVerticalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap()));

        jSplitPane1.setRightComponent(jPanel9);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1052, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));

        jTabbedPane1.addTab("", new javax.swing.ImageIcon(getClass().getResource("/assets/Main/button1.png")), this); // NOI18N

    }

    @Override
    public void treeExpanded(TreeExpansionEvent tee) {
        try {
            update();
        } catch (HeadlessException | IOException ex) {
            Logger.getLogger(articlePan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void treeCollapsed(TreeExpansionEvent tee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void treeWillExpand(TreeExpansionEvent tee) throws ExpandVetoException {
    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent tee) throws ExpandVetoException {
    }
}
