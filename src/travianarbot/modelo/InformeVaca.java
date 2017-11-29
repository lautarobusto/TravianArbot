package travianarbot.modelo;

public class InformeVaca {

    private String id;
    private String asunto;
    private String fecha;
    private String hora;
    private float eficiencia;
    private float perdidas;
    private int Z_aldea_atacante;
    private int Z_aldea_defensora;

    public InformeVaca(String id, String asunto, String fecha, String hora, float eficiencia, float perdidas, int Z_aldea_atacante, int Z_aldea_defensora) {
        this.id = id;
        this.asunto = asunto;
        this.fecha = fecha;
        this.hora = hora;
        this.eficiencia = eficiencia;
        this.perdidas = perdidas;
        this.Z_aldea_atacante = Z_aldea_atacante;
        this.Z_aldea_defensora = Z_aldea_defensora;
    }

    public InformeVaca() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public float getEficiencia() {
        return eficiencia;
    }

    public void setEficiencia(float eficiencia) {
        this.eficiencia = eficiencia;
    }

    public float getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(float perdidas) {
        this.perdidas = perdidas;
    }

    public int getZ_aldea_atacante() {
        return Z_aldea_atacante;
    }

    public void setZ_aldea_atacante(int Z_aldea_atacante) {
        this.Z_aldea_atacante = Z_aldea_atacante;
    }

    public int getZ_aldea_defensora() {
        return Z_aldea_defensora;
    }

    public void setZ_aldea_defensora(int Z_aldea_defensora) {
        this.Z_aldea_defensora = Z_aldea_defensora;
    }

    @Override
    public String toString() {
        return "InformeVaca{" + "id=" + id + ", asunto=" + asunto + ", fecha=" + fecha + ", hora=" + hora + ", eficiencia=" + eficiencia + ", perdidas=" + perdidas + ", Z_aldea_atacante=" + Z_aldea_atacante + ", Z_aldea_defensora=" + Z_aldea_defensora + '}';
    }

}
