package MainPackage;

import java.awt.HeadlessException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Oussama
 */
public class MainWindow extends JFrame {

    private String BackgroundLocation1 = "src\\assets\\Interfaces\\interface17.jpg";
    private String BackgroundLocation2 = "src\\assets\\Interfaces\\interface1.jpg";
    private String LogoLocation = "src\\assets\\LogoTitle\\logo.png";
    private String MyTitle = "LeaderPapetrie v1.0 Beta";

    public MainWindow(Boolean adm) throws HeadlessException, IOException {
        try {
            UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        if(adm)
            this.getContentPane().add(new MainPanel(BackgroundLocation1));
        else
            this.getContentPane().add(new MainSimplePanel(BackgroundLocation2));
        this.setVisible(true);
        this.setTitle(MyTitle);
        this.setIconImage(new ImageIcon(LogoLocation).getImage());
        this.setSize(900, 628);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
}
