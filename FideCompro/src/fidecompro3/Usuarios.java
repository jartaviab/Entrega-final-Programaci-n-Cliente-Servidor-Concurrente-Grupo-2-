
package fidecompro3;

public class Usuarios {
    private int id_usuario;
    private String nombre;
    private String apellidos;
    private String contrasena;
    private int cedula;
    private int edad;
    private String correo_electronico;
    private String puesto;
public Usuarios(int id_usuario, String nombre, String apellidos, String contrasena, int cedula, int edad, String correo_electronico, String puesto){
    this.id_usuario = id_usuario;
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.contrasena = contrasena;
    this.cedula = cedula;
    this.edad = edad;
    this.correo_electronico = correo_electronico;
    this.puesto = puesto;
}
public int getid_usuario(){
    return id_usuario;
}
public void setid_usuario(int id_usuario){
    this.id_usuario = id_usuario;
}
public String getnombre(){
    return nombre;
}
public void setnombre(String nombre){
    this.nombre = nombre;
}
public String getapellidos(){
    return apellidos;
}
public int getcedula(){
    return cedula;
}
public void cedula(int cedula){
    this.cedula= cedula;
}
public void setapellidos(String apellidos){
    this.apellidos = apellidos;
}
public String getcontrasena(){
    return contrasena;
}
public void setcontrasena(String contrasena){
    this.contrasena = contrasena;
}
public int getedad(){
    return edad;
}
public void edad(int edad){
    this.edad = edad;
}
public String getcorreo_electronico(){
    return correo_electronico;
}
public void setcorreo_electronico(String correo_electronico){
    this.correo_electronico = correo_electronico;
}
public String getpuesto(){
    return puesto;
}
public void setpuesto(String puesto){
    this.puesto = puesto;
}

}  

