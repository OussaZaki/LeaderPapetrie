package MainPackage;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import javax.swing.JToolTip;

/**
 *
 * @author Oussama
 */
public class CustomTipTool extends JToolTip {

    public CustomTipTool() {
        super();
        resize(150,50);
    }

    @Override
    public void paintComponent(Graphics g) {
          
        // set the parent to not be opaque
        Component parent = this.getParent();
        if (parent != null) {
            if (parent instanceof JComponent) {
                JComponent jparent = (JComponent) parent;
                if (jparent.isOpaque()) {
                    jparent.setOpaque(false);
                }
            }
        }
        // create a round rectangle
        Shape round;
        round = new Rectangle2D.Float(0, 0, 150, 50);

        // draw the white background
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.white);
        g2.fill(round);

        // draw the text
        String text = this.getComponent().getToolTipText();
        if (text != null) {
            FontMetrics fm = g2.getFontMetrics();
            int h = fm.getAscent();
            g2.setColor(Color.black);
            g2.drawString(text, 5, (this.getHeight() + h) / 2);
        }
    }
}
