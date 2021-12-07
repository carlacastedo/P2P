package controladores;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import p2p.cliente.Cliente;

public class VSolicitarController implements Initializable {

    @FXML
    private TextField txtBuscar;
    @FXML
    private ListView<String> listaUsuarios;
    @FXML
    private Button btnSolicitar;
    @FXML
    private ListView<String> listaEnviadas;
    
    private Cliente cliente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //que el boton no se pueda pulsar si no hay nada en la lista
        BooleanBinding listaSeleccionada = Bindings.size(this.listaUsuarios.getSelectionModel().getSelectedItems()).greaterThanOrEqualTo(1);
        this.btnSolicitar.disableProperty().bind(listaSeleccionada.not());
    }

    //metodo que inicializa los atributos de la ventana
    public void inicializarAtributos(Cliente c, ArrayList<String> enviadas) {
        this.cliente = c;
        this.verEnviadas(enviadas);
    }

    @FXML//metodo que solicita amistad a un usuario no amigo del usuario actual
    private void solicitar(ActionEvent event) {
        try {
            String solicitado = this.listaUsuarios.getSelectionModel().getSelectedItem();
            this.cliente.solicitarAmistad(solicitado);
            this.txtBuscar.setText("");
            this.listaUsuarios.getItems().clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Se ha enviado una solicitud de amistad");
            alert.showAndWait();
            this.listaEnviadas.getItems().add(solicitado);
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML//metodo que busca usuarios que siguen un patron, para solicitar amistad
    private void filtrarUsuarios(KeyEvent event) {
        ArrayList<String> usuarios;
        try {
            String busqueda = this.txtBuscar.getText();
            //debemos conocer como minimo 2 caracteres de su nombre de usuario
            if (busqueda.length() >= 2) {
                //devolvemos los usuarios no amigos que cumplen el criterio
                usuarios = this.cliente.consultarNoAmigos(busqueda);
                this.actualizarUsuarios(usuarios);
            } else if (busqueda.length() == 0) {
                this.listaUsuarios.getItems().clear();
            }
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //metodo para actualizar la vista de los usuarios no amigos
    public void actualizarUsuarios(ArrayList<String> usuarios) {
        ObservableList sol = FXCollections.observableArrayList();
        for (String a : usuarios) {
            sol.add(a);
        }
        Platform.runLater(() -> {
            listaUsuarios.setItems(sol);
        });
    }

    //metodo que actualiza la lista de solicitudes enviadas
    public void verEnviadas(ArrayList<String> usuarios) {
        ObservableList sol = FXCollections.observableArrayList();
        for (String a : usuarios) {
            sol.add(a);
        }
        Platform.runLater(() -> {
            listaEnviadas.setItems(sol);
        });
    }
}
