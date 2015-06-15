/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author seront
 */
@Embeddable
public class AfluenciaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_linea")
    private int idLinea;
    @Basic(optional = false)
    @Column(name = "id_pk_estacion")
    private double idPkEstacion;
    @Basic(optional = false)
    @Column(name = "id_fecha_inicial")
    private double idFechaInicial;

    public AfluenciaPK() {
    }

    public AfluenciaPK(int idLinea, double idPkEstacion, double idFechaInicial) {
        this.idLinea = idLinea;
        this.idPkEstacion = idPkEstacion;
        this.idFechaInicial = idFechaInicial;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public double getIdPkEstacion() {
        return idPkEstacion;
    }

    public void setIdPkEstacion(double idPkEstacion) {
        this.idPkEstacion = idPkEstacion;
    }

    public double getIdFechaInicial() {
        return idFechaInicial;
    }

    public void setIdFechaInicial(double idFechaInicial) {
        this.idFechaInicial = idFechaInicial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idLinea;
        hash += (int) idPkEstacion;
        hash += (int) idFechaInicial;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfluenciaPK)) {
            return false;
        }
        AfluenciaPK other = (AfluenciaPK) object;
        if (this.idLinea != other.idLinea) {
            return false;
        }
        if (this.idPkEstacion != other.idPkEstacion) {
            return false;
        }
        if (this.idFechaInicial != other.idFechaInicial) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.AfluenciaPK[ idLinea=" + idLinea + ", idPkEstacion=" + idPkEstacion + ", idFechaInicial=" + idFechaInicial + " ]";
    }
    
}
