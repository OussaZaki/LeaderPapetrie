package MainPackage;

import LoginTest.LoginFrame;
import gestion.ui.GestionFrame;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) throws HeadlessException, IOException {
        try {
            UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    LoginFrame lg = new LoginFrame();
                } catch (HeadlessException | IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}