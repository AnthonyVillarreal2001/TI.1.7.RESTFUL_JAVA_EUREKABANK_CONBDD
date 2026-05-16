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
public class PruebaTransferencia {
    public static void main(String[] args) {
        try {
            String cuentaOrigen = "00100001";
            String cuentaDestino = "00100002";
            double importe = 100;
            String codEmp = "0001";
            EurekaService service = new EurekaService();
            service.registrarTransferencia(cuentaOrigen, cuentaDestino, importe, codEmp);
            System.out.println("Transferencia realizada con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
