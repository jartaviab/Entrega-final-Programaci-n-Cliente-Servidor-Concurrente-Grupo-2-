package fidecompro3;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Nombre", "Descripción", "Precio", "Inventario", "Cantidad"};
    private final List<Producto> productos;

    public ProductTableModel(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public int getRowCount() {
        return productos.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto producto = productos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return producto.getIdProducto();
            case 1:
                return producto.getNombre();
            case 2:
                return producto.getDescripcion();
            case 3:
                return producto.getPrecio();
            case 4:
                return producto.getInventario();
            case 5:
                return producto.getCantidadSeleccionada();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 5) {
            try {
                int cantidad = Integer.parseInt(value.toString()); // Convertir el valor a Integer
                productos.get(rowIndex).setCantidadSeleccionada(cantidad);
                fireTableCellUpdated(rowIndex, columnIndex);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "La cantidad debe ser un número entero.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 5; // Solo la columna de cantidad es editable
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 5) {
            return Integer.class; // La columna de cantidad debe ser de tipo Integer
        }
        return super.getColumnClass(columnIndex);
    }

    public void setProductos(List<Producto> productos) {
        this.productos.clear();
        this.productos.addAll(productos);
        fireTableDataChanged();
    }
}
