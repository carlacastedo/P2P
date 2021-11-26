package controladores;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import p2p.cliente.Cliente;

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

    private Cliente cliente;
    @FXML
    private Label lblError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BooleanBinding modificar = this.txtContrasenaActual.textProperty().isEmpty().or(
                this.txtContrasenaNueva.textProperty().isEmpty().or(
                        this.txtRepiteContrasena.textProperty().isEmpty()));
        this.btnModificar.disableProperty().bind(modificar);
    }

    public void inicializarAtributos(Cliente c) {
        this.cliente = c;
    }

    @FXML
    private void modificar(ActionEvent event) {
        String contrasenaNueva = this.txtContrasenaNueva.getText();
        String contrasenaAntigua = this.txtContrasenaActual.getText();
        String repetida = this.txtRepiteContrasena.getText();
        Boolean cambiada = false;
        if (repetida.equals(contrasenaNueva)) {
            this.lblError.setVisible(false);
            try {
                cambiada = this.cliente.modificarContraseña(contrasenaAntigua, contrasenaNueva);
                if(cambiada){
                    ((Stage) this.txtContrasenaActual.getScene().getWindow()).close();
                }
            } catch (RemoteException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            this.lblError.setVisible(true);
        }
    }

    @FXML
    private void borrarError(KeyEvent event) {
        this.lblError.setVisible(false);
    }

}
