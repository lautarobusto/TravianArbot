package travianarbot.modelo;

public class Armada {

    private int id_armada = -1;
    private String nombre_armada;
    private int t1;
    private int t2;
    private int t3;
    private int t4;
    private int t5;
    private int t6;
    private int t7;
    private int t8;
    private int t9;
    private int t10;
    private int t11;
    private int elo;

    public Armada() {
    }

    public Armada(int id_armada, String nombre_armada, int t1, int t2, int t3, int t4, int t5, int t6, int t7, int t8, int t9, int t10, int t11, int elo) {
        this.id_armada = id_armada;
        this.nombre_armada = nombre_armada;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        this.t7 = t7;
        this.t8 = t8;
        this.t9 = t9;
        this.t10 = t10;
        this.t11 = t11;
        this.elo = elo;
    }

    public Armada(String nombre_armada, int t1, int t2, int t3, int t4, int t5, int t6, int t7, int t8, int t9, int t10, int t11, int elo) {
        this.nombre_armada = nombre_armada;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        this.t7 = t7;
        this.t8 = t8;
        this.t9 = t9;
        this.t10 = t10;
        this.t11 = t11;
        this.elo = elo;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getId_armada() {
        return id_armada;
    }

    public void setId_armada(int id_armada) {
        this.id_armada = id_armada;
    }

    public String getNombre_armada() {
        return nombre_armada;
    }

    public void setNombre_armada(String nombre_armada) {
        this.nombre_armada = nombre_armada;
    }

    public int getT1() {
        return t1;
    }

    public void setT1(int t1) {
        this.t1 = t1;
    }

    public int getT2() {
        return t2;
    }

    public void setT2(int t2) {
        this.t2 = t2;
    }

    public int getT3() {
        return t3;
    }

    public void setT3(int t3) {
        this.t3 = t3;
    }

    public int getT4() {
        return t4;
    }

    public void setT4(int t4) {
        this.t4 = t4;
    }

    public int getT5() {
        return t5;
    }

    public void setT5(int t5) {
        this.t5 = t5;
    }

    public int getT6() {
        return t6;
    }

    public void setT6(int t6) {
        this.t6 = t6;
    }

    public int getT7() {
        return t7;
    }

    public void setT7(int t7) {
        this.t7 = t7;
    }

    public int getT8() {
        return t8;
    }

    public void setT8(int t8) {
        this.t8 = t8;
    }

    public int getT9() {
        return t9;
    }

    public void setT9(int t9) {
        this.t9 = t9;
    }

    public int getT10() {
        return t10;
    }

    public void setT10(int t10) {
        this.t10 = t10;
    }

    public int getT11() {
        return t11;
    }

    public void setT11(int t11) {
        this.t11 = t11;
    }

    @Override
    public String toString() {
        return "Armada{" + "id_armada=" + id_armada + ", nombre_armada=" + nombre_armada + ", t1=" + t1 + ", t2=" + t2 + ", t3=" + t3 + ", t4=" + t4 + ", t5=" + t5 + ", t6=" + t6 + ", t7=" + t7 + ", t8=" + t8 + ", t9=" + t9 + ", t10=" + t10 + ", t11=" + t11 + '}';
    }

    public int[] toArray() {
        int[] lista = {t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11};
        return lista;
    }

}
