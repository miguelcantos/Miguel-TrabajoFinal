/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import DAO.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author DAW
 */
public class FXMLDocumentController implements Initializable {

    
    
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField contrasenya;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }    

    @FXML
    private void abrirInvitado(ActionEvent event) {
        
            
             try {
                FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("pantallaVista.fxml"));
                Parent root1= (Parent)fxmlLoader.load();
                Stage stage= new Stage();
                stage.setScene(new Scene(root1));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
    
             } catch (IOException e) {
             }
            
           
    }

    @FXML
    private void abrirIniciarSesion(ActionEvent event) {
        
        ObservableList<Empleados> lista= FXCollections.observableArrayList();
        
        PreparedStatement stmt= null;
        ResultSet rs = null;
        boolean salir=true;
        
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        
            try {
                
                stmt = con.prepareStatement("SELECT * From empleado");
                rs = stmt.executeQuery();   
            
                while(rs.next()){ 
                    Empleados E = new Empleados (rs.getInt("idEmpleado"), rs.getString("nombreUsuario"), rs.getString("contrasenya"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("direccion"), rs.getString("ciudad"), rs.getString("telefono"), rs.getString("email"), rs.getDouble("salario"));
                    lista.add(E);
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
        
                  
            for (int i = 0; i < lista.size(); i++) {
            
                if(usuario.getText().equals(lista.get(i).getNombreUsuario()) && contrasenya.getText().equals(lista.get(i).getContrasenya())){
                
                    try {
                        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("pantallaModificacion.fxml"));
                        Parent root1= (Parent)fxmlLoader.load();
                        Stage stage= new Stage();
                        stage.setScene(new Scene(root1));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();
                        
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    i=100;
                    salir=false;
                }    
            }
            
            
        if(salir==true){
        
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText(" ERROR DE USUARIO O CONTRASEÑA ");
            alert.showAndWait();
            
            usuario.setText("");
            contrasenya.setText("");
        }
        
        
        
    }
    
    
}    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

