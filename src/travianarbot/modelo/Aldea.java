package travianarbot.modelo;

public class Aldea {

    private int id_aldea;
    private int id_cuenta;
    private String nombre_aldea;
    private String tipo_terreno;
    private int coordenada_x;
    private int coordenada_y;

    public Aldea() {
    }

    public Aldea(int id_aldea, int id_cuenta, String nombre_aldea, String tipo_terreno, int coordenada_x, int coordenada_y) {
        this.id_aldea = id_aldea;
        this.id_cuenta = id_cuenta;
        this.nombre_aldea = nombre_aldea;
        this.tipo_terreno = tipo_terreno;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
    }

    public int getId_aldea() {
        return id_aldea;
    }

    public void setId_aldea(int id_aldea) {
        this.id_aldea = id_aldea;
    }

    public int getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(int id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public String getNombre_aldea() {
        return nombre_aldea;
    }

    public void setNombre_aldea(String nombre_aldea) {
        this.nombre_aldea = nombre_aldea;
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
        return "Aldea{" + "id_aldea=" + id_aldea + ", id_cuenta=" + id_cuenta + ", nombre_aldea=" + nombre_aldea + ", tipo_terreno=" + tipo_terreno + ", coordenada_x=" + coordenada_x + ", coordenada_y=" + coordenada_y + '}';
    }

}
