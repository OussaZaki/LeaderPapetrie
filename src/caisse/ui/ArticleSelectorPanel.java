package caisse.ui;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class ArticleSelectorPanel extends JPanel {
    ArticleSelectorPanel(CaisseControler controller) {
        final ArticleSelector comp = new ArticleSelector(controller);
        this.setLayout(new GridLayout(0, 2));
        this.add(new CategorieSelector(controller, comp.getModel()));

        this.add(comp);
       
    }

}
