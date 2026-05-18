/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.prueba;

import ec.edu.monster.servicio.EurekaService;

/**
 *
 * @author LENOVO
 */
public class PruebaRetiro {
    public static void main(String[] args) {
        try {
            String cuenta = "00100001";
            double importe = 200;
            String codEmp = "0001";

            EurekaService service = new EurekaService();
            service.registrarRetiro(cuenta, importe, codEmp);

            System.out.println("Retiro realizado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


