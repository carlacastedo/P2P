/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

/**
 *
 * @author anton
 */
public class main {

    public static void main(String[] args) {
        BaseDatos bd = new BaseDatos();
        bd.consultarUsuarios();
        bd.insertarUsuario("eliseo", "fcbarcelona");
        bd.insertarUsuario("dani", "madrid");
        bd.modificarContraseña("dani", "fonseca");
        bd.enviarSolicitud("dani", "eliseo");
        bd.consultarSolicitudes("eliseo");
        bd.aceptarSolicitud("dani", "eliseo");
        bd.denegarSolicitud("juan", "carla");
        bd.enviarSolicitud("juan", "carla"); 
        bd.eliminarAmigo("dani", "eliseo");
    }
}
