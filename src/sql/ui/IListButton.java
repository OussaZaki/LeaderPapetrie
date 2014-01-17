package sql.ui;

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author ilm
 * 
 *         TODO To change the template for this generated type comment go to Window - Preferences -
 *         Java - Code Style - Code Templates
 */
public class IListButton extends JButton {
    private static ImageIcon icon = null;

    /**
     *  
     */
    public IListButton() {
        super();
        init();
    }

    /**
     * @param text
     */
    public IListButton(String text) {
        super(text);
        init();
    }

    /**
     * @param text
     * @param icon
     */
    public IListButton(String text, Icon icon) {
        super(text, icon);
        init();
    }

    /**
     * @param a
     */
    public IListButton(Action a) {
        super(a);
        init();
    }

    /**
     * @param icon
     */
    public IListButton(Icon icon) {
        super(icon);
        init();
    }

    private final void init() {
        if (icon == null) {
            icon = new ImageIcon("src\\assets\\BoxPanel\\liste.png");
        }
        setIcon(icon);
        setPreferredSize(new Dimension(24, 16));
        initButton(this);
    }

    public static final void initButton(JButton b) {
        b.setBorder(null);
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
    }
}
