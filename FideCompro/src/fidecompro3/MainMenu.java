package fidecompro3;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {
    private final JButton productsButton;
    private final JButton facturaButton;
    private final JButton exitButton;

    public MainMenu() {
        setTitle("Menú Principal");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        productsButton = new JButton("Productos");
        productsButton.setBounds(50, 30, 200, 25);
        add(productsButton);

        facturaButton = new JButton("Factura");
        facturaButton.setBounds(50, 60, 200, 25);
        add(facturaButton);

        exitButton = new JButton("Salir");
        exitButton.setBounds(50, 90, 200, 25);
        add(exitButton);

        productsButton.addActionListener((ActionEvent e) -> {
            new ProductWindow().setVisible(true);
            dispose();
        });

        facturaButton.addActionListener((ActionEvent e) -> {
            new FacturaWindow().setVisible(true);
            dispose();
        });

        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
    }

   
}
