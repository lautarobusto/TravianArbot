package travianarbot.modelo;

public class InformeOfensivo {

    private String id_informe;
    private String asunto_informe;
    private String fecha_Informe;
    private String hora_informe;
    private int botin_posible;
    private int botin_real;
    private float eficiencia;
    private int id_aldea_atacante;
    private int id_vaca_defensora;

    public InformeOfensivo(String id_informe, String asunto_informe, String fecha_Informe, String hora_informe, int botin_posible, int botin_real, float eficiencia, int id_aldea_atacante, int id_vaca_defensora) {
        this.id_informe = id_informe;
        this.asunto_informe = asunto_informe;
        this.fecha_Informe = fecha_Informe;
        this.hora_informe = hora_informe;
        this.botin_posible = botin_posible;
        this.botin_real = botin_real;
        this.eficiencia = eficiencia;
        this.id_aldea_atacante = id_aldea_atacante;
        this.id_vaca_defensora = id_vaca_defensora;
    }

    public InformeOfensivo() {
    }

    public String getId_informe() {
        return id_informe;
    }

    public void setId_informe(String id_informe) {
        this.id_informe = id_informe;
    }

    public String getAsunto_informe() {
        return asunto_informe;
    }

    public void setAsunto_informe(String asunto_informe) {
        this.asunto_informe = asunto_informe;
    }

    public String getFecha_Informe() {
        return fecha_Informe;
    }

    public void setFecha_Informe(String fecha_Informe) {
        this.fecha_Informe = fecha_Informe;
    }

    public String getHora_informe() {
        return hora_informe;
    }

    public void setHora_informe(String hora_informe) {
        this.hora_informe = hora_informe;
    }

    public int getBotin_posible() {
        return botin_posible;
    }

    public void setBotin_posible(int botin_posible) {
        this.botin_posible = botin_posible;
    }

    public int getBotin_real() {
        return botin_real;
    }

    public void setBotin_real(int botin_real) {
        this.botin_real = botin_real;
    }

    public float getEficiencia() {
        return eficiencia;
    }

    public void setEficiencia(float eficiencia) {
        this.eficiencia = eficiencia;
    }

    public int getId_aldea_atacante() {
        return id_aldea_atacante;
    }

    public void setId_aldea_atacante(int id_aldea_atacante) {
        this.id_aldea_atacante = id_aldea_atacante;
    }

    public int getId_vaca_defensora() {
        return id_vaca_defensora;
    }

    public void setId_vaca_defensora(int id_vaca_defensora) {
        this.id_vaca_defensora = id_vaca_defensora;
    }

    @Override
    public String toString() {
        return "InformeOfensivo{" + "id_informe=" + id_informe + ", asunto_informe=" + asunto_informe + ", fecha_Informe=" + fecha_Informe + ", hora_informe=" + hora_informe + ", botin_posible=" + botin_posible + ", botin_real=" + botin_real + ", eficiencia=" + eficiencia + ", id_aldea_atacante=" + id_aldea_atacante + ", id_vaca_defensora=" + id_vaca_defensora + '}';
    }

}
