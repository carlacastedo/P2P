/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author anton
 */
public class AutenticacionControlador implements Initializable {

    @FXML
    private TextArea txtUsuario;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private Button btnRegistrarse;
    @FXML
    private Button btnIniciarSesion;

    private BaseDatos bbdd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bbdd = new BaseDatos();

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

    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
    }

}
