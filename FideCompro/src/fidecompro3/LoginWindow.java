package fidecompro3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;

public class LoginWindow extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton createUserButton;
    private final JLabel errorMessage;

    public LoginWindow() {
        setTitle("Inicio de Sesión");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Usuario:");
        usernameLabel.setBounds(20, 20, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(20, 50, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        add(passwordField);

        loginButton = new JButton("Iniciar Sesión");
        loginButton.setBounds(20, 80, 150, 25);
        add(loginButton);

        createUserButton = new JButton("Crear Usuario");
        createUserButton.setBounds(20, 110, 150, 25);
        add(createUserButton);

        errorMessage = new JLabel();
        errorMessage.setBounds(20, 140, 250, 25);
        add(errorMessage);

        loginButton.addActionListener((ActionEvent e) -> login());

        createUserButton.addActionListener((ActionEvent e) -> createUser());
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Socket socket = new Socket("localhost", 12346);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Enviar comando de inicio de sesión y credenciales al servidor
            out.writeObject("INICIAR_SESION");
            out.writeObject(username);
            out.writeObject(password);

            // Leer la respuesta del servidor
            String response = (String) in.readObject();
            if ("SESION_INICIADA".equals(response)) {
                new MainMenu().setVisible(true);
                this.dispose();
            } else {
                errorMessage.setText("Usuario o contraseña incorrectos");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            errorMessage.setText("Error de conexión con el servidor");
        }
    }

    private void createUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Socket socket = new Socket("localhost", 12346);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Enviar comando de creación de usuario y credenciales al servidor
            out.writeObject("CREAR_USUARIO");
            out.writeObject(username);
            out.writeObject(password);

            // Leer la respuesta del servidor
            String response = (String) in.readObject();
            if ("USUARIO_CREADO".equals(response)) {
                JOptionPane.showMessageDialog(this, "Usuario creado exitosamente");
            } else if ("USUARIO_EXISTE".equals(response)) {
                errorMessage.setText("El usuario ya existe");
            } else {
                errorMessage.setText("Error al crear usuario");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            errorMessage.setText("Error de conexión con el servidor");
        }
    }

  
}
