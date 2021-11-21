/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author anton
 */
public class Cliente{

    private P2PInterface servidor;

//    public static void main(String[] args) {
//        try {
//            InputStreamReader is = new InputStreamReader(System.in);
//            BufferedReader br = new BufferedReader(is);
//            System.out.println("Introduce el nombre de host:");
//            String nombre = br.readLine();
//            System.out.println("Introduce el numero de puerto:");
//            String portNum = br.readLine();
//            Integer puerto = Integer.parseInt(portNum);
//            String registryURL = "rmi://" + nombre + ":" + puerto + "/p2p";
//            servidor = (P2PInterface) Naming.lookup(registryURL);
//            //Llamamos a la función que realiza el cálculo requerido
//            //paresValidos = mc.MonteCarlo(pares);
//            //Si hay alguna excepción se imprime
//        } catch (IOException | NumberFormatException | NotBoundException e) {
//            System.out.println(e.getMessage());
//        }
//        //esto no se para que sirve pero es para que se abra la ventana
//        launch(args);
//    }

    public Cliente(String host, int puerto) {
        try {
            String registryURL = "rmi://" + host + ":" + puerto + "/p2p";
            servidor = (P2PInterface) Naming.lookup(registryURL);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.out.println(ex.getMessage());
        }

    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        /*Stage vAutenticacion = FXMLLoader.load(getClass().getResource("VentanaAutenticacion.fxml"));
//        vAutenticacion.show();*/
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/ventanas/VentanaAutenticacion.fxml"));
//            // Cargo la ventana
//            Pane ventana = (Pane) loader.load();
//
//            // Cargo el scene
//            Scene scene = new Scene(ventana);
//            AutenticacionControlador controlador = loader.getController();
//            controlador.inicializarAtributos(this);
//
//            // Seteo la scene y la muestro
//            stage.setScene(scene);
//            stage.setResizable(false);
//            stage.show();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public P2PInterface getServidor() {
        return servidor;
    }
}
