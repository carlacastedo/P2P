/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import p2p.cliente.Cliente;

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
    private ListView<String> listaAmigos;
    @FXML
    private ImageView imgUsuario;
    @FXML
    private Label lblNombre;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabAmigos;
    @FXML
    private Tab tabSolicitudes;
    @FXML
    private ListView<String> listaSolicitudes;
    
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
        //iniciarListas();

    }

    public void inicializarAtributos(Cliente c, String usuario) {
        this.c = c;
        this.lblNombre.setText(usuario);
    }

    @FXML
    private void enviarMensaje(ActionEvent event) {

    }

    public void modificarLista(String amigo) {
        this.listaAmigos.getItems().add(amigo);
    }

    private void iniciarListas() {
        ObservableList listaListView = FXCollections.observableArrayList();
        ObservableList listaListView2 = FXCollections.observableArrayList();

        listaListView.add("Maria");
        listaListView.add("Juan");

        listaListView2.add("Anton");
        listaListView2.add("Sergio");

        //Asocia unos elemenos determiandos a la lista
        listaAmigos.setItems(listaListView);
        listaSolicitudes.setItems(listaListView2);
        /*
        // obtiene el elemento seleccionado en la posición indicada
        listaAmigos.getSelectionModel().select(0);
        // obtiene el elemento seleccioando
        listaAmigos.getSelectionModel().getSelectedItem();
        //obtiene el índice del elemento seleccionado
        listaAmigos.getSelectionModel().getSelectedIndex();

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Alumno alumno = (Alumno) newValue;
                System.out.println("Seleccionado " + alumno.getNombre() + " " + alumno.getApellido());
            }
        });

        // modifica el tipo de selección de la lista
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Obtiene el elemento con el foco activo
        listView.setTooltip(new Tooltip(((Alumno) listView.getFocusModel().getFocusedItem()).getNombre()));
         */
    }

}
