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

    private int id_cuenta;
    private String nombre_cuenta;
    private String contraseña_cuenta;
    private String servidor_cuenta;
    private int id_raza;
    private String lowRes;

    public Cuenta() {
    }

    public Cuenta(int id_cuenta, String nombre_cuenta, String contraseña_cuenta, String servidor_cuenta, int id_raza, String lowRes) {
        this.id_cuenta = id_cuenta;
        this.nombre_cuenta = nombre_cuenta;
        this.contraseña_cuenta = contraseña_cuenta;
        this.servidor_cuenta = servidor_cuenta;
        this.id_raza = id_raza;
        this.lowRes = lowRes;
    }

    public Cuenta(String nombre_cuenta, String contraseña_cuenta, String servidor_cuenta, int id_raza, String lowRes) {
        this.nombre_cuenta = nombre_cuenta;
        this.contraseña_cuenta = contraseña_cuenta;
        this.servidor_cuenta = servidor_cuenta;
        this.id_raza = id_raza;
        this.lowRes = lowRes;
    }

    public String getLowRes() {
        return lowRes;
    }

    public void setLowRes(String lowRes) {
        this.lowRes = lowRes;
    }

    public int getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(int id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public String getNombre_cuenta() {
        return nombre_cuenta;
    }

    public void setNombre_cuenta(String nombre_cuenta) {
        this.nombre_cuenta = nombre_cuenta;
    }

    public String getContraseña_cuenta() {
        return contraseña_cuenta;
    }

    public void setContraseña_cuenta(String contraseña_cuenta) {
        this.contraseña_cuenta = contraseña_cuenta;
    }

    public String getServidor_cuenta() {
        return servidor_cuenta;
    }

    public void setServidor_cuenta(String servidor_cuenta) {
        this.servidor_cuenta = servidor_cuenta;
    }

    public int getId_raza() {
        return id_raza;
    }

    public void setId_raza(int id_raza) {
        this.id_raza = id_raza;
    }

    @Override
    public String toString() {
        return "Cuenta{" + "id_cuenta=" + id_cuenta + ", nombre_cuenta=" + nombre_cuenta + ", contrase\u00f1a_cuenta=" + contraseña_cuenta + ", servidor_cuenta=" + servidor_cuenta + ", id_raza=" + id_raza + ", lowRes=" + lowRes + '}';
    }

    

}
