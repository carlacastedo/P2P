/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import p2p.Cliente;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class VClienteController implements Initializable {

    @FXML
    private TextField txtBuscar;
    @FXML
    private TextField txtMensaje;
    @FXML
    private Button btnEnviar;
    @FXML
    private ListView<?> listaAmigos;
    @FXML
    private ImageView imgUsuario;
    @FXML
    private Label lblNombre;
    
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
        BooleanBinding botonDeshabilitado = txtMensaje.textProperty().isEmpty();
        btnEnviar.disableProperty().bind(botonDeshabilitado);
        
    }

    public void inicializarAtributos(Cliente c, String usuario) {
        this.c = c;
        this.lblNombre.setText(usuario);
    }

    @FXML
    private void enviarMensaje(ActionEvent event) {
    }

}
