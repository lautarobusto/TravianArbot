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
public class Cuenta {

    private int id;
    private String nombre;
    private String contraseña;
    private String servidor;
    private int id_raza;
    private String lowRes;

    public Cuenta() {
    }

    public Cuenta(String nombre, String contraseña, String servidor, int id_raza, String lowRes) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.servidor = servidor;
        this.id_raza = id_raza;
        this.lowRes = lowRes;
    }

    public Cuenta(int id, String nombre, String contraseña, String servidor, int id_raza, String lowRes) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.servidor = servidor;
        this.id_raza = id_raza;
        this.lowRes = lowRes;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public int getId_raza() {
        return id_raza;
    }

    public void setId_raza(int id_raza) {
        this.id_raza = id_raza;
    }

    public String getLowRes() {
        return lowRes;
    }

    public void setLowRes(String lowRes) {
        this.lowRes = lowRes;
    }

    @Override
    public String toString() {
        return "Cuenta{" + "id=" + id + ", nombre=" + nombre + ", contrase\u00f1a=" + contraseña + ", servidor=" + servidor + ", id_raza=" + id_raza + ", lowRes=" + lowRes + '}';
    }

}
