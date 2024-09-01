/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fidecompro3;

public class Factura extends Producto{
    private int id_factura;
    private Cliente cliente;
    private String producto;
    private String fecha_y_hora;
    private int total_a_pagar;
    private String metodo_de_pago;

public Factura(int id_producto, String nombre, String descripcion, int precio,int inventario , int id_factura, Cliente cliente,String producto, String fecha_y_hora, int total_a_pagar, String metodo_de_pago){
    super(id_producto, nombre, descripcion, precio, inventario);
    this.id_factura = id_factura;
    this.cliente = cliente;
    this.producto = producto;
    this.fecha_y_hora = fecha_y_hora;
    this.metodo_de_pago = metodo_de_pago;
}
public int getid_factura(){
    return id_factura;
}
public void setid_factura(int id_factura){
    this.id_factura = id_factura;
}
public Cliente getcliente(){
    return cliente;
}
public void setCliente(Cliente cliente){
    this.cliente = cliente;
}
public String getproducto(){
    return producto;
}
public void setproducto(String producto){
    this.producto = producto;
}
public String getfecha_y_hora(){
    return fecha_y_hora;
}
public void setfecha_y_hora(String fecha_y_hora){
    this.fecha_y_hora = fecha_y_hora;
}
public int gettotal_a_pagar(){
    return total_a_pagar;
}
public void settotal_a_pagar(int total_a_pagar){
    this.total_a_pagar = total_a_pagar;
}
public String metodo_de_pago(){
    return metodo_de_pago;
}
public void setmetodo_de_pago(String metodo_de_pago){
    this.metodo_de_pago = metodo_de_pago;
}
}
