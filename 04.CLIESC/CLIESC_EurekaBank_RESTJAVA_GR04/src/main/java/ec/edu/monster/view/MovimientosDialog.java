package ec.edu.monster.view;

import ec.edu.monster.model.Movimiento;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MovimientosDialog extends JDialog {
  public MovimientosDialog(JFrame owner, String cuenta, List<Movimiento> data){
    super(owner, "Movimientos de Cuenta", true);
    setSize(820,540);
    setLocationRelativeTo(owner);

    JPanel root = new JPanel(new BorderLayout());
    root.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

    JLabel title = new JLabel("Movimientos de Cuenta", SwingConstants.CENTER);
    title.setFont(title.getFont().deriveFont(Font.BOLD,20f));
    JLabel chip = new JLabel(cuenta, SwingConstants.CENTER);
    chip.setOpaque(true); chip.setBackground(new Color(190,0,0)); chip.setForeground(Color.WHITE);
    chip.setBorder(BorderFactory.createEmptyBorder(6,16,6,16));

    JPanel head = new JPanel(new BorderLayout());
    head.add(title, BorderLayout.NORTH);
    head.add(chip, BorderLayout.CENTER);

    DefaultTableModel m = new DefaultTableModel(
        new Object[]{"NRO","FECHA","TIPO","ACCIÓN","IMPORTE"}, 0) {
      public boolean isCellEditable(int r,int c){ return false; }
    };
    for (Movimiento x: data){
      m.addRow(new Object[]{x.getNromov(), x.getFecha(), x.getTipo(), x.getAccion(), x.getImporte()});
    }
    JTable tbl = new JTable(m);

    if (data.isEmpty()){
      JPanel empty = new JPanel(new BorderLayout());
      empty.add(new JLabel("No hay movimientos registrados para esta cuenta.", SwingConstants.CENTER));
      root.add(head, BorderLayout.NORTH);
      root.add(empty, BorderLayout.CENTER);
    } else {
      root.add(head, BorderLayout.NORTH);
      root.add(new JScrollPane(tbl), BorderLayout.CENTER);
    }
    setContentPane(root);
  }
}
