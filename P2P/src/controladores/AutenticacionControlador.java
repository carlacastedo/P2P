/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import p2p.cliente.Cliente;

/**
 * FXML Controller class
 *
 * @author anton
 */
public class AutenticacionControlador implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private Button btnRegistrarse;
    @FXML
    private Button btnIniciarSesion;

    private Cliente c;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // asocia el estado del botón con el estado de los text fields
        BooleanBinding botonDeshabilitado = txtUsuario.textProperty().isEmpty().or(txtContraseña.textProperty().isEmpty());
        btnIniciarSesion.disableProperty().bind(botonDeshabilitado);
        btnRegistrarse.disableProperty().bind(botonDeshabilitado);

    }

    public void inicializarAtributos(Cliente c) {
        this.c = c;
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
        //comprobamos si el usuario existe o no en el sistema
        try {
            if (!this.c.existeUsuario(this.txtUsuario.getText(), this.txtContraseña.getText())) {
                //creamos el usuario en la base de datos
                this.c.insertarUsuario(this.txtUsuario.getText(), this.txtContraseña.getText());
                //informamos de la creacion
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Usuario creado");
                a.showAndWait();
                //abrimos la ventana de los clientes
                VClienteController controlador = abrirVentanaCliente();
                //cerramos la ventana de autenticacion
                Stage myStage = (Stage) this.txtUsuario.getScene().getWindow();
                myStage.close();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("El usuario ya existe en el sistema");
                a.showAndWait();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        try {
            if (this.c.existeUsuario(this.txtUsuario.getText(), this.txtContraseña.getText())) {
                //abrimos la ventana del cliente
                VClienteController controlador = abrirVentanaCliente();
                //cerramos la ventana de autenticacion
                Stage myStage = (Stage) this.txtUsuario.getScene().getWindow();
                myStage.close();
                c.registrarCliente(this.txtUsuario.getText(), controlador);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Autenticacion incorrecta");
                a.showAndWait();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private VClienteController abrirVentanaCliente() throws IOException {
        VClienteController controlador = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ventanas/VCliente.fxml"));
            // Cargo la ventana
            Pane ventana = (Pane) loader.load();
            // Cargo el scene
            Scene scene = new Scene(ventana);
            Stage stage = new Stage();
            controlador = loader.getController();
            controlador.inicializarAtributos(c, this.txtUsuario.getText());
            // Seteo la scene y la muestro
            stage.setScene(scene);
            stage.setTitle("Chat");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return controlador;
    }
}
