package travianarbot.modelo;

public class Vaca {

    private int id_vaca = -1;
    private String nombre_vaca;
    private int id_aldea;
    private String id_movimiento;
    private int coordenada_x;
    private int coordenada_y;
    private float eficiencia = 100;
    private float elo = 100;
    private int id_armada;
    private int z;
    private boolean activo;

    public Vaca() {
    }

    public Vaca(String nombre_vaca, int id_aldea, String id_movimiento, int coordenada_x, int coordenada_y, int id_armada, int z, boolean activo) {
        this.nombre_vaca = nombre_vaca;
        this.id_aldea = id_aldea;
        this.id_movimiento = id_movimiento;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.id_armada = id_armada;
        this.z = z;
        this.activo = activo;
    }

    public Vaca(int id_vaca, String nombre_vaca, int id_aldea, String id_movimiento, int coordenada_x, int coordenada_y, float eficiencia, float elo, int id_armada, int z, boolean activo) {
        this.id_vaca = id_vaca;
        this.nombre_vaca = nombre_vaca;
        this.id_aldea = id_aldea;
        this.id_movimiento = id_movimiento;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.id_armada = id_armada;
        this.z = z;
        this.activo = activo;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public int getId_vaca() {
        return id_vaca;
    }

    public void setId_vaca(int id_vaca) {
        this.id_vaca = id_vaca;
    }

    public String getNombre_vaca() {
        return nombre_vaca;
    }

    public void setNombre_vaca(String nombre_vaca) {
        this.nombre_vaca = nombre_vaca;
    }

    public int getId_aldea() {
        return id_aldea;
    }

    public void setId_aldea(int id_aldea) {
        this.id_aldea = id_aldea;
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

    public float getElo() {
        return elo;
    }

    public void setElo(float elo) {
        this.elo = elo;
    }

    public int getId_armada() {
        return id_armada;
    }

    public void setId_armada(int id_armada) {
        this.id_armada = id_armada;
    }

    @Override
    public String toString() {
        return "Vaca{" + "id_vaca=" + id_vaca + ", nombre_vaca=" + nombre_vaca + ", id_aldea=" + id_aldea + ", id_movimiento=" + id_movimiento + ", coordenada_x=" + coordenada_x + ", coordenada_y=" + coordenada_y + ", eficiencia=" + eficiencia + ", elo=" + elo + ", id_armada=" + id_armada + ", activo=" + activo + '}';
    }

    

}
