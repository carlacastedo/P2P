package controladores;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import p2p.cliente.Cliente;

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

    //atributo para guardar los nombres de los amigos con mensajes sin leer
    private ArrayList<String> sinLeer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.chats = new HashMap<>();
        sinLeer = new ArrayList<>();

        // asocia el estado del botón con el estado de los text fields
        BooleanBinding botonEnviar = txtMensaje.textProperty().isEmpty();
        BooleanBinding escribir = lblDestinatario.textProperty().isEmpty();
        btnEnviar.disableProperty().bind(botonEnviar);
        this.txtMensaje.disableProperty().bind(escribir);

        //definimos un estilo de item para poner en negrita los chats sin leer
        listaAmigos.setCellFactory(cell -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        if (item != null && sinLeer.contains(item)) {
                            super.setStyle("-fx-font-weight: bold");
                        } else {
                            super.setStyle("-fx-font-weight: normal");
                        }
                    }
                    super.setText(item);
                }
            };
        });
    }

    public void inicializarAtributos(Cliente c, String usuario) {
        this.c = c;
        this.lblNombre.setText(usuario);
    }

    @FXML //metodo que envia un mensaje a un amigo conectado seleccionado en la lista
    private void enviarMensaje(ActionEvent event) {
        String nuevoMensaje = "Tu: " + this.txtMensaje.getText() + "\n";
        String destinatario = this.lblDestinatario.getText();
        //mostramos en la pantalla de mensajes
        this.txtChat.appendText(nuevoMensaje);
        //añadimos el mensaje al chat que ya tenemos
        String conversacion = this.txtChat.getText();
        //lo guardamos en los chats
        this.chats.put(destinatario, conversacion);
        try {
            //enviamos el mensaje a nuestro amigo
            this.c.enviarMensaje(this.lblDestinatario.getText(), this.txtMensaje.getText());
            //ponemos el chat como primero
            this.ordenarChats(destinatario);
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
        //vaciamos el txtMensaje
        this.txtMensaje.setText("");
    }

    //metodo que recibe un mensaje de un amigo
    public void recibirMensaje(String mensaje, String emisor) {
        //construimos el mensaje recibido
        String recibido = emisor + ": " + mensaje + "\n";
        //guardamos la conversacion
        String conversacion = this.chats.get(emisor) + recibido;
        this.chats.put(emisor, conversacion);
        //ponemos en negrita
        if (!this.sinLeer.contains(emisor) && !this.listaAmigos.getSelectionModel().getSelectedItem().equals(emisor)) {
            this.sinLeer.add(emisor);
        }
        //ponemos el chat como primero
        this.ordenarChats(emisor);
        if (this.listaAmigos.getSelectionModel().getSelectedItem() != null && this.listaAmigos.getSelectionModel().getSelectedItem().equals(emisor)) {
            this.txtChat.appendText(recibido);
        }
    }

    @FXML
    private void aceptarSolicitud(MouseEvent event) {
        String solicitante = this.listaSolicitudes.getSelectionModel().getSelectedItem();
        try {
            c.aceptarSolicitud(solicitante);
            //eliminamos la solicitud de la lista
            this.listaSolicitudes.getItems().remove(solicitante);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Se ha aceptado la solicitud");
            alert.showAndWait();
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void denegarSolicitud(MouseEvent event) {
        try {
            String solicitante = this.listaSolicitudes.getSelectionModel().getSelectedItem();
            c.denegarSolicitud(solicitante, this.lblNombre.getText());
            //eliminamos la solicitud de la lista
            this.listaSolicitudes.getItems().remove(solicitante);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Se ha denegado la solicitud");
            alert.showAndWait();
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML//metodo que coloca los mensajes con un amigo en la ventana del chat
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
            //comprobamos si el chat estaba sin leer para marcarlo como leido
            if (this.sinLeer.contains(destinatario)) {
                sinLeer.remove(destinatario);
            }
        } else {
            //si no esta conectado no podemos escribir
            this.lblEnLinea.setVisible(false);
            this.lblDesconectado.setVisible(true);
            this.txtChat.setText("Tu amigo no se encuentra en linea");
            this.txtMensaje.setEditable(false);
        }
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
            stage.getIcons().add(new Image("/imagenes/chatIcono.png"));
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
            controlador.inicializarAtributos(c, this.c.consultarSolicitudesEnviadas());
            // Seteo la scene y la muestro
            stage.getIcons().add(new Image("/imagenes/chatIcono.png"));
            stage.setScene(scene);
            stage.setTitle("Solicitar Amistad");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
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

    //metodo que guarda el amigo en el hashmap de chats y si esta seleccionado
    //modifica los parametros de la ventana
    public void conectarAmigo(String amigo) {
        this.chats.put(amigo, "");
        if ((this.listaAmigos.getSelectionModel().getSelectedItem()) != null && (this.listaAmigos.getSelectionModel().getSelectedItem().equals(amigo))) {
            this.lblDesconectado.setVisible(false);
            this.lblEnLinea.setVisible(true);
            this.txtMensaje.setEditable(true);
            this.txtChat.setText(this.chats.get(amigo));
        }
    }

    //metodo que borra el amigo en el hashmap de chats y si esta seleccionado
    //modifica los parametros de la ventana
    public void desconectarAmigo(String amigo) {
        this.chats.remove(amigo);
        if (this.listaAmigos.getSelectionModel().getSelectedItem().equals(amigo)) {
            this.lblDesconectado.setVisible(true);
            this.lblEnLinea.setVisible(false);
            this.txtChat.setText("Tu amigo no se encuentra en linea");
            this.txtMensaje.setEditable(false);
        }
    }

    //metodo que pone a un usuario de primero en la lista de amigos
    private void ordenarChats(String destinatario) {
        //ordenar los chats
        ArrayList<String> ordenados = new ArrayList<>();
        ordenados.add(destinatario);
        for (String amigo : this.listaAmigos.getItems()) {
            if (!amigo.equals(destinatario)) {
                ordenados.add(amigo);
            }
        }
        this.actualizarAmigos(ordenados);
    }

    //metodo que coloca los amigos en la listView y los conectados en el HashMap de chats
    public void inicializarAmigos(ArrayList<String> amigos, ArrayList<String> amigosConectados) {
        ObservableList sol = FXCollections.observableArrayList();
        //añadimos todos a la lista
        for (String a : amigos) {
            sol.add(a);
        }
        //añadimos los conectados al hashmap de chats
        for (String a : amigosConectados) {
            if (!this.chats.containsKey(a)) {
                this.chats.put(a, "");
            }
        }
        Platform.runLater(() -> {
            listaAmigos.setItems(sol);
        });
    }

    //metodo que coloca los amigos en la listView
    public void actualizarAmigos(ArrayList<String> amigos) {
        ObservableList sol = FXCollections.observableArrayList();
        for (String a : amigos) {
            sol.add(a);
        }
        Platform.runLater(() -> {
            listaAmigos.setItems(sol);
        });
    }

    //metodo que coloca las solicitudes en la listView
    public void actualizarSolicitudes(ArrayList<String> solicitudes) {
        ObservableList sol = FXCollections.observableArrayList();
        for (String s : solicitudes) {
            sol.add(s);
        }
        Platform.runLater(() -> {
            listaSolicitudes.setItems(sol);
        });
    }
}
