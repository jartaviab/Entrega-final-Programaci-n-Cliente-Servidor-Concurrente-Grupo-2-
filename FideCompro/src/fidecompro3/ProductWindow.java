package fidecompro3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ProductWindow extends JFrame {
    private final JTable productTable;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton updateButton;
    private final JButton exitButton;

    private final ArrayList<Producto> productList;
    private final ProductTableModel tableModel;

    public ProductWindow() {
        setTitle("Gestión de Productos");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        productList = new ArrayList<>();
        tableModel = new ProductTableModel(productList);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(20, 20, 450, 150);
        add(scrollPane);

        addButton = new JButton("Agregar Producto");
        addButton.setBounds(20, 180, 200, 25);
        add(addButton);

        deleteButton = new JButton("Eliminar Producto");
        deleteButton.setBounds(230, 180, 200, 25);
        add(deleteButton);

        updateButton = new JButton("Editar Producto");
        updateButton.setBounds(20, 210, 200, 25);
        add(updateButton);

        exitButton = new JButton("Salir");
        exitButton.setBounds(230, 210, 200, 25);
        add(exitButton);

        addButton.addActionListener((ActionEvent e) -> addProduct());
        deleteButton.addActionListener((ActionEvent e) -> deleteProduct());
        updateButton.addActionListener((ActionEvent e) -> updateProduct());
        exitButton.addActionListener((ActionEvent e) -> {
     
            new MainMenu().setVisible(true);
            dispose();
        });

    
        loadProducts();
    }

private void loadProducts() {
    try (Socket socket = new Socket("localhost", 12346);
         ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
         ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

        out.writeObject("OBTENER_PRODUCTOS");

        @SuppressWarnings("unchecked")
        ArrayList<Producto> productos = (ArrayList<Producto>) in.readObject();
        productList.clear();
        productList.addAll(productos);
        tableModel.fireTableDataChanged();
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "No se puede conectar con el servidor o cargar los productos.");
        System.exit(1);
    }
}

    private void addProduct() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField inventoryField = new JTextField();

        Object[] message = {
            "ID:", idField,
            "Nombre:", nameField,
            "Descripción:", descriptionField,
            "Precio:", priceField,
            "Inventario:", inventoryField,
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Agregar Producto", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String description = descriptionField.getText();
                int price = Integer.parseInt(priceField.getText());
                int inventory = Integer.parseInt(inventoryField.getText());

                Producto nuevoProducto = new Producto(id, name, description, price, inventory);

                try (Socket socket = new Socket("localhost", 12346);
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    out.writeObject("AGREGAR_PRODUCTO");
                    out.writeObject(nuevoProducto);

                    String response = (String) in.readObject();
                    if ("PRODUCTO_AGREGADO".equals(response)) {
                         loadProducts(); 
                        JOptionPane.showMessageDialog(this, "Producto agregado exitosamente.");
                    } else {
                        
                    }
                } catch (IOException | ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(this, "Error al conectar con el servidor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para ID, Precio e Inventario.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            Producto product = productList.get(selectedRow);

            try (Socket socket = new Socket("localhost", 12346);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                out.writeObject("ELIMINAR_PRODUCTO");
                out.writeObject(product.getIdProducto());

                String response = (String) in.readObject();
                if ("PRODUCTO_ELIMINADO".equals(response)) {
                    loadProducts(); 
                    JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente.");
                } else {
                    
                }
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error al conectar con el servidor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            Producto product = productList.get(selectedRow);

            JTextField idField = new JTextField(String.valueOf(product.getIdProducto()));
            JTextField nameField = new JTextField(product.getNombre());
            JTextField descriptionField = new JTextField(product.getDescripcion());
            JTextField priceField = new JTextField(String.valueOf(product.getPrecio()));
            JTextField inventoryField = new JTextField(String.valueOf(product.getInventario()));

            Object[] message = {
                "ID:", idField,
                "Nombre:", nameField,
                "Descripción:", descriptionField,
                "Precio:", priceField,
                "Inventario:", inventoryField,
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Editar Producto", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String description = descriptionField.getText();
                    int price = Integer.parseInt(priceField.getText());
                    int inventory = Integer.parseInt(inventoryField.getText());

                    product.setIdProducto(id);
                    product.setNombre(name);
                    product.setDescripcion(description);
                    product.setPrecio(price);
                    product.setInventario(inventory);

                    try (Socket socket = new Socket("localhost", 12346);
                         ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                         ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                        out.writeObject("ACTUALIZAR_PRODUCTO");
                        out.writeObject(product);

                        String response = (String) in.readObject();
                        if ("PRODUCTO_ACTUALIZADO".equals(response)) {
                            loadProducts();
                            JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente.");
                        } else {
                       
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        JOptionPane.showMessageDialog(this, "Error al conectar con el servidor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese valores válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un producto para editar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
   
     
}
