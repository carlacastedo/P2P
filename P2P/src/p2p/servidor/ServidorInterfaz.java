package p2p.servidor;

import java.rmi.*;
import java.util.ArrayList;
import p2p.cliente.ClienteInterfaz;

public interface ServidorInterfaz extends Remote {

    //metodo que abre sesion de un cliente en la aplicacion
    public void iniciarSesion(ClienteInterfaz cliente) throws java.rmi.RemoteException;

    //metodo que cierra sesion de un cliente en la aplicacion
    public void cerrarSesion(ClienteInterfaz cliente) throws java.rmi.RemoteException;

    //metodo que comprueba si un usuario existe en la base de datos dado su nombre y contraseña
    public Boolean autenticarUsuario(String usuario, String contraseña) throws java.rmi.RemoteException;

    //metodo que inserta un usuario en el sistema si es su primera vez en la aplicacion
    public void insertarUsuario(String usuario, String contraseña) throws java.rmi.RemoteException;

    //metodo que devuelve los amigos de un usuario 
    public ArrayList<String> consultarAmigos(String usuario) throws java.rmi.RemoteException;

    //metodo que devuelve los usuarios no amigos de un usuario
    public ArrayList<String> consultarNoAmigos(String nombreCliente, String busqueda) throws java.rmi.RemoteException;

    //metodo que filtra los usuarios por su nombre
    public ArrayList<String> filtrarAmigos(String filtro, String nombreCliente) throws java.rmi.RemoteException;

    //metodo que devuelve las solicitudes pendientes de un usuario
    public ArrayList<String> consultarSolicitudes(String solicitado) throws java.rmi.RemoteException;
    
    //metodo que devuelve las solicitudes enviadas de un usuario
    public ArrayList<String> consultarSolicitudesEnviadas(String solicitante) throws java.rmi.RemoteException;

    //metodo que envia una solicitud a un cliente por su nombre
    public void enviarSolicitud(String solicitante, String solicitado) throws java.rmi.RemoteException;

    //metodo que acepta una solicitud pendiente de un cliente solicitado
    public void aceptarSolicitud(String solicitante, String solicitado) throws java.rmi.RemoteException;

    //metodo que deniega la solicitud de un solicitante
    public void denegarSolicitud(String solicitante, String solicitado) throws java.rmi.RemoteException;

    //metodo para modificar la contraseña del usuario que inicia sesion en la aplicacion
    public Boolean modificarContraseña(String usuario, String contrasenaAntigua, String contrasenaNueva) throws java.rmi.RemoteException;

    public void eliminarAmigo(String solicitante, String solicitado) throws java.rmi.RemoteException;

    //metodo que devuelve la interfaz remota de un amigo conectado
    public ClienteInterfaz solicitarInterfaz(String amigo) throws java.rmi.RemoteException;

}
