package ec.edu.monster.view;

import ec.edu.monster.model.Movimiento;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
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
    // custom renderer: show badges for TIPO and color rows by type
    tbl.setDefaultRenderer(Object.class, new TableCellRenderer(){
      private final DefaultTableCellRenderer def = new DefaultTableCellRenderer();
      @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component c = def.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String tipo = String.valueOf(table.getModel().getValueAt(row, 2));
        Color bg = switchTipoColor(tipo);
        if (!isSelected) c.setBackground(bg);
        return c;
      }
    });
    tbl.getColumnModel().getColumn(2).setCellRenderer(new TipoBadgeRenderer());

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

  private static Color switchTipoColor(String tipo){
    if (tipo == null) return Color.WHITE;
    String t = tipo.toLowerCase();
    if (t.contains("dep") || t.contains("ing")) return new Color(220, 255, 230);
    if (t.contains("ret" ) || t.contains("sal")) return new Color(255, 230, 230);
    if (t.contains("tr") || t.contains("tra")) return new Color(240, 245, 255);
    return Color.WHITE;
  }

  static class TipoBadgeRenderer extends JLabel implements TableCellRenderer {
    TipoBadgeRenderer(){ setOpaque(true); setHorizontalAlignment(SwingConstants.CENTER); setBorder(BorderFactory.createEmptyBorder(6,10,6,10)); }
    @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
      String tipo = String.valueOf(value);
      setText(tipo);
      Color bg = switchTipoColor(tipo);
      Color fg = Color.DARK_GRAY;
      if (tipo != null && (tipo.toLowerCase().contains("dep") || tipo.toLowerCase().contains("ing"))) { bg = new Color(46,204,113); fg = Color.WHITE; }
      if (tipo != null && (tipo.toLowerCase().contains("ret") || tipo.toLowerCase().contains("sal"))) { bg = new Color(231,76,60); fg = Color.WHITE; }
      if (tipo != null && (tipo.toLowerCase().contains("tr") || tipo.toLowerCase().contains("tra"))) { bg = new Color(230,126,34); fg = Color.WHITE; }
      setBackground(bg);
      setForeground(fg);
      return this;
    }
  }
}
