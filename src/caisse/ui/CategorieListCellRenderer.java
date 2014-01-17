package caisse.ui;

import caisse.model.FamilleArticle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CategorieListCellRenderer implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel l = new JLabel(" " + ((FamilleArticle) value).getName()) {
            @Override
            public void paint(Graphics g) {
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g);
                g.setColor(Color.LIGHT_GRAY);
//                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        };
        l.setOpaque(true);
        if (isSelected) {
            l.setBackground(new Color(232, 242, 254));
            l.setForeground(Color.BLACK);
        } else {
            l.setBackground(Color.WHITE);
            l.setForeground(Color.GRAY);
        }
        l.setFont(l.getFont().deriveFont(24f));
        return l;
    }
}
