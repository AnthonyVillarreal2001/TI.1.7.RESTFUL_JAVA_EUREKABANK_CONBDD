package ec.edu.monster.util;

import java.text.NumberFormat;
import java.util.Locale;

public class Money {
  private static final NumberFormat F = NumberFormat.getCurrencyInstance(new Locale("es","EC"));
  public static String fmt(double v){ return F.format(v); }
}
