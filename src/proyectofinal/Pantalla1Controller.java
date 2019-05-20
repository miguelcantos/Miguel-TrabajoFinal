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
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author DAW
 */
public class Pantalla1Controller implements Initializable {

    @FXML
    private TextField lIdEmpleado;
    @FXML
    private TextField lNombreUsuario;
    @FXML
    private TextField lContrasenya;
    @FXML
    private TextField lNombre;
    @FXML
    private TextField lApellido;
    @FXML
    private TextField lDireccion;
    @FXML
    private TextField lCiudad;
    @FXML
    private TextField lTelefono;
    @FXML
    private TextField lEmail;
    @FXML
    private TextField lSalario;
    @FXML
    private TableView<Empleados> tableView;
    @FXML
    private TableColumn<Empleados, Integer> tID;
    @FXML
    private TableColumn<Empleados, String> tNomUsu;
    @FXML
    private TableColumn<Empleados, String> tContrasenya;
    @FXML
    private TableColumn<Empleados, String> tNombre;
    @FXML
    private TableColumn<Empleados, String> tApellido;
    @FXML
    private TableColumn<Empleados, String> tDireccion;
    @FXML
    private TableColumn<Empleados, String> tCiudad;
    @FXML
    private TableColumn<Empleados, String> tTelefono;
    @FXML
    private TableColumn<Empleados, String> tEmail;
    @FXML
    private TableColumn<Empleados, Double> tSalario;
    
    ObservableList<Empleados> lista = FXCollections.observableArrayList();

     private final ListChangeListener<Empleados> selectorTablaEmpleados = new ListChangeListener<Empleados>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Empleados> e) {
            ponerEmpleadoSeleccionado();
        }
    };

    public Empleados getTablaEmpleadosSeleccionado() {
        if (tableView != null) {
            List<Empleados> tabla = tableView.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Empleados clienteSeleccionado = tabla.get(0);
                return clienteSeleccionado;
            }
        }
        return null;
    }

    public void ponerEmpleadoSeleccionado() {
        final Empleados empleado = getTablaEmpleadosSeleccionado();
        int posicionEmpleado = lista.indexOf(empleado);
        this.noEditable();
        if (empleado != null) {
            lIdEmpleado.setText(Integer.toString(empleado.getIdEmpleado()));
            lNombreUsuario.setText(empleado.getNombreUsuario());
            lContrasenya.setText(empleado.getContrasenya());
            lNombre.setText(empleado.getNombre());
            lApellido.setText(empleado.getApellido());
            lDireccion.setText(empleado.getDireccion());
            lCiudad.setText(empleado.getCiudad());
            lTelefono.setText(empleado.getTelefono());
            lEmail.setText(empleado.getEmail());
            lSalario.setText(Double.toString(empleado.getSalario()));
        }
    }
    
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rellenarTableView();

        final ObservableList<Empleados> tablaEmpleadosSel = tableView.getSelectionModel().getSelectedItems();
            tablaEmpleadosSel.addListener(selectorTablaEmpleados);
        
        this.noEditable();
    }

    
    
    private void noEditable(){
    lIdEmpleado.setEditable(false);
    lNombreUsuario.setEditable(false);
    lContrasenya.setEditable(false);
    lNombre.setEditable(false);
    lApellido.setEditable(false);
    lDireccion.setEditable(false);
    lCiudad.setEditable(false);
    lTelefono.setEditable(false);
    lEmail.setEditable(false);
    lSalario.setEditable(false);
    }
    private void rellenarTableView(){
        tableView.getItems().clear();
        Empleados.llenarEmpleados (lista);
        tableView.setItems (lista);

        tID.setCellValueFactory(new PropertyValueFactory< Empleados, Integer>("idEmpleado"));
        tNomUsu.setCellValueFactory(new PropertyValueFactory< Empleados, String>("nombreUsuario"));
        tContrasenya.setCellValueFactory(new PropertyValueFactory< Empleados, String>("contrasenya"));
        tNombre.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombre"));
        tApellido.setCellValueFactory(new PropertyValueFactory< Empleados, String>("apellido"));
        tDireccion.setCellValueFactory(new PropertyValueFactory< Empleados, String>("direccion"));
        tCiudad.setCellValueFactory(new PropertyValueFactory< Empleados, String>("ciudad"));
        tTelefono.setCellValueFactory(new PropertyValueFactory< Empleados, String>("telefono"));
        tEmail.setCellValueFactory(new PropertyValueFactory< Empleados, String>("email"));
        tSalario.setCellValueFactory(new PropertyValueFactory< Empleados, Double>("salario"));
    }
    private void siEditable(){
    lIdEmpleado.setEditable(false);
    lNombreUsuario.setEditable(true);
    lContrasenya.setEditable(true);
    lNombre.setEditable(true);
    lApellido.setEditable(true);
    lDireccion.setEditable(true);
    lCiudad.setEditable(true);
    lTelefono.setEditable(true);
    lEmail.setEditable(true);
    lSalario.setEditable(true);
    }

    @FXML
    private void botonEliminar(ActionEvent event) {
    Integer id= Integer.parseInt(lIdEmpleado.getText());
    PreparedStatement stmt=null;
      Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
         alert.setHeaderText(null);
        alert.setContentText("¿Seguro que quieres eliminar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            System.out.println("OK");
            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();
             try {
                stmt = con.prepareStatement("DELETE from empleado where idEmpleado=?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
            }finally{
                try {
                    if (stmt != null) {
                        stmt.close();
                    }    
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }finally{            
                    conexion.desconectar(con);
                }
            }
        } else {
            System.out.println("CANCEL");
        }
        
     rellenarTableView();
     tableView.refresh();
        
    }
    
    
}
