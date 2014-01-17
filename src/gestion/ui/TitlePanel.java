package gestion.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Oussama
 */
class TitlePanel extends JPanel {
    
    private Image backgroundImage;
    private String LogoLocation = "src\\assets\\main\\title.jpg";
    
    public TitlePanel() throws IOException {
        backgroundImage = ImageIO.read(new File(LogoLocation));
        this.setBackground(new java.awt.Color(255, 255, 255));
        this.setBorder(new javax.swing.border.EmptyBorder(18, 0, 17, 5));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image.
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
