package ec.edu.monster.modelo;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OperacionCuentaResponse")
public class OperacionCuentaResponse {

    private int estado;
    private double saldo;

    public OperacionCuentaResponse() {
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
