package ec.edu.monster.prueba;

import ec.edu.monster.servicio.EurekaService; 
/**
 *
 * @author LENOVO
 */
public class PruebaDeposito {
    
    public static void main(String[] args) { 

        try { 

            //datos 
            String cuenta = "00100001"; 
            double importe = 400; 
            String codEmp = "0001"; 
            //proceso 
            EurekaService service = new EurekaService(); 
            service.registrarDeposito(cuenta, importe, codEmp); 
            System.out.println("Proceso ok"); 

        } catch (Exception e) { 
            e.printStackTrace();     
        }   
    }}
 
