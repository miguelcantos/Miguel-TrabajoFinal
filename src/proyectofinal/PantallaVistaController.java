/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
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
    private TableView<?> tableEmpleados;
    @FXML
    private TableView<?> tablePedidos;

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
    
    }

    @FXML
    private void aTablePedidos(ActionEvent event) {
    ocultarTodo();
    tablePedidos.setVisible(true);
    
    }
}
