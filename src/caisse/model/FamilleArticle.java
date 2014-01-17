package caisse.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FamilleArticle {
    private static List<FamilleArticle> topLevelCategories = new ArrayList<FamilleArticle>();
    private String name;
    // Sous catégories
    private List<FamilleArticle> l = new ArrayList<FamilleArticle>();
    // Articles dans cette categorie
    private List<Article> articles = new ArrayList<Article>();
    private FamilleArticle parent;

    public FamilleArticle(String string) {
        this(string, false);
    }

    public FamilleArticle(String string, boolean top) {
        this.name = string;
        if (top) {
            topLevelCategories.add(this);
        }
    }
    
    public static void deleteTop(){
        topLevelCategories.removeAll(topLevelCategories);
    }
    
    @Override
    public String toString() {
        return name;
    }

    public void add(FamilleArticle s) {
        l.add(s);
        s.setParentCategorie(this);
    }

    private void setParentCategorie(FamilleArticle categorie) {
        this.parent = categorie;
    }

    public FamilleArticle getParent() {
        return parent;
    }

    void addArticle(Article a) {
        this.articles.add(a);
    }

    public static List<FamilleArticle> getTopLevelCategories() {
        return topLevelCategories;
    }

    public List<FamilleArticle> getSubCategories() {
        return l;
    }

    public String getName() {
        return name;
    }

    public List<Article> getArticles() {
        final List<Article> result = new ArrayList<Article>();
        result.addAll(articles);
        for (FamilleArticle c : l) {
            result.addAll(c.getArticles());
        }
        Collections.sort(result, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return result;
    }
}
