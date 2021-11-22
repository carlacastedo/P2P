package p2p;

import java.rmi.*;
import java.util.ArrayList;

/*
    Interfaz remota para 
 */
public interface ServidorInterfaz extends Remote {

    public void registrarCliente(ClienteInterfaz cliente) throws java.rmi.RemoteException;

    public void quitarCliente(ClienteInterfaz cliente) throws java.rmi.RemoteException;

    public String consultarUsuarios() throws java.rmi.RemoteException;

    public ArrayList<String> consultarAmigos(String usuario) throws java.rmi.RemoteException;

    public void consultarSolicitudes(String solicitado) throws java.rmi.RemoteException;

    public void insertarUsuario(String usuario, String contraseña) throws java.rmi.RemoteException;

    public void enviarSolicitud(String solicitante, String solicitado) throws java.rmi.RemoteException;

    public void aceptarSolicitud(String solicitante, String solicitado) throws java.rmi.RemoteException;

    public void denegarSolicitud(String solicitante, String solicitado) throws java.rmi.RemoteException;

    public void eliminarAmigo(String solicitante, String solicitado) throws java.rmi.RemoteException;

    public void modificarContraseña(String usuario, String contraseña) throws java.rmi.RemoteException;

    public Boolean existeUsuario(String usuario, String contraseña) throws java.rmi.RemoteException;
}
