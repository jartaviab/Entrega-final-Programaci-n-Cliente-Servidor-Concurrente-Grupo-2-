
package fidecompro3;



public class Cliente {
    private int id_cliente;
    private String nombre;
    private String direccion;
    private String telefono;
    private int cedula;
    private String correo_electronico;
public Cliente (int id_cliente, String nombre, String direccion, String telefono, int cedula, String correo_electronico ){
    this.id_cliente = id_cliente;
    this.nombre = nombre;
    this.direccion = direccion;
    this.telefono = telefono;
    this.cedula = cedula;
    this.correo_electronico = correo_electronico;
}
public int getid_cliente(){
    return id_cliente;
}
public void setid_cliente(int id_cliente){
    this.id_cliente = id_cliente;
}
public String getnombre(){
    return nombre;
}
public void setnombre(String nombre){
    this.nombre = nombre;
}
public String getdireccion(){
    return direccion;
}
public void setdireccion(String direccion){
    this.direccion = direccion;
}
public String gettelefono(){
    return telefono;
}
public void settelefono(String telefono){
    this.telefono = telefono;
}
public int getcedula(){
    return cedula;
}
public void cedula(int cedula){
    this.cedula= cedula;
}
public String getcorreo_electronico(){
    return correo_electronico;
}
public void setcorreo_electronico(String correo_electronico){
    this.correo_electronico = correo_electronico;
}
}
