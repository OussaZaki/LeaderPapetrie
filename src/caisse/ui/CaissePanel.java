package caisse.ui;

import LoginTest.LoginPan;
import caisse.model.Article;
import caisse.model.FamilleArticle;
import caisse.model.Ticket;
import utils.ExceptionHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class CaissePanel extends JPanel implements CaisseListener {

    private CaisseControler controler;
    private StatusBar st;
    private Ticket leTicket;
    
    public CaissePanel(final CaisseFrame caisseFrame) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);
        this.setOpaque(isOpaque());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;

        this.controler = new CaisseControler(caisseFrame);

        c.fill = GridBagConstraints.HORIZONTAL;
        this.st = new StatusBar("toolbar.png", "toolbar_menu.png");
        this.st.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getX() < 110) {
                        CaissePanel.this.controler.saveAndClearTicket();
                } else if (e.getX() > 165 && e.getX() < 275) {
                    // Menu
                    try {
                        caisseFrame.showMenu();
                    } catch (Throwable ex) {
                        ExceptionHandler.handle("Erreur d'affichage du menu", ex);
                    }
                }

            }
        });
        this.st.setPrevious(true);
        this.add(this.st, c);
        
        TicketPanel t = new TicketPanel(this.controler);
        //fillExampleArticle();
        loadArticles();
        c.gridx = 0;
        c.gridy++;
        c.weightx = 0;
        c.weighty = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.fill = GridBagConstraints.NONE;
        this.add(t, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx++;
        c.weightx = 1;
        c.gridy--;
        c.gridheight = 2;
        this.add(new ArticleSelectorPanel(this.controler), c);

        c.gridx++;
        c.weightx = 0;
        this.add(new PaiementPanel(this.controler), c);
        this.controler.addCaisseListener(this);
    }

    private void fillExampleArticle() {
    }

    private void loadArticles() {
        FamilleArticle familles;
        for (sql.model.FamilleArticle famille : LoginPan.FamilletList) {
            System.out.println(famille);
            familles = new FamilleArticle(famille.toString(), true);
            FamilleArticle sousF;
            for (Object sousfamille : famille.getFamilleArticles()) {
                sousfamille = (sql.model.FamilleArticle) sousfamille;
                sousF = new FamilleArticle(sousfamille.toString());
                Article al;
                for (sql.model.Article article : LoginPan.ArticleList) {
                    if (article.getFamilleArticle().equals(sousfamille)) {
                        al = new Article(sousF, article.getLibelle(), article.getIdArticle());
                        al.setPrice(article.getPVente());
                    }
                }
                familles.add(sousF);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        System.err.println("CaissePanel.paint()" + this.getWidth() + " x " + this.getHeight());
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Prix
        int x = 300;
        int y = 110;
        String dirhames;
        String centimes;
        Rectangle2D r;
        g.setFont(g.getFont().deriveFont(66f));
        final float total = this.controler.getTotal();
        dirhames = CaisseControler.getDirhames(total) + ".";
        centimes = CaisseControler.getCentimes(total);
        r = g.getFontMetrics().getStringBounds(dirhames, g);
        x = x - (int) r.getWidth();
        g.drawString(dirhames, x, y);
        g.setFont(g.getFont().deriveFont(40f));
        g.drawString(centimes, x + (int) r.getWidth(), y);
        // Paiement
        y += 40;
        x = 300;
        final float paye = this.controler.getPaidTotal();
        dirhames = CaisseControler.getDirhames(paye) + ".";
        centimes = CaisseControler.getCentimes(paye);

        g.setFont(g.getFont().deriveFont(18f));
        Rectangle2D r2 = g.getFontMetrics().getStringBounds("Payé", g);
        if (paye >= total) {
            g.setColor(Color.DARK_GRAY);
        } else {
            g.setColor(Color.ORANGE);
        }
        g.setFont(g.getFont().deriveFont(32f));
        r = g.getFontMetrics().getStringBounds(dirhames, g);
        g.drawString(dirhames, x - (int) r.getWidth(), y);
        g.setFont(g.getFont().deriveFont(24f));
        g.drawString(centimes, x, y);
        g.setFont(g.getFont().deriveFont(18f));
        g.setColor(Color.GRAY);
        g.drawString("Payé", x - (int) r2.getWidth() - (int) r.getWidth() - 10, y);
        // A rendre
        final boolean minimalHeight = this.getHeight() < 750;
        if (!minimalHeight) {
            y += 40;
            x = 300;
        } else {
            x = 140;
        }
        float aRendre = paye - total;
        if (aRendre != 0) {
            String label;
            if (aRendre > 0) {
                label = "Rendu";
            } else {
                if (!minimalHeight) {
                    label = "Reste à payer";
                } else {
                    label = "Doit";
                }
                aRendre = -aRendre;
            }

            dirhames = CaisseControler.getDirhames(aRendre) + ".";
            centimes = CaisseControler.getCentimes(aRendre);

            g.setFont(g.getFont().deriveFont(18f));
            Rectangle2D r3 = g.getFontMetrics().getStringBounds(label, g);

            g.setColor(Color.DARK_GRAY);
            g.setFont(g.getFont().deriveFont(32f));
            r = g.getFontMetrics().getStringBounds(dirhames, g);
            g.drawString(dirhames, x - (int) r.getWidth(), y);
            g.setFont(g.getFont().deriveFont(24f));
            g.drawString(centimes, x, y);
            g.setFont(g.getFont().deriveFont(18f));
            g.setColor(Color.GRAY);
            g.drawString(label, x - (int) r3.getWidth() - (int) r.getWidth() - 10, y);

        }

    }

    @Override
    public void caisseStateChanged() {
        repaint();
    }
}