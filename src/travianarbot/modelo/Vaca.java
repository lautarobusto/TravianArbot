package travianarbot.modelo;

public class Vaca {

    private int id;
    private String nombre;
    private int id_aldea_origen;
    private String id_movimiento;
    private int coordenada_x;
    private int coordenada_y;
    private float eficiencia = 100;
    private int puntos_ataque;
    private int id_armada_activa;
    private boolean progresion_activa;
    private int id_progresion;
    private int estado_armada;
    private int modificador_armada;
    private int z;
    private boolean activo;
    private String info;

    public Vaca() {
    }

    public Vaca(int id, String nombre, int id_aldea_origen, String id_movimiento, int coordenada_x, int coordenada_y, int puntos_ataque, int id_armada_activa, boolean progresion_activa, int id_progresion, int estado_armada, int modificador_armada, int z, boolean activo, String info) {
        this.id = id;
        this.nombre = nombre;
        this.id_aldea_origen = id_aldea_origen;
        this.id_movimiento = id_movimiento;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.puntos_ataque = puntos_ataque;
        this.id_armada_activa = id_armada_activa;
        this.progresion_activa = progresion_activa;
        this.id_progresion = id_progresion;
        this.estado_armada = estado_armada;
        this.modificador_armada = modificador_armada;
        this.z = z;
        this.activo = activo;
        this.info = info;
    }

    public Vaca(String nombre, int id_aldea_origen, String id_movimiento, int coordenada_x, int coordenada_y, int puntos_ataque, int id_armada_activa, boolean progresion_activa, int id_progresion, int estado_armada, int modificador_armada, int z, boolean activo, String info) {
        this.nombre = nombre;
        this.id_aldea_origen = id_aldea_origen;
        this.id_movimiento = id_movimiento;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.puntos_ataque = puntos_ataque;
        this.id_armada_activa = id_armada_activa;
        this.progresion_activa = progresion_activa;
        this.id_progresion = id_progresion;
        this.estado_armada = estado_armada;
        this.modificador_armada = modificador_armada;
        this.z = z;
        this.activo = activo;
        this.info = info;
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

    public int getPuntos_ataque() {
        return puntos_ataque;
    }

    public void setPuntos_ataque(int puntos_ataque) {
        this.puntos_ataque = puntos_ataque;
    }

    public int getId_armada_activa() {
        return id_armada_activa;
    }

    public void setId_armada_activa(int id_armada_activa) {
        this.id_armada_activa = id_armada_activa;
    }

    public boolean isProgresion_activa() {
        return progresion_activa;
    }

    public void setProgresion_activa(boolean progresion_activa) {
        this.progresion_activa = progresion_activa;
    }

    public int getId_progresion() {
        return id_progresion;
    }

    public void setId_progresion(int id_progresion) {
        this.id_progresion = id_progresion;
    }

    public int getEstado_armada() {
        return estado_armada;
    }

    public void setEstado_armada(int estado_armada) {
        this.estado_armada = estado_armada;
    }

    public int getModificador_armada() {
        return modificador_armada;
    }

    public void setModificador_armada(int modificador_armada) {
        this.modificador_armada = modificador_armada;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Vaca{" + "id=" + id + ", nombre=" + nombre + ", id_aldea_origen=" + id_aldea_origen + ", id_movimiento=" + id_movimiento + ", coordenada_x=" + coordenada_x + ", coordenada_y=" + coordenada_y + ", eficiencia=" + eficiencia + ", puntos_ataque=" + puntos_ataque + ", id_armada_activa=" + id_armada_activa + ", progresion_activa=" + progresion_activa + ", id_progresion=" + id_progresion + ", estado_armada=" + estado_armada + ", modificador_armada=" + modificador_armada + ", z=" + z + ", activo=" + activo + ", info=" + info + '}';
    }

}
