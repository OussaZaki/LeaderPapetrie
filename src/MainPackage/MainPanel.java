package MainPackage;

import caisse.ui.CaisseFrame;
import gestion.ui.GestionFrame;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ToolTipManager;
import users.GestionUsers;

/**
 *
 * @author Oussama
 */
public class MainPanel extends JPanel {

    public int i = 0;
    private Image backgroundImage;
    private JButton buttons[];
    private String icons[];
    private String hint[] = {"Utilisateurs", "Gestion", "Réglage", "Caisse", "Aide", "Quitter"};
    private final int nbrButton = 6;
    private JToolTip _tooltip[];
    private String nameButton = "src\\assets\\Buttons\\button";

    public MainPanel(String fileName) throws IOException {
        ToolTipManager.sharedInstance().setInitialDelay(5);
        backgroundImage = ImageIO.read(new File(fileName));
        this.setLayout(null);


        // Creat Icons & Hints
        icons = new String[3 * nbrButton];
        for (i = 0; i < 3 * nbrButton; i++) {
            icons[i] = nameButton + i + ".png";
        }

        buttons = new JButton[nbrButton];

        for (i = 0; i < nbrButton; i++) {
            buttons[i] = new MainButtons(new ImageIcon(icons[3 * i]));
            buttons[i].setFocusable(true);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setCursor(Cursor.getPredefinedCursor(12));
            buttons[i].setBorder(null);
            buttons[i].setFocusPainted(false);
            buttons[i].setToolTipText(hint[i]);
            this.add(buttons[i]);
        }

        buttons[5].setBounds(748, 484, 90, 90);
        buttons[4].setBounds(610, 484, 90, 90);
        buttons[3].setBounds(477, 484, 90, 90);
        buttons[2].setBounds(748, 397, 90, 90);
        buttons[1].setBounds(610, 397, 90, 90);
        buttons[0].setBounds(477, 397, 90, 90);

        buttons[5].addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (JOptionPane.showConfirmDialog(null, "êtes vous sûr de vouloir quitter?", "Vérification", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        buttons[5].getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                    buttons[5].setIcon(new ImageIcon(icons[16]));
                } else {
                    buttons[5].setIcon(new ImageIcon(icons[15]));
                }
                if (model.isPressed()) {
                    buttons[5].setIcon(new ImageIcon(icons[17]));
                }
            }
        });
        buttons[4].getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                    buttons[4].setIcon(new ImageIcon(icons[13]));
                } else {
                    buttons[4].setIcon(new ImageIcon(icons[12]));
                }
                if (model.isPressed()) {
                    buttons[4].setIcon(new ImageIcon(icons[14]));
                }
            }
        });
        buttons[3].addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CaisseFrame.main(new String[]{""});
            }
        });
        buttons[3].getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                    buttons[3].setIcon(new ImageIcon(icons[10]));
                } else {
                    buttons[3].setIcon(new ImageIcon(icons[9]));
                }
                if (model.isPressed()) {
                    buttons[3].setIcon(new ImageIcon(icons[11]));
                }
            }
        });
        buttons[2].getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                    buttons[2].setIcon(new ImageIcon(icons[7]));
                } else {
                    buttons[2].setIcon(new ImageIcon(icons[6]));
                }
                if (model.isPressed()) {
                    buttons[2].setIcon(new ImageIcon(icons[8]));
                }
            }
        });
        buttons[1].addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GestionFrame gestion = null;
                try {
                    gestion = new GestionFrame();
                } catch (IOException ex) {
                    Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        buttons[1].getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                    buttons[1].setIcon(new ImageIcon(icons[4]));
                } else {
                    buttons[1].setIcon(new ImageIcon(icons[3]));
                }
                if (model.isPressed()) {
                    buttons[1].setIcon(new ImageIcon(icons[5]));
                }
            }
        });

        buttons[0].addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GestionUsers.main(new String[]{""});

            }
        });
        buttons[0].getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                    buttons[0].setIcon(new ImageIcon(icons[1]));

                } else {
                    buttons[0].setIcon(new ImageIcon(icons[0]));
                }
                if (model.isPressed()) {
                    buttons[0].setIcon(new ImageIcon(icons[2]));
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image.
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
