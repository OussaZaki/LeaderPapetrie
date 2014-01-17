package caisse.ui;

import caisse.model.Article;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.math.RoundingMode;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class ArticleListCellRenderer implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        final Article article = (Article) value;
        JPanel p = new JPanel() {
            @Override
            public void paint(Graphics g) {
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g);
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        };
        p.setOpaque(true);
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        JLabel l = new JLabel(" " + article.getName());
        p.setOpaque(true);
        if (isSelected) {
            p.setBackground(new Color(232, 242, 254));
            p.setForeground(Color.BLACK);
        } else {
            p.setBackground(Color.WHITE);
            p.setForeground(Color.GRAY);
        }

        c.gridwidth = 1;
        c.weightx = 1;
        p.add(l, c);
        c.gridx++;
        c.weightx = 0;
        final JLabel l2 = new JLabel(article.getPrice() + " Dhs");
        p.add(l2, c);
        l.setFont(l.getFont().deriveFont(24f));
        l2.setFont(l2.getFont().deriveFont(24f));
        return p;
    }
}