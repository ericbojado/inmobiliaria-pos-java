package inmobiliaria.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {

    private String rutaDB = "src/inmobiliaria.bd/Inmobiliaria_BD.accdb";
    private String url = "jdbc:ucanaccess://" + rutaDB;
    private Connection conexion = null;

    //Abrir la conexión
    public Connection conectar() {
        try {
        	//Driver a utilizar
        	Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        	
            conexion = DriverManager.getConnection(url);
            
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        return conexion;
    }

    //Enviar sentencias
    public void ejecutarSentencia(String sql) {
        try {
        	
        	Statement sentencia = conexion.createStatement();
        	
        	sentencia.executeUpdate(sql);
            System.out.println("Operación realizada con éxito.");
            
        } catch (SQLException e) {
            System.err.println("Error al ejecutar sentencia: " + e.getMessage());
        }
    }

    //Recuperar información (SELECT)
    /*ESTA PARTE ESTÁ EN PRUEBA
    public void consultar(String sql) {
        try {
        	
        	Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID") + " | Nombre: " + rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }
    }*/

    //Cerrar la conexión
    public void cerrar() {
        try {
        	//Validar que la conexion este abierta y no se haya cerrado
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar: " + e.getMessage());
        }
    }
}
