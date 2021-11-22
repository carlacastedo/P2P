/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import controladores.AutenticacionControlador;
import controladores.VClienteController;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
    
    public Boolean existeUsuario(String usuario, String contraseña) throws RemoteException{
        return servidor.existeUsuario(usuario, contraseña);
    }
    
    public void insertarUsuario(String usuario, String contraseña) throws RemoteException{
        servidor.insertarUsuario(usuario,contraseña);
    }
    
    public void registrarCliente(String nombre,VClienteController controlador) throws RemoteException{
        cliente = new ClienteImpl(nombre,controlador);
        servidor.registrarCliente(cliente);
    }

}
