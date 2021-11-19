package p2p;

import java.sql.Connection;
import java.sql.SQLException;

public class FachadaBaseDatos {

    // Esta clase sigue el patrón Singleton
    private static FachadaBaseDatos instancia;

    private final Connection conexion;
    
    public static FachadaBaseDatos getInstance() {
        if (instancia == null) {
            instancia = new FachadaBaseDatos();
            }
        return instancia;
    }

    private FachadaBaseDatos() {
        conexion = GeneradorConexion.generarConexionBaseDatos();
    }
    
    // Otros
    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("No se pudo cerrar la conexión a la BDD correctamente.");
        }
    }

    

    

    

    

    
    
    

    

    
}
