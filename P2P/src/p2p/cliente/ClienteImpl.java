package p2p.cliente;

import controladores.VClienteController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClienteImpl extends UnicastRemoteObject implements ClienteInterfaz {

    private final VClienteController controlador;

    public ClienteImpl(VClienteController controlador) throws RemoteException {
        super();
        this.controlador = controlador;
    }

    @Override
    public void verSolicitudes(ArrayList<String> solicitudes) throws RemoteException {
        //actualizamos las solicitudes en la ventana
        this.controlador.actualizarSolicitudes(solicitudes);
    }

    @Override
    public void verAmigos(ArrayList<String> amigos, ArrayList<String> amigosConectados) throws RemoteException {
        if (amigosConectados == null) {
            //actualizamos los amigos en la ventana
            this.controlador.actualizarAmigos(amigos);
        } else {
            this.controlador.inicializarAmigos(amigos, amigosConectados);
        }
    }

    @Override
    public void recibirMensaje(String mensaje, String amigo) throws RemoteException {
        //recibimos los mensajes en la ventana
        this.controlador.recibirMensaje(mensaje, amigo);
    }

    @Override
    public void conectarAmigo(String amigo) throws java.rmi.RemoteException {
        //conectamos a un amigo en la ventana
        this.controlador.conectarAmigo(amigo);
    }

    @Override
    public void desconectarAmigo(String amigo) throws java.rmi.RemoteException {
        //desconectamos a un amigo en la ventana
        this.controlador.desconectarAmigo(amigo);
    }
}
