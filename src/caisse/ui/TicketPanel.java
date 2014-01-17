package caisse.ui;

import caisse.model.Article;
import ui.touch.ScrollableList;
import utils.Pair;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sql.model.VousEtesCo;

public class TicketPanel extends JPanel implements CaisseListener {
    private final Image bg;

    private final ListModel dataModel;
    private final List<ListDataListener> listeners = new ArrayList<>();
    JLabel lTotal = new JLabel("", SwingConstants.RIGHT);
    JLabel lNumero = new JLabel("", SwingConstants.LEFT);
    private final CaisseControler controler;

    private final ScrollableList list;

    TicketPanel(final CaisseControler controler) {
        this.controler = controler;
        this.controler.addCaisseListener(this);

        this.setOpaque(false);
        this.bg = new ImageIcon(TicketPanel.class.getResource("ticket.png")).getImage();
        this.setLayout(null);

        this.dataModel = new ListModel() {

            @Override
            public void addListDataListener(final ListDataListener l) {
                TicketPanel.this.listeners.add(l);
            }

            @Override
            public Object getElementAt(final int index) {
                return controler.getItems().get(index);
            }

            @Override
            public int getSize() {
                return controler.getItems().size();
            }

            @Override
            public void removeListDataListener(final ListDataListener l) {
                TicketPanel.this.listeners.remove(l);
            }

        };

        this.list = new ScrollableList(this.dataModel) {
            private final TicketCellRenderer renderer = new TicketCellRenderer();

            @Override
            public void paintCell(final Graphics g, final Object value, final int index, final boolean isSelected, final int posY) {
                g.translate(0, posY);
                this.renderer.paint(g, TicketPanel.this.list, value, index, isSelected);
                g.translate(0, -posY);
            }
        };
        this.list.setOpaque(false);
        this.list.setSize(308, 450);
        this.list.setFixedCellHeight(40);
        this.list.setLocation(33, 18);
        this.add(this.list);

        this.lTotal.setSize(276 - 10, 32);
        this.lTotal.setLocation(68, 500 - 32);
        this.lTotal.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(this.lTotal);
        this.lNumero.setSize(276 - 10, 32);
        this.lNumero.setLocation(38, 495);
        this.lNumero.setForeground(Color.DARK_GRAY);
        this.lNumero.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(this.lNumero);

        this.list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(final ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    final Object selectedValue = TicketPanel.this.list.getSelectedValue();
                    if (selectedValue != null) {
                        final Article a = ((Pair<Article, Integer>) selectedValue).getFirst();
                        controler.setArticleSelected(a);
                    }
                }

            }
        });

    }

    @Override
    protected void paintComponent(final Graphics g) {
        g.drawImage(this.bg, 0, 0, null);
        super.paintComponent(g);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(480, 550);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(480, 707);
    }

    public void fire() {
        for (final ListDataListener l : this.listeners) {
            l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, this.listeners.size()));
        }

    }

    @Override
    public void caisseStateChanged() {
        final Article articleSelected = this.controler.getArticleSelected();
        for (final ListDataListener l : this.listeners) {
            l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, this.listeners.size()));
        }
        this.lTotal.setText("TOTAL:  " + String.valueOf(this.controler.getTotal()) + " Dhs");
        this.lNumero.setText("Ticket " + this.controler.getTicketNumber() + " - Caissier : " + VousEtesCo.utilisateur.getUserName());
        // Rien à  selectionner
        if (articleSelected == null) {
            this.list.clearSelection();
            return;
        }
        try {
            // Deja selectionné
            if (this.list.getSelectedValue() != null && articleSelected != null && articleSelected.equals(((Pair<Article, Integer>) this.list.getSelectedValue()).getFirst())) {
                return;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (articleSelected != null) {

            for (int i = 0; i < this.dataModel.getSize(); i++) {
                final Pair<Article, Integer> item = (Pair<Article, Integer>) this.dataModel.getElementAt(i);
                if (item.getFirst().equals(articleSelected)) {
                    this.list.setSelectedValue(item, true);
                    break;
                }
            }
        }

    }
}