package ec.edu.monster.service;

import ec.edu.monster.model.Movimiento;
import ec.edu.monster.model.OperacionCuentaResponse;
import java.util.List;

public interface EurekaApi {
  boolean validarIngreso(String usuario, String password) throws Exception;
  java.util.List<Movimiento> traerMovimientos(String cuenta) throws Exception;
  OperacionCuentaResponse regDeposito(String cuenta, double importe) throws Exception;
  OperacionCuentaResponse regRetiro(String cuenta, double importe) throws Exception;
  OperacionCuentaResponse regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) throws Exception;
}
