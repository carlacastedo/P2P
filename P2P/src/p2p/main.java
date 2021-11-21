/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 *
 * @author anton
 */
public class main extends Application{

    public static void main(String[] args) {
        //esto no se para que sirve pero es para que se abra la ventana
        launch(args);
        BaseDatos bd = new BaseDatos();
//        bd.consultarUsuarios();
//        bd.insertarUsuario("eliseo", "fcbarcelona");
//        bd.insertarUsuario("dani", "madrid");
//        bd.modificarContraseña("dani", "fonseca");
//        bd.enviarSolicitud("dani", "eliseo");
//        bd.consultarSolicitudes("eliseo");
//        bd.aceptarSolicitud("dani", "eliseo");
//        bd.denegarSolicitud("juan", "carla");
//        bd.enviarSolicitud("juan", "carla"); 
//        bd.eliminarAmigo("dani", "eliseo");

    }

    @Override
    public void start(Stage stage) throws Exception {
        Stage vAutenticacion = FXMLLoader.load(getClass().getResource("VentanaAutenticacion.fxml"));
        vAutenticacion.show();
    }
}
