package ec.edu.monster.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import ec.edu.monster.util.Assets;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class HomeView extends JFrame {

  /* callbacks */
  private Consumer<String> onConsultarMov;
  private BiConsumer<String,String> onDepositar;
  private BiConsumer<String,String> onRetirar;
  private TriConsumer<String,String,String> onTransferir;

  /* header */
  private final JButton btnUserChip = new JButton("USER");
  private final JButton btnLogout   = new JButton("Cerrar Sesión");

  /* campos */
  private final JTextField txtMovCuenta = new JTextField();

  private final JTextField txtDepCuenta = new JTextField();
  private final JTextField txtDepImporte = new JTextField();

  private final JTextField txtRetCuenta = new JTextField();
  private final JTextField txtRetImporte = new JTextField();

  private final JTextField txtTrfOrigen = new JTextField();
  private final JTextField txtTrfDestino = new JTextField();
  private final JTextField txtTrfImporte = new JTextField();

  private final JButton btnMov = new JButton("VER MOVIMIENTOS");
  private final JButton btnDep = new JButton("DEPOSITAR");
  private final JButton btnRet = new JButton("RETIRAR");
  private final JButton btnTrf = new JButton("TRANSFERIR");

  public HomeView() {
    setTitle("EurekaBank | Operaciones");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setMinimumSize(new Dimension(1120, 640));
    setLocationRelativeTo(null);
    setContentPane(buildRoot());
    wireActions();
    pack();
  }

  /* API */
  public void setUser(String name){
    btnUserChip.setText((name==null||name.isBlank())? "USUARIO" : name.trim());
  }
  public void onConsultarMov(Consumer<String> h){ this.onConsultarMov = h; }
  public void onDepositar(BiConsumer<String,String> h){ this.onDepositar = h; }
  public void onRetirar(BiConsumer<String,String> h){ this.onRetirar = h; }
  public void onTransferir(TriConsumer<String,String,String> h){ this.onTransferir = h; }
  public void setSaldo(String ignored) { /* no-op */ }
  public void toastOK(String msg){ JOptionPane.showMessageDialog(this, msg, "Operación exitosa", JOptionPane.INFORMATION_MESSAGE); }
  public void toastError(String msg){ JOptionPane.showMessageDialog(this, msg, "Error en la operación", JOptionPane.ERROR_MESSAGE); }

  /* UI */
  private JComponent buildRoot(){
    JPanel root = new GradientBackground();
    root.setLayout(new BorderLayout());
    root.add(buildHeader(), BorderLayout.NORTH);
    root.add(buildCards(), BorderLayout.CENTER);
    return root;
  }

  private JComponent buildHeader(){
    JPanel bar = new JPanel(new BorderLayout()){
      @Override protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(new GradientPaint(0,0,new Color(255,255,255,240),
                                      0,getHeight(),new Color(255,255,255,220)));
        g2.fillRect(0,0,getWidth(),getHeight());
        g2.dispose();
      }
    };
    bar.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(200,0,0)));

    bar.add(buildBrandLeft(), BorderLayout.WEST);

    JPanel right = new JPanel();
    right.setOpaque(false);
    styleUserChip(btnUserChip);
    styleLogout(btnLogout);
    right.add(btnUserChip);
    right.add(btnLogout);
    bar.add(right, BorderLayout.EAST);
    return bar;
  }

  private JComponent buildBrandLeft() {
    JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
    left.setOpaque(false);

    JLabel logo = new JLabel();
    Icon ic = Assets.brandHeader(38);
    if (ic != null) logo.setIcon(ic);

    JLabel title = new JLabel("EurekaBank");
    title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
    title.setForeground(new Color(211,37,43));

    JLabel tagline = new JLabel("Tu banco, tu equipo.");
    tagline.setFont(tagline.getFont().deriveFont(Font.PLAIN, 12f));
    tagline.setForeground(new Color(125,125,125));

    JPanel text = new JPanel(new GridLayout(2,1,0,0));
    text.setOpaque(false);
    text.add(title);
    text.add(tagline);

    left.add(logo);
    left.add(text);
    return left;
  }

  private JComponent buildCards(){
    JPanel grid = new JPanel(new GridBagLayout());
    grid.setOpaque(false);

    GridBagConstraints gc = new GridBagConstraints();
    gc.gridx = 0; gc.gridy = 0; gc.insets = new Insets(24,24,24,24);
    gc.fill = GridBagConstraints.BOTH; gc.weightx = 1; gc.weighty = 1;

    grid.add(makeCard("Consultar Movimientos", IconType.BARS, buildMovForm()), gc);
    gc.gridx++;
    grid.add(makeCard("Depósito", IconType.MONEY, buildDepForm()), gc);
    gc.gridx++;
    grid.add(makeCard("Retiro", IconType.WALLET, buildRetForm()), gc);
    gc.gridx++;
    grid.add(makeCard("Transferencia", IconType.CARD, buildTrfForm()), gc);

    return grid;
  }

  private JComponent buildMovForm(){
    JPanel p = new JPanel(new GridBagLayout()); p.setOpaque(false);
    GridBagConstraints gc = new GridBagConstraints();
    gc.gridx = 0; gc.weightx = 1; gc.fill = GridBagConstraints.HORIZONTAL; gc.insets = new Insets(6,0,6,0);
    styleField(txtMovCuenta, "Número de cuenta");
    stylePrimary(btnMov);

    gc.gridy = 0; p.add(txtMovCuenta, gc);
    gc.gridy = 1; gc.insets = new Insets(14,0,0,0); p.add(btnMov, gc);
    return wrapBottom(p);
  }

  private JComponent buildDepForm(){
    JPanel p = new JPanel(new GridBagLayout()); p.setOpaque(false);
    GridBagConstraints gc = new GridBagConstraints();
    gc.gridx = 0; gc.weightx = 1; gc.fill = GridBagConstraints.HORIZONTAL; gc.insets = new Insets(6,0,6,0);
    styleField(txtDepCuenta, "Cuenta");
    styleField(txtDepImporte, "Importe");
    stylePrimary(btnDep);

    gc.gridy = 0; p.add(txtDepCuenta, gc);
    gc.gridy = 1; p.add(txtDepImporte, gc);
    gc.gridy = 2; gc.insets = new Insets(14,0,0,0); p.add(btnDep, gc);
    return wrapBottom(p);
  }

  private JComponent buildRetForm(){
    JPanel p = new JPanel(new GridBagLayout()); p.setOpaque(false);
    GridBagConstraints gc = new GridBagConstraints();
    gc.gridx = 0; gc.weightx = 1; gc.fill = GridBagConstraints.HORIZONTAL; gc.insets = new Insets(6,0,6,0);
    styleField(txtRetCuenta, "Cuenta");
    styleField(txtRetImporte, "Importe");
    stylePrimary(btnRet);

    gc.gridy = 0; p.add(txtRetCuenta, gc);
    gc.gridy = 1; p.add(txtRetImporte, gc);
    gc.gridy = 2; gc.insets = new Insets(14,0,0,0); p.add(btnRet, gc);
    return wrapBottom(p);
  }

  private JComponent buildTrfForm(){
    JPanel p = new JPanel(new GridBagLayout()); p.setOpaque(false);
    GridBagConstraints gc = new GridBagConstraints();
    gc.gridx = 0; gc.weightx = 1; gc.fill = GridBagConstraints.HORIZONTAL; gc.insets = new Insets(6,0,6,0);
    styleField(txtTrfOrigen, "Cuenta origen");
    styleField(txtTrfDestino, "Cuenta destino");
    styleField(txtTrfImporte, "Importe");
    stylePrimary(btnTrf);

    gc.gridy = 0; p.add(txtTrfOrigen, gc);
    gc.gridy = 1; p.add(txtTrfDestino, gc);
    gc.gridy = 2; p.add(txtTrfImporte, gc);
    gc.gridy = 3; gc.insets = new Insets(14,0,0,0); p.add(btnTrf, gc);
    return wrapBottom(p);
  }

  private JComponent makeCard(String title, IconType icon, JComponent bottom){
    JPanel card = new JPanel(new BorderLayout());
    card.setOpaque(false);

    CardHeader header = new CardHeader(title, icon);
    header.setPreferredSize(new Dimension(260, 240));
    card.add(header, BorderLayout.CENTER);

    card.add(bottom, BorderLayout.SOUTH);
    return card;
  }

  private JPanel wrapBottom(JComponent content){
    JPanel panel = new JPanel(new BorderLayout()){
      @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int arc = UIScale.scale(18);
        g2.setColor(new Color(255,255,255,230));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        g2.dispose();
        setOpaque(false);
      }
    };
    panel.setBorder(BorderFactory.createEmptyBorder(14,16,16,16));
    panel.setOpaque(false);
    panel.add(content, BorderLayout.CENTER);
    return panel;
  }

  private void styleField(JTextField f, String placeholder){
    f.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
    f.putClientProperty(FlatClientProperties.STYLE, "arc:18; margin:4,10,4,10; focusWidth:2;");
    f.setColumns(16);
  }

  private void stylePrimary(JButton b){
    b.putClientProperty(FlatClientProperties.STYLE,
        "arc:22; background:#B51217; foreground: white; " +
        "hoverBackground:#A50F14; pressedBackground:#8E0D11; " +
        "borderWidth:0; innerFocusWidth:0;");
    b.setFont(b.getFont().deriveFont(Font.BOLD, 13f));
    b.setMargin(new Insets(8, 10, 8, 10));
  }

  private void styleUserChip(JButton b){
    b.putClientProperty(FlatClientProperties.STYLE,
        "arc:999; background:#1E5CCB; foreground: white; " +
        "hoverBackground:#184EA9; pressedBackground:#143F88; borderWidth:0;");
    b.setFocusable(false);
  }

  private void styleLogout(JButton b){
    b.setText("Cerrar Sesión");
    b.putClientProperty(FlatClientProperties.STYLE,
        "arc:999; background:#B51217; foreground:white; " +
        "hoverBackground:#A50F14; pressedBackground:#8E0D11; borderWidth:0;");
    b.setFocusable(false);
    b.addActionListener(e -> dispose());
  }

  private void wireActions(){
    btnMov.addActionListener(e -> { if (onConsultarMov != null) onConsultarMov.accept(txtMovCuenta.getText().trim()); });
    btnDep.addActionListener(e -> { if (onDepositar != null) onDepositar.accept(txtDepCuenta.getText().trim(), txtDepImporte.getText().trim()); });
    btnRet.addActionListener(e -> { if (onRetirar != null) onRetirar.accept(txtRetCuenta.getText().trim(), txtRetImporte.getText().trim()); });
    btnTrf.addActionListener(e -> { if (onTransferir != null) onTransferir.accept(
        txtTrfOrigen.getText().trim(), txtTrfDestino.getText().trim(), txtTrfImporte.getText().trim()); });
  }

  /* custom painting */

  static class GradientBackground extends JPanel {
    @Override protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g.create();
      int w = getWidth(), h = getHeight();
      g2.setPaint(new GradientPaint(0,0,new Color(6,28,58), 0,h,new Color(12,53,109)));
      g2.fillRect(0,0,w,h);
      g2.setComposite(AlphaComposite.SrcOver.derive(.14f));
      g2.setColor(new Color(90,120,210));
      g2.fillOval((int)(w*.12),(int)(h*.22), UIScale.scale(200), UIScale.scale(200));
      g2.fillOval((int)(w*.74),(int)(h*.26), UIScale.scale(140), UIScale.scale(140));
      g2.dispose();
    }
  }

  enum IconType { BARS, MONEY, WALLET, CARD }

  static class CardHeader extends JComponent {
    private final String title; private final IconType type;
    CardHeader(String title, IconType type){ this.title=title; this.type=type; }
    @Override protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      int w=getWidth(), h=getHeight(), arc=UIScale.scale(22);

      GradientPaint gp = new GradientPaint(0,0,new Color(204,0,0), 0,h,new Color(11,64,135));
      g2.setPaint(gp);
      g2.fillRoundRect(0,0,w,h,arc,arc);

      g2.setColor(new Color(255,255,255,230));
      g2.setFont(getFont().deriveFont(Font.BOLD, 14f));
      FontMetrics fm = g2.getFontMetrics();
      int tw = fm.stringWidth(title);
      g2.drawString(title, (w - tw)/2, h - UIScale.scale(18));

      g2.setStroke(new BasicStroke(UIScale.scale(4f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
      g2.setColor(Color.WHITE);
      int cx = w/2, cy = h/2 - UIScale.scale(16);
      switch (type){
        case BARS -> {
          int bw = UIScale.scale(12), space = UIScale.scale(8);
          g2.drawRoundRect(cx - bw*2 - space, cy - bw, bw, bw*2, 6,6);
          g2.drawRoundRect(cx - bw/2,         cy - bw*2, bw, bw*3, 6,6);
          g2.drawRoundRect(cx + bw + space,   cy - bw/2, bw, bw*2, 6,6);
        }
        case MONEY -> {
          int rw = UIScale.scale(60), rh = UIScale.scale(40);
          g2.drawRoundRect(cx - rw/2, cy - rh/2, rw, rh, 10,10);
          g2.drawLine(cx, cy - UIScale.scale(12), cx, cy + UIScale.scale(12));
          g2.drawArc(cx - UIScale.scale(10), cy - UIScale.scale(8), UIScale.scale(20), UIScale.scale(16), 310, 220);
        }
        case WALLET -> {
          int rw = UIScale.scale(64), rh = UIScale.scale(38);
          g2.drawRoundRect(cx - rw/2, cy - rh/2, rw, rh, 12,12);
          g2.drawRoundRect(cx + UIScale.scale(10), cy - UIScale.scale(10), UIScale.scale(22), UIScale.scale(20), 8,8);
        }
        case CARD -> {
          int rw = UIScale.scale(70), rh = UIScale.scale(42);
          g2.drawRoundRect(cx - rw/2, cy - rh/2, rw, rh, 10,10);
          g2.drawLine(cx - rw/2 + UIScale.scale(6), cy - rh/2 + UIScale.scale(14),
                      cx + rw/2 - UIScale.scale(6), cy - rh/2 + UIScale.scale(14));
          g2.drawLine(cx - rw/2 + UIScale.scale(18), cy + rh/2 - UIScale.scale(12),
                      cx + rw/2 - UIScale.scale(18), cy + rh/2 - UIScale.scale(12));
        }
      }
      g2.dispose();
    }
  }

  @FunctionalInterface
  public interface TriConsumer<A,B,C> { void accept(A a, B b, C c); }
}
