package travianarbot.modelo;

import java.util.Comparator;
import java.util.Objects;

public class Aldea {

    private int id;
    private int id_cuenta;
    private String nombre;
    private String tipo_terreno;
    private int coordenada_x;
    private int coordenada_y;
    private int z;

    public Aldea() {
    }

    public Aldea(int id, int id_cuenta, String nombre, String tipo_terreno, int coordenada_x, int coordenada_y, int z) {
        this.id = id;
        this.id_cuenta = id_cuenta;
        this.nombre = nombre;
        this.tipo_terreno = tipo_terreno;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.z = z;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getId_aldea() {
        return id;
    }

    public void setId_aldea(int id) {
        this.id = id;
    }

    public int getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(int id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public String getNombre_aldea() {
        return nombre;
    }

    public void setNombre_aldea(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo_terreno() {
        return tipo_terreno;
    }

    public void setTipo_terreno(String tipo_terreno) {
        this.tipo_terreno = tipo_terreno;
    }

    public int getCoordenada_x() {
        return coordenada_x;
    }

    public void setCoordenada_x(int coordenada_x) {
        this.coordenada_x = coordenada_x;
    }

    public int getCoordenada_y() {
        return coordenada_y;
    }

    public void setCoordenada_y(int coordenada_y) {
        this.coordenada_y = coordenada_y;
    }

    @Override
    public String toString() {
        return "Aldea{" + "id=" + id + ", id_cuenta=" + id_cuenta + ", nombre=" + nombre + ", tipo_terreno=" + tipo_terreno + ", coordenada_x=" + coordenada_x + ", coordenada_y=" + coordenada_y + ", z=" + z + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof Aldea)) {
            return false;
        }
        Aldea aldea = (Aldea) o;
        return id == aldea.id && Objects.equals(nombre, aldea.nombre);

    }

    public static class CompId implements Comparator<Aldea> {

        @Override
        public int compare(Aldea arg0, Aldea arg1) {
            return arg0.id - arg1.id;
        }
    }

}
