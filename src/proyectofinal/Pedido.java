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
public class Pedido {
      private String idPedido;
      private String idCliente;
      private String fechaPedido;

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }


    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Pedido(String idPedido, String idCliente, String fechaPedido) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fechaPedido = fechaPedido;
    }
      
    public static void llenarPedido (ObservableList<Pedido> lista){
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        ResultSet rs=null;
        PreparedStatement stmt=null;
        try {
            stmt = con.prepareStatement("SELECT * FROM pedido");
            stmt.executeQuery();
            rs = stmt.executeQuery();

            while(rs.next()){
            lista.add(
                new Pedido(
                    rs.getString("idPedido"),
                    rs.getString("idCliente"),
                    rs.getDate("fechaPedido").toString() ));

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
