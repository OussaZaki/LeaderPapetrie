package caisse.model;

import java.util.HashMap;
import java.util.Map;

public class Article {
    private FamilleArticle s;
    private String name;
    private float price;
    String barCode = "empty barcode";
    String code = "";
    private final int id;
    private static Map<String, Article> codes = new HashMap<String, Article>();

    public Article(FamilleArticle s1, String string, int id) {
        this.s = s1;
        this.id = id;
        this.name = string;
        s1.addArticle(this);
    }

    public int getId() {
        return this.id;
    }

    public void setBarCode(String bar) {
        this.barCode = bar;
        codes.put(bar, this);
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    };

    public String getBarCode() {
        return this.barCode;
    }

    public FamilleArticle getCategorie() {
        return this.s;
    }

    @Override
    public String toString() {

        return "Article:" + this.name + " " + this.price;
    }

    public static Article getArticleFromBarcode(String code) {
        return codes.get(code);
    }
}
