package ec.edu.monster.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import ec.edu.monster.util.Assets;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.function.BiConsumer;

public class LoginView extends JFrame {

  private final JTextField txtUser = new JTextField();
  private final JPasswordField txtPass = new JPasswordField();
  private final JButton btnLogin = new JButton("INGRESAR");
  private final JLabel lblError = new JLabel();

  private BiConsumer<String,String> onLogin;

  public LoginView() {
    setTitle("EurekaBank | Ingreso");
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setMinimumSize(new Dimension(900, 540));
    setLocationRelativeTo(null);
    setContentPane(buildRoot());
    pack();
  }

  /* ===== UI ===== */

  private JComponent buildRoot() {
    BackgroundPanel root = new BackgroundPanel();
    root.setLayout(new BorderLayout());
    root.add(buildHeader(), BorderLayout.NORTH);
    root.add(buildCenterCard(), BorderLayout.CENTER);
    return root;
  }

  private JComponent buildHeader() {
    JPanel header = new JPanel(new BorderLayout()){
      @Override protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(new GradientPaint(0,0,new Color(255,255,255,240),
                                      0,getHeight(),new Color(255,255,255,220)));
        g2.fillRect(0,0,getWidth(),getHeight());
        g2.dispose();
      }
    };
    header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200,0,0)));
    header.add(buildBrandLeft(), BorderLayout.WEST);
    return header;
  }

  private JComponent buildBrandLeft() {
    JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
    left.setOpaque(false);

    // logo (si no existe, se muestra solo el texto)
    JLabel logo = new JLabel();
    Icon ic = Assets.brandHeader(34); // carga /img/brand_header.png
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

  private JComponent buildCenterCard() {
    JPanel wrap = new JPanel(new GridBagLayout());
    wrap.setOpaque(false);

    JPanel card = new RoundedCard();
    card.setLayout(new GridBagLayout());
    card.setOpaque(false);
    card.setBorder(BorderFactory.createEmptyBorder(28, 36, 28, 36));

    GridBagConstraints gc = new GridBagConstraints();
    gc.gridx = 0; gc.gridy = 0; gc.weightx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
    gc.insets = new Insets(0,0,18,0);
    card.add(buildCardHeader(), gc);

    gc.gridy++;
    card.add(buildForm(), gc);

    wrap.add(card);
    return wrap;
  }

  private JComponent buildCardHeader() {
    JPanel p = new JPanel(new BorderLayout());
    p.setOpaque(false);

    JComponent avatar = new CircleAvatar(new Color(211, 37, 43));
    avatar.setPreferredSize(new Dimension(72,72));
    JPanel avatarWrap = new JPanel();
    avatarWrap.setOpaque(false);
    avatarWrap.add(avatar);

    JLabel title = new JLabel("Ingreso al sistema", SwingConstants.CENTER);
    title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
    title.setForeground(new Color(180,0,0));

    p.add(avatarWrap, BorderLayout.NORTH);
    p.add(Box.createVerticalStrut(8), BorderLayout.CENTER);

    JPanel tWrap = new JPanel(new BorderLayout());
    tWrap.setOpaque(false);
    tWrap.add(title, BorderLayout.CENTER);
    p.add(tWrap, BorderLayout.SOUTH);

    return p;
  }

  private JComponent buildForm() {
    JPanel form = new JPanel();
    form.setOpaque(false);
    form.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();
    gc.gridx = 0; gc.weightx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
    gc.insets = new Insets(6, 0, 6, 0);

    styleField(txtUser, "Usuario");
    styleField(txtPass, "Contraseña");
    styleButton(btnLogin);

    lblError.setForeground(new Color(231,76,60));
    lblError.setVisible(false);
    lblError.setHorizontalAlignment(SwingConstants.CENTER);

    // Enter = login
    ActionListener al = e -> fireLogin();
    txtUser.addActionListener(al);
    txtPass.addActionListener(al);
    btnLogin.addActionListener(al);

    gc.gridy = 0; form.add(txtUser, gc);
    gc.gridy = 1; form.add(txtPass, gc);
    gc.gridy = 2; form.add(lblError, gc);
    gc.gridy = 3; gc.insets = new Insets(16, 0, 0, 0); form.add(btnLogin, gc);

    return form;
  }

  private void styleField(JTextField field, String placeholder) {
    field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
    field.putClientProperty(FlatClientProperties.STYLE,
        "arc:50; margin:6,14,6,14; focusWidth:2; background: #FFFFFFEE; ");
    field.setColumns(22);
  }

  private void styleButton(JButton b) {
    b.putClientProperty(FlatClientProperties.STYLE,
        "arc:28; background:#e67e22; foreground: #FFFFFF; " +
        "hoverBackground:#f39c12; pressedBackground:#d46f12; " +
        "borderWidth:0; innerFocusWidth:0;");
    b.setFont(b.getFont().deriveFont(Font.BOLD, 14f));
    b.setMargin(new Insets(10,10,10,10));
  }

  /* ===== Callbacks ===== */

  private void fireLogin() {
    if (onLogin != null) {
      String u = txtUser.getText().trim();
      String p = new String(txtPass.getPassword());
      onLogin.accept(u, p);
    }
  }

  public void onLogin(BiConsumer<String,String> handler) { this.onLogin = handler; }

  public void showError(String msg) {
    lblError.setText(msg);
    lblError.setVisible(true);
    revalidate();
    repaint();
  }

  public void clearError(){ lblError.setVisible(false); lblError.setText(""); }

  /* ===== Custom painting ===== */

  static class GradientBackground extends JPanel {
    @Override protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g.create();
      int w = getWidth(), h = getHeight();
      g2.setPaint(new GradientPaint(0,0,new Color(6,28,58), 0,h,new Color(12,53,109)));
      g2.fillRect(0,0,w,h);

      g2.setComposite(AlphaComposite.SrcOver.derive(.15f));
      g2.setColor(new Color(90,120,210));
      g2.fillOval((int)(w*0.12), (int)(h*0.18), UIScale.scale(180), UIScale.scale(180));
      g2.fillOval((int)(w*0.70), (int)(h*0.22), UIScale.scale(130), UIScale.scale(130));
      g2.dispose();
    }
  }

  /** Background that paints sullivan.JPG + dark overlay */
  static class BackgroundPanel extends JPanel {
    private BufferedImage bg;
    BackgroundPanel(){ try{ loadBg(); }catch(Exception ignored){} }
    private void loadBg(){
      try{
        URL u = Assets.class.getResource("/ec/edu/monster/util/img/fondo.jpeg");
        if (u == null) u = Assets.class.getResource("/ec/edu/monster/util/img/fondo.jpg");
        if (u != null) bg = ImageIO.read(u);
      } catch(IOException ex){ System.err.println("No se pudo cargar sullivan.JPG: "+ex.getMessage()); }
    }
    @Override protected void paintComponent(Graphics g){
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g.create();
      int w = getWidth(), h = getHeight();
      if (bg != null){
        double scale = Math.max((double)w / bg.getWidth(), (double)h / bg.getHeight());
        int iw = (int)(bg.getWidth()*scale), ih = (int)(bg.getHeight()*scale);
        int x = (w-iw)/2, y = (h-ih)/2;
        g2.drawImage(bg, x, y, iw, ih, null);
      } else {
        g2.setPaint(new GradientPaint(0,0,new Color(10,61,98),0,h,new Color(12,53,109)));
        g2.fillRect(0,0,w,h);
      }
      g2.setColor(new Color(0,0,0,160));
      g2.fillRect(0,0,w,h);
      g2.dispose();
    }
  }

  static class RoundedCard extends JPanel {
    @Override protected void paintComponent(Graphics g) {
      int arc = UIScale.scale(24);
      int shadow = UIScale.scale(12);
      int x = shadow, y = shadow, w = getWidth() - shadow*2, h = getHeight() - shadow*2;

      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      g2.setColor(new Color(0,0,0,40));
      g2.fillRoundRect(x, y, w, h, arc, arc);

      g2.setColor(Color.WHITE);
      g2.fillRoundRect(x - UIScale.scale(4), y - UIScale.scale(6), w, h, arc, arc);
      g2.dispose();
      super.paintComponent(g);
      setOpaque(false);
    }
  }

  static class CircleAvatar extends JComponent {
    private final Color color;
    CircleAvatar(Color c) { this.color = c; setPreferredSize(new Dimension(72,72)); }
    @Override protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      int d = Math.min(getWidth(), getHeight());
      g2.setColor(color);
      g2.fillOval(0, 0, d, d);
      g2.setColor(Color.WHITE);
      int cx = d/2;
      g2.fillOval(cx - d/8, d/4 - d/8, d/4, d/4);
      g2.fillRoundRect(cx - d/4, d/2, d/2, d/3, d/6, d/6);
      g2.dispose();
    }
  }
}
