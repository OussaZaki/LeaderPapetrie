package caisse.ui;

import caisse.model.Ticket;
import ui.DefaultListModel;
import ui.touch.ScrollableList;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListeDesTicketsPanel extends JPanel implements ListSelectionListener {

    private JList l;
    private CaisseFrame frame;
    private Image bg;
    private JPanel ticketP;
    private ScrollableList ticketList;
    private DefaultListModel ticketLlistModel;

    ListeDesTicketsPanel(CaisseFrame caisseFrame) {
        this.frame = caisseFrame;
        this.setBackground(Color.WHITE);
        this.setOpaque(true);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;

        // Place pour le menu
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(48, 44));
        p.setMinimumSize(new Dimension(48, 44));
        p.setOpaque(false);
        this.add(p, c);

        // Liste des tickets
        c.gridy++;
        c.gridwidth = 1;
        c.weighty = 1;
        c.gridheight = 2;

        ticketLlistModel = new DefaultListModel();
//        ticketLlistModel.addAll(new Vector<Ticket>(Caisse.allTickets()));
        final Font f = new Font("Arial", Font.PLAIN, 24);
        ticketList = new ScrollableList(ticketLlistModel) {
            @Override
            public void paintCell(Graphics g, Object object, int index, boolean isSelected, int posY) {
                g.setFont(f);

                if (isSelected) {
                    g.setColor(new Color(232, 242, 254));
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(0, posY, getWidth(), getCellHeight());

                //
                g.setColor(Color.GRAY);
                g.drawLine(0, posY + this.getCellHeight() - 1, this.getWidth(), posY + this.getCellHeight() - 1);

                if (isSelected) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.GRAY);
                }
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Ticket article = (Ticket) object;
                String label = "Ticket " + article.getCode();

                String dirhame = String.valueOf(article.getTotal()) + "Dhs";

                int wDirhame = (int) g.getFontMetrics().getStringBounds(dirhame, g).getWidth();
                g.drawString(label, 10, posY + 39);
                g.drawString(dirhame, getWidth() - 5 - wDirhame, posY + 39);

            }
        };
        this.add(ticketList, c);
        // Ticket
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx++;
        c.gridheight = 1;
        c.insets = new Insets(10, 10, 10, 10);
        ticketP = new JPanel();

        JScrollPane scrollPane = new JScrollPane(ticketP);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setMinimumSize(new Dimension(400, 200));
        this.add(scrollPane, c);

        ticketList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                Object selectedValue = ticketList.getSelectedValue();
                setSelectedTicket(selectedValue);
            }
        });

        // Menu

        c.gridy++;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        final Font font = new Font("Arial", Font.PLAIN, 46);
        l = new JList(new String[] { "Imprimer", "Effacer", "", "Retour Menu" });
        l.setCellRenderer(new ListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel l = new JLabel(value.toString()) {
                    @Override
                    public void paint(Graphics g) {

                        super.paint(g);

                        g.setColor(Color.LIGHT_GRAY);
                        g.drawLine(0, 0, this.getWidth(), 0);
                    }
                };
                l.setFont(font);
                return l;
            }

        });
        l.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        l.getSelectionModel().addListSelectionListener(this);

        l.setFixedCellHeight(80);
        this.add(l, c);
        bg = new ImageIcon(TicketPanel.class.getResource("toolbar.png")).getImage();
        setFont(new Font("Arial", Font.BOLD, 24));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        Object selectedValue = ticketList.getSelectedValue();
        int selectedIndex = l.getSelectedIndex();
        if (selectedIndex == 0) {
            // Imprimer
//            if (selectedValue != null) {
//                TicketPrinter prt = Caisse.getTicketPrinter();
//                ((Ticket) selectedValue).print(prt);
//            }
        } else if (selectedIndex == 1) {
            // Effacer
            if (selectedValue != null) {
                ticketLlistModel.removeElement(selectedValue);
                ticketList.clearSelection();
                ((Ticket) selectedValue).deleteTicket();
            }
        } else if (selectedIndex == 3) {
            // Retour
            frame.showMenu();
        }
        l.clearSelection();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final int w = this.getWidth();
        int imWidth = bg.getWidth(null);
        for (int x = 0; x <= w; x += imWidth) {
            g.drawImage(bg, x, 0, null);
        }

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(250, 250, 250));
        String str = "Liste des tickets";

        Rectangle2D r = g.getFontMetrics().getStringBounds(str, g);
        int x = (int) (this.getWidth() - r.getWidth()) / 2;
        g.drawString(str, x, 30);
    }

    public void setSelectedTicket(Object selectedValue) {
//        ticketP.clear();
//        if (selectedValue != null) {
////            ((Ticket) selectedValue).print(ticketP);
//            try {
//                ticketP.printBuffer();
//            } catch (Exception e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//        }
//        ticketList.setSelectedValue(selectedValue, true);
    }
}
