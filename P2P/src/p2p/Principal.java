/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import controladores.AutenticacionControlador;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author anton
 */
public class Principal extends Application {

    private static Cliente cliente;

    public static void main(String[] args) {
        cliente = new Cliente("localhost", 1500);
        launch(args);
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
            controlador.inicializarAtributos(cliente);

            // Seteo la scene y la muestro
            stage.setScene(scene);
            stage.setTitle("Autenticacion");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
