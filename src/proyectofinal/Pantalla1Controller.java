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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DAW
 */
public class Pantalla1Controller implements Initializable {

    int eleccion = 0;
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
    @FXML
    private Button bModificar;
    @FXML
    private Button bCrearNuevo;
    @FXML
    private Button bGuardar;
    @FXML
    private Button bcerrar;

    public Empleados getTablaEmpleadosSeleccionado() {
        if (tableView != null) {
            List<Empleados> tabla = tableView.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Empleados empleadosSeleccionado = tabla.get(0);
                return empleadosSeleccionado;
            }
        }
        return null;
    }

    public void ponerEmpleadoSeleccionado() {
        bGuardar.setVisible(false);
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
        bGuardar.setVisible(false);
        lIdEmpleado.setEditable(false);
    }

    private void todoVacio() {
        lIdEmpleado.setText(null);
        lNombreUsuario.setText(null);
        lContrasenya.setText(null);
        lNombre.setText(null);
        lApellido.setText(null);
        lDireccion.setText(null);
        lCiudad.setText(null);
        lTelefono.setText(null);
        lEmail.setText(null);
        lSalario.setText(null);
    }

    private void noEditable() {

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

    private void rellenarTableView() {
        tableView.getItems().clear();
        Empleados.llenarEmpleados(lista);
        tableView.setItems(lista);

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

    private void siEditable() {

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
        Integer id = Integer.parseInt(lIdEmpleado.getText());
        PreparedStatement stmt = null;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
        alert.setHeaderText(null);
        alert.setContentText("¿Seguro que quieres eliminar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("OK");
            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();
            try {
                stmt = con.prepareStatement("DELETE from empleado where idEmpleado=?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Correcto");
                alert1.setHeaderText(null);
                alert1.setContentText(" Se ha eliminado el empleado ");
                alert1.showAndWait();
                
            } catch (SQLException e) {
                 Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Error");
                alert2.setHeaderText(null);
                alert2.setContentText(" No se ha  eliminado el empleado ");
                alert2.showAndWait();
                
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                } finally {
                    conexion.desconectar(con);
                }
            }
        } else {
            System.out.println("CANCEL");
        }

        rellenarTableView();
        tableView.refresh();
        noEditable();
        todoVacio();

    }

    @FXML
    private void botonModificar(ActionEvent event) {
        siEditable();
        bGuardar.setVisible(true);
        eleccion = 1;
    }

    @FXML
    private void botonCrearNuevo(ActionEvent event) {

        todoVacio();
        siEditable();

        eleccion = 2;

        bGuardar.setVisible(true);
        lIdEmpleado.setVisible(false);

    }

    @FXML
    private void botonGuardar(ActionEvent event) {

        bGuardar.setVisible(false);

        if (eleccion == 1 ) {
            if (comprobarCampos() == true || soloNumeros(lTelefono.getText()) == false || soloLetras(lNombre.getText()) == false || soloLetras(lApellido.getText()) == false || soloLetras(lCiudad.getText()) == false) {
                Alert alert0 = new Alert(AlertType.INFORMATION);
                alert0.setTitle("Error");
                alert0.setHeaderText(null);
                alert0.setContentText(" Tienes campos incorrectos ");
                alert0.showAndWait();

                todoVacio();
                noEditable();
            } else {

                Integer id = Integer.parseInt(lIdEmpleado.getText());
                Double salario = Double.parseDouble(lSalario.getText());
                PreparedStatement stmt = null;
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Estas seguro?");
                alert.setHeaderText(null);
                alert.setContentText("¿Seguro que quieres modificar este empleado?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    System.out.println("OK");
                    Conexion conexion = new Conexion();
                    Connection con = conexion.conectar();
                    try {

                        stmt = con.prepareStatement("UPDATE empleado SET  nombreUsuario=?, contrasenya=?, nombre=?, apellido=?, direccion=?, ciudad=?, telefono=?, email=?, salario=? WHERE idEmpleado=?");
                        stmt.setString(1, lNombreUsuario.getText());
                        stmt.setString(2, lContrasenya.getText());
                        stmt.setString(3, lNombre.getText());
                        stmt.setString(4, lApellido.getText());
                        stmt.setString(5, lDireccion.getText());
                        stmt.setString(6, lCiudad.getText());
                        stmt.setString(7, lTelefono.getText());
                        stmt.setString(8, lEmail.getText());
                        stmt.setDouble(9, salario);
                        stmt.setInt(10, id);
                        stmt.executeUpdate();

                        Alert alert1 = new Alert(AlertType.INFORMATION);
                        alert1.setTitle("Correcto");
                        alert1.setHeaderText(null);
                        alert1.setContentText(" Se ha modificado el empleado ");
                        alert1.showAndWait();

                        rellenarTableView();
                        tableView.refresh();
                        noEditable();
                        todoVacio();

                    } catch (SQLException e) {
                        Alert alert2 = new Alert(AlertType.INFORMATION);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText(" No se ha modificado el empleado ");
                        alert2.showAndWait();
                        rellenarTableView();
                        tableView.refresh();
                        noEditable();
                        todoVacio();

                    } finally {
                        try {
                            if (stmt != null) {
                                stmt.close();
                            }
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        } finally {
                            conexion.desconectar(con);
                        }
                    }
                } else {
                    System.out.println("CANCEL");
                    noEditable();
                    todoVacio();
                }
            }
        } else if (eleccion == 2) {
            if (comprobarCampos() == true || soloNumeros(lTelefono.getText()) == false || soloLetras(lNombre.getText()) == false || soloLetras(lApellido.getText()) == false || soloLetras(lCiudad.getText()) == false) {
                Alert alert0 = new Alert(AlertType.INFORMATION);
                alert0.setTitle("Error");
                alert0.setHeaderText(null);
                alert0.setContentText(" Tienes campos incorrectos ");
                alert0.showAndWait();

                todoVacio();
                noEditable();
                lIdEmpleado.setVisible(true);
            } else {
                Double salario = Double.parseDouble(lSalario.getText());
                PreparedStatement stmt = null;
                Alert alert3 = new Alert(AlertType.CONFIRMATION);
                alert3.setTitle("Estas seguro");
                alert3.setHeaderText(null);
                alert3.setContentText("¿Seguro que quieres crear este empleado?");
                Optional<ButtonType> result1 = alert3.showAndWait();
                if (result1.isPresent() && result1.get() == ButtonType.OK) {
                    System.out.println("OK");
                    Conexion conexion = new Conexion();
                    Connection con = conexion.conectar();
                    try {

                        stmt = con.prepareStatement("INSERT INTO empleado (nombreUsuario, contrasenya, nombre, apellido, direccion, ciudad, telefono, email, salario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                        stmt.setString(1, lNombreUsuario.getText());
                        stmt.setString(2, lContrasenya.getText());
                        stmt.setString(3, lNombre.getText());
                        stmt.setString(4, lApellido.getText());
                        stmt.setString(5, lDireccion.getText());
                        stmt.setString(6, lCiudad.getText());
                        stmt.setString(7, lTelefono.getText());
                        stmt.setString(8, lEmail.getText());
                        stmt.setDouble(9, salario);
                        stmt.executeUpdate();

                        Alert alert4 = new Alert(AlertType.INFORMATION);
                        alert4.setTitle("Correcto");
                        alert4.setHeaderText(null);
                        alert4.setContentText(" Se ha creado el empleado ");
                        alert4.showAndWait();

                        rellenarTableView();
                        tableView.refresh();
                        todoVacio();
                        noEditable();
                        lIdEmpleado.setVisible(true);

                    } catch (SQLException e) {
                        Alert alert5 = new Alert(AlertType.CONFIRMATION);
                        alert5.setTitle("ERROR");
                        alert5.setHeaderText(null);
                        alert5.setContentText("No se ha creado el elemento, quierer borrar todo?");
                        Optional<ButtonType> result2 = alert5.showAndWait();
                        if (result2.isPresent() && result2.get() == ButtonType.OK) {
                            System.out.println("OK");
                            rellenarTableView();
                            tableView.refresh();
                            noEditable();
                            todoVacio();
                            lIdEmpleado.setVisible(true);
                        } else {
                            lIdEmpleado.setVisible(false);
                            bGuardar.setVisible(true);
                            siEditable();
                        }

                    } finally {
                        try {
                            if (stmt != null) {
                                stmt.close();
                            }
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        } finally {
                            conexion.desconectar(con);
                        }
                    }
                } else {
                    System.out.println("CANCEL");
                    noEditable();
                    todoVacio();
                }

            }
        }
        
    }

    @FXML
    private void botonCerrar(ActionEvent event) {
        Stage stage = (Stage) bcerrar.getScene().getWindow();
        stage.close();
    }
    
    private  boolean soloNumeros(String a){

          Pattern  p = Pattern.compile("[^0-9]");
          Matcher m = p.matcher(a);
          boolean resultado = m.find();

          return resultado;
    }
    
    private  boolean soloLetras(String a){

          Pattern  p = Pattern.compile("[^A-Za-z]");
          Matcher m = p.matcher(a);
          boolean resultado = m.find();

          return resultado;
    }
    
    private boolean comprobarCampos() {
        boolean solucion = false;
        if (lNombreUsuario.getText() == null
                || lContrasenya.getText() == null
                || lNombre.getText() == null
                || lApellido.getText() == null
                || lDireccion.getText() == null
                || lCiudad.getText() == null
                || lTelefono.getText() == null
                || lEmail.getText() == null) {

            solucion = true;

        }

        return solucion;
    }

}
