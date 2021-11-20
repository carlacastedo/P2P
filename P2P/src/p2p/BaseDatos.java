/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    public void listarUsuarios() {
        Connection con = conexion;
        PreparedStatement stmUsuarios = null;
        ResultSet rsUsuarios;

        String consulta = "select * "
                + "from usuarios";

        try {
            stmUsuarios = con.prepareStatement(consulta);
            rsUsuarios = stmUsuarios.executeQuery();
            while (rsUsuarios.next()) {
                System.out.println(rsUsuarios.getString("nombre") + " " + rsUsuarios.getString("contraseña"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(stmUsuarios!=null){
                stmUsuarios.close();
                }
                
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

}
