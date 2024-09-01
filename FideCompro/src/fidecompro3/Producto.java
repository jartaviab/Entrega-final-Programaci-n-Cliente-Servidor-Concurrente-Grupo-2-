package fidecompro3;

import java.io.Serializable;

public class Producto implements Serializable {
    

    private int idProducto;
    private String nombre;
    private String descripcion;
    private int precio;
    private int inventario;
    private int cantidadSeleccionada;

   
    public Producto() {
     
        this.idProducto = 0;
        this.nombre = "";
        this.descripcion = "";
        this.precio = 0;
        this.inventario = 0;
        this.cantidadSeleccionada = 0;
    }

    // Constructor con parámetros
    public Producto(int idProducto, String nombre, String descripcion, int precio, int inventario) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.inventario = inventario;
        
    }

    // Getters y Setters
    public int getIdProducto() { 
        return idProducto; 
    }
    public void setIdProducto(int idProducto) { 
        this.idProducto = idProducto; 
    }

    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getDescripcion() { 
        return descripcion; 
    }
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }

    public int getPrecio() { 
        return precio; 
    }
    public void setPrecio(int precio) { 
        this.precio = precio; 
    }

    public int getInventario() { 
        return inventario; 
    }
    public void setInventario(int inventario) { 
        this.inventario = inventario; 
    }

    public int getCantidadSeleccionada() { 
        return cantidadSeleccionada; 
    }
    public void setCantidadSeleccionada(int cantidadSeleccionada) { 
        this.cantidadSeleccionada = cantidadSeleccionada; 
    }
}
