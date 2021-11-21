/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
public class Cliente extends Application{

    public static void main(String[] args){
        try {
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);
            System.out.println("Introduce el nombre de host:");
            String nombre = br.readLine();
            System.out.println("Introduce el numero de puerto:");
            String portNum = br.readLine();
            Integer puerto = Integer.parseInt(portNum);
            String registryURL
                    = "rmi://" + nombre + ":" + puerto + "/p2p";
            P2PInterface p2p = (P2PInterface) Naming.lookup(registryURL);
            //Llamamos a la función que realiza el cálculo requerido
            //paresValidos = mc.MonteCarlo(pares);
            //Si hay alguna excepción se imprime
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        } catch (NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        //esto no se para que sirve pero es para que se abra la ventana
            launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        /*Stage vAutenticacion = FXMLLoader.load(getClass().getResource("VentanaAutenticacion.fxml"));
        vAutenticacion.show();*/
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("VCliente.fxml"));
            // Cargo la ventana
            Pane ventana = (Pane) loader.load();

            // Cargo el scene
            Scene scene = new Scene(ventana);
            VClienteController controlador = loader.getController();
            controlador.inicializarAtributos(this);

            // Seteo la scene y la muestro
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
