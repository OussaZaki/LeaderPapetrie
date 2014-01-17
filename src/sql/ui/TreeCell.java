package sql.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultTreeCellRenderer;


/**
 *
 * @author Oussama
 */
public class TreeCell extends DefaultTreeCellRenderer implements ActionListener, MouseListener {

    private JLabel label;
    private JPanel panel;
    private Object value;
    final ImageIcon leafIcon = new ImageIcon("/assets/BoxPanel/famille.png");

    public TreeCell() {
        this.setLeafIcon(null);
        this.setClosedIcon(leafIcon);
        this.setOpenIcon(leafIcon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String val = value.toString();
        System.out.println("Pressed: " + val);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        String val = value.toString();
        System.out.println("Clicked: " + val);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}