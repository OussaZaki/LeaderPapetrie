package LoginTest;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import sql.HibernateUtil;
import sql.model.User;
import MainPackage.MainButtons;
import MainPackage.MainWindow;
import gestion.ui.articlePan;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sql.model.Article;
import sql.model.FamilleArticle;
import sql.model.Fournisseur;
import sql.model.VousEtesCo;
import sql.model.mySession;

public class LoginPan extends javax.swing.JPanel {

    public static List<FamilleArticle> FamilletList;
    public static List<FamilleArticle> sousFamilletList;
    public static List<Fournisseur> fournisseurList;
    public static List<Article> ArticleList;
    public static List<User> allUsersList;
    public static List<User> userList;
    private static String QUERY_BASED_ON_userName = "from User a where a.userName like '";
    private javax.swing.JButton CloseButton;
    private javax.swing.JButton reduire;
    private Image backgroundImage;
    private Point initialClick;
    private JFrame parent;
    protected TimerThread timerThread;

    private void runQuery() throws HeadlessException, IOException {
        wrongId.setText(" ");
        wrongPwd.setText(" ");
        executeHQLQuery(QUERY_BASED_ON_userName + Login.getText() + "'");
    }

    static public void runQueryFamille() throws HeadlessException, IOException {
        Query q = mySession.session.createQuery("from FamilleArticle a where a.familleArticle is null");
        FamilletList = q.list();
    }

    static public void runQuerySousFamille() throws HeadlessException, IOException {
        Query qu = mySession.session.createQuery("from FamilleArticle a where a.familleArticle is not null");
        sousFamilletList = qu.list();
    }

    static public void runQueryFournisseur() throws HeadlessException, IOException {
        Query qe = mySession.session.createQuery("from Fournisseur");
        fournisseurList = qe.list();
    }

    static public void runQueryArticle() throws HeadlessException, IOException {
        Query qy = mySession.session.createQuery("from Article");
        ArticleList = qy.list();
    }

    private void executeHQLQuery(String hql) throws HeadlessException, IOException {
        if (Login.getText().equals("")) {
            wrongId.setText("* Veuillez entrer un identifiant");
        } else {
            try {
                mySession.setSession();
                Query q = mySession.session.createQuery(hql);
                runQueryFamille();
                runQuerySousFamille();
                runQueryFournisseur();
                runQueryArticle();
                List<User> resultList = q.list();
                if (resultList.isEmpty()) {
                    wrongId.setText("* Identifiant inexistant");
                } else {
                    for (User u : resultList) {
                        if (!u.getPsswd().equals(psw.getText())) {
                            wrongPwd.setText("* Mot de passe incorrect");
                        } else {
                            VousEtesCo.setUtilisateur(u);
                            parent.setVisible(false);
                            MainWindow fen = new MainWindow(u.getIsAdmin());
                            parent.dispose();
                        }
                    }
                }
                //displayResult(resultList);
                mySession.session.getTransaction().commit();
            } catch (HibernateException he) {
            }
        }
    }

    public LoginPan(String fileName, final JFrame parent) throws IOException {

        this.parent = parent;
        backgroundImage = ImageIO.read(new File(fileName));
        this.setLayout(null);
        CloseButton = new MainButtons(new ImageIcon("src\\assets\\Login\\close.png"));
        CloseButton.setFocusable(true);
        CloseButton.setContentAreaFilled(false);
        CloseButton.setCursor(Cursor.getPredefinedCursor(12));
        CloseButton.setBorder(null);
        CloseButton.setFocusPainted(false);
        this.add(CloseButton);
        CloseButton.setBounds(430, 8, 30, 30);
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (JOptionPane.showConfirmDialog(null, "êtes vous sûr de vouloir quitter?", "Vérification", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        reduire = new MainButtons(new ImageIcon("src\\assets\\Login\\reduire.png"));
        reduire.setFocusable(true);
        reduire.setContentAreaFilled(false);
        reduire.setCursor(Cursor.getPredefinedCursor(12));
        reduire.setBorder(null);
        reduire.setFocusPainted(false);
        this.add(reduire);
        reduire.setBounds(405, 8, 30, 30);

        reduire.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isPressed()) {
                    parent.setState(parent.ICONIFIED);
                }
            }
        });
        initComponents();
        parent.getRootPane().setDefaultButton(connexion);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                // get location of Window
                int thisX = parent.getLocation().x;
                int thisY = parent.getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
                int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                parent.setLocation(X, Y);
            }
        });

        timerThread = new TimerThread(dateLabel, timeLabel);
        timerThread.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        psw = new javax.swing.JPasswordField();
        Login = new javax.swing.JTextField();
        loginTF = new javax.swing.JLabel();
        pswTF = new javax.swing.JLabel();
        connexion = new javax.swing.JButton();
        welcome = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        wrongId = new javax.swing.JLabel();
        wrongPwd = new javax.swing.JLabel();

        psw.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        psw.setToolTipText("Veuillez entrer votre mot de passe");
        psw.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        Login.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Login.setToolTipText("Veuillez entrer votre identifiant");
        Login.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        loginTF.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        loginTF.setText("Identifiant");

        pswTF.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pswTF.setText("Mot de passe");

        connexion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        connexion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Login/connexion.png")));
        connexion.setBorderPainted(false);
        connexion.setContentAreaFilled(false);
        connexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connexionActionPerformed(evt);
            }
        });
        connexion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                connexionKeyPressed(evt);
            }
        });

        welcome.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
        welcome.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        welcome.setText("Veuillez-vous connecter");

        dateLabel.setForeground(new java.awt.Color(255, 255, 255));

        timeLabel.setForeground(new java.awt.Color(255, 255, 255));

        wrongId.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        wrongId.setForeground(new java.awt.Color(255, 0, 0));
        wrongId.setText("  ");

        wrongPwd.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        wrongPwd.setForeground(new java.awt.Color(255, 0, 0));
        wrongPwd.setText("  ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(welcome)
                        .addGap(148, 148, 148))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pswTF)
                            .addComponent(loginTF))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Login)
                                .addComponent(psw, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(wrongPwd)
                                    .addComponent(wrongId))))
                        .addGap(84, 84, 84))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(dateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(connexion, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dateLabel)
                        .addComponent(timeLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(welcome)
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(loginTF))
                        .addGap(0, 0, 0)
                        .addComponent(wrongId, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(psw, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pswTF))
                        .addGap(0, 0, 0)
                        .addComponent(wrongPwd, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(connexion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void connexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connexionActionPerformed
        try {
            runQuery();
        } catch (HeadlessException | IOException ex) {
            Logger.getLogger(LoginPan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_connexionActionPerformed

    private void connexionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_connexionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                runQuery();
            } catch (HeadlessException | IOException ex) {
                Logger.getLogger(LoginPan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_connexionKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Login;
    private javax.swing.JButton connexion;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel loginTF;
    private javax.swing.JPasswordField psw;
    private javax.swing.JLabel pswTF;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel welcome;
    private javax.swing.JLabel wrongId;
    private javax.swing.JLabel wrongPwd;
    // End of variables declaration//GEN-END:variables

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
