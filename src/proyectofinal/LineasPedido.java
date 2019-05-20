/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import DAO.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author DAW
 */
public class LineasPedido {
      private int idLineasPedido;
      private int idMaterial;
      private int idPedido;
      private Double cantidadMetros; 
      private Double precio;

    public int getIdLineasPedido() {
        return idLineasPedido;
    }

    public void setIdLineasPedido(int idLineasPedido) {
        this.idLineasPedido = idLineasPedido;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Double getCantidadMetros() {
        return cantidadMetros;
    }

    public void setCantidadMetros(Double cantidadMetros) {
        this.cantidadMetros = cantidadMetros;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public LineasPedido(int idLineasPedido, int idMaterial, int idPedido, Double cantidadMetros, Double precio) {
        this.idLineasPedido = idLineasPedido;
        this.idMaterial = idMaterial;
        this.idPedido = idPedido;
        this.cantidadMetros = cantidadMetros;
        this.precio = precio;
    }

    
      
     public static void llenarLineasPedido (ObservableList<LineasPedido> lista){
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        ResultSet rs=null;
        PreparedStatement stmt=null;
        try {
            stmt = con.prepareStatement("SELECT * FROM lineasPedido");
            stmt.executeQuery();
            rs = stmt.executeQuery();

            while(rs.next()){
            lista.add(
                new LineasPedido(
                    rs.getInt("idLineasPedido"),
                    rs.getInt("idMaterial"),
                    rs.getInt("idPedido"),
                    rs.getDouble("cantidadMetros"),
                    rs.getDouble("precio") ));

            }
        } catch (SQLException e) {
            }finally{
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }    
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }finally{            
                    conexion.desconectar(con);
                }
            }
        
    }    
      
}
