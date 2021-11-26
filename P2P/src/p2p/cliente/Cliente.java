/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.cliente;

import controladores.AutenticacionControlador;
import p2p.servidor.ServidorInterfaz;
import controladores.VClienteController;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author anton
 */
public class Cliente extends Application {

    private static ClienteInterfaz cliente;
    private static ServidorInterfaz servidor;

    public static void main(String[] args) {

        try {
            String host = "localhost";
            String puerto = "1500";
            String registryURL = "rmi://" + host + ":" + puerto + "/p2p";
            servidor = (ServidorInterfaz) Naming.lookup(registryURL);
            launch(args);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ventanas/VentanaAutenticacion.fxml"));
            // Cargo la ventana
            Pane ventana = (Pane) loader.load();
            // Cargo el scene
            Scene scene = new Scene(ventana);
            AutenticacionControlador controlador = loader.getController();
            controlador.inicializarAtributos(this);

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
    public void stop(){
        try {
            servidor.quitarCliente(cliente);
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Boolean existeUsuario(String usuario, String contraseña) throws RemoteException {
        return servidor.autenticarUsuario(usuario, contraseña);
    }

    public void insertarUsuario(String usuario, String contraseña) throws RemoteException {
        servidor.insertarUsuario(usuario, contraseña);
    }

    public void registrarCliente(String nombre, VClienteController controlador) throws RemoteException {
        cliente = new ClienteImpl(nombre, controlador);
        servidor.registrarCliente(cliente);
    }

    public void aceptarSolicitud(String amigo) throws RemoteException {
        servidor.aceptarSolicitud(amigo, cliente.getNombreCliente());
    }

    public void denegarSolicitud(String amigo, String solicitado) throws RemoteException {
        servidor.denegarSolicitud(amigo, solicitado);
    }

    public void enviarMensaje(String amigo, String mensaje) {
        //implementar
    }

    public ArrayList<String> filtrarAmigos(String filtro) throws RemoteException {
        return servidor.filtrarAmigos(filtro, cliente.getNombreCliente());
    }

    public Boolean modificarContraseña(String contrasenaAntigua, String contrasenaNueva) throws RemoteException {
        return servidor.modificarContraseña(cliente.getNombreCliente(), contrasenaAntigua, contrasenaNueva);
    }

    public ArrayList<String> consultarNoAmigos(String busqueda) throws RemoteException {
        return servidor.consultarNoAmigos(cliente.getNombreCliente(), busqueda);
    }

    public void solicitarAmistad(String solicitado) throws RemoteException {
        servidor.enviarSolicitud(cliente.getNombreCliente(), solicitado);
    }

}
