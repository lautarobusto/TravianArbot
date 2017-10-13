/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travianarbot.modelo;

/**
 *
 * @author Lautaro
 */
public class Tropa {

    private int id_tropa;
    private String nombre_tropa;
    private int ataque_tropa;
    private int defensa_infanteria_tropa;
    private int defensa_caballeria_tropa;
    private int velocidad_tropa;
    private int transporte_tropa;
    private int id_raza;
    private int manutencion_tropa;

    public Tropa(int id_tropa, String nombre_tropa, int ataque_tropa, int defensa_infanteria_tropa, int defensa_caballeria_tropa, int velocidad_tropa, int transporte_tropa, int id_raza, int manutencion_tropa) {
        this.id_tropa = id_tropa;
        this.nombre_tropa = nombre_tropa;
        this.ataque_tropa = ataque_tropa;
        this.defensa_infanteria_tropa = defensa_infanteria_tropa;
        this.defensa_caballeria_tropa = defensa_caballeria_tropa;
        this.velocidad_tropa = velocidad_tropa;
        this.transporte_tropa = transporte_tropa;
        this.id_raza = id_raza;
        this.manutencion_tropa = manutencion_tropa;
    }

    public int getTransporte_tropa() {
        return transporte_tropa;
    }

    public void setTransporte_tropa(int transporte_tropa) {
        this.transporte_tropa = transporte_tropa;
    }

    public Tropa() {
    }

    public int getId_tropa() {
        return id_tropa;
    }

    public void setId_tropa(int id_tropa) {
        this.id_tropa = id_tropa;
    }

    public String getNombre_tropa() {
        return nombre_tropa;
    }

    public void setNombre_tropa(String nombre_tropa) {
        this.nombre_tropa = nombre_tropa;
    }

    public int getAtaque_tropa() {
        return ataque_tropa;
    }

    public void setAtaque_tropa(int ataque_tropa) {
        this.ataque_tropa = ataque_tropa;
    }

    public int getDefensa_infanteria_tropa() {
        return defensa_infanteria_tropa;
    }

    public void setDefensa_infanteria_tropa(int defensa_infanteria_tropa) {
        this.defensa_infanteria_tropa = defensa_infanteria_tropa;
    }

    public int getDefensa_caballeria_tropa() {
        return defensa_caballeria_tropa;
    }

    public void setDefensa_caballeria_tropa(int defensa_caballeria_tropa) {
        this.defensa_caballeria_tropa = defensa_caballeria_tropa;
    }

    public int getVelocidad_tropa() {
        return velocidad_tropa;
    }

    public void setVelocidad_tropa(int velocidad_tropa) {
        this.velocidad_tropa = velocidad_tropa;
    }

    public int getId_raza() {
        return id_raza;
    }

    public void setId_raza(int id_raza) {
        this.id_raza = id_raza;
    }

    public int getManutencion_tropa() {
        return manutencion_tropa;
    }

    public void setManutencion_tropa(int manutencion_tropa) {
        this.manutencion_tropa = manutencion_tropa;
    }

    @Override
    public String toString() {
        return "Tropa{" + "id_tropa=" + id_tropa + ", nombre_tropa=" + nombre_tropa + ", ataque_tropa=" + ataque_tropa + ", defensa_infanteria_tropa=" + defensa_infanteria_tropa + ", defensa_caballeria_tropa=" + defensa_caballeria_tropa + ", velocidad_tropa=" + velocidad_tropa + ", transporte_tropa=" + transporte_tropa + ", id_raza=" + id_raza + ", manutencion_tropa=" + manutencion_tropa + '}';
    }

}
