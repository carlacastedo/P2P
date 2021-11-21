package p2p;

import java.rmi.*;

/*
    Interfaz remota para el cálculo de pi mediante el método de MonteCarlo
*/

public interface P2PInterface extends Remote {
   public void consultarUsuarios();
   public void consultarAmigos(String usuario);
   public void consultarSolicitudes(String solicitado);
   public void insertarUsuario(String usuario, String contraseña);
   public void enviarSolicitud(String solicitante, String solicitado);
   public void aceptarSolicitud(String solicitante, String solicitado);
   public void denegarSolicitud(String solicitante, String solicitado);
   public void eliminarAmigo(String solicitante, String solicitado);
   public void modificarContraseña(String usuario, String contraseña);
   
}
