package p2p.servidor;

import java.rmi.*;
import java.util.ArrayList;
import p2p.cliente.ClienteInterfaz;


public interface ServidorInterfaz extends Remote {

    public void registrarCliente(ClienteInterfaz cliente) throws java.rmi.RemoteException;

    public void quitarCliente(ClienteInterfaz cliente) throws java.rmi.RemoteException;

    public String consultarUsuarios() throws java.rmi.RemoteException;

    public ArrayList<String> consultarAmigos(String usuario) throws java.rmi.RemoteException;

    public ArrayList<String> consultarSolicitudes(String solicitado) throws java.rmi.RemoteException;

    public void insertarUsuario(String usuario, String contraseña) throws java.rmi.RemoteException;

    public void enviarSolicitud(String solicitante, String solicitado) throws java.rmi.RemoteException;

    public void aceptarSolicitud(String solicitante, String solicitado) throws java.rmi.RemoteException;

    public void denegarSolicitud(String solicitante, String solicitado) throws java.rmi.RemoteException;

    public void eliminarAmigo(String solicitante, String solicitado) throws java.rmi.RemoteException;

    public Boolean modificarContraseña(String usuario, String contrasenaAntigua, String contrasenaNueva) throws java.rmi.RemoteException;

    public Boolean autenticarUsuario(String usuario, String contraseña) throws java.rmi.RemoteException;

    public ArrayList<String> filtrarAmigos(String filtro, String nombreCliente) throws java.rmi.RemoteException;

    public ArrayList<String> consultarNoAmigos(String nombreCliente, String busqueda)throws java.rmi.RemoteException;
}
