package fidecompro3;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    
    private ServerSocket serverSocket;
    private Connection connection;

    public Servidor(int puerto) {
        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor iniciado.");
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/ProductosFidecompro3", "root", "12345");
            System.out.println("Conexión a la base de datos exitosa.");
            System.out.println("Servidor iniciado en el puerto " + puerto);
        } catch (IOException | SQLException e) {
          
        }
    }

    public void iniciar() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new ManejadorCliente(clientSocket, connection).start();
            } catch (IOException e) {
               
            }
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor(12346);
        servidor.iniciar();
    }
}

class ManejadorCliente extends Thread {
    private Socket socket;
    private Connection connection;

    public ManejadorCliente(Socket socket, Connection connection) {
        this.socket = socket;
        this.connection = connection;
    }

    @Override
    public void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            String comando = (String) in.readObject();

            switch (comando) {
                case "INICIAR_SESION":
                    manejarIniciarSesion(in, out);
                    break;
                case "CREAR_USUARIO":
                    manejarCrearUsuario(in, out);
                    break;
                case "OBTENER_PRODUCTOS":
                    manejarObtenerProductos(out);
                    break;
                case "AGREGAR_PRODUCTO":
                    manejarAgregarProducto(in, out);
                    break;
                case "ACTUALIZAR_PRODUCTO":
                    manejarActualizarProducto(in, out);
                    break;
                case "ELIMINAR_PRODUCTO":
                    manejarEliminarProducto(in, out);
                    break;
                default:
                    out.writeObject("COMANDO_INVALIDO");
                    break;
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
      
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                
            }
        }
    }

    private void manejarIniciarSesion(ObjectInputStream in, ObjectOutputStream out) throws IOException, SQLException, ClassNotFoundException {
        String usuario = (String) in.readObject();
        String contraseña = (String) in.readObject();

        try (PreparedStatement statement = connection.prepareStatement("SELECT password FROM Usuarios WHERE username = ?")) {
            statement.setString(1, usuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String contraseñaAlmacenada = resultSet.getString("password");
                    if (contraseñaAlmacenada.equals(contraseña)) {
                        out.writeObject("SESION_INICIADA");
                    } else {
                        out.writeObject("FALLO_INICIO_SESION");
                    }
                } else {
                    out.writeObject("FALLO_INICIO_SESION");
                }
            }
        }
    }

    private void manejarCrearUsuario(ObjectInputStream in, ObjectOutputStream out) throws IOException, SQLException, ClassNotFoundException {
        String usuario = (String) in.readObject();
        String contraseña = (String) in.readObject();

        try (PreparedStatement checkUser = connection.prepareStatement("SELECT username FROM Usuarios WHERE username = ?")) {
            checkUser.setString(1, usuario);
            try (ResultSet resultSet = checkUser.executeQuery()) {
                if (resultSet.next()) {
                    out.writeObject("USUARIO_EXISTE");
                } else {
                    try (PreparedStatement statement = connection.prepareStatement("INSERT INTO Usuarios (username, password) VALUES (?, ?)")) {
                        statement.setString(1, usuario);
                        statement.setString(2, contraseña);
                        statement.executeUpdate();
                        out.writeObject("USUARIO_CREADO");
                    }
                }
            }
        }
    }

    private void manejarObtenerProductos(ObjectOutputStream out) throws IOException, SQLException {
        List<Producto> productos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Productos");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id_producto");
                String nombre = resultSet.getString("nombre");
                String descripcion = resultSet.getString("descripcion");
                int precio = resultSet.getInt("precio");
                int inventario = resultSet.getInt("inventario");
                productos.add(new Producto(id, nombre, descripcion, precio, inventario));
            }

            out.writeObject(productos);
        }
    }

    private void manejarAgregarProducto(ObjectInputStream in, ObjectOutputStream out) throws IOException, SQLException, ClassNotFoundException {
        Producto producto = (Producto) in.readObject();

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO Productos (nombre, descripcion, precio, inventario) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setInt(3, producto.getPrecio());
            statement.setInt(4, producto.getInventario());
            statement.executeUpdate();
            out.writeObject("PRODUCTO_AGREGADO");
        }
    }

    private void manejarActualizarProducto(ObjectInputStream in, ObjectOutputStream out) throws IOException, SQLException, ClassNotFoundException {
        Producto producto = (Producto) in.readObject();

        try (PreparedStatement statement = connection.prepareStatement("UPDATE Productos SET nombre = ?, descripcion = ?, precio = ?, inventario = ? WHERE id_producto = ?")) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setInt(3, producto.getPrecio());
            statement.setInt(4, producto.getInventario());
            statement.setInt(5, producto.getIdProducto());
            statement.executeUpdate();
            out.writeObject("PRODUCTO_ACTUALIZADO");
        }
    }

    private void manejarEliminarProducto(ObjectInputStream in, ObjectOutputStream out) throws IOException, SQLException, ClassNotFoundException {
        int idProducto = (Integer) in.readObject();

        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Productos WHERE id_producto = ?")) {
            statement.setInt(1, idProducto);
            statement.executeUpdate();
            out.writeObject("PRODUCTO_ELIMINADO");
        }
    }
}
