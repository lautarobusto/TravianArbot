package travianarbot.modelo;

public class Vaca {

    private int id=-1;
    private String nombre;
    private int id_aldea_origen;
    private String id_movimiento;
    private int coordenada_x;
    private int coordenada_y;
    private float eficiencia = 100;
    private int id_armada;
    private boolean activo;
    private int z;

    public Vaca() {
    }

    public Vaca(String nombre, int id_aldea_origen, String id_movimiento, int coordenada_x, int coordenada_y, int id_armada, boolean activo, int z) {
        this.nombre = nombre;
        this.id_aldea_origen = id_aldea_origen;
        this.id_movimiento = id_movimiento;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.id_armada = id_armada;
        this.activo = activo;
        this.z = z;
    }

    public Vaca(int id, String nombre, int id_aldea_origen, String id_movimiento, int coordenada_x, int coordenada_y, int id_armada, boolean activo, int z) {
        this.id = id;
        this.nombre = nombre;
        this.id_aldea_origen = id_aldea_origen;
        this.id_movimiento = id_movimiento;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.id_armada = id_armada;
        this.activo = activo;
        this.z = z;
    }

    public Vaca(int id, String nombre, int id_aldea_origen, String id_movimiento, int coordenada_x, int coordenada_y, float eficiencia, int id_armada, boolean activo, int z) {
        this.id = id;
        this.nombre = nombre;
        this.id_aldea_origen = id_aldea_origen;
        this.id_movimiento = id_movimiento;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.eficiencia = eficiencia;
        this.id_armada = id_armada;
        this.activo = activo;
        this.z = z;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_aldea_origen() {
        return id_aldea_origen;
    }

    public void setId_aldea_origen(int id_aldea_origen) {
        this.id_aldea_origen = id_aldea_origen;
    }

    public String getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(String id_movimiento) {
        this.id_movimiento = id_movimiento;
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

    public float getEficiencia() {
        return eficiencia;
    }

    public void setEficiencia(float eficiencia) {
        this.eficiencia = eficiencia;
    }

    public int getId_armada() {
        return id_armada;
    }

    public void setId_armada(int id_armada) {
        this.id_armada = id_armada;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Vaca{" + "id=" + id + ", nombre=" + nombre + ", id_aldea_origen=" + id_aldea_origen + ", id_movimiento=" + id_movimiento + ", coordenada_x=" + coordenada_x + ", coordenada_y=" + coordenada_y + ", eficiencia=" + eficiencia + ", id_armada=" + id_armada + ", activo=" + activo + ", z=" + z + '}';
    }

}
