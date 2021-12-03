/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import p2p.cliente.Cliente;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class VSolicitarController implements Initializable {

    @FXML
    private TextField txtBuscar;
    @FXML
    private ListView<String> listaUsuarios;
    @FXML
    private Button btnSolicitar;

    private Cliente cliente;
    @FXML
    private ListView<String> listaEnviadas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BooleanBinding listaSeleccionada = Bindings.size(this.listaUsuarios.getSelectionModel().getSelectedItems()).greaterThanOrEqualTo(1);
        this.btnSolicitar.disableProperty().bind(listaSeleccionada.not());
    }

    public void inicializarAtributos(Cliente c, ArrayList<String> enviadas) {
        this.cliente = c;
        this.actualizarEnviadas(enviadas);
    }

    @FXML
    private void solicitar(ActionEvent event) {
        try {
            String solicitado = this.listaUsuarios.getSelectionModel().getSelectedItem();
            this.cliente.solicitarAmistad(solicitado);
            this.txtBuscar.setText("");
            this.listaUsuarios.getItems().clear();
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void filtrarUsuarios(KeyEvent event) {
        ArrayList<String> usuarios;
        try {
            String busqueda = this.txtBuscar.getText();
            if (busqueda.length() >= 2) {
                usuarios = this.cliente.consultarNoAmigos(busqueda);
                this.actualizarUsuarios(usuarios);
            } else if (busqueda.length() == 0) {
                this.listaUsuarios.getItems().clear();
            }
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void actualizarUsuarios(ArrayList<String> usuarios) {
        ObservableList sol = FXCollections.observableArrayList();
        for (String a : usuarios) {
            sol.add(a);
        }
        Platform.runLater(() -> {
            listaUsuarios.setItems(sol);
        });
    }
    
    public void actualizarEnviadas(ArrayList<String> usuarios) {
        ObservableList sol = FXCollections.observableArrayList();
        for (String a : usuarios) {
            sol.add(a);
        }
        Platform.runLater(() -> {
            listaEnviadas.setItems(sol);
        });
    }
}
