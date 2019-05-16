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
public class Material {
      private String idMaterial;
      private String nombre;
      private Double precioXmetro;

    public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecioXmetro() {
        return precioXmetro;
    }

    public void setPrecioXmetro(Double precioXmetro) {
        this.precioXmetro = precioXmetro;
    }

    public Material(String idMaterial, String nombre, Double precioXmetro) {
        this.idMaterial = idMaterial;
        this.nombre = nombre;
        this.precioXmetro = precioXmetro;
    }
      
      
    public static void llenarMateriales (ObservableList<Material> lista){
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        ResultSet rs=null;
        PreparedStatement stmt=null;
        try {
            stmt = con.prepareStatement("SELECT * FROM material");
            stmt.executeQuery();
            rs = stmt.executeQuery();

            while(rs.next()){
            lista.add(
                new Material(
                    rs.getString("idMaterial"),
                    rs.getString("nombre"),
                    rs.getDouble("precioXmetro") ));

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
