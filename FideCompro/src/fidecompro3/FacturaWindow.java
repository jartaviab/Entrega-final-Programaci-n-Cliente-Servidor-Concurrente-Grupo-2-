package fidecompro3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FacturaWindow extends JFrame {
    private final JTextField nameField;
    private final JTextField idField;
    private final JButton generateButton;
    private final JTextArea facturaArea;
    private final JButton exitButton;
    private final JTable productTable;

    private final ProductTableModel tableModel;

    public FacturaWindow() {
        // Configuración de la ventana
        setTitle("Generación de Factura");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        tableModel = new ProductTableModel(new ArrayList<>());
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(20, 20, 550, 150);
        add(scrollPane);

        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setBounds(20, 180, 80, 25);
        add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(100, 180, 165, 25);
        add(nameField);

        JLabel idLabel = new JLabel("Cédula:");
        idLabel.setBounds(20, 210, 80, 25);
        add(idLabel);

        idField = new JTextField(20);
        idField.setBounds(100, 210, 165, 25);
        add(idField);

        generateButton = new JButton("Generar Factura");
        generateButton.setBounds(20, 240, 150, 25);
        add(generateButton);

        facturaArea = new JTextArea();
        JScrollPane facturaScrollPane = new JScrollPane(facturaArea);
        facturaScrollPane.setBounds(20, 270, 550, 110);
        add(facturaScrollPane);

        exitButton = new JButton("Salir");
        exitButton.setBounds(20, 390, 150, 25);
        add(exitButton);

        generateButton.addActionListener(this::generateFactura);
        exitButton.addActionListener(e -> {
            new MainMenu().setVisible(true);
            dispose();
        });

        // Cargar productos desde el servidor
        cargarProductosDesdeServidor();
    }

    private void cargarProductosDesdeServidor() {
        String serverAddress = "localhost";
        int serverPort = 12346;

        try (Socket socket = new Socket(serverAddress, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Solicitar productos al servidor
            out.writeObject("OBTENER_PRODUCTOS");
            System.out.println("Cliente: Enviado OBTENER_PRODUCTOS");

            // Leer la lista de productos desde el servidor
            List<Producto> productos = (List<Producto>) in.readObject();
            System.out.println("Cliente: Productos recibidos");

            // Actualizar el modelo de la tabla con los productos recibidos
            tableModel.setProductos(productos);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error de comunicación con el servidor.", "Error de IO", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error al procesar los productos recibidos.", "Error de clase no encontrada", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void generateFactura(ActionEvent e) {
        String name = nameField.getText();
        String id = idField.getText();

        if (name.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder factura = new StringBuilder("Factura\n\n");
        factura.append("Cliente: ").append(name).append("\n");
        factura.append("Cédula: ").append(id).append("\n\n");

        int total = 0;
        for (int i = 0; i < productTable.getRowCount(); i++) {
            Object cantidadObj = tableModel.getValueAt(i, 5); // Obtén el valor de la celda

            int cantidadSeleccionada;
            try {
                cantidadSeleccionada = Integer.parseInt(cantidadObj.toString()); // Convertir a Integer
            } catch (NumberFormatException ex) {
                cantidadSeleccionada = 0; // Valor por defecto en caso de error
            }

            if (cantidadSeleccionada > 0) {
                int precio = (Integer) tableModel.getValueAt(i, 3); // Obtén el precio
                String nombreProducto = (String) tableModel.getValueAt(i, 1); // Obtén el nombre del producto
                int subtotal = cantidadSeleccionada * precio;
                total += subtotal;
                factura.append(cantidadSeleccionada)
                       .append("x ")
                       .append(nombreProducto)
                       .append(" ")
                       .append(subtotal)
                       .append(" colones\n");
            }
        }

        factura.append("\nSuma total: ").append(total).append(" colones");
        facturaArea.setText(factura.toString());
    }

    
   
}
