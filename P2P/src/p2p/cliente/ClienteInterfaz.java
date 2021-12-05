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

    //public String getNombreCliente() throws java.rmi.RemoteException;

    //metodo que añade a un amigo como conectado 
    public void conectarAmigo(String amigo) throws java.rmi.RemoteException;

    //metodo que elimina a un amigo de los amigos conectados de un usuario
    public void desconectarAmigo(String nombreCliente) throws java.rmi.RemoteException;

    //metodo que muestra las solicitudes que le han hecho a un usuario
    public void verSolicitudes(ArrayList<String> solicitudes) throws java.rmi.RemoteException;

    //metodo que muestra los amigos de un usuario, y en caso de iniciar sesion, tambien los que estan conectados
    public void verAmigos(ArrayList<String> amigos, ArrayList<String> amigosConectados) throws java.rmi.RemoteException;

    //metodo que permite recibir un mensaje de un amigo
    public void recibirMensaje(String mensaje, String amigo) throws java.rmi.RemoteException;
}
