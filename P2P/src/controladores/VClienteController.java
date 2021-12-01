/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private ListView<String> listaSolicitudes;
    @FXML
    private Label lblSolicitudes;
    @FXML
    private TextArea txtChat;
    @FXML
    private Button btnDenegar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Label lblDestinatario;
    @FXML
    private Button btnModificarContrasena;
    @FXML
    private Button btnSolicitarAmistad;
    @FXML
    private Label lblEnLinea;
    @FXML
    private Label lblDesconectado;

    private Cliente c;

    private HashMap<String, String> chats;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // asocia el estado del botón con el estado de los text fields
        BooleanBinding botonEnviar = txtMensaje.textProperty().isEmpty();
        BooleanBinding escribir = lblDestinatario.textProperty().isEmpty();
        btnEnviar.disableProperty().bind(botonEnviar);
        this.txtMensaje.disableProperty().bind(escribir);
        this.chats = new HashMap<>();
    }

    public void inicializarAtributos(Cliente c, String usuario) {
        this.c = c;
        this.lblNombre.setText(usuario);
    }

    @FXML
    private void enviarMensaje(ActionEvent event) {
        String conversacion = this.txtChat.getText();
        //añadimos el mensaje al chat que ya tenemos
        conversacion = "Tu: " + this.txtMensaje.getText() + "\n";
        //lo guardamos en los chats
        this.chats.put(this.lblDestinatario.getText(), conversacion);
        //mostramos en la pantalla de mensajes
        this.txtChat.setText(conversacion);
        //vaciamos el txtMensaje
        this.txtMensaje.setText("");
    }

    @FXML
    private void denegarSolicitud(MouseEvent event) {
        try {
            String solicitante = this.listaSolicitudes.getSelectionModel().getSelectedItem();
            c.denegarSolicitud(solicitante, this.lblNombre.getText());
            this.listaSolicitudes.getItems().remove(solicitante);
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void aceptarSolicitud(MouseEvent event) {
        String solicitante = this.listaSolicitudes.getSelectionModel().getSelectedItem();
        try {
            c.aceptarSolicitud(solicitante);
            this.listaSolicitudes.getItems().remove(solicitante);
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void abrirChat(MouseEvent event) {
        String destinatario = this.listaAmigos.getSelectionModel().getSelectedItem();
        this.lblDestinatario.setText(destinatario);
        //comprobamos si el amigo esta conectado o no
        if (this.chats.containsKey(destinatario)) {
            //si esta conectado podremos escribir
            this.txtMensaje.setEditable(true);
            this.lblDesconectado.setVisible(false);
            this.lblEnLinea.setVisible(true);
            this.txtChat.setText(this.chats.get(destinatario));
        } else {
            //si no esta conectado no podemos escribir
            this.lblEnLinea.setVisible(false);
            this.lblDesconectado.setVisible(true);
            this.txtChat.setText("Tu amigo no se encuentra en linea");
            this.txtMensaje.setEditable(false);
        }
    }

    @FXML
    private void filtrarAmigos(KeyEvent event) {
        try {
            ArrayList<String> lista = this.c.filtrarAmigos(this.txtBuscar.getText());
            this.actualizarAmigos(lista);
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //metodo que coloca las solicitudes en la listView
    public void actualizarSolicitudes(ArrayList<String> solicitudes) {
        ObservableList sol = FXCollections.observableArrayList();
        for (String s : solicitudes) {
            sol.add(s);
        }
        this.listaSolicitudes.setItems(sol);
    }

    //metodo que coloca los amigos en la listView y los conectados en el HashMap de chats
    public void inicializarAmigos(ArrayList<String> amigos, ArrayList<String> amigosConectados) {
        ObservableList sol = FXCollections.observableArrayList();
        for (String a : amigos) {
            sol.add(a);
        }
        
        for (String a : amigosConectados) {
            if (!this.chats.containsKey(a)) {
                this.chats.put(a, "");
            }
        }
        this.listaAmigos.setItems(sol);
    }
    
    //metodo que coloca los amigos conecados en la listView
    public void actualizarAmigos(ArrayList<String> amigos) {
        ObservableList sol = FXCollections.observableArrayList();
        for (String a : amigos) {
            sol.add(a);
        }
        this.listaAmigos.setItems(sol);
    }

    @FXML
    private void modificarContrasena(MouseEvent event) {
        VModificarContrasenaController controlador;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ventanas/VModificarContrasena.fxml"));
            // Cargo la ventana
            Pane ventana = (Pane) loader.load();
            // Cargo el scene
            Scene scene = new Scene(ventana);
            Stage stage = new Stage();
            controlador = loader.getController();
            controlador.inicializarAtributos(c);
            // Seteo la scene y la muestro
            stage.setScene(scene);
            stage.setTitle("Modificación Contraseña");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void solicitarAmistad(MouseEvent event) {
        VSolicitarController controlador = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ventanas/VSolicitar.fxml"));
            // Cargo la ventana
            Pane ventana = (Pane) loader.load();
            // Cargo el scene
            Scene scene = new Scene(ventana);
            Stage stage = new Stage();
            controlador = loader.getController();
            controlador.inicializarAtributos(c);
            // Seteo la scene y la muestro
            stage.setScene(scene);
            stage.setTitle("Solicitar Amistad");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void recibirMensaje(String mensaje, String emisor) {
        String conversacion = this.chats.get(emisor) + emisor + ": " + mensaje + "\n";
        this.chats.put(emisor, conversacion);
    }

    public void conectarAmigo(String amigo) {
        this.chats.put(amigo, "");
        if(this.listaAmigos.getSelectionModel().getSelectedItem().equals(amigo)){
            this.lblDesconectado.setVisible(false);
            this.lblEnLinea.setVisible(true);
            this.txtMensaje.setEditable(true);
            this.txtChat.setText(this.chats.get(amigo));
        }
    }

    public void desconectarAmigo(String amigo) {
        this.chats.remove(amigo);
        if(this.listaAmigos.getSelectionModel().getSelectedItem().equals(amigo)){
            this.lblDesconectado.setVisible(true);
            this.lblEnLinea.setVisible(false);
            this.txtChat.setText("Tu amigo no se encuentra en linea");
            this.txtMensaje.setEditable(false);
        }
    }
}
