package p2p;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class GeneradorConexion {

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

}
