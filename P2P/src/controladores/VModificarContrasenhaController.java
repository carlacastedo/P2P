package controladores;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class VModificarContrasenhaController implements Initializable {

    @FXML
    private PasswordField txtContrasenaActual;
    @FXML
    private PasswordField txtContrasenaNueva;
    @FXML
    private PasswordField txtRepiteContrasena;
    @FXML
    private Button btnModificar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modificar(ActionEvent event) {
    }
    
}
