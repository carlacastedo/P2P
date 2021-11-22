/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author anton
 */
public class ServidorImpl extends UnicastRemoteObject implements ServidorInterfaz {

    private ArrayList<ClienteInterfaz> clientes;
    private BBDD baseDatos;

    public ServidorImpl() throws RemoteException {
        super();
        this.clientes = new ArrayList<>();
        this.baseDatos = new BBDD();
    }

    @Override
    public synchronized void registrarCliente(ClienteInterfaz cliente) throws java.rmi.RemoteException {
        if (!(clientes.contains(cliente))) {
            clientes.add(cliente);
            System.out.println("Nuevo cliente registrado");
            hacerCallbacks();
        }
    }

    public synchronized void quitarCliente(ClienteInterfaz cliente) throws java.rmi.RemoteException {
        if (clientes.remove(cliente)) {
            System.out.println("Cliente se fue");
        } else {
            System.out.println("unregister: client wasn't registered.");
        }
    }

    private synchronized void hacerCallbacks() throws java.rmi.RemoteException {
        // make callback to each registered client
        System.out.println("**************************************\n Callbacks initiated ---");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println("doing " + i + "-th callback\n");
            // convert the vector object to a callback object
            ClienteInterfaz nextClient = (ClienteInterfaz) clientes.get(i);
            // invoke the callback method
            // nextClient.notifyMe("Number of registered clients=" + clientes.size());
        }// end for
        System.out.println("********************************\n Server completed callbacks ---");
    }

    @Override
    public String consultarUsuarios() throws RemoteException {
        return this.baseDatos.consultarUsuarios();
    }

    @Override
    public void consultarAmigos(String usuario) throws RemoteException {
        this.baseDatos.consultarAmigos(usuario);
    }

    @Override
    public void consultarSolicitudes(String solicitado) throws RemoteException {
        this.baseDatos.consultarSolicitudes(solicitado);
    }

    @Override
    public void insertarUsuario(String usuario, String contraseña) throws RemoteException {
        this.baseDatos.insertarUsuario(usuario, contraseña);
    }

    @Override
    public void enviarSolicitud(String solicitante, String solicitado) throws RemoteException {
        this.baseDatos.enviarSolicitud(solicitante, solicitado);
    }

    @Override
    public void aceptarSolicitud(String solicitante, String solicitado) throws RemoteException {
        this.baseDatos.aceptarSolicitud(solicitante, solicitado);
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
    public void modificarContraseña(String usuario, String contraseña) throws RemoteException {
        this.baseDatos.modificarContraseña(usuario, contraseña);
    }

    @Override
    public Boolean existeUsuario(String usuario, String contraseña) throws RemoteException {
        return this.baseDatos.existeUsuario(usuario, contraseña);
    }

}
