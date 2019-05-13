/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import DAO.Conexion;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DAW
 */
public class PantallaVistaController implements Initializable {

    @FXML
    private Button cerrar;
    @FXML
    private TableView<?> tableCliente;
    @FXML
    private TableView<?> tableMaterial;
    @FXML
    private TableView<Empleados> tableEmpleados;
    @FXML
    private TableView<?> tablePedidos;

    
    PreparedStatement stmt= null;
    ResultSet rs = null;
    @FXML
    private TableColumn<Empleados, String> eNomUsua;
    @FXML
    private TableColumn<Empleados, String> eNombre;
    @FXML
    private TableColumn<Empleados, String> eApellido;
    @FXML
    private TableColumn<Empleados, String> eDireccion;
    @FXML
    private TableColumn<Empleados, String> eCiudad;
    @FXML
    private TableColumn<Empleados, String> eTelefono;
    @FXML
    private TableColumn<Empleados, String> eEmail;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ocultarTodo();
       tableCliente.setVisible(true);
       
    }    

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) cerrar.getScene().getWindow();
        stage.close();
        
    }
    
    
    
    
    
    
    private void ocultarTodo(){
    
    tableCliente.setVisible(false);
    tableMaterial.setVisible(false);
    tableEmpleados.setVisible(false);
    tablePedidos.setVisible(false);
    
    }

    @FXML
    private void aTableCliente(ActionEvent event) {
    ocultarTodo();
    tableCliente.setVisible(true);
    
    }

    @FXML
    private void aTableMaterial(ActionEvent event) {
    ocultarTodo();
    tableMaterial.setVisible(true);
    
    }

    @FXML
    private void aTableEmpleados(ActionEvent event) {
    ocultarTodo();
    tableEmpleados.setVisible(true);
    
    ObservableList<Empleados> lista= FXCollections.observableArrayList();
    Empleados.llenarEmpleados(lista);
    tableEmpleados.setItems(lista);
   
            eNomUsua.setCellValueFactory(new PropertyValueFactory<Empleados,String>("nombreUsuario"));
            eNombre.setCellValueFactory(new PropertyValueFactory<Empleados,String>("nombre"));
            eApellido.setCellValueFactory(new PropertyValueFactory<Empleados,String>("apellido"));
            eDireccion.setCellValueFactory(new PropertyValueFactory<Empleados,String>("direccion"));
            eCiudad.setCellValueFactory(new PropertyValueFactory<Empleados,String>("ciudad"));
            eTelefono.setCellValueFactory(new PropertyValueFactory<Empleados,String>("telefono"));
            eEmail.setCellValueFactory(new PropertyValueFactory<Empleados,String>("email"));
            
        eNomUsua.setResizable(false);
    
    
    
    
    
    }
    
    

    @FXML
    private void aTablePedidos(ActionEvent event) {
    ocultarTodo();
    tablePedidos.setVisible(true);
    
    }
}
