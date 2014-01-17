package caisse.ui;

import caisse.model.Article;
import caisse.model.FamilleArticle;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ArticleModel implements ListModel {
    private final List<Article> items = new ArrayList<Article>();

    private List<ListDataListener> listeners = new ArrayList<ListDataListener>();

    private FamilleArticle categorie;

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public Object getElementAt(int index) {
        return items.get(index);
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    public void setCategorie(FamilleArticle c) {
        this.categorie = c;
        this.items.clear();
        if (c != null) {
            this.items.addAll(c.getArticles());
        }
        fire();
    }

    private void fire() {
        for (ListDataListener l : listeners) {
            l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, this.listeners.size()));
        }
    }

    public FamilleArticle getRoot() {
        return this.categorie;
    }
}