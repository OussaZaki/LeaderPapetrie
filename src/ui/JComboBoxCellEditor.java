package ui;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.table.TableCellEditor;

public class JComboBoxCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JComboBox comboBox;

    public JComboBoxCellEditor(final JComboBox comboBox) {
        this.comboBox = comboBox;
        JComponent editorComponent = (JComponent) comboBox.getEditor().getEditorComponent();
        editorComponent.setBorder(null);
    }

    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }

    public Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
        comboBox.setSelectedItem(value);
        return comboBox;
    }

}
