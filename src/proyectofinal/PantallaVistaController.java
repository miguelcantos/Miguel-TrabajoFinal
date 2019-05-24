/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import DAO.Conexion;
import java.io.File;
import java.io.FileWriter;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
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
    private TableView<Cliente> tableCliente;
    @FXML
    private TableView<Material> tableMaterial;
    @FXML
    private TableView<Empleados> tableEmpleados;
    @FXML
    private TableView<Pedido> tablePedidos;

    
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
    @FXML
    private TableColumn<Cliente, String> cNombre;
    @FXML
    private TableColumn<Cliente, String> cApellido;
    @FXML
    private TableColumn<Cliente, String> cDireccion;
    @FXML
    private TableColumn<Cliente, String> cCiudad;
    @FXML
    private TableColumn<Cliente, String> cTelefono;
    @FXML
    private TableColumn<Cliente, String> cEmail;
    @FXML
    private TableColumn<Material, String> mIdMaterial;
    @FXML
    private TableColumn<Material, String> mNombre;
    @FXML
    private TableColumn<Material, Double> mPrecioXmetro;
    @FXML
    private TableColumn<Pedido, String> pIdPedido;
    @FXML
    private TableColumn<Pedido, String> pIdCliente;

    @FXML
    private TableColumn<Pedido, String> pFechaPedido;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ocultarTodo();
       tableCliente.setVisible(true);
       
       ObservableList<Cliente> lista= FXCollections.observableArrayList();
       Cliente.llenarClientes(lista);
       tableCliente.setItems(lista);
   
            cNombre.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nombre"));
            cApellido.setCellValueFactory(new PropertyValueFactory<Cliente,String>("apellido"));
            cDireccion.setCellValueFactory(new PropertyValueFactory<Cliente,String>("direccion"));
            cCiudad.setCellValueFactory(new PropertyValueFactory<Cliente,String>("ciudad"));
            cTelefono.setCellValueFactory(new PropertyValueFactory<Cliente,String>("telefono"));
            cEmail.setCellValueFactory(new PropertyValueFactory<Cliente,String>("email"));
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
    
    ObservableList<Cliente> lista= FXCollections.observableArrayList();
    Cliente.llenarClientes(lista);
    tableCliente.setItems(lista);
   
            cNombre.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nombre"));
            cApellido.setCellValueFactory(new PropertyValueFactory<Cliente,String>("apellido"));
            cDireccion.setCellValueFactory(new PropertyValueFactory<Cliente,String>("direccion"));
            cCiudad.setCellValueFactory(new PropertyValueFactory<Cliente,String>("ciudad"));
            cTelefono.setCellValueFactory(new PropertyValueFactory<Cliente,String>("telefono"));
            cEmail.setCellValueFactory(new PropertyValueFactory<Cliente,String>("email"));
    }

    @FXML
    private void aTableMaterial(ActionEvent event) {
    ocultarTodo();
    tableMaterial.setVisible(true);
    ObservableList<Material> lista= FXCollections.observableArrayList();
       Material.llenarMateriales(lista);
       tableMaterial.setItems(lista);
   
            mNombre.setCellValueFactory(new PropertyValueFactory<Material,String>("nombre"));
            mIdMaterial.setCellValueFactory(new PropertyValueFactory<Material,String>("idMaterial"));
            mPrecioXmetro.setCellValueFactory(new PropertyValueFactory<Material,Double>("precioXmetro"));
            
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
            
            //eNomUsua.setResizable(false);    
    }
    
    

    @FXML
    private void aTablePedidos(ActionEvent event) {
    ocultarTodo();
    tablePedidos.setVisible(true);
    ObservableList<Pedido> lista= FXCollections.observableArrayList();
       Pedido.llenarPedido(lista);
       tablePedidos.setItems(lista);
   
            pIdPedido.setCellValueFactory(new PropertyValueFactory<Pedido,String>("idPedido"));
            pIdCliente.setCellValueFactory(new PropertyValueFactory<Pedido,String>("idCliente"));
            pFechaPedido.setCellValueFactory(new PropertyValueFactory<Pedido,String>("fechaPedido"));
    }

    
    
    private String generarDatos(){
        String cadena = "";   
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        ResultSet rs=null;
        PreparedStatement stmt=null;
        try {
            stmt = con.prepareStatement("SELECT * FROM cliente");
            stmt.executeQuery();
            rs = stmt.executeQuery();

            while(rs.next()){
            
                cadena+= "CLIENTE" + "\n";
                cadena+= "nombre: " + rs.getString("nombre") + "\n";
                cadena+= "apellido: " + rs.getString("apellido") + "\n";
                cadena+= "direccion: " + rs.getString("direccion") + "\n";
                cadena+= "ciudad: " + rs.getString("ciudad") + "\n";
                cadena+= "telefono: " + rs.getString("telefono") + "\n";
                cadena+= "email: " + rs.getString("email")  + "\n";
                cadena+= "\n";

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
        rs=null;
        stmt=null;
        con = conexion.conectar();
        try {
            stmt = con.prepareStatement("SELECT * FROM empleado");
            stmt.executeQuery();
            rs = stmt.executeQuery();

            while(rs.next()){
            
                cadena+= "EMPLEADO" + "\n";
                cadena+= "nombre usuario: " + rs.getString("nombreUsuario") + "\n";
                cadena+= "nombre: " +  rs.getString("nombre") + "\n";
                cadena+= "apellido: " + rs.getString("apellido") + "\n";
                cadena+= "direccion: " + rs.getString("direccion") + "\n";
                cadena+= "ciudad: " +  rs.getString("ciudad") + "\n";
                cadena+= "telefono: " + rs.getString("telefono") + "\n";
                cadena+= "email: " + rs.getString("email")  + "\n";
                cadena+= "\n";

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
        rs=null;
        stmt=null;
        con = conexion.conectar();
        try {
            stmt = con.prepareStatement("SELECT * FROM pedido");
            stmt.executeQuery();
            rs = stmt.executeQuery();

            while(rs.next()){
                cadena+= "PEDIDO" + "\n";
                cadena+= "idPEdido: " + rs.getInt("idPedido") + "\n";
                cadena+= "idCliente: " + rs.getInt("idCliente") + "\n";
                cadena+= "Fecha de pedido: " + rs.getDate("fechaPedido").toString()  + "\n";
                cadena+= "\n";
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
        rs=null;
        stmt=null;
        con = conexion.conectar();
        try {
            stmt = con.prepareStatement("SELECT * FROM material");
            stmt.executeQuery();
            rs = stmt.executeQuery();

            while(rs.next()){
                cadena+= "MATERIAL" + "\n";
                cadena+= "idMaterial: " + rs.getInt("idMaterial") + "\n";
                cadena+= "Nombre: " + rs.getString("nombre") + "\n";
                cadena+= "Precio x metro: " + rs.getDouble("precioXmetro")  + "\n";
                cadena+= "\n";
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
    return cadena;}
    
    
    @FXML
    private void botonImprimir(ActionEvent event) {
        
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar Fichero");
        fc.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Blog de notas",".txt"));
        Stage stage = (Stage) cerrar.getScene().getWindow();
        File selectFile = fc.showSaveDialog(stage);
        FileWriter fw = null;
        String cadena = "";
        if (selectFile != null) {
            try {
                fw = new FileWriter(selectFile);
                cadena = generarDatos() ;
                fw.write(cadena);
            } catch (IOException ex) {
                System.out.println("error al escribir");
            } finally {
                try {
                    if (fw != null) {
                        fw.close();
                    }
                } catch (Exception ex) {
                    System.out.println("error");
                }
            }
        }
    }
        
        
    }

