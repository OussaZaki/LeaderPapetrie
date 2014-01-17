package gestion.ui;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Oussama
 */
public class ModelArticle extends DefaultTableModel {
     boolean[] canEdit;
    public ModelArticle() {
        super(new Object[][]{},
                new String[]{
            "Nom", "Famille", "P. d'achat", "P. de vente", "Qté en stock", "Qté minimale"});
        canEdit = new boolean[]{
            false, false, false, false, false, false};
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }
}
