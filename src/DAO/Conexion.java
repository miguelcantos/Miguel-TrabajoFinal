
package DAO;

import java.sql.*;


public class Conexion {
    
    public Connection conectar(){
        
        Connection con=null;
        
        try{            
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://192.168.1.46/papel?useSSL=false";
            String user="bd";
            String pass="123abcDEF.";
            con = DriverManager.getConnection(url, user, pass);
        }
        catch(Exception ex){            
            System.out.println("Ha sido imposible crear la conexion");
        }
        
       return con; 
    }
    
    public void desconectar(Connection con){
        
        try{
            if (con!=null) con.close();
            
        } catch(Exception ex){
            
            System.out.println("Ha sido imposible cerrar la conexion");
        }
        
    }
    
}
