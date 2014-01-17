package caisse.ui;

import caisse.model.Article;
import caisse.model.Paiement;
import caisse.model.Ticket;
import utils.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import sql.model.Facture;
import sql.model.LigneFacture;
import sql.model.VousEtesCo;
import sql.model.mySession;

public class CaisseControler {

    private Article articleSelected;
    private Paiement paiementSelected;
    private Ticket t;
    private List<CaisseListener> listeners = new ArrayList<>();
    private Paiement p1 = new Paiement(Paiement.ESPECES);
    private CaisseFrame caisseFrame;
    private Facture facture;

    public CaisseControler(CaisseFrame caisseFrame) {
        this.caisseFrame = caisseFrame;
        this.t = new Ticket(1);
        this.t.addPaiement(this.p1);
    }

    public Article getArticleSelected() {
        return this.articleSelected;
    }

    public Paiement getPaiementSelected() {
        return this.paiementSelected;
    }

    void setArticleSelected(Article a) {
        if (a != articleSelected) {
            this.articleSelected = a;
            this.paiementSelected = null;
            fire();
        }
    }

    void setPaiementSelected(Paiement p) {
        this.paiementSelected = p;
        this.articleSelected = null;
        fire();
    }

    // Listeners
    private void fire() {
        int stop = this.listeners.size();
        for (int i = 0; i < stop; i++) {
            this.listeners.get(i).caisseStateChanged();
        }
    }

    void addCaisseListener(CaisseListener l) {
        this.listeners.add(l);
    }

    // Articles
    void addArticle(Article a) {
        this.t.addArticle(a);
        fire();
    }

    void incrementArticle(Article a) {
        this.t.incrementArticle(a);
        fire();
    }

    void removeArticle(Article a) {
        this.t.removeArticle(a);
        fire();
    }

    // Paiements
    public List<Paiement> getPaiements() {
        return this.t.getPaiements();
    }

    public void addPaiement(Paiement p) {
        this.t.addPaiement(p);
        fire();
    }

    public void clearPaiement(Paiement paiement) {
//        if (this.p1.equals(paiement) || this.p2.equals(paiement) || this.p3.equals(paiement)) {
//            paiement.setMontantInCents(0);
//        }
        if (this.p1.equals(paiement)) {
            paiement.setMontant(0);
        }
        fire();
    }

    public void setPaiementValue(Paiement paiement, float v) {
        paiement.setMontant(v);
        fire();
    }

    // Totaux
    public float getTotal() {
        return this.t.getTotal();
    }

    public float getPaidTotal() {
        return this.t.getPaidTotal();
    }

    //
    public List<Pair<Article, Integer>> getItems() {
        return this.t.getArticles();
    }

    public int getItemCount(Article article) {
        return this.t.getItemCount(article);
    }

    public void clearArticle(Article article) {
        this.t.clearArticle(article);
        this.setArticleSelected(null);
    }

    public void setArticleCount(Article article, int count) {
        this.t.setArticleCount(article, count);
        this.setArticleSelected(null);
    }


    void autoFillPaiement(Paiement p) {
        float montant = p.getMontant();

        p.setMontant(getTotal() - getPaidTotal() + montant);
        setPaiementSelected(p);
    }

    public boolean canAddPaiement(int type) {
        final int paiementCount = this.t.getPaiements().size();
        if (paiementCount >= 6) {
            return false;
        }
        for (int i = 0; i < paiementCount; i++) {
            Paiement p = this.t.getPaiements().get(i);
            if (p.getType() == type && p.getMontant() <= 0) {
                return false;
            }
        }

        return true;
    }

    public static String getCentimes(float prix) {
        String s = String.valueOf(prix - (int)prix);
        s = s.substring(2);
        if (s.length() < 2) {
            s += "0";
        }
        return s;
    }

    public static String getDirhames(float prix) {
        String s = String.valueOf((int)prix);
        return s;
    }

    public void saveAndClearTicket() {
        if (this.t.getTotal() > 0) {
            if (this.getPaidTotal() >= this.getTotal()) {

                facture = new Facture();
                facture.setMontantTotal(t.getTotal());
                facture.setUser(VousEtesCo.utilisateur);
                Set<LigneFacture> lignesFacture = t.getLignesFacture();
                facture.setLigneFactures(lignesFacture);
                facture.setDateValid(Calendar.getInstance().getTime());
                facture.setNbrLigne(t.getNumberTicket());
                mySession.session.beginTransaction();
                mySession.session.saveOrUpdate(facture);
                mySession.session.getTransaction().commit();
                
                for (LigneFacture ligneFacture : lignesFacture) {
                    mySession.session.beginTransaction();
                    mySession.session.merge(ligneFacture);
                    mySession.session.getTransaction().commit();
                }
                this.t.save();
                t = new Ticket(1);
                p1 = new Paiement(Paiement.ESPECES);
                this.t.addPaiement(this.p1);
                this.setPaiementSelected(null);
                this.setArticleSelected(null);

            }
        }
    }

    public int getTicketNumber() {
        return this.t.getNumber();
    }
}