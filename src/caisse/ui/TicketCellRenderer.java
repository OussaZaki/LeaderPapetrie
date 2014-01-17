package caisse.ui;

//import org.openconcerto.erp.core.finance.tax.model.TaxeCache;
import caisse.model.Article;
import ui.touch.ScrollableList;
import utils.Pair;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

public class TicketCellRenderer implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Pair<Article, Integer> item = (Pair<Article, Integer>) value;
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        final JLabel l1 = new JLabel(item.getSecond().toString(), SwingConstants.RIGHT);

        p.add(l1, c);
        c.gridx++;
        c.weightx = 1;
        Article article = item.getFirst();
        final JLabel l2 = new JLabel(article.getName().toUpperCase(), SwingConstants.LEFT);
        p.add(l2, c);
        c.gridx++;
        c.weightx = 0;

        
        float priceT = article.getPrice()*item.getSecond();
        final JLabel l3 = new JLabel(String.valueOf(priceT), SwingConstants.RIGHT);
        p.add(l3, c);

        //
        l1.setOpaque(false);
        l2.setOpaque(false);
        l3.setOpaque(false);

        if (isSelected) {
            p.setOpaque(true);
            p.setBackground(new Color(232, 242, 254));
        } else {
            p.setOpaque(false);
        }
        // l2.setFont(f);
        l1.setFont(new Font("Arial", Font.PLAIN, 18));
        l2.setFont(new Font("Arial", Font.PLAIN, 18));
        l3.setFont(new Font("Arial", Font.PLAIN, 18));

        return p;
    }
    
    // Jouer sur l'affichage du ticket
    public void paint(Graphics g, ScrollableList list, Object value, int index, boolean isSelected) {
        @SuppressWarnings("unchecked")
        final Pair<Article, Integer> item = (Pair<Article, Integer>) value;

        if (isSelected) {
            g.setColor(new Color(232, 242, 254));
            g.fillRect(0, 0, list.getWidth(), list.getCellHeight());
        }
        g.setColor(Color.BLACK);

        final int inset = 5;
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        final int height = g.getFontMetrics().getMaxAscent() + g.getFontMetrics().getMaxDescent() + inset;
        
        // represente le nombre d'article
        final String s1 = item.getSecond().toString();
        g.drawString(s1, inset, height);
        final int width1 = (int) g.getFontMetrics().getStringBounds("999 ", g).getWidth() + inset * 2;
        
        // l'Article selectionné
        Article article = item.getFirst();
        // Le nom à afficher
        String s2 = article.getName().toUpperCase();
        final int maxLength = 18;
        // l'abréviation si c'est long
        if (s2.length() > maxLength)
            s2 = s2.substring(0, maxLength + 1) + "...";
        g.drawString(s2, width1 + inset, height);
     
        final String s3 = String.valueOf(article.getPrice());
        final int width3 = (int) g.getFontMetrics().getStringBounds(s3, g).getWidth() + inset * 2;
        g.drawString(s3, list.getWidth() - width3, height);
    }

}
