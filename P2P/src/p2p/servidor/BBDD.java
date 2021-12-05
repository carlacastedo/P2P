package p2p.servidor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class BBDD {
    //Conexión con la base de datos
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

    //Método que devuelve los no amigos de usuario que cumplen el criterio de búsqueda
    public ArrayList<String> consultarNoAmigos(String usuario, String busqueda) {
        ArrayList<String> noAmigos = new ArrayList<>();
        
        //Se busca a todos los usuarios excepto a los que son amigos o tienen una solicitud
        //de amistad pendiente con el usuario y exceptuando también al propio usuario
        String consulta = "select nombre from usuarios where LOWER(nombre) like LOWER(?) "
                + "EXCEPT "
                + "(select solicitante from solicitar_amistad "
                + "where solicitado=? and LOWER(solicitante) like LOWER(?) "
                + "UNION "
                + "select solicitado from solicitar_amistad "
                + "where solicitante=? and LOWER(solicitado) like LOWER(?) "
                + "UNION "
                + "select nombre from usuarios "
                + "where nombre=?)";
        try (PreparedStatement stmUsuario = conexion.prepareStatement(consulta)) {
            stmUsuario.setString(1, busqueda + "%");
            stmUsuario.setString(2, usuario);
            stmUsuario.setString(3, busqueda + "%");
            stmUsuario.setString(4, usuario);
            stmUsuario.setString(5, busqueda + "%");
            stmUsuario.setString(6, usuario);
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

    //Método que devuelve una lista con los amigos de un determinado usuario
    public ArrayList<String> consultarAmigos(String usuario) {
        ArrayList<String> amigos = new ArrayList<>();
        
        //Se busca a todas las entradas de la tabla solicitar_amistad donde
        //el usuario es parte de la clave primaria y es estado es aceptada
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

    //Método que devuelve una lista de los amigos del usuario que cumplen un 
    //determinado filtro
    public ArrayList<String> filtrarAmigos(String usuario, String filtro) {
        ArrayList<String> amigos = new ArrayList<>();
        
        //Buscamos los usuarios que tienen una solicitud aceptada con el usuario
        //filtrando por sus nombres
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

    //Método que inserta un nuevo usuario en la base de datos
    public void insertarUsuario(String usuario, String contraseña) {
        //Se inserta el usuario introduciendo para ello los campos requeridos
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

    //Método que devuelve una lista de solicitudes de amistad que el usuario tiene
    //pendientes de aceptar o rechazar
    public ArrayList<String> consultarSolicitudes(String solicitado) {
        ArrayList<String> solicitudes = new ArrayList<>();
        
        //Seleccionamos todos los usuarios con una solicitud donde el 
        //solicitante es el usuario y el estado es pendiente
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
    
    //Método que devuelve una lista de solicitudes de amistad que el usuario tiene
    //pendientes de aceptar o rechazar
    public ArrayList<String> consultarSolicitudesEnviadas(String solicitante) {
        ArrayList<String> solicitudes = new ArrayList<>();
        
        //Seleccionamos todos los usuarios con una solicitud donde el 
        //solicitante es el usuario y el estado es pendiente
        String consulta = "select solicitado from solicitar_amistad "
                + "where solicitante=? and estado='pendiente'";

        try (PreparedStatement stmUsuario = conexion.prepareStatement(consulta)) {
            stmUsuario.setString(1, solicitante);
            try (ResultSet rsUsuario = stmUsuario.executeQuery()) {
                while (rsUsuario.next()) {
                    solicitudes.add(rsUsuario.getString("solicitado"));
                }
            }
        } catch (SQLException e) {
            System.err.println(consulta + "\n" + e.getMessage());
        }
        return solicitudes;
    }

    //Método que registra una solicitud de amistad entre dos personas
    public void enviarSolicitud(String solicitante, String solicitado) {
        //Intertamos el registro de la solicitud indicando para ello, quien ha solicitado
        //amistad y a quien, y fijamos el estado a pendiente
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

    //Método que acepta una solicitud que el usuario tiene pendiente
    public void aceptarSolicitud(String solicitante, String solicitado) {
        //Se busca la solicitud indicada y se cambia pendiente por aceptada
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

    //Método que elimina una solicitud de amistad al haber sido esta denegada por
    //el usuario solicitado
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

    //Método que elimina la amistad entre dos usuarios
    public void eliminarAmigo(String solicitante, String solicitado) {
        //Se elimina el registro de la solicitud de amistad entre los dos usuarios
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

    //Método que modifica la contraseña de un usuario
    public void modificarContraseña(String usuario, String contraseña) {
        //Se cambia el valor de la contraseña del usuario 
        String modificacion = "update usuarios set contraseña=? where nombre=?";

        try (PreparedStatement stmSolAmis = conexion.prepareStatement(modificacion)) {
            stmSolAmis.setString(1, contraseña);
            stmSolAmis.setString(2, usuario);
            stmSolAmis.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    //Método que comprueba si un usario está en la base de datos y si ha introducido correctamente
    //su contraseña
    public Boolean autenticarUsuario(String usuario, String contraseña) throws java.rmi.RemoteException {
        Boolean autenticado = false;
        //Buscamos un usuario con el nombre y la contraseña dados, si se encuentra
        //entonces se ha autenticado al usuario
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
