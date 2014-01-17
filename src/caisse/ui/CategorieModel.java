package caisse.ui;

import caisse.model.FamilleArticle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class CategorieModel implements ListModel {
    private final List<FamilleArticle> items = new ArrayList<FamilleArticle>();

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

    public void setRoot(FamilleArticle c) {
        this.categorie = c;
        this.items.clear();
        if (c == null) {
            this.items.addAll(FamilleArticle.getTopLevelCategories());
        } else {
            this.items.addAll(c.getSubCategories());
        }
        Collections.sort(items, new Comparator<FamilleArticle>() {
            @Override
            public int compare(FamilleArticle o1, FamilleArticle o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
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