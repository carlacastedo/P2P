/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.cliente;

import controladores.VClienteController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author anton
 */
public class ClienteImpl extends UnicastRemoteObject implements ClienteInterfaz {

    private final String nombreCliente;
    private final VClienteController controlador;

    public ClienteImpl(String nombre, VClienteController controlador) throws RemoteException {
        super();
        this.nombreCliente = nombre;
        this.controlador = controlador;
    }

    @Override
    public String getNombreCliente() throws java.rmi.RemoteException {
        return nombreCliente;
    }

    @Override
    public void verSolicitudes(ArrayList<String> solicitudes) throws RemoteException {
        this.controlador.actualizarSolicitudes(solicitudes);
    }

    @Override
    public void verAmigos(ArrayList<String> amigos, ArrayList<String> amigosConectados) throws RemoteException {
        if (amigosConectados == null) {
            this.controlador.actualizarAmigos(amigos);
        } else {
            this.controlador.inicializarAmigos(amigos, amigosConectados);
        }
    }

    @Override
    public void recibirMensaje(String mensaje) throws RemoteException {
        this.controlador.recibirMensaje(mensaje, mensaje);
    }

    @Override
    public void conectarAmigo(String amigo) throws java.rmi.RemoteException {
        this.controlador.conectarAmigo(amigo);
    }

    @Override
    public void desconectarAmigo(String amigo) throws java.rmi.RemoteException {
        this.controlador.desconectarAmigo(amigo);
    }

}
