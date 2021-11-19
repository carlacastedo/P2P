package p2p;

import java.sql.Connection;

public abstract class AbstractDAO {

    private final Connection conexion;

    public AbstractDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Connection getConexion() {
        return this.conexion;
    }
}
