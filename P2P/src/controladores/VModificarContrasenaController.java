package controladores;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import p2p.cliente.Cliente;

public class VModificarContrasenaController implements Initializable {

    @FXML
    private PasswordField txtContrasenaActual;
    @FXML
    private PasswordField txtContrasenaNueva;
    @FXML
    private PasswordField txtRepiteContrasena;
    @FXML
    private Button btnModificar;
    @FXML
    private Label lblError;

    private Cliente cliente;

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

    @FXML//metodo para modificar la contraseña de un usuario si la contraseña antigua es correcta
    private void modificar(ActionEvent event) {
        String contrasenaNueva = this.txtContrasenaNueva.getText();
        String contrasenaAntigua = this.txtContrasenaActual.getText();
        String repetida = this.txtRepiteContrasena.getText();
        Boolean cambiada = false;
        //comprobamos que las nuevas contraseñas coincidan
        if (repetida.equals(contrasenaNueva)) {
            this.lblError.setVisible(false);
            try {
                //intentamos modificar la contraseña
                cambiada = this.cliente.modificarContraseña(contrasenaAntigua, contrasenaNueva);
                //si se ha cambiado correctamente informamos por pantalla del exito
                if (cambiada) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Información");
                    alert.setContentText("Se ha modificado la contraseña");
                    alert.showAndWait();
                    this.txtContrasenaActual.setText("");
                    this.txtContrasenaNueva.setText("");
                    this.txtRepiteContrasena.setText("");
                    ((Stage) this.txtContrasenaActual.getScene().getWindow()).close();
                } else {//si se ha cambiado correctamente informamos por pantalla del error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("La contraseña actual introducida no es correcta");
                    alert.showAndWait();
                    this.txtContrasenaActual.setText("");
                    this.txtContrasenaNueva.setText("");
                    this.txtRepiteContrasena.setText("");
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
