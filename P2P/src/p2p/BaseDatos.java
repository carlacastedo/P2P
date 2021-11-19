/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.*;

/**
 *
 * @author basesdatos
 */
public class BaseDatos {

    private Connection conexion;

    public BaseDatos() {
        conexion = generarConexionBaseDatos();
    }

    public static Connection generarConexionBaseDatos() {
        Properties configuracion = new Properties();
        FileInputStream archivoConfiguracion;
        Connection conexionGenerada = null;

        try {
            archivoConfiguracion = new FileInputStream("baseDatos.properties");
            configuracion.load(archivoConfiguracion);
            archivoConfiguracion.close();

            conexionGenerada = generarConexionDesdePropiedades(configuracion);

        } catch (IOException | SQLException f) {
            System.err.println(f.getMessage());
        }
        return conexionGenerada;
    }

    public static Connection generarConexionDesdePropiedades(Properties configuracion) throws SQLException {
        Properties usuario = new Properties();
        Connection conexionObtenida;

        String urlConexion = generarUrlConexion(configuracion);

        usuario.setProperty("user", configuracion.getProperty("usuario"));
        usuario.setProperty("password", configuracion.getProperty("clave"));

        conexionObtenida = java.sql.DriverManager.getConnection(urlConexion, usuario);

        return conexionObtenida;
    }

    private static String generarUrlConexion(Properties configuracion) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("jdbc:");
        urlBuilder.append(configuracion.getProperty("gestor"));
        urlBuilder.append("://");
        urlBuilder.append(configuracion.getProperty("servidor"));
        urlBuilder.append(":");
        urlBuilder.append(configuracion.getProperty("puerto"));
        urlBuilder.append("/");
        urlBuilder.append(configuracion.getProperty("baseDatos"));

        return urlBuilder.toString();
    }

    public void consultarUsuarios() {
        Connection con = this.conexion;
        PreparedStatement stmUsuarios = null;
        ResultSet rsUsuarios;
        String consulta = "select * from usuarios";
        try {
            stmUsuarios = con.prepareStatement(consulta);
            rsUsuarios = stmUsuarios.executeQuery();
            while (rsUsuarios.next()) {
                System.out.println(rsUsuarios.getString("nombre") + rsUsuarios.getString("contraseña"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                stmUsuarios.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void conseguirAmigos(String usuario) {
        Connection con = this.conexion;

        String consulta = "select solicitante from solicitar_amistad"
                            +"where solicitado=? and estado='aceptado'"
                            +"UNION"
                            +"select solicitado from solicitar_amistad"
                            +"where solicitante=? and estado='aceptado'";

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
}
