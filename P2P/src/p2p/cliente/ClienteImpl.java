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
    public String getNombreCliente()  throws java.rmi.RemoteException{
        return nombreCliente;
    }

    @Override
    public void notificar(String mensaje, String tipo) throws java.rmi.RemoteException {
        this.controlador.recibirNotificacion(mensaje, tipo);
    }

    @Override
    public void verSolicitudes(ArrayList<String> solicitudes) throws RemoteException {
        this.controlador.actualizarSolicitudes(solicitudes);
    }

    @Override
    public void verAmigos(ArrayList<String> amigos) throws RemoteException {
        this.controlador.actualizarAmigos(amigos);
    }
    
}
