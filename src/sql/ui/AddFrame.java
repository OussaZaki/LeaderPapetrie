package sql.ui;

import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import sql.model.Article;

/**
 *
 * @author Oussama
 */
public class AddFrame extends JFrame {

    int type_pan;
    private String LogoLocation = "src\\assets\\LogoTitle\\logo.png";
    private Image backgroundImage;
    private String check;

    public AddFrame() throws HeadlessException {
    }

    public AddFrame(String string, int type_pan) throws HeadlessException, IOException {
        super(string);
        backgroundImage = ImageIO.read(new File(LogoLocation));
        setIconImage(backgroundImage);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        switch (type_pan) {
            case 1: {
                this.setSize(400, 180);
                this.setLocationRelativeTo(null);
                this.setResizable(true);
                this.add(new AddFamille(this));
                this.setVisible(true);
                break;
            }
            case 3: {
                this.setSize(470, 410);
                this.setLocationRelativeTo(null);
                this.setResizable(true);
                this.add(new AddArticle(this));
                this.setVisible(true);
                break;
            }
        }
    }

    public AddFrame(String string, int type_pan, String check) throws HeadlessException, IOException {
        super(string);
        this.check = check;
        backgroundImage = ImageIO.read(new File(LogoLocation));
        setIconImage(backgroundImage);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        switch (type_pan) {
            case 2: {
                this.setSize(400, 180);
                this.setLocationRelativeTo(null);
                this.setResizable(false);
                this.add(new modifyFamille(this, check));
                this.setVisible(true);
                break;
            }
        }
    }

    public AddFrame(String string, Article modifyMe) throws HeadlessException, IOException {
        super(string);
        this.check = check;
        backgroundImage = ImageIO.read(new File(LogoLocation));
        setIconImage(backgroundImage);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(400, 180);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(new modifyArticle(this, modifyMe));
        this.setVisible(true);
    }
}
