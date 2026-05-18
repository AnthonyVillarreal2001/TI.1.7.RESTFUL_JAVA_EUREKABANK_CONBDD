package ec.edu.monster.modelo;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date; 

/**
 *
 * @author LENOVO
 */
@XmlRootElement(name = "movimiento")
public class Movimiento {

    private String cuenta;
    private int nromov;
    private Date fecha;
    private String tipo;
    private String accion;
    private double importe;
    private String referencia; // Nuevo campo agregado

    public Movimiento() {
    }

    public Movimiento(String cuenta, int nromov, Date fecha, String tipo, String accion, double importe, String referencia) {
        this.cuenta = cuenta;
        this.nromov = nromov;
        this.fecha = fecha;
        this.tipo = tipo;
        this.accion = accion;
        this.importe = importe;
        this.referencia = referencia;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public int getNromov() {
        return nromov;
    }

    public void setNromov(int nromov) {
        this.nromov = nromov;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    // Método para obtener la fecha como una cadena de texto en el formato deseado
    public String getFechaAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }

    public void setFechaFromString(String fechaString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = sdf.parse(fechaString);
            this.fecha = fecha;
        } catch (ParseException e) {
            // Manejar la excepción en caso de que ocurra un error al convertir la fecha
            e.printStackTrace();
        }
    }
}
