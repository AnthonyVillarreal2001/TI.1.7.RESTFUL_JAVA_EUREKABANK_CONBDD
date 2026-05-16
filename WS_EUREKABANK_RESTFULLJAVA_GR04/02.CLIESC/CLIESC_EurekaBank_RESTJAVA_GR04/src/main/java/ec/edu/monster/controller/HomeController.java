package ec.edu.monster.controller;

import ec.edu.monster.model.Movimiento;
import ec.edu.monster.model.OperacionCuentaResponse;
import ec.edu.monster.service.EurekaApi;
import ec.edu.monster.util.Money;
import ec.edu.monster.util.Validation;
import ec.edu.monster.view.HomeView;
import ec.edu.monster.view.MovimientosDialog;

import java.util.List;

public class HomeController {
  private final HomeView view;
  private final EurekaApi api;

  public HomeController(HomeView view, EurekaApi api) {
    this.view = view;
    this.api = api;

    view.setUser(Session.usuario == null ? "USUARIO" : Session.usuario.toUpperCase());

    /* === Consultar movimientos === */
    view.onConsultarMov(cta -> {
      try {
        if (!Validation.isCuentaValida(cta)) {
          view.toastError("Cuenta inválida. Debe tener 8 dígitos.");
          return;
        }
        List<Movimiento> movs = api.traerMovimientos(cta);
        new MovimientosDialog(view, cta, movs).setVisible(true);
      } catch (Exception e) {
        view.toastError("No se pudo obtener movimientos: " + e.getMessage());
      }
    });

    /* === Depósito === */
    view.onDepositar((cta, imp) -> {
      try {
        if (!Validation.isCuentaValida(cta)) {
          view.toastError("Cuenta inválida. Debe tener 8 dígitos.");
          return;
        }
        double val = Validation.parseImportePositivo(imp);

        OperacionCuentaResponse r = api.regDeposito(cta, val);
        if (r != null && r.getEstado() == 1) {
          Session.cuentaFoco = cta;
          Session.saldoActual = r.getSaldo();
          view.setSaldo(Money.fmt(r.getSaldo()));
          view.toastOK("Depósito " + Money.fmt(val) + " realizado. Saldo: " + Money.fmt(r.getSaldo()));
        } else {
          // El servidor no expone el motivo exacto: mostramos causas probables.
          view.toastError("""
              Depósito rechazado por el servidor.
              • Verifique que la cuenta existe y está ACTIVA.
              """);
        }
      } catch (IllegalArgumentException ve) {
        view.toastError(ve.getMessage());
      } catch (Exception e) {
        view.toastError("Error conectando al servicio: " + e.getMessage());
      }
    });

    /* === Retiro === */
    view.onRetirar((cta, imp) -> {
      try {
        if (!Validation.isCuentaValida(cta)) {
          view.toastError("Cuenta inválida. Debe tener 8 dígitos.");
          return;
        }
        double val = Validation.parseImportePositivo(imp);

        OperacionCuentaResponse r = api.regRetiro(cta, val);
        if (r != null && r.getEstado() == 1) {
          Session.cuentaFoco = cta;
          Session.saldoActual = r.getSaldo();
          view.setSaldo(Money.fmt(r.getSaldo()));
          view.toastOK("Retiro " + Money.fmt(val) + " realizado. Saldo: " + Money.fmt(r.getSaldo()));
        } else {
          view.toastError("""
              Retiro rechazado.
              • Posible saldo insuficiente.
              • O bien la cuenta no existe o no está ACTIVA.
              """);
        }
      } catch (IllegalArgumentException ve) {
        view.toastError(ve.getMessage());
      } catch (Exception e) {
        view.toastError("Error conectando al servicio: " + e.getMessage());
      }
    });

    /* === Transferencia === */
    view.onTransferir((o, d, imp) -> {
      try {
        if (!Validation.isCuentaValida(o)) { view.toastError("Cuenta origen inválida (8 dígitos)."); return; }
        if (!Validation.isCuentaValida(d)) { view.toastError("Cuenta destino inválida (8 dígitos)."); return; }
        if (o.equals(d)) { view.toastError("La cuenta origen y destino no pueden ser iguales."); return; }
        double val = Validation.parseImportePositivo(imp);

        OperacionCuentaResponse r = api.regTransferencia(o, d, val);
        if (r != null && r.getEstado() == 1) {
          Session.cuentaFoco = o;
          Session.saldoActual = r.getSaldo(); // saldo de la cuenta ORIGEN
          view.setSaldo(Money.fmt(r.getSaldo()));
          view.toastOK("Transferencia realizada. Saldo origen: " + Money.fmt(r.getSaldo()));
        } else {
          view.toastError("""
              Transferencia rechazada.
              • Posible saldo insuficiente en la cuenta ORIGEN.
              • La cuenta origen o destino no existe o no está ACTIVA.
              """);
        }
      } catch (IllegalArgumentException ve) {
        view.toastError(ve.getMessage());
      } catch (Exception e) {
        view.toastError("Error conectando al servicio: " + e.getMessage());
      }
    });
  }

  public void show() { view.setVisible(true); }
}
