/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.cliente;

import java.rmi.*;
import java.util.ArrayList;

/**
 *
 * @author anton
 */
public interface ClienteInterfaz extends Remote {

    public String getNombreCliente() throws java.rmi.RemoteException;

    public void notificar(String amigo) throws java.rmi.RemoteException;
    
    public void verSolicitudes(ArrayList<String> solicitudes) throws java.rmi.RemoteException;

    public void verAmigos(ArrayList<String> consultarAmigos)throws java.rmi.RemoteException;
}
