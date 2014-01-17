package sql.model;
// Generated 15 janv. 2014 17:39:49 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * FamilleArticle generated by hbm2java
 */
public class FamilleArticle  implements java.io.Serializable {


     private Short idFamille;               //id
     private FamilleArticle familleArticle; //Parent
     private String nomFamille;
     private Set familleArticles = new HashSet(0);
     private Set<FamilleArticle> articles = new HashSet(0);

    public FamilleArticle() {
    }

	
    public FamilleArticle(String nomFamille) {
        this.nomFamille = nomFamille;
    }
    public FamilleArticle(FamilleArticle familleArticle, String nomFamille, Set familleArticles, Set articles) {
       this.familleArticle = familleArticle;
       this.nomFamille = nomFamille;
       this.familleArticles = familleArticles;
       this.articles = articles;
    }
   
    public Short getIdFamille() {
        return this.idFamille;
    }
    
    public void setIdFamille(Short idFamille) {
        this.idFamille = idFamille;
    }
    public FamilleArticle getFamilleArticle() {
        return this.familleArticle;
    }
    
    public void setFamilleArticle(FamilleArticle familleArticle) {
        this.familleArticle = familleArticle;
    }
    public String getNomFamille() {
        return this.nomFamille;
    }
    
    public void setNomFamille(String nomFamille) {
        this.nomFamille = nomFamille;
    }
    public Set<FamilleArticle> getFamilleArticles() {
        return this.familleArticles;
    }
    
    public void setFamilleArticles(Set familleArticles) {
        this.familleArticles = familleArticles;
    }
    public Set getArticles() {
        return this.articles;
    }
    
    public void setArticles(Set articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return nomFamille;
    }

    


}


