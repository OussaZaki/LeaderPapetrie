package caisse.model;

public class Paiement {

    private int type;
    private float montant;

    public Paiement(int type) {
        this.type = type;
        this.montant = 0;

    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        if (montant >= 0)
            this.montant = montant;
    }

    public int getType() {
        return type;
    }

    public static final int ESPECES = 1;
}