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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BooleanBinding listaSeleccionada = Bindings.size(this.listaUsuarios.getSelectionModel().getSelectedItems()).greaterThanOrEqualTo(1);
        this.btnSolicitar.disableProperty().bind(listaSeleccionada.not());
    }

    public void inicializarAtributos(Cliente c) {
        this.cliente = c;
    }

    @FXML
    private void solicitar(ActionEvent event) {
        try {
            this.cliente.solicitarAmistad(this.listaUsuarios.getSelectionModel().getSelectedItem());
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
            }else if(busqueda.length()==0){
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
        this.listaUsuarios.setItems(sol);
    }

}
