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
                    this.clientes.get(a).notificar(cliente.getNombreCliente() + " esta en linea", "conexion");
                }
            }

            System.out.println(amigosConectados);
            cliente.verAmigos(amigosConectados);

            //buscamos las solicitudes pendientes
            cliente.verSolicitudes(this.baseDatos.consultarSolicitudes(cliente.getNombreCliente()));
        }
    }

    @Override
    public synchronized void quitarCliente(ClienteInterfaz cliente) throws java.rmi.RemoteException {
        if (clientes.remove(cliente.getNombreCliente()) != null) {
            System.out.println(cliente.getNombreCliente() + " ha cerrado sesion");
        } else {
            System.out.println("el cliente no habia iniciado sesion");
        }
    }

//    private synchronized void notificarAmigos(String nombre) throws java.rmi.RemoteException {
//        ArrayList<String> amigos = consultarAmigos(nombre);
//        System.out.println("amigos de " + nombre + ": " + amigos.toString());
//        // make callback to each registered client
//        System.out.println("**************************************\n Callbacks initiated ---");
//        for (int i = 0; i < clientes.size(); i++) {
//            //this.consultarAmigos(clientes.);
//            System.out.println("doing " + i + "-th callback\n");
//            // convert the vector object to a callback object
//            ClienteInterfaz cliente = (ClienteInterfaz) clientes.get(i);
//            if (amigos.contains(cliente.getNombreCliente())) {
//                cliente.notificar(nombre);
//            }
//
//        }// end for
//        System.out.println("********************************\n Server completed callbacks ---");
//    }
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
    public Boolean modificarContraseña(String usuario, String contrasenaAntigua, String contrasenaNueva) throws RemoteException {
        if (this.existeUsuario(usuario, contrasenaAntigua)) {
            this.baseDatos.modificarContraseña(usuario, contrasenaNueva);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean existeUsuario(String usuario, String contraseña) throws RemoteException {
        return this.baseDatos.autenticarUsuario(usuario, contraseña);
    }

    @Override
    public ArrayList<String> filtrarAmigos(String filtro, String nombreCliente) throws RemoteException {
        ArrayList<String> amigos = this.baseDatos.filtrarAmigos(nombreCliente, filtro);
        ArrayList<String> amigosConectados = new ArrayList<>();

        //comprobamos los amigos que estan conectados
        for (String a : amigos) {
            if (clientes.keySet().contains(a)) {
                amigosConectados.add(a);
            }
        }
        return amigosConectados;
    }

    @Override
    public ArrayList<String> consultarNoAmigos(String nombreCliente, String busqueda) throws RemoteException {
        return this.baseDatos.consultarNoAmigos(nombreCliente, busqueda);
    }

}
