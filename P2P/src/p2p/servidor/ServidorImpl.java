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

public class ServidorImpl extends UnicastRemoteObject implements ServidorInterfaz {

    private HashMap<String, ClienteInterfaz> clientes;
    private BBDD baseDatos;

    public ServidorImpl() throws RemoteException {
        super();
        this.clientes = new HashMap<>();
        this.baseDatos = new BBDD();
    }

    @Override
    public synchronized void iniciarSesion(ClienteInterfaz cliente, String nombre) throws java.rmi.RemoteException {
        //comprobamos si no ha iniciado sesion en la aplicacion
        if (!(clientes.containsValue(cliente))) {
            //lo añadimos al hashmap de interfaces de clientes conectados
            clientes.put(nombre, cliente);
            //mensaje de informacion
            System.out.println(nombre + " ha iniciado sesion");

            //consultamos todos sus amigos, esten conectados o no
            ArrayList<String> amigos = this.baseDatos.consultarAmigos(nombre);
            ArrayList<String> amigosConectados = new ArrayList<>();

            //comprobamos los amigos que estan conectados, que serán los registrados en la aplicacion y a los que
            //habrá que notificar
            for (String a : amigos) {
                if (clientes.keySet().contains(a)) {
                    amigosConectados.add(a);
                    //notificamos de la conexion a los amigos del cliente que se acaba de conectar
                    this.clientes.get(a).conectarAmigo(nombre);
                }
            }
            //metemos en la interfaz grafica todos los amigos y guardamos los conectados
            cliente.verAmigos(amigos, amigosConectados);

            //buscamos las solicitudes pendientes y las mostramos
            cliente.verSolicitudes(this.baseDatos.consultarSolicitudes(nombre));
        }
    }

    @Override
    public synchronized void cerrarSesion(ClienteInterfaz cliente, String nombre) throws java.rmi.RemoteException {
        //si ha iniciado sesion lo quitamos
        if (clientes.containsKey(nombre)) {
            clientes.remove(nombre);
            System.out.println(nombre + " ha cerrado sesion");
            //notificamos a sus amigos de la desconexion
            ArrayList<String> amigos = this.baseDatos.consultarAmigos(nombre);

            //comprobamos los amigos que estan conectados
            for (String a : amigos) {
                if (clientes.keySet().contains(a)) {
                    //notificamos de la desconexion a sus amigos
                    this.clientes.get(a).desconectarAmigo(nombre);
                }
            }
        } else {
            System.out.println("El cliente no habia iniciado sesion");
        }
    }

    @Override
    public void insertarUsuario(String usuario, String contraseña) throws RemoteException {
        //insertamos un usuario en la base de datos
        this.baseDatos.insertarUsuario(usuario, contraseña);
    }

    @Override
    public Boolean autenticarUsuario(String usuario, String contraseña) throws RemoteException {
        //comprobamos si los datos son correctos
        return this.baseDatos.autenticarUsuario(usuario, contraseña);
    }

    @Override
    public synchronized Boolean modificarContraseña(String usuario, String contrasenaAntigua, String contrasenaNueva) throws RemoteException {
        //comprobamos si es el usuario el que va a cambiar su contraseña
        if (this.autenticarUsuario(usuario, contrasenaAntigua)) {
            //modidicamos la contraseña en la base de datos
            this.baseDatos.modificarContraseña(usuario, contrasenaNueva);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<String> consultarAmigos(String usuario) throws RemoteException {
        return this.baseDatos.consultarAmigos(usuario);
    }

    @Override
    public ArrayList<String> filtrarAmigos(String filtro, String nombreCliente) throws RemoteException {
        return this.baseDatos.filtrarAmigos(nombreCliente, filtro);
    }

    @Override
    public ArrayList<String> consultarNoAmigos(String nombreCliente, String busqueda) throws RemoteException {
        return this.baseDatos.consultarNoAmigos(nombreCliente, busqueda);
    }

    @Override
    public ArrayList<String> consultarSolicitudes(String solicitado) throws RemoteException {
        return this.baseDatos.consultarSolicitudes(solicitado);
    }

    @Override
    public synchronized void enviarSolicitud(String solicitante, String solicitado) throws RemoteException {
        //comprobar que no te puedes enviar una solicitud a ti mismo
        if (!solicitado.equals(solicitante)) {
            //metemos la solicitud en la base de datos
            this.baseDatos.enviarSolicitud(solicitante, solicitado);
            //mostramos las solicitudes en la interfaz del solicitado en caso de que este conectado
            if (this.clientes.containsKey(solicitado)) {
                this.clientes.get(solicitado).verSolicitudes(this.consultarSolicitudes(solicitado));
            }
        }
    }

    @Override
    public synchronized void aceptarSolicitud(String solicitante, String solicitado) throws RemoteException {
        //aceptamos la solicitud en la base de datos
        this.baseDatos.aceptarSolicitud(solicitante, solicitado);
        //metemos el nuevo amigo en la ventana
        this.clientes.get(solicitado).verAmigos(this.consultarAmigos(solicitado), null);
        //si el solicitante esta conectado le añadimos su nuevo amigo y notificamos de la conexion
        // al amigo
        if (this.clientes.get(solicitante) != null) {
            this.clientes.get(solicitante).verAmigos(this.consultarAmigos(solicitante), null);
            this.clientes.get(solicitante).conectarAmigo(solicitado);
            this.clientes.get(solicitado).conectarAmigo(solicitante);
        }
    }

    @Override
    public void denegarSolicitud(String solicitante, String solicitado) throws RemoteException {
        //eliminamos la solicitud de la base de datos
        this.baseDatos.denegarSolicitud(solicitante, solicitado);
    }

    @Override
    public ArrayList<String> consultarSolicitudesEnviadas(String solicitante) throws RemoteException {
        return this.baseDatos.consultarSolicitudesEnviadas(solicitante);
    }

    @Override
    public ClienteInterfaz solicitarInterfaz(String amigo) {
        if (this.clientes.containsKey(amigo)) {
            return this.clientes.get(amigo);
        } else {
            return null;
        }
    }

}
