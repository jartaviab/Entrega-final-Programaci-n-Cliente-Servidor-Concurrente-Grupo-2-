package fidecompro3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CrearTablas {
    public static void main(String[] args) {
        String url = "jdbc:derby://localhost:1527/ProductosFidecompro3"; // Ajusta la URL para tu base de datos
        String user = "root"; // Usuario de tu base de datos
        String password = "12345"; // Contraseña de tu base de datos

        // SQL para eliminar tablas si existen
        String dropUsersTableSQL = "DROP TABLE Usuarios";
        String dropProductsTableSQL = "DROP TABLE Productos";

        // SQL para crear tablas
        String createUsersTableSQL = "CREATE TABLE Usuarios (" +
                                     "username VARCHAR(50) PRIMARY KEY," +
                                     "password VARCHAR(255) NOT NULL" +
                                     ")";

        String createProductsTableSQL = "CREATE TABLE Productos (" +
                                        "id_producto INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY," +
                                        "nombre VARCHAR(255)," +
                                        "descripcion VARCHAR(255)," +
                                        "precio INT," +
                                        "inventario INT" +
                                        ")";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            
            // Eliminar las tablas si existen
            try {
                stmt.execute(dropUsersTableSQL);
            } catch (SQLException e) {
                // Tabla no existe, ignorar el error
            }

            try {
                stmt.execute(dropProductsTableSQL);
            } catch (SQLException e) {
                // Tabla no existe, ignorar el error
            }

            // Crear las tablas
            stmt.execute(createUsersTableSQL);
            stmt.execute(createProductsTableSQL);
            
            System.out.println("Tablas 'Usuarios' y 'Productos' creadas exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
