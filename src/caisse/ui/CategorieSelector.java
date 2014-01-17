package caisse.ui;

import caisse.model.Article;
import caisse.model.FamilleArticle;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CategorieSelector extends JPanel implements ListSelectionListener, CaisseListener {

    private CategorieModel model;
    private JList list;
    private StatusBar comp;
    private FamilleArticle previous;
    private ArticleModel articleModel;
    private CaisseControler controller;

    CategorieSelector(CaisseControler controller, final ArticleModel articleModel) {
        this.articleModel = articleModel;
        this.controller = controller;

        controller.addCaisseListener(this);

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        this.comp = new StatusBar();
        this.comp.setTitle("Catégories");
        this.add(this.comp, c);

        c.weighty = 1;
        c.gridy++;
        this.model = new CategorieModel();
        this.model.setRoot(null);
        this.list = new JList(this.model);
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.list.setCellRenderer(new CategorieListCellRenderer());
        this.list.setFixedCellHeight(64);
        this.add(this.list, c);
        this.list.getSelectionModel().addListSelectionListener(this);

        this.comp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // User pressed "Previous" button on category
                final FamilleArticle newCategory = CategorieSelector.this.previous;
                CategorieSelector.this.model.setRoot(newCategory);
                CategorieSelector.this.list.clearSelection();
                if (newCategory == null) {
                    CategorieSelector.this.comp.setTitle("Catégories");
                    CategorieSelector.this.comp.setPrevious(false);
                    CategorieSelector.this.previous = null;
                } else {
                    CategorieSelector.this.comp.setTitle(newCategory.getName());
                    CategorieSelector.this.comp.setPrevious(true);
                    CategorieSelector.this.previous = newCategory.getParent();
                }
                articleModel.setCategorie(newCategory);
            }
        });
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        final Object sel = this.list.getSelectedValue();
        if (sel != null && !e.getValueIsAdjusting()) {
            final FamilleArticle c = (FamilleArticle) sel;
            if (!c.getSubCategories().isEmpty()) {
                // Descend la hierarchie
                this.previous = this.model.getRoot();
                this.model.setRoot(c);
                this.comp.setTitle(c.getName());
                this.comp.setPrevious(true);
                this.list.clearSelection();
            }
            this.articleModel.setCategorie(c);
            this.controller.setArticleSelected(null);
        }
    }

    @Override
    public void caisseStateChanged() {
        final Article articleSelected = this.controller.getArticleSelected();
        if (articleSelected != null) {
            final FamilleArticle c = articleSelected.getCategorie();
            if (c.getParent() != null) {
                this.previous = c.getParent().getParent();
                this.model.setRoot(c.getParent());
                this.comp.setTitle(c.getParent().getName());
            }
            this.comp.setPrevious(true);
            this.list.setSelectedValue(c, true);
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.GRAY);
        g.drawLine(0, 0, 0, this.getHeight());
    }
}