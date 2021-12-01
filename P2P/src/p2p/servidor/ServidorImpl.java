/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import p2p.cliente.ClienteInterfaz;

/**
 *
 * @author anton
 */
public class ServidorImpl extends UnicastRemoteObject implements ServidorInterfaz {

    private HashMap<String, ClienteInterfaz> clientes;
    private BBDD baseDatos;

    public ServidorImpl() throws RemoteException {
        super();
        this.clientes = new HashMap<>();
        this.baseDatos = new BBDD();
    }

    @Override
    public synchronized void registrarCliente(ClienteInterfaz cliente) throws java.rmi.RemoteException {
        if (!(clientes.containsValue(cliente))) {
            clientes.put(cliente.getNombreCliente(), cliente);
            System.out.println(cliente.getNombreCliente() + " ha iniciado sesion");

            ArrayList<String> amigos = this.baseDatos.consultarAmigos(cliente.getNombreCliente());
            ArrayList<String> amigosConectados = new ArrayList<>();

            //comprobamos los amigos que estan conectados
            for (String a : amigos) {
                if (clientes.keySet().contains(a)) {
                    amigosConectados.add(a);
                    //notificamos de la conexion a sus amigos
                    this.clientes.get(a).conectarAmigo(cliente.getNombreCliente());
                }
            }

            //ver todos los amigos y los que estan conectados en el momento
            cliente.verAmigos(amigos, amigosConectados);

            //buscamos las solicitudes pendientes
            cliente.verSolicitudes(this.baseDatos.consultarSolicitudes(cliente.getNombreCliente()));
        }
    }

    @Override
    public synchronized void quitarCliente(ClienteInterfaz cliente) throws java.rmi.RemoteException {
        if (clientes.remove(cliente.getNombreCliente()) != null) {
            System.out.println(cliente.getNombreCliente() + " ha cerrado sesion");
            //desconectarlo de los amigos
            ArrayList<String> amigos = this.baseDatos.consultarAmigos(cliente.getNombreCliente());
            ArrayList<String> amigosConectados = new ArrayList<>();

            //comprobamos los amigos que estan conectados
            for (String a : amigos) {
                if (clientes.keySet().contains(a)) {
                    amigosConectados.add(a);
                    //notificamos de la conexion a sus amigos
                    this.clientes.get(a).desconectarAmigo(cliente.getNombreCliente());
                }
            }

        } else {
            System.out.println("El cliente no habia iniciado sesion");
        }
    }

    @Override
    public String consultarUsuarios() throws RemoteException {
        return this.baseDatos.consultarUsuarios();
    }

    @Override
    public ArrayList<String> consultarAmigos(String usuario) throws RemoteException {
        return this.baseDatos.consultarAmigos(usuario);
    }

    @Override
    public ArrayList<String> consultarSolicitudes(String solicitado) throws RemoteException {
        return this.baseDatos.consultarSolicitudes(solicitado);
    }

    @Override
    public void insertarUsuario(String usuario, String contraseña) throws RemoteException {
        this.baseDatos.insertarUsuario(usuario, contraseña);
    }

    @Override
    public void enviarSolicitud(String solicitante, String solicitado) throws RemoteException {
        this.baseDatos.enviarSolicitud(solicitante, solicitado);
        if (this.clientes.get(solicitado) != null) {
            this.clientes.get(solicitado).verSolicitudes(this.consultarSolicitudes(solicitado));
        }
    }

    @Override
    public void aceptarSolicitud(String solicitante, String solicitado) throws RemoteException {
        this.baseDatos.aceptarSolicitud(solicitante, solicitado);
        if (this.clientes.get(solicitado) != null) {
            this.clientes.get(solicitado).verAmigos(this.consultarAmigos(solicitado), null);
        }
    }

    @Override
    public void denegarSolicitud(String solicitante, String solicitado) throws RemoteException {
        this.baseDatos.denegarSolicitud(solicitante, solicitado);
    }

    @Override
    public void eliminarAmigo(String solicitante, String solicitado) throws RemoteException {
        this.baseDatos.eliminarAmigo(solicitante, solicitado);
    }

    @Override
    public Boolean modificarContraseña(String usuario, String contrasenaAntigua, String contrasenaNueva) throws RemoteException {
        if (this.autenticarUsuario(usuario, contrasenaAntigua)) {
            this.baseDatos.modificarContraseña(usuario, contrasenaNueva);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean autenticarUsuario(String usuario, String contraseña) throws RemoteException {
        return this.baseDatos.autenticarUsuario(usuario, contraseña);
    }

    @Override
    public ArrayList<String> filtrarAmigos(String filtro, String nombreCliente) throws RemoteException {
        return this.baseDatos.filtrarAmigos(nombreCliente, filtro);
    }

    @Override
    public ArrayList<String> consultarNoAmigos(String nombreCliente, String busqueda) throws RemoteException {
        return this.baseDatos.consultarNoAmigos(nombreCliente, busqueda);
    }

}
