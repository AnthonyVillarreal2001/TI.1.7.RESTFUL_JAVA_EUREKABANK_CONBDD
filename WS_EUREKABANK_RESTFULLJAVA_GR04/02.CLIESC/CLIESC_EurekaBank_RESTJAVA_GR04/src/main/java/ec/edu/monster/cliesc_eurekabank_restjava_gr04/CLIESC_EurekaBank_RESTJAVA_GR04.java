package ec.edu.monster.cliesc_eurekabank_restjava_gr04;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ec.edu.monster.controller.LoginController;
import ec.edu.monster.view.LoginView;

public class CLIESC_EurekaBank_RESTJAVA_GR04 {

  public static void main(String[] args) {
    // Look & Feel (tema claro FlatLaf)
    FlatLightLaf.setup();
    // Ajustes globales suaves (bordes redondeados, foco, etc.)
    UIManager.put("Component.arc", 20);
    UIManager.put("Button.arc", 22);
    UIManager.put("TextComponent.arc", 20);
    UIManager.put("Component.focusWidth", 2);
    UIManager.put("ScrollBar.showButtons", false);
    UIManager.put("TitlePane.unifiedBackground", true);

    SwingUtilities.invokeLater(() -> {
      LoginController app = new LoginController(new LoginView());
      app.show();
    });
  }
}
