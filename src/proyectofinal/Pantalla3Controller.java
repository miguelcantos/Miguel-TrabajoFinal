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
public class Pantalla3Controller implements Initializable {

    @FXML
    private TextField lIdMaterial;
    @FXML
    private TextField lNombre;
    @FXML
    private TextField lPrecioXMetro;
    @FXML
    private Button bModificar;
    @FXML
    private Button bEliminar;
    @FXML
    private Button bCrearNuevo;
    @FXML
    private Button bGuardar;
    @FXML
    private TableView<Material> tableView;
    @FXML
    private TableColumn<Material, Integer> tID;
    @FXML
    private TableColumn<Material, String> tNombre;
    @FXML
    private TableColumn<Material, Double> tPrecioXMetro;

    int eleccion = 0;
    ObservableList<Material> lista = FXCollections.observableArrayList();

    private final ListChangeListener<Material> selectorTablaMaterial = new ListChangeListener<Material>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Material> m) {
            ponerMaterialSeleccionado();
        }
    };
    @FXML
    private Button bCerrar;

    public Material getTablaMaterialSeleccionado() {
        if (tableView != null) {
            List<Material> tabla = tableView.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Material materialSeleccionado = tabla.get(0);
                return materialSeleccionado;
            }
        }
        return null;
    }

    public void ponerMaterialSeleccionado() {
        bGuardar.setVisible(false);
        final Material material = getTablaMaterialSeleccionado();
        int posicionEmpleado = lista.indexOf(material);
        this.noEditable();
        if (material != null) {
            lIdMaterial.setText(Integer.toString(material.getIdMaterial()));
            lNombre.setText(material.getNombre());
            lPrecioXMetro.setText(Double.toString(material.getPrecioXmetro()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rellenarTableView();

        final ObservableList<Material> tablaMaterialSel = tableView.getSelectionModel().getSelectedItems();
        tablaMaterialSel.addListener(selectorTablaMaterial);

        this.noEditable();
        bGuardar.setVisible(false);
        lIdMaterial.setEditable(false);
    }

    private void todoVacio() {
        lIdMaterial.setText(null);
        lNombre.setText(null);
        lPrecioXMetro.setText(null);
    }

    private void noEditable() {

        lNombre.setEditable(false);
        lPrecioXMetro.setEditable(false);
    }

    private void rellenarTableView() {
        tableView.getItems().clear();
        Material.llenarMateriales(lista);
        tableView.setItems(lista);

        tID.setCellValueFactory(new PropertyValueFactory< Material, Integer>("idMaterial"));
        tNombre.setCellValueFactory(new PropertyValueFactory<Material, String>("nombre"));
        tPrecioXMetro.setCellValueFactory(new PropertyValueFactory< Material, Double>("precioXmetro"));
    }

    private void siEditable() {

        lNombre.setEditable(true);
        lPrecioXMetro.setEditable(true);
    }

    private boolean comprobarCampos() {
        boolean solucion = false;
        if (lNombre.getText() == null
                || lPrecioXMetro.getText() == null) {

            solucion = true;

        }

        return solucion;
    }

    @FXML
    private void botonModificar(ActionEvent event) {
        if (comprobarCampos() == true) {
            System.out.println("ERROR");
        } else {
            siEditable();
            bGuardar.setVisible(true);
            eleccion = 1;
        }

    }

    @FXML
    private void botonEliminar(ActionEvent event) {
        if (comprobarCampos() == true) {
            System.out.println("ERROr");
        } else {
            Integer id = Integer.parseInt(lIdMaterial.getText());
            PreparedStatement stmt = null;
            PreparedStatement stmt2 = null;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar");
            alert.setHeaderText(null);
            alert.setContentText("¿Seguro que quieres eliminar?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.out.println("OK");
                Conexion conexion = new Conexion();
                Connection con = conexion.conectar();
                try {
                    stmt = con.prepareStatement("DELETE from lineasPedido where idMaterial=?");
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Correcto");
                    alert1.setHeaderText(null);
                    alert1.setContentText(" Se ha eliminado el material ");
                    alert1.showAndWait();
                    try {
                        stmt2 = con.prepareStatement("DELETE from material where idMaterial=?");
                        stmt2.setInt(1, id);
                        stmt2.executeUpdate();
                        Alert alert10 = new Alert(Alert.AlertType.INFORMATION);
                        alert10.setTitle("Correcto");
                        alert10.setHeaderText(null);
                        alert10.setContentText(" Se ha eliminado el material");
                        alert10.showAndWait();

                    } catch (SQLException e) {
                        Alert alert20 = new Alert(Alert.AlertType.INFORMATION);
                        alert20.setTitle("Error");
                        alert20.setHeaderText(null);
                        alert20.setContentText(" No se ha  eliminado el material ");
                        alert20.showAndWait();

                    } finally {
                        try {
                            if (stmt2 != null) {
                                stmt2.close();
                            }
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } catch (SQLException e) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Error");
                    alert2.setHeaderText(null);
                    alert2.setContentText(" No se ha  eliminado el material");
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
    }

    @FXML
    private void botonCrearNuevo(ActionEvent event) {
        todoVacio();
        siEditable();

        eleccion = 2;

        bGuardar.setVisible(true);
        lIdMaterial.setVisible(false);
    }

    @FXML
    private void botonGuardar(ActionEvent event) {
        bGuardar.setVisible(false);

        if (eleccion == 1) {
            if (comprobarCampos() == true) {
                Alert alert0 = new Alert(Alert.AlertType.INFORMATION);
                alert0.setTitle("Error");
                alert0.setHeaderText(null);
                alert0.setContentText(" Tienes campos incorrectos ");
                alert0.showAndWait();

                todoVacio();
                noEditable();
            } else {

                Integer id = Integer.parseInt(lIdMaterial.getText());
                Double precioxmetro = Double.parseDouble(lPrecioXMetro.getText());
                PreparedStatement stmt = null;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Estas seguro?");
                alert.setHeaderText(null);
                alert.setContentText("¿Seguro que quieres modificar este material?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    System.out.println("OK");
                    Conexion conexion = new Conexion();
                    Connection con = conexion.conectar();
                    try {

                        stmt = con.prepareStatement("UPDATE material SET  nombre=?, precioXmetro=? WHERE idMaterial=?");
                        stmt.setString(1, lNombre.getText());
                        stmt.setDouble(2, precioxmetro);
                        stmt.setInt(3, id);
                        stmt.executeUpdate();

                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Correcto");
                        alert1.setHeaderText(null);
                        alert1.setContentText(" Se ha modificado el usuario ");
                        alert1.showAndWait();

                        rellenarTableView();
                        tableView.refresh();
                        noEditable();
                        todoVacio();

                    } catch (SQLException e) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText(" No se ha modificado el material ");
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
            if (comprobarCampos() == true) {
                Alert alert0 = new Alert(Alert.AlertType.INFORMATION);
                alert0.setTitle("Error");
                alert0.setHeaderText(null);
                alert0.setContentText(" Tienes campos incorrectos ");
                alert0.showAndWait();

                todoVacio();
                noEditable();
                lIdMaterial.setVisible(true);
            } else {
                Double precioxmetro2 = Double.parseDouble(lPrecioXMetro.getText());
                PreparedStatement stmt = null;
                Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
                alert3.setTitle("Estas seguro");
                alert3.setHeaderText(null);
                alert3.setContentText("¿Seguro que quieres crear este material?");
                Optional<ButtonType> result1 = alert3.showAndWait();
                if (result1.isPresent() && result1.get() == ButtonType.OK) {
                    System.out.println("OK");
                    Conexion conexion = new Conexion();
                    Connection con = conexion.conectar();
                    try {

                        stmt = con.prepareStatement("INSERT INTO material ( nombre, precioXmetro) VALUES (?, ?)");

                        stmt.setString(1, lNombre.getText());
                        stmt.setDouble(2, precioxmetro2);
                        stmt.executeUpdate();

                        Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
                        alert4.setTitle("Correcto");
                        alert4.setHeaderText(null);
                        alert4.setContentText(" Se ha creado el material ");
                        alert4.showAndWait();

                        rellenarTableView();
                        tableView.refresh();
                        todoVacio();
                        noEditable();
                        lIdMaterial.setVisible(true);

                    } catch (SQLException e) {
                        Alert alert5 = new Alert(Alert.AlertType.CONFIRMATION);
                        alert5.setTitle("ERROR");
                        alert5.setHeaderText(null);
                        alert5.setContentText("No se ha creado el material, quierer borrar todo?");
                        Optional<ButtonType> result2 = alert5.showAndWait();
                        if (result2.isPresent() && result2.get() == ButtonType.OK) {
                            System.out.println("OK");
                            rellenarTableView();
                            tableView.refresh();
                            noEditable();
                            todoVacio();
                            lIdMaterial.setVisible(true);
                        } else {
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
        lIdMaterial.setVisible(true);
    }

    @FXML
    private void botonCerrar(ActionEvent event) {
        Stage stage = (Stage) bCerrar.getScene().getWindow();
        stage.close();
    }

}
