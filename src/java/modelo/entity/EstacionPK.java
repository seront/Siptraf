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
 * @author Kelvins Insua
 */
@Embeddable
public class EstacionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_linea")
    private int idLinea;
    @Basic(optional = false)
    @Column(name = "id_pk_estacion")
    private double idPkEstacion;

    public EstacionPK() {
    }

    public EstacionPK(int idLinea, double idPkEstacion) {
        this.idLinea = idLinea;
        this.idPkEstacion = idPkEstacion;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idLinea;
        hash += (int) idPkEstacion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstacionPK)) {
            return false;
        }
        EstacionPK other = (EstacionPK) object;
        if (this.idLinea != other.idLinea) {
            return false;
        }
        if (this.idPkEstacion != other.idPkEstacion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.EstacionPK[ idLinea=" + idLinea + ", idPkEstacion=" + idPkEstacion + " ]";
    }
    
}
