/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author anton
 */
public class ClienteImpl extends UnicastRemoteObject implements ClienteInterfaz {

    private String nombreCliente;

    public ClienteImpl(String nombre) throws RemoteException {
        super();
        this.nombreCliente=nombre;
    }

    @Override
    public String getNombreCliente() {
        return nombreCliente;
    }

    
    
    
}
