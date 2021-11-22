/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import controladores.VClienteController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author anton
 */
public class ClienteImpl extends UnicastRemoteObject implements ClienteInterfaz {

    private String nombreCliente;
    private VClienteController controlador;

    public ClienteImpl(String nombre,VClienteController controlador ) throws RemoteException {
        super();
        this.nombreCliente=nombre;
    }

    @Override
    public String getNombreCliente() {
        return nombreCliente;
    }

    
    
    
}
