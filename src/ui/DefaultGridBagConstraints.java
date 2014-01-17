package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JComponent;

public class DefaultGridBagConstraints extends GridBagConstraints {

    /**
     * Contraintes par dÃ©faut pour les UI
     */
    private static final long serialVersionUID = 3654011883969624207L;

    public DefaultGridBagConstraints() {
        this.gridx = 0;
        this.gridy = 0;
        this.insets = getDefaultInsets();
        this.fill = GridBagConstraints.HORIZONTAL;
        this.anchor = GridBagConstraints.WEST;
    }

    public static Insets getDefaultInsets() {
        return new Insets(2, 3, 2, 2);
    }

    public static void lockMinimumSize(JComponent c) {
        c.setMinimumSize(new Dimension(c.getPreferredSize()));
    }

    public static void lockMaximumSize(JComponent c) {
        c.setMaximumSize(new Dimension(c.getPreferredSize()));
    }
}
