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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author DAW
 */
public class Pantalla5Controller implements Initializable {

    @FXML
    private TableView<LineasPedido> tableView;
    @FXML
    private TextField lIdLineasPedido;
    @FXML
    private TextField lIdMaterial;
    @FXML
    private TextField lIdPedido;
    @FXML
    private TextField lCantidadMetros;
    @FXML
    private Button bModificar;
    @FXML
    private Button bEliminar;
    @FXML
    private Button bCrearNuevo;
    @FXML
    private Button bGuardar;
    @FXML
    private Button bcerrar;
    @FXML
    private TableColumn<LineasPedido, Integer> tID;
    @FXML
    private TableColumn<LineasPedido, Integer> tIdMaterial;
    @FXML
    private TableColumn<LineasPedido, Integer> tIdPedido;
    @FXML
    private TableColumn<LineasPedido, Double> tCantidadMetros;
    @FXML
    private TableColumn<LineasPedido, Double> tTotalProducto;

    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void botonModificar(ActionEvent event) {
    }

    @FXML
    private void botonEliminar(ActionEvent event) {
    }

    @FXML
    private void botonCrearNuevo(ActionEvent event) {
    }

    @FXML
    private void botonGuardar(ActionEvent event) {
    }

    @FXML
    private void botonCerrar(ActionEvent event) {
    }
    
}
