package ec.edu.monster.util;

import javax.swing.JOptionPane;
import java.awt.Component;

public final class Debug {
  public static boolean ENABLED = true;

  public static void log(String msg){
    if (ENABLED) System.out.println("[DEBUG] " + msg);
  }

  public static void popup(Component parent, String title, String msg){
    if (ENABLED) JOptionPane.showMessageDialog(parent, msg, title, JOptionPane.INFORMATION_MESSAGE);
  }
}
