/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p.servidor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author basesdatos
 */
public class BBDD {

    private Connection conexion;

    public BBDD() {
        super();
        Properties configuracion = new Properties();
        FileInputStream arqConfiguracion;

        //creamos la conexion con la base de datos
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

    public String consultarUsuarios() {
        PreparedStatement stmUsuarios = null;
        ResultSet rsUsuarios;
        String texto = "";
        String consulta = "select nombre from usuarios";
        try {
            stmUsuarios = conexion.prepareStatement(consulta);
            rsUsuarios = stmUsuarios.executeQuery();
            while (rsUsuarios.next()) {
                texto += rsUsuarios.getString("nombre");
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

    public ArrayList<String> consultarNoAmigos(String usuario) {
        ArrayList<String> noAmigos = new ArrayList<>();
        String consulta = "select nombre from usuarios "
                + "EXCEPT "
                + "(select solicitante from solicitar_amistad "
                + "where solicitado=? and estado='aceptado' "
                + "UNION "
                + "select solicitado from solicitar_amistad "
                + "where solicitante=? and estado='aceptado' "
                + "UNION "
                + "select nombre from usuarios "
                + "where nombre=?)";
        try (PreparedStatement stmUsuario = conexion.prepareStatement(consulta)) {
            stmUsuario.setString(1, usuario);
            stmUsuario.setString(2, usuario);
            stmUsuario.setString(3, usuario);
            try (ResultSet rsUsuario = stmUsuario.executeQuery()) {
                while (rsUsuario.next()) {
                    noAmigos.add(rsUsuario.getString("nombre"));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return noAmigos;
    }

    public ArrayList<String> consultarAmigos(String usuario) {
        ArrayList<String> amigos = new ArrayList<>();
        String consulta = "select solicitante from solicitar_amistad "
                + "where solicitado=? and estado='aceptado' "
                + "UNION "
                + "select solicitado from solicitar_amistad "
                + "where solicitante=? and estado='aceptado' ";

        try (PreparedStatement stmUsuario = conexion.prepareStatement(consulta)) {
            stmUsuario.setString(1, usuario);
            stmUsuario.setString(2, usuario);
            try (ResultSet rsUsuario = stmUsuario.executeQuery()) {
                while (rsUsuario.next()) {
                    amigos.add(rsUsuario.getString("solicitante"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return amigos;
    }

    public ArrayList<String> filtrarAmigos(String usuario, String filtro) {
        ArrayList<String> amigos = new ArrayList<>();
        String consulta = "select solicitante from solicitar_amistad "
                + "where solicitado=? and LOWER(solicitante) like LOWER(?) and estado='aceptado' "
                + "UNION "
                + "select solicitado from solicitar_amistad "
                + "where solicitante=? and LOWER(solicitado) like LOWER(?) and estado='aceptado' ";

        try (PreparedStatement stmUsuario = conexion.prepareStatement(consulta)) {
            stmUsuario.setString(1, usuario);
            stmUsuario.setString(2, filtro + "%");
            stmUsuario.setString(3, usuario);
            stmUsuario.setString(4, filtro + "%");
            try (ResultSet rsUsuario = stmUsuario.executeQuery()) {
                while (rsUsuario.next()) {
                    amigos.add(rsUsuario.getString("solicitante"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return amigos;
    }

    public void insertarUsuario(String usuario, String contraseña) {
        String consulta = "insert into usuarios(nombre, contraseña) "
                + "values(?, ?)";
        try (PreparedStatement stmUsuario = conexion.prepareStatement(consulta)) {
            stmUsuario.setString(1, usuario);
            stmUsuario.setString(2, contraseña);
            stmUsuario.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<String> consultarSolicitudes(String solicitado) {
        ArrayList<String> solicitudes = new ArrayList<>();
        String consulta = "select solicitante from solicitar_amistad "
                + "where solicitado=? and estado='pendiente'";

        try (PreparedStatement stmUsuario = conexion.prepareStatement(consulta)) {
            stmUsuario.setString(1, solicitado);
            try (ResultSet rsUsuario = stmUsuario.executeQuery()) {
                while (rsUsuario.next()) {
                    solicitudes.add(rsUsuario.getString("solicitante"));
                }
            }
        } catch (SQLException e) {
            System.err.println(consulta + "\n" + e.getMessage());
        }
        return solicitudes;
    }

    public void enviarSolicitud(String solicitante, String solicitado) {
        String consulta = "insert into solicitar_amistad(solicitante, solicitado, estado) "
                + "values(?, ?, 'pendiente')";

        try (PreparedStatement stmSolAmis = conexion.prepareStatement(consulta)) {
            stmSolAmis.setString(1, solicitante);
            stmSolAmis.setString(2, solicitado);
            stmSolAmis.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void aceptarSolicitud(String solicitante, String solicitado) {
        String modificacion = "update solicitar_amistad set estado='aceptado' where solicitante=? and"
                + " solicitado=?";

        try (PreparedStatement stmSolAmis = conexion.prepareStatement(modificacion)) {
            stmSolAmis.setString(1, solicitante);
            stmSolAmis.setString(2, solicitado);
            stmSolAmis.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void denegarSolicitud(String solicitante, String solicitado) {
        String borrado = "delete from solicitar_amistad where solicitante=? and"
                + " solicitado=? and estado='pendiente'";

        try (PreparedStatement stmSolAmis = conexion.prepareStatement(borrado)) {
            stmSolAmis.setString(1, solicitante);
            stmSolAmis.setString(2, solicitado);
            stmSolAmis.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void eliminarAmigo(String solicitante, String solicitado) {
        String insercion = "delete from solicitar_amistad where solicitante=? and"
                + " solicitado=? and estado='aceptado'";

        try (PreparedStatement stmSolAmis = conexion.prepareStatement(insercion)) {
            stmSolAmis.setString(1, solicitante);
            stmSolAmis.setString(2, solicitado);
            stmSolAmis.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void modificarContraseña(String usuario, String contraseña) {
        String modificacion = "update usuarios set contraseña=? where nombre=?";

        try (PreparedStatement stmSolAmis = conexion.prepareStatement(modificacion)) {
            stmSolAmis.setString(1, contraseña);
            stmSolAmis.setString(2, usuario);
            stmSolAmis.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public Boolean autenticarUsuario(String usuario, String contraseña) throws java.rmi.RemoteException {
        Boolean autenticado = false;
        String consulta = "select from usuarios where nombre=? and contraseña=?";

        try (PreparedStatement stmUsuario = conexion.prepareStatement(consulta)) {
            stmUsuario.setString(1, usuario);
            stmUsuario.setString(2, contraseña);
            try (ResultSet rsUsuario = stmUsuario.executeQuery()) {
                if (rsUsuario.next()) {
                    autenticado = true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return autenticado;
    }
}
