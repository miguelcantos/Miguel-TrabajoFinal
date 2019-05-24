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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DAW
 */
public class Pantalla4Controller implements Initializable {

    @FXML
    private TextField lIdPedido;
    @FXML
    private TextField lIdCliente;
    @FXML
    private DatePicker lFechaPedido;
    @FXML
    private Button bModificar;
    @FXML
    private Button bEliminar;
    @FXML
    private Button bCrearNuevo;
    @FXML
    private Button bGuardar;
    @FXML
    private TableView<Pedido> tableView;
    @FXML
    private TableColumn<Pedido, Integer> tID;
    @FXML
    private TableColumn<Pedido, Integer> tIdCliente;
    @FXML
    private TableColumn<Pedido, String> tFechaPedido;

    int eleccion = 0;
    ObservableList<Pedido> lista = FXCollections.observableArrayList();

    private final ListChangeListener<Pedido> selectorTablaPedido = new ListChangeListener<Pedido>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Pedido> p) {
            ponerPedidoSeleccionado();
        }
    };
    @FXML
    private Button bcerrar;

    public Pedido getTablaPedidoSeleccionado() {
        if (tableView != null) {
            List<Pedido> tabla = tableView.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Pedido pedidoSeleccionado = tabla.get(0);
                return pedidoSeleccionado;
            }
        }
        return null;
    }

    public void ponerPedidoSeleccionado() {
        bGuardar.setVisible(false);
        final Pedido pedido = getTablaPedidoSeleccionado();
        int posicionPedido = lista.indexOf(pedido);
        this.noEditable();
        if (pedido != null) {
            lIdPedido.setText(Integer.toString(pedido.getIdPedido()));
            lIdCliente.setText(Integer.toString(pedido.getIdCliente()));
            lFechaPedido.setValue(LocalDate.parse(pedido.getFechaPedido()));

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rellenarTableView();

        final ObservableList<Pedido> tablaClienteSel = tableView.getSelectionModel().getSelectedItems();
        tablaClienteSel.addListener(selectorTablaPedido);

        this.noEditable();
        todoVacio();
        bGuardar.setVisible(false);
        lIdPedido.setEditable(false);
    }

    private void todoVacio() {
        lIdPedido.setText(null);
        lIdCliente.setText(null);
        lFechaPedido.setValue(null);
    }

    private void noEditable() {

        lIdCliente.setEditable(false);
        lFechaPedido.setEditable(false);
    }

    private void rellenarTableView() {
        tableView.getItems().clear();
        Pedido.llenarPedido(lista);
        tableView.setItems(lista);

        tID.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("idPedido"));
        tIdCliente.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("idCliente"));
        tFechaPedido.setCellValueFactory(new PropertyValueFactory< Pedido, String>("fechaPedido"));

    }

    private void siEditable() {
        lIdCliente.setEditable(true);
        lFechaPedido.setEditable(true);
    }

    private boolean comprobarCampos() {
        boolean solucion = false;
        if (lIdCliente.getText() == null
                || lFechaPedido.getValue() == null) {

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
            lIdCliente.setEditable(false);
            bGuardar.setVisible(true);
            eleccion = 1;
        }
    }

    @FXML
    private void botonEliminar(ActionEvent event) {
        if (comprobarCampos() == true) {
            System.out.println("ERROR");
        } else {
            Integer id = Integer.parseInt(lIdPedido.getText());
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
                    stmt = con.prepareStatement("DELETE from lineasPedido where idPedido=?");
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Correcto");
                    alert1.setHeaderText(null);
                    alert1.setContentText(" Se ha eliminado el pedido ");
                    alert1.showAndWait();
                    try {
                        stmt2 = con.prepareStatement("DELETE from pedido where idPedido=?");
                        stmt2.setInt(1, id);
                        stmt2.executeUpdate();
                        Alert alert10 = new Alert(Alert.AlertType.INFORMATION);
                        alert10.setTitle("Correcto");
                        alert10.setHeaderText(null);
                        alert10.setContentText(" Se ha eliminado el pedido ");
                        alert10.showAndWait();

                    } catch (SQLException e) {
                        Alert alert20 = new Alert(Alert.AlertType.INFORMATION);
                        alert20.setTitle("Error");
                        alert20.setHeaderText(null);
                        alert20.setContentText(" No se ha  eliminado el pedido ");
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
                    alert2.setContentText(" No se ha  eliminado el pedido ");
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
        lIdPedido.setVisible(false);
    }

    @FXML
    private void botonLineasPedido(ActionEvent event) {
        if (lIdPedido.getText() == null) {
            System.out.println("Error");

        } else {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Pantalla5.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                Pantalla5Controller controller = fxmlLoader.<Pantalla5Controller>getController();
                controller.rellenarTableView(lIdPedido.getText());
                stage.setScene(new Scene(root1));
                stage.initModality(Modality.APPLICATION_MODAL);
                //stage.initStyle(StageStyle.UNDECORATED);
                stage.show();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void botonCerrar(ActionEvent event) {
        Stage stage = (Stage) bcerrar.getScene().getWindow();
        stage.close();
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

                Integer id = Integer.parseInt(lIdPedido.getText());
                Integer idCliente = Integer.parseInt(lIdCliente.getText());
                Date Fecha = Date.valueOf(lFechaPedido.getValue());
                PreparedStatement stmt = null;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Estas seguro?");
                alert.setHeaderText(null);
                alert.setContentText("¿Seguro que quieres modificar este pedido?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    System.out.println("OK");
                    Conexion conexion = new Conexion();
                    Connection con = conexion.conectar();
                    try {

                        stmt = con.prepareStatement("UPDATE pedido SET  idCliente=?, fechaPedido=? WHERE idPedido=?");
                        stmt.setInt(1, idCliente);
                        stmt.setDate(2, Fecha);
                        stmt.setInt(3, id);
                        stmt.executeUpdate();

                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Correcto");
                        alert1.setHeaderText(null);
                        alert1.setContentText(" Se ha modificado el pedido ");
                        alert1.showAndWait();

                        rellenarTableView();
                        tableView.refresh();
                        noEditable();
                        todoVacio();

                    } catch (SQLException e) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText(" No se ha modificado el pedido ");
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
                lIdPedido.setVisible(true);
            } else {
                Integer idCliente = Integer.parseInt(lIdCliente.getText());
                Date Fecha = Date.valueOf(lFechaPedido.getValue());
                PreparedStatement stmt = null;
                Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
                alert3.setTitle("Estas seguro");
                alert3.setHeaderText(null);
                alert3.setContentText("¿Seguro que quieres crear este pedido?");
                Optional<ButtonType> result1 = alert3.showAndWait();
                if (result1.isPresent() && result1.get() == ButtonType.OK) {
                    System.out.println("OK");
                    Conexion conexion = new Conexion();
                    Connection con = conexion.conectar();
                    try {

                        stmt = con.prepareStatement("INSERT INTO pedido ( idCliente, fechaPedido) VALUES (?, ?)");

                        stmt.setInt(1, idCliente);
                        stmt.setDate(2, Fecha);
                        stmt.executeUpdate();

                        Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
                        alert4.setTitle("Correcto");
                        alert4.setHeaderText(null);
                        alert4.setContentText(" Se ha creado el pedido ");
                        alert4.showAndWait();

                        rellenarTableView();
                        tableView.refresh();
                        todoVacio();
                        noEditable();
                        lIdPedido.setVisible(true);

                    } catch (SQLException e) {
                        Alert alert5 = new Alert(Alert.AlertType.CONFIRMATION);
                        alert5.setTitle("ERROR");
                        alert5.setHeaderText(null);
                        alert5.setContentText("No se ha creado el pedido, quierer borrar todo?");
                        Optional<ButtonType> result2 = alert5.showAndWait();
                        if (result2.isPresent() && result2.get() == ButtonType.OK) {
                            System.out.println("OK");
                            rellenarTableView();
                            tableView.refresh();
                            noEditable();
                            todoVacio();
                            lIdPedido.setVisible(true);
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
        lIdPedido.setVisible(true);
    }

}
