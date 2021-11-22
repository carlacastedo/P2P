/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author anton
 */
public class ClienteImpl implements ClienteInterfaz{

    private ServidorInterfaz servidor;

    public ClienteImpl(String host, int puerto) {
        try {
            String registryURL = "rmi://" + host + ":" + puerto + "/p2p";
            servidor = (ServidorInterfaz) Naming.lookup(registryURL);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ServidorInterfaz getServidor() {
        return servidor;
    }
}
