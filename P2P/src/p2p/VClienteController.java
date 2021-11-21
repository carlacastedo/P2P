/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class VClienteController implements Initializable {

    @FXML
    private TextField txtBuscar;
    @FXML
    private TextField textMensaje;
    
    private Cliente c;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Hola");
    }   
    
    public void inicializarAtributos(Cliente c) {
        this.c= c;
    }

    
}
