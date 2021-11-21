/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.Properties;

/**
 *
 * @author basesdatos
 */
public class P2PImpl extends UnicastRemoteObject implements P2PInterface{

    private Connection conexion;

    public P2PImpl()throws RemoteException {
        super( );
        Properties configuracion = new Properties();
        FileInputStream arqConfiguracion;

        try {
            arqConfiguracion = new FileInputStream("baseDatos.properties");
            configuracion.load(arqConfiguracion);
            arqConfiguracion.close();

            Properties usuario = new Properties();

            String gestor = configuracion.getProperty("gestor");

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));
            this.conexion = java.sql.DriverManager.getConnection("jdbc:" + gestor + "://"
                    + configuracion.getProperty("servidor") + ":"
                    + configuracion.getProperty("puerto") + "/"
                    + configuracion.getProperty("baseDatos"),
                    usuario);
        } catch (FileNotFoundException f) {
            System.out.println(f.getMessage());
        } catch (IOException | java.sql.SQLException i) {
            System.out.println(i.getMessage());
        }
    }

    @Override
    public String consultarUsuarios() {
        Connection con = this.conexion;
        PreparedStatement stmUsuarios = null;
        ResultSet rsUsuarios;
        String texto="";
        String consulta = "select nombre from usuarios";
        try {
            stmUsuarios = con.prepareStatement(consulta);
            rsUsuarios = stmUsuarios.executeQuery();
            while (rsUsuarios.next()) {
                texto+=rsUsuarios.getString("nombre");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (stmUsuarios != null) {
                    stmUsuarios.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return texto;
    }

    @Override
    public void consultarAmigos(String usuario) {
        Connection con = this.conexion;

        String consulta = "select solicitante from solicitar_amistad "
                + "where solicitado=? and estado='aceptado' "
                + "UNION "
                + "select solicitado from solicitar_amistad "
                + "where solicitante=? and estado='aceptado' ";

        try (PreparedStatement stmUsuario = con.prepareStatement(consulta)) {
            stmUsuario.setString(1, usuario);
            stmUsuario.setString(2, usuario);
            try (ResultSet rsUsuario = stmUsuario.executeQuery()) {
                while (rsUsuario.next()) {
                    System.out.println(rsUsuario.getString("solicitante"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void consultarSolicitudes(String solicitado) {
        Connection con = this.conexion;

        String consulta = "select solicitante from solicitar_amistad "
                + "where solicitado=? and estado='pendiente'";
        try (PreparedStatement stmUsuario = con.prepareStatement(consulta)) {
            stmUsuario.setString(1, solicitado);
            try (ResultSet rsUsuario = stmUsuario.executeQuery()) {
                while (rsUsuario.next()) {
                    System.out.println(rsUsuario.getString("solicitante"));
                }
            }
        } catch (SQLException e) {
            System.err.println(consulta + "\n" + e.getMessage());
        }
    }

    @Override
    public void insertarUsuario(String usuario, String contraseña) {
        Connection con = this.conexion;

        String consulta = "insert into usuarios(nombre, contraseña) "
                + "values(?, ?)";
        try (PreparedStatement stmUsuario = con.prepareStatement(consulta)) {
            stmUsuario.setString(1, usuario);
            stmUsuario.setString(2, contraseña);
            stmUsuario.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void enviarSolicitud(String solicitante, String solicitado) {
        Connection con = this.conexion;

        String consulta = "insert into solicitar_amistad(solicitante, solicitado, estado) "
                + "values(?, ?, 'pendiente')";

        try (PreparedStatement stmSolAmis = con.prepareStatement(consulta)) {
            stmSolAmis.setString(1, solicitante);
            stmSolAmis.setString(2, solicitado);
            stmSolAmis.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void aceptarSolicitud(String solicitante, String solicitado) {
        Connection con = this.conexion;

        String modificacion = "update solicitar_amistad set estado='aceptado' where solicitante=? and"
                + " solicitado=?";

        try (PreparedStatement stmSolAmis = con.prepareStatement(modificacion)) {
            stmSolAmis.setString(1, solicitante);
            stmSolAmis.setString(2, solicitado);
            stmSolAmis.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void denegarSolicitud(String solicitante, String solicitado) {
        Connection con = this.conexion;

        String borrado = "delete from solicitar_amistad where solicitante=? and"
                + " solicitado=? and estado='pendiente'";

        try (PreparedStatement stmSolAmis = con.prepareStatement(borrado)) {
            stmSolAmis.setString(1, solicitante);
            stmSolAmis.setString(2, solicitado);
            stmSolAmis.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void eliminarAmigo(String solicitante, String solicitado) {
        Connection con = this.conexion;

        String insercion = "delete from solicitar_amistad where solicitante=? and"
                + " solicitado=? and estado='aceptado'";

        try (PreparedStatement stmSolAmis = con.prepareStatement(insercion)) {
            stmSolAmis.setString(1, solicitante);
            stmSolAmis.setString(2, solicitado);
            stmSolAmis.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modificarContraseña(String usuario, String contraseña) {
        Connection con = this.conexion;

        String modificacion = "update usuarios set contraseña=? where nombre=?";

        try (PreparedStatement stmSolAmis = con.prepareStatement(modificacion)) {
            stmSolAmis.setString(1, contraseña);
            stmSolAmis.setString(2, usuario);
            stmSolAmis.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

}
