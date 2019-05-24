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
import javafx.scene.control.Label;
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
    
    int eleccion = 0;
    ObservableList<LineasPedido> lista = FXCollections.observableArrayList();
    private final ListChangeListener<LineasPedido> selectorTablaLineasPedido = new ListChangeListener<LineasPedido>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends LineasPedido> c) {
            ponerLineasPedidoSeleccionado();
        }
    };
    @FXML
    private Label importante;

    public LineasPedido getTablaLineasPedidoSeleccionado() {
        if (tableView != null) {
            List<LineasPedido> tabla = tableView.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final LineasPedido lineasPedidoSeleccionado = tabla.get(0);
                return lineasPedidoSeleccionado;
            }
        }
        return null;
    }

    public void ponerLineasPedidoSeleccionado() {
        bGuardar.setVisible(false);
        final LineasPedido lineasPedido = getTablaLineasPedidoSeleccionado();
        int posicionLineasPedido = lista.indexOf(lineasPedido);
        this.noEditable();
        if (lineasPedido != null) {
            lIdLineasPedido.setText(Integer.toString(lineasPedido.getIdLineasPedido()));
            lIdMaterial.setText(Integer.toString(lineasPedido.getIdMaterial()));
            lIdPedido.setText(Integer.toString(lineasPedido.getIdPedido()));
            lCantidadMetros.setText(Double.toString(lineasPedido.getCantidadMetros()));

        }
    }

    private void todoVacio() {
        lIdLineasPedido.setText(null);
        lIdMaterial.setText(null);
        lCantidadMetros.setText(null);
        lIdPedido.setText(null);

    }

    private void noEditable() {

        lIdLineasPedido.setEditable(false);
        lIdMaterial.setEditable(false);
        lIdPedido.setEditable(false);
        lCantidadMetros.setEditable(false);

    }

    public void rellenarTableView(String id1) {

        Integer id = Integer.parseInt(id1);
        tableView.getItems().clear();
        LineasPedido.llenarLineasPedido(lista, id);
        tableView.setItems(lista);
        
        importante.setText(id1);
        
        tID.setCellValueFactory(new PropertyValueFactory< LineasPedido, Integer>("idLineasPedido"));
        tIdMaterial.setCellValueFactory(new PropertyValueFactory< LineasPedido, Integer>("idMaterial"));
        tIdPedido.setCellValueFactory(new PropertyValueFactory< LineasPedido, Integer>("idPedido"));
        tCantidadMetros.setCellValueFactory(new PropertyValueFactory< LineasPedido, Double>("cantidadMetros"));
        tTotalProducto.setCellValueFactory(new PropertyValueFactory< LineasPedido, Double>("precio"));

        final ObservableList<LineasPedido> tablaLineasPedidoSel = tableView.getSelectionModel().getSelectedItems();
        tablaLineasPedidoSel.addListener(selectorTablaLineasPedido);

        this.noEditable();
        todoVacio();
        bGuardar.setVisible(false);
    }

    private void siEditable() {
        lIdMaterial.setEditable(true);
        lCantidadMetros.setEditable(true);

    }

    private boolean comprobarCampos() {
        boolean solucion = false;
        if (lCantidadMetros.getText() == null
                || lIdPedido.getText() == null
                || lIdMaterial.getText() == null) {

            solucion = true;

        }

        return solucion;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void botonModificar(ActionEvent event) {
        if (comprobarCampos() == true) {
            System.out.println("Error");

        } else {
            siEditable();
            lIdMaterial.setEditable(false);
            lIdPedido.setEditable(false);
            bGuardar.setVisible(true);
            eleccion = 1;
        }
    }

    @FXML
    private void botonEliminar(ActionEvent event) {
        if (comprobarCampos() == true) {
            System.out.println("ERROR");
        } else {
            Integer id = Integer.parseInt(lIdLineasPedido.getText());
            PreparedStatement stmt = null;
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
                    stmt = con.prepareStatement("DELETE from lineasPedido where idLineasPedido=?");
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Correcto");
                    alert1.setHeaderText(null);
                    alert1.setContentText(" Se ha eliminado el elemento ");
                    alert1.showAndWait();

                } catch (SQLException e) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Error");
                    alert2.setHeaderText(null);
                    alert2.setContentText(" No se ha  eliminado la lineas de pedido ");
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

            rellenarTableView(lIdPedido.getText());
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
            lIdLineasPedido.setVisible(false);
            lIdPedido.setEditable(false);
            lIdPedido.setText(importante.getText());
        
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

                Integer id = Integer.parseInt(lIdLineasPedido.getText());
                Integer idPedido = Integer.parseInt(lIdPedido.getText());
                Integer idMaterial = Integer.parseInt(lIdMaterial.getText());
                Double cantidadMetros = Double.parseDouble(lCantidadMetros.getText());

                PreparedStatement stmt = null;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Estas seguro?");
                alert.setHeaderText(null);
                alert.setContentText("¿Seguro que quieres modificar esta linea de pedido?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    System.out.println("OK");
                    Conexion conexion = new Conexion();
                    Connection con = conexion.conectar();
                    try {

                        stmt = con.prepareStatement("UPDATE lineasPedido SET  idMaterial=?, idPedido=?, cantidadMetros=? WHERE idLineasPedido=?");
                        stmt.setInt(1, idMaterial);
                        stmt.setInt(2, idPedido);
                        stmt.setDouble(3, cantidadMetros);
                        stmt.setInt(4, id);
                        stmt.executeUpdate();

                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Correcto");
                        alert1.setHeaderText(null);
                        alert1.setContentText(" Se ha modificado la linea de pedido ");
                        alert1.showAndWait();

                        rellenarTableView(lIdPedido.getText());
                        tableView.refresh();
                        noEditable();
                        todoVacio();

                    } catch (SQLException e) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText(" No se ha modificado la linea de pedido ");
                        alert2.showAndWait();
                        rellenarTableView(lIdPedido.getText());
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
                lIdLineasPedido.setVisible(true);
            } else {
                Integer idPedido = Integer.parseInt(lIdPedido.getText());
                Integer idMaterial = Integer.parseInt(lIdMaterial.getText());
                Double cantidadMetros = Double.parseDouble(lCantidadMetros.getText());
                PreparedStatement stmt = null;
                Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
                alert3.setTitle("Estas seguro");
                alert3.setHeaderText(null);
                alert3.setContentText("¿Seguro que quieres crear esta linea de Pedido?");
                Optional<ButtonType> result1 = alert3.showAndWait();
                if (result1.isPresent() && result1.get() == ButtonType.OK) {
                    System.out.println("OK");
                    Conexion conexion = new Conexion();
                    Connection con = conexion.conectar();
                    try {

                        stmt = con.prepareStatement("INSERT INTO lineasPedido ( idMaterial, idPedido, cantidadMetros) VALUES (?, ?, ?)");
                        stmt.setInt(1, idMaterial);
                        stmt.setInt(2, idPedido);
                        stmt.setDouble(3, cantidadMetros);
                        stmt.executeUpdate();

                        Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
                        alert4.setTitle("Correcto");
                        alert4.setHeaderText(null);
                        alert4.setContentText(" Se ha creado una Linea de Pedido ");
                        alert4.showAndWait();
                        rellenarTableView(lIdPedido.getText());
                        tableView.refresh();
                        todoVacio();
                        noEditable();
                        lIdLineasPedido.setVisible(true);

                    } catch (SQLException e) {
                        Alert alert5 = new Alert(Alert.AlertType.CONFIRMATION);
                        alert5.setTitle("ERROR");
                        alert5.setHeaderText(null);
                        alert5.setContentText("No se ha creado la Linea de Pedido, quierer borrar todo?");
                        Optional<ButtonType> result2 = alert5.showAndWait();
                        if (result2.isPresent() && result2.get() == ButtonType.OK) {
                            System.out.println("OK");
                            rellenarTableView(lIdPedido.getText());
                            tableView.refresh();
                            noEditable();
                            todoVacio();
                            lIdLineasPedido.setVisible(true);
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
        lIdLineasPedido.setVisible(true);
    }

    @FXML
    private void botonCerrar(ActionEvent event) {
        Stage stage = (Stage) bcerrar.getScene().getWindow();
        stage.close();
    }

}
