package caisse.ui;

import caisse.Caisse;
import caisse.model.Ticket;


import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class CaisseFrame extends JFrame {

    CaissePanel t;
     private String LogoLocation = "src\\assets\\LogoTitle\\logo.png";
    private String MyTitle = "LeaderPapetrie - Caisse";
    private Image backgroundImage;
    
    public CaisseFrame() throws IOException {
        backgroundImage = ImageIO.read(new File(LogoLocation));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(MyTitle);
        setIconImage(backgroundImage);
        t = new CaissePanel(this);
        setContentPane(t);
        setFocusable(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Caisse.createConnexion();
                    CaisseFrame f = new CaisseFrame();
                    f.setUndecorated(true);
                    f.pack();
                    f.setLocation(0, 0);
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    f.setSize(screenSize);
                    f.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(CaisseFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void showMenu() {
        System.out.println("CaisseFrame.showMenu()");
        this.invalidate();
        this.setContentPane(new CaisseMenuPanel(this));
        this.validate();
        this.repaint();

    }

    public void showCaisse() {
        System.out.println("CaisseFrame.showCaisse()");
        this.invalidate();
        this.setContentPane(this.t);
        this.validate();
        this.repaint();

    }

    public void showTickets(Ticket t) {
        System.out.println("CaisseFrame.showMenu()");
        this.invalidate();
        ListeDesTicketsPanel panel = new ListeDesTicketsPanel(this);
        panel.setSelectedTicket(t);
        this.setContentPane(panel);
        this.validate();
        this.repaint();

    }
}
