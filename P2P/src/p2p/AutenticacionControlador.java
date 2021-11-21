/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

    private P2PImpl bbdd;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            bbdd = new P2PImpl();
        } catch (RemoteException ex) {
            Logger.getLogger(AutenticacionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        // asocia el estado del botón con el estado de los text fields
        BooleanBinding botonDeshabilitado = txtUsuario.textProperty().isEmpty().or(txtContraseña.textProperty().isEmpty());

        btnIniciarSesion.disableProperty().bind(botonDeshabilitado);
        btnRegistrarse.disableProperty().bind(botonDeshabilitado);

    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
        //        if (!this.txtUsuario.getText().isEmpty() && !this.txtContraseña.getText().isEmpty()) {
        //            bbdd.insertarUsuario(this.txtUsuario.getText(), this.txtContraseña.getText());
        //        }

        try {
            Stage myStage = (Stage) this.txtUsuario.getScene().getWindow();
            myStage.close();
            abrirVentanaCliente();
        } catch (IOException ex) {
            Logger.getLogger(AutenticacionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        try {
            Stage myStage = (Stage) this.txtUsuario.getScene().getWindow();
            myStage.close();
            abrirVentanaCliente();
        } catch (IOException ex) {
            Logger.getLogger(AutenticacionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void abrirVentanaCliente() throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("VCliente.fxml"));
            // Cargo la ventana
            Pane ventana = (Pane) loader.load();
            // Cargo el scene
            Scene scene = new Scene(ventana);
            Stage stage = new Stage();
            VClienteController controlador = loader.getController();

            // Seteo la scene y la muestro
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
