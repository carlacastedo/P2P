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

    private ServidorInterfaz servidor;

    public ClienteImpl(ServidorInterfaz servidor) throws RemoteException {
        super();
        this.servidor = servidor;
    }

    @Override
    public ServidorInterfaz getServidor() throws java.rmi.RemoteException {
        return servidor;
    }
}
