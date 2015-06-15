/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author seront
 */
public class EstacionTiempo {
    double idPkEstacion;
    long tiempoAsimilado;

    public EstacionTiempo(double idPkEstacion, long tiempoAsimilado) {
        this.idPkEstacion = idPkEstacion;
        this.tiempoAsimilado = tiempoAsimilado;
    }

    public double getIdPkEstacion() {
        return idPkEstacion;
    }

    public void setIdPkEstacion(double idPkEstacion) {
        this.idPkEstacion = idPkEstacion;
    }

    public long getTiempoAsimilado() {
        return tiempoAsimilado;
    }

    public void setTiempoAsimilado(long tiempoAsimilado) {
        this.tiempoAsimilado = tiempoAsimilado;
    }
    
    
}
