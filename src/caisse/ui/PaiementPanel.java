package caisse.ui;


import caisse.model.Article;
import caisse.model.Paiement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PaiementPanel extends JPanel implements CaisseListener, MouseListener {
    private static final int OFFSETY = 50+72;
    private static final int LINE_HEIGHT = 64;
    private Image bg, bg2, bgESPECES;
    private CaisseControler controller;
    private String calculatorValue = "";

    /**
     * Mode '+' ajout d'une quantité '*' multiplication '-' soustraction ' ' remplacement
     * */
    private char mode = ' ';
    private boolean init = true;

    public PaiementPanel(CaisseControler controller) {
        this.controller = controller;
        this.controller.addCaisseListener(this);
        this.setOpaque(false);
        bg = new ImageIcon(TicketPanel.class.getResource("calculator.png")).getImage();
        bg2 = new ImageIcon(TicketPanel.class.getResource("calculator_selected.png")).getImage();
        bgESPECES = new ImageIcon(TicketPanel.class.getResource("especes.png")).getImage();
        this.addMouseListener(this);

        this.setLayout(null);
        StatusBar st = new StatusBar();
        st.setTitle("Règlement");
        st.setLocation(0, 0);
        st.setSize(320, (int) st.getPreferredSize().getHeight());

        this.add(st);

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        int y = this.getHeight() - this.bg.getHeight(null);
        if (controller.getArticleSelected() != null || controller.getPaiementSelected() != null) {
            g.drawImage(bg2, 0, y, null);
        } else {
            g.drawImage(bg, 0, y, null);
        }

        g.drawLine(0, 0, 0, this.getHeight());
        y = OFFSETY;
        for (Paiement p : controller.getPaiements()) {
           if (p.getType() == Paiement.ESPECES) {
                g.drawImage(bgESPECES, 0, y, null);
                drawMontant(g, p, 300, y);
            }
            y += LINE_HEIGHT;
        }
        drawCalculator(g);
        super.paint(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.RED);
        for (int x = 0; x < 320; x += 80) {
            for (int y = 28; y < 900; y += 72) {
                g.drawLine(x, y, x + 2, y + 2);
            }
        }

    }

    private void drawCalculator(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int y = this.getHeight() - this.bg.getHeight(null);

        g.setFont(new Font("Arial", Font.PLAIN, 32));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final Article article = controller.getArticleSelected();
        g.setColor(Color.DARK_GRAY);
        if (article != null) {
            String string = calculatorValue;
            g.setFont(g.getFont().deriveFont(52f));
            Rectangle2D r1 = g.getFontMetrics().getStringBounds(string, g2);
            g.drawString(string, (int) (260 - r1.getWidth()), 500 + y);
            g.setFont(g.getFont().deriveFont(14f));
            g.drawString("Quantité", 10, 460 + y);

        } else {
            final Paiement paiement = controller.getPaiementSelected();
            if (paiement != null) {
                String string = calculatorValue;
                g.setFont(g.getFont().deriveFont(52f));
                Rectangle2D r1 = g.getFontMetrics().getStringBounds(string, g2);
                g.drawString(string, (int) (300 - r1.getWidth()), 500 + y);
                g.setFont(g.getFont().deriveFont(14f));
                String str = "Paiement ";
                if (paiement.getType() == Paiement.ESPECES) {
                    str += " en espèces";
                }
                g.drawString(str, 10, 460 + y);
            }
        }
        g.setFont(g.getFont().deriveFont(14f));
        g.drawString("" + mode, 10, 480 + y);
    }

    private char getToucheFrom(int x, int y) {
        int yy = (this.getHeight() - y) / 72;
        int xx = x / 80;
        switch (yy) {
        case 0:
            if (xx == 0) {
                return '0';
            } else if (xx == 1) {
                return '0';
            } else if (xx == 2) {
                return '.';
            } else if (xx == 3) {
                return '=';
            } else {
                break;
            }
        case 1:
            if (xx == 0) {
                return '1';
            } else if (xx == 1) {
                return '2';
            } else if (xx == 2) {
                return '3';
            } else if (xx == 3) {
                return '=';
            } else {
                break;
            }
        case 2:
            if (xx == 0) {
                return '4';
            } else if (xx == 1) {
                return '5';
            } else if (xx == 2) {
                return '6';
            } else if (xx == 3) {
                return '+';
            } else {
                break;
            }
        case 3:
            if (xx == 0) {
                return '7';
            } else if (xx == 1) {
                return '8';
            } else if (xx == 2) {
                return '9';
            } else if (xx == 3) {
                return '+';
            } else {
                break;
            }
        case 4:
            if (xx == 0) {
                return 'c';
            } else if (xx == 1) {
                return 'c';
            } else if (xx == 2) {
                return '*';
            } else if (xx == 3) {
                return '-';
            } else {
                break;
            }

        }
        return '?';
    }

    private void drawMontant(Graphics g, Paiement p, int x, int y) {
        y = y + 45;
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.GRAY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int dirhams = (int)p.getMontant();
        float a = p.getMontant()-(int)p.getMontant();
        int cents = Integer.parseInt(String.valueOf(a).substring(2));
        
        String sCentimes = String.valueOf(cents);
        if (sCentimes.length() < 2) {
            sCentimes = "0" + sCentimes;
        }
        g.setFont(getFont().deriveFont(18f));
        Rectangle2D r1 = g.getFontMetrics().getStringBounds(sCentimes, g2);
        g.drawString(sCentimes, (int) (x - r1.getWidth()), y);
        //
        g.setFont(g.getFont().deriveFont(36f));
        g.setFont(g.getFont().deriveFont(Font.BOLD));
        g.setColor(Color.WHITE);
        String sDirhames = String.valueOf(dirhams) + ".";
        Rectangle2D r2 = g.getFontMetrics().getStringBounds(sDirhames, g2);
        g.drawString(sDirhames, (int) (x - r1.getWidth() - r2.getWidth()), y);
    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(320, 900);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(320, 900);
    }

    @Override
    public void caisseStateChanged() {
        if (controller.getArticleSelected() != null) {
            initCaisseArticle();
        } else if (controller.getPaiementSelected() != null) {
            initCaissePaiement();
        }

        repaint();

    }

    private void initCaisseArticle() {
        calculatorValue = String.valueOf(controller.getItemCount(controller.getArticleSelected()));
        init = true;
        mode = ' ';
    }

    private void initCaissePaiement() {
        calculatorValue = String.valueOf(controller.getPaiementSelected().getMontant());
        init = true;
        mode = ' ';
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        char c = getToucheFrom(e.getX(), e.getY());
        if (c != '?') {
            handleCharacter(c);
        } else {
            Paiement p = getPaiementFrom(e.getY());
            if (p != null) {
                if (e.getX() > 320 - 64 && p.getType() != Paiement.ESPECES && controller.canAddPaiement(p.getType())) {
                    p = new Paiement(p.getType());
                    controller.addPaiement(p);
                }
                controller.autoFillPaiement(p);
                this.calculatorValue = String.valueOf(p.getMontant());
                if (p.getType() == Paiement.ESPECES) {
//                    controller.openDrawer();
                }
            }
            controller.setPaiementSelected(p);
        }
    }

    private void handleCharacter(char c) {
        System.out.println("Handle: " + c);
        if (c == '?')
            return;
        final Article article = controller.getArticleSelected();

        if (c == '+' || c == '-' || c == '*') {
            mode = c;

            repaint();
            return;
        }

        if (article != null) {
            // Changement de quantité
            if (c == 'c' || c == '/') {
                System.out.println("Clear quantité");
                mode = ' ';
                controller.clearArticle(article);
            } else if (c == '=' || c == '\n') {
                if (!init) {
                    int v = Integer.parseInt(calculatorValue);
                    if (mode == ' ') {
                        controller.setArticleCount(article, v);
                    } else if (mode == '+') {
                        controller.setArticleCount(article, controller.getItemCount(article) + v);
                    } else if (mode == '-') {
                        controller.setArticleCount(article, controller.getItemCount(article) - v);
                    } else if (mode == '*') {
                        controller.setArticleCount(article, controller.getItemCount(article) * v);
                    }
                }
                initCaisseArticle();
            } else if (Character.isDigit(c)) {
                if (init) {
                    calculatorValue = "";
                    init = false;
                }
                if (calculatorValue.length() < 8) {
                    calculatorValue += c;
                }
            }

        } else {
            final Paiement paiement = controller.getPaiementSelected();
            if (paiement != null) {
                // Changement de paiement
                if (c == 'c' || c == '/') {
                    System.out.println("Clear paiement");
                    mode = ' ';
                    controller.clearPaiement(paiement);
                } else if (c == '.' && (calculatorValue.indexOf('.') < 0)) {

                    calculatorValue += ".";

                } else if (c == '=' || c == '\n') {
                    if (!init) {
                        float v = getCentsFrom(this.calculatorValue);
                        if (mode == ' ') {
                            controller.setPaiementValue(paiement, v);
                        } else if (mode == '+') {
                            controller.setPaiementValue(paiement, paiement.getMontant() + v);
                        } else if (mode == '-') {
                            controller.setPaiementValue(paiement, paiement.getMontant() - v);
                        } else if (mode == '*') {
                            controller.setPaiementValue(paiement, paiement.getMontant() * v);
                        }
                    }
                    initCaissePaiement();
                    controller.setPaiementSelected(null);
                } else if (Character.isDigit(c)) {
                    if (init) {
                        calculatorValue = "";
                        init = false;
                    }
                    if (calculatorValue.length() < 9) {
                        int i = calculatorValue.indexOf('.');
                        if (i < 0 || (i >= 0 && calculatorValue.length() - i < 3))
                            calculatorValue += c;
                    }
                }
            }
        }
        repaint();
    }

    private float getCentsFrom(String str) {
        return Float.parseFloat(str);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    private Paiement getPaiementFrom(int y) {
        int index = (y - OFFSETY) / LINE_HEIGHT;
        if (index < controller.getPaiements().size() && index >= 0) {
            return controller.getPaiements().get(index);
        }
        return null;
    }

}

