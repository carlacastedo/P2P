package p2p.cliente;

import controladores.VAutenticacionController;
import p2p.servidor.ServidorInterfaz;
import controladores.VClienteController;
import java.io.IOException;
import static java.lang.System.exit;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Cliente extends Application {

    private static ClienteInterfaz cliente;
    private static ServidorInterfaz servidor;
    private String nombreCliente;

    public static void main(String[] args) {
        try {
            String host = "localhost";
            String puerto = "1500";
            String registryURL = "rmi://" + host + ":" + puerto + "/p2p";
            servidor = (ServidorInterfaz) Naming.lookup(registryURL);
            //iniciamos la aplicacion con ventanas
            launch(args);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //metodo que se ejecuta al iniciar la aplicacion con ventanas
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ventanas/VAutenticacion.fxml"));
            // Cargo la ventana
            Pane ventana = (Pane) loader.load();
            // Cargo el scene
            Scene scene = new Scene(ventana);
            VAutenticacionController controlador = loader.getController();
            controlador.inicializarAtributos(this);
            stage.getIcons().add(new Image("/imagenes/chatIcono.png"));
            // Seteo la scene y la muestro
            stage.setScene(scene);
            stage.setTitle("Autenticacion");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //metodo que se ejecuta al cerrar el cliente
    @Override
    public void stop() {
        try {
            servidor.cerrarSesion(cliente, this.nombreCliente);
            exit(0);
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Boolean existeUsuario(String nombre, String contraseña) throws RemoteException {
        return servidor.autenticarUsuario(nombre, contraseña);
    }

    public void insertarUsuario(String nombre, String contraseña) throws RemoteException {
        this.nombreCliente = nombre;
        servidor.insertarUsuario(nombreCliente, contraseña);
    }

    public void registrarCliente(String nombre, VClienteController controlador) throws RemoteException {
        this.nombreCliente = nombre;
        cliente = new ClienteImpl(controlador);
        servidor.iniciarSesion(cliente, nombreCliente);
    }

    public void aceptarSolicitud(String amigo) throws RemoteException {
        servidor.aceptarSolicitud(amigo, this.nombreCliente);
    }

    public void denegarSolicitud(String amigo, String solicitado) throws RemoteException {
        servidor.denegarSolicitud(amigo, solicitado);
    }

    public void enviarMensaje(String amigo, String mensaje) throws RemoteException {
        //solicitamos la interfaz remota del amigo al servidor
        ClienteInterfaz a = servidor.solicitarInterfaz(amigo);
        //recibimos el mensaje en la interfaz del amigo
        a.recibirMensaje(mensaje, this.nombreCliente);
    }

    public ArrayList<String> filtrarAmigos(String filtro) throws RemoteException {
        return servidor.filtrarAmigos(filtro, this.nombreCliente);
    }

    public Boolean modificarContraseña(String contrasenaAntigua, String contrasenaNueva) throws RemoteException {
        return servidor.modificarContraseña(this.nombreCliente, contrasenaAntigua, contrasenaNueva);
    }

    public ArrayList<String> consultarNoAmigos(String busqueda) throws RemoteException {
        return servidor.consultarNoAmigos(this.nombreCliente, busqueda);
    }

    public void solicitarAmistad(String solicitado) throws RemoteException {
        servidor.enviarSolicitud(this.nombreCliente, solicitado);
    }

    public ArrayList<String> consultarAmigos(String usuario) throws RemoteException {
        return servidor.consultarAmigos(usuario);
    }

    public ArrayList<String> consultarSolicitudesEnviadas() throws RemoteException {
        return servidor.consultarSolicitudesEnviadas(this.nombreCliente);
    }

    public String getNombreCliente() {
        return nombreCliente;
    }
}
