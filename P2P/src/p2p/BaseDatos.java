/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 *
 * @author basesdatos
 */
public class BaseDatos {

    private Connection conexion;

    public BaseDatos() {
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
