package ec.edu.monster.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/** Tipo que devuelve el servidor dentro del elemento <resultado> */
@XmlRootElement(name = "OperacionCuentaResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class OperacionCuentaResponse {
  private int estado;   // 1 OK, -1 error
  private double saldo; // saldo actualizado

  public int getEstado() { return estado; }
  public void setEstado(int estado) { this.estado = estado; }
  public double getSaldo() { return saldo; }
  public void setSaldo(double saldo) { this.saldo = saldo; }
}
