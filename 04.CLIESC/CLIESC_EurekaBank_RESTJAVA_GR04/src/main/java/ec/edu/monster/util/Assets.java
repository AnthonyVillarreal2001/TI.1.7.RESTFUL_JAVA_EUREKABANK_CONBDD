package ec.edu.monster.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public final class Assets {
  private Assets(){}

  /** Devuelve el ícono del header (preferimos brand_header; fallback a logo-ldu). */
  public static ImageIcon brandHeader(int height){
    // 1) Imagen compuesta si existe
    ImageIcon ic = load("/img/brand_header.png", height);
    if (ic != null) return ic;
    ic = load("/brand_header.png", height);
    if (ic != null) return ic;

    // 2) Fallback: tu archivo
    ic = load("/img/logo-ldu.png", height);
    if (ic != null) return ic;
    ic = load("/logo-ldu.png", height);
    if (ic != null) return ic;

    System.err.println("[Assets] No se encontró brand_header.png ni logo-ldu.png en el classpath.");
    return null; // las vistas mostrarán solo el texto si es null
  }

  /** Carga un recurso del classpath y lo escala a la altura indicada. */
  public static ImageIcon load(String path, int targetHeight){
    try {
      URL url = Assets.class.getResource(path);
      System.out.println("[Assets] Intentando cargar: " + path + " -> " + url);
      if (url == null) return null;
      Image img = new ImageIcon(url).getImage();
      if (targetHeight > 0)
        img = img.getScaledInstance(-1, targetHeight, Image.SCALE_SMOOTH);
      return new ImageIcon(img);
    } catch (Exception ex){
      System.err.println("[Assets] Error cargando " + path + ": " + ex.getMessage());
      return null;
    }
  }
}
