package ec.edu.monster.prueba;

import ec.edu.monster.db.AccesoDB; 
import java.sql.Connection; 
import java.sql.SQLException;
/**
 *
 * @author LENOVO
 */
public class PruebaConexionBD {
    
    public static void main(String[] args) { 

        try { 
            Connection cn = AccesoDB.getConnection(); 
            System.out.println("Prueba de conexion exitosa"); 
            cn.close(); 

        } catch (SQLException e) { 
            e.printStackTrace(); 
        } 
    } 
} 
