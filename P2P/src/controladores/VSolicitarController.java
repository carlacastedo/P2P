/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class VSolicitarController implements Initializable {

    @FXML
    private TextField txtBuscar;
    @FXML
    private ListView<?> listaUsuarios;
    @FXML
    private Button btnSolicitar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void solicitar(ActionEvent event) {
    }

    @FXML
    private void filtrarUsuarios(KeyEvent event) {
    }
    
}
