package caisse.model;

import LoginTest.LoginPan;
import utils.ExceptionHandler;
import utils.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import sql.model.LigneFacture;
import sql.model.LigneFactureId;
import sql.model.mySession;

public class Ticket {

    private static boolean inited = false;
    // Propre a ticket
    private List<Paiement> paiements = new ArrayList<>();
    private final List<Pair<Article, Integer>> items = new ArrayList<Pair<Article, Integer>>();
    private Date date;
    private static int number;
    private int numberTicket;
    // Propre à  la caisse
    private int caisseNumber;
    Set<LigneFacture> lignesFacture;
    //private static final SQLTable tableArticle = Configuration.getInstance().getRoot().findTable("ARTICLE");


    public Set<LigneFacture> getLignesFacture() {
        return lignesFacture;
    }

    public Ticket(int caisse) {
        this.caisseNumber = caisse;
        this.date = Calendar.getInstance().getTime();
//        System.out.println(mySession.session.createSQLQuery("select MAX(Id_facture) FROM facture").uniqueResult());
//        BigInteger big = (BigInteger) mySession.session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult();
        if(mySession.session.createSQLQuery("select MAX(Id_facture) FROM facture").uniqueResult() == null)
            number = 0;
        else
            number = (Integer) mySession.session.createSQLQuery("select MAX(Id_facture) FROM facture").uniqueResult();
        initNumber();
    }

    public void setNumber(int i) {
        this.number = i;
    }

    public void setDate(Date d) {
        this.date = d;
    }

    private void initNumber() {
        Ticket.number++;
    }

    String getPrefixCode() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
        int j = cal.get(Calendar.DAY_OF_MONTH);
        int m = cal.get(Calendar.MONTH) + 1;
        int a = cal.get(Calendar.YEAR) - 2000;
        String code = "";
        code += format(2, this.getCaisseNumber());
        code += format(2, j) + format(2, m) + format(2, a);
        return code;
    }

    public String getCode() {
        String code = getPrefixCode();
        code += format(5, this.getNumber());
        return code;
    }

    /**
     * Numero du ticket fait ce jour, compteur remis Ã  1 chaque jour
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Numero de la caisse, de 1 à n
     */
    private int getCaisseNumber() {
        return this.caisseNumber;
    }

    public void save() {
        // Update Hour & Minute
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);

        //Output

    }

    public void print() {
    }

    private File getOutputDir() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
        int j = cal.get(Calendar.DAY_OF_MONTH);
        int m = cal.get(Calendar.MONTH) + 1;
        int a = cal.get(Calendar.YEAR);
        final String defaultLocation = "";
        File defaultDir = new File(defaultLocation);
        File outputDir = new File(defaultDir, "Tickets");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        File outputDirYear = new File(outputDir, format(4, a));
        if (!outputDirYear.exists()) {
            outputDirYear.mkdir();
        }
        File outputDirMonth = new File(outputDirYear, format(2, m));
        if (!outputDirMonth.exists()) {
            outputDirMonth.mkdir();
        }
        File outputDirDay = new File(outputDirMonth, format(2, j));
        if (!outputDirDay.exists()) {
            outputDirDay.mkdir();
        }
        if (!outputDirDay.exists()) {
            ExceptionHandler.handle("Impossible de créer le dossier des tickets.\n\n" + outputDirDay.getAbsolutePath());
        }
        return outputDirDay;
    }

    public Date getCreationDate() {
        return this.date;
    }

    private static String format(int l, int value) {
        return format(l, String.valueOf(value));
    }

    private static String format(int l, String string) {
        if (string.length() > l) {
            string = string.substring(0, l);
        }
        final StringBuffer str = new StringBuffer(l);
        final int stop = l - string.length();
        for (int i = 0; i < stop; i++) {
            str.append('0');
        }
        str.append(string);
        return str.toString();
    }

    public void addPaiement(Paiement p1) {
        this.paiements.add(p1);

    }

    public void addArticle(Article a) {
        boolean alreadyExist = false;
        for (Pair<Article, Integer> line : this.items) {
            if (line.getFirst().equals(a)) {
                alreadyExist = true;
                break;
            }
        }
        if (!alreadyExist) {
            Pair<Article, Integer> line = new Pair<>(a, 1);
            this.items.add(line);
        }

    }

    public void incrementArticle(Article a) {
        boolean alreadyExist = false;
        for (Pair<Article, Integer> line : this.items) {
            if (line.getFirst().equals(a)) {
                alreadyExist = true;
                line.setSecond(line.getSecond() + 1);
                break;
            }
        }
        if (!alreadyExist) {
            Pair<Article, Integer> line = new Pair<>(a, 1);
            this.items.add(line);
        }

    }

    public List<Paiement> getPaiements() {
        return this.paiements;
    }

    public float getTotal() {

        float s = 0;
        int i = 0;
        lignesFacture = new HashSet(0);
        for (Pair<Article, Integer> line : this.items) {
            final int count = line.getSecond();
            Article art = line.getFirst();
            LigneFacture ligne = new LigneFacture();
            ligne.setQteArticle(count);
            for (sql.model.Article article : LoginPan.ArticleList) {
                if (article.getIdArticle().equals(art.getId())) {
                    ligne.setArticle(article);
                    ligne.setId(new LigneFactureId(this.number, article.getIdArticle()));
                    break;
                }
            }
            ligne.setSubtotal(1);
            s += ligne.getSubtotal();
            System.out.println(ligne);
            System.out.println(s);
            lignesFacture.add(ligne);
            i++;
        }
        this.numberTicket = i;
        return s;
    }

    public int getNumberTicket() {
        return numberTicket;
    }

    public List<Pair<Article, Integer>> getArticles() {
        return this.items;
    }

    public void clearArticle(Article article) {
        Pair<Article, Integer> toRemove = null;

        for (Pair<Article, Integer> line : this.items) {
            if (line.getFirst().equals(article)) {
                toRemove = line;
                break;
            }
        }
        if (toRemove != null) {

            this.items.remove(toRemove);

        }

    }

    public void setArticleCount(Article article, int count) {
        if (count <= 0) {
            this.clearArticle(article);
            return;
        }
        Pair<Article, Integer> toModify = null;

        for (Pair<Article, Integer> line : this.items) {
            if (line.getFirst().equals(article)) {
                toModify = line;
                break;
            }
        }
        if (toModify != null) {
            System.out.println("Ticket.setArticleCount():" + article + " " + count);
            toModify.setSecond(count);
        }

    }

    public int getItemCount(Article article) {
        for (Pair<Article, Integer> line : this.items) {
            if (line.getFirst().equals(article)) {
                return line.getSecond();
            }
        }
        return 0;
    }

    public float getPaidTotal() {
        float paid = 0;
        for (Paiement p : this.paiements) {
            paid += p.getMontant();
        }
        return paid;
    }

    public void removeArticle(Article a) {
        Pair<Article, Integer> lineToDelete = null;
        for (Pair<Article, Integer> line : this.items) {
            if (line.getFirst().equals(a)) {
                final int count = line.getSecond() + 1;
                if (count <= 0) {
                    lineToDelete = line;
                }
                line.setSecond(count);
                break;
            }
        }
        if (lineToDelete != null) {
            this.items.remove(lineToDelete);
        }

    }

    @Override
    public String toString() {
        return "Ticket " + getCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Ticket) {
            Ticket t = (Ticket) obj;
            return t.getCode().equals(getCode());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getCode().hashCode();
    }

    public void deleteTicket() {
    }
}
