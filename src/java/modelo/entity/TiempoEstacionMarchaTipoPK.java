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
public class TiempoEstacionMarchaTipoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_pk_estacion")
    private double idPkEstacion;
    @Basic(optional = false)
    @Column(name = "id_marcha_tipo")
    private int idMarchaTipo;

    public TiempoEstacionMarchaTipoPK() {
    }

    public TiempoEstacionMarchaTipoPK(double idPkEstacion, int idMarchaTipo) {
        this.idPkEstacion = idPkEstacion;
        this.idMarchaTipo = idMarchaTipo;
    }

    public double getIdPkEstacion() {
        return idPkEstacion;
    }

    public void setIdPkEstacion(double idPkEstacion) {
        this.idPkEstacion = idPkEstacion;
    }

    public int getIdMarchaTipo() {
        return idMarchaTipo;
    }

    public void setIdMarchaTipo(int idMarchaTipo) {
        this.idMarchaTipo = idMarchaTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPkEstacion;
        hash += (int) idMarchaTipo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiempoEstacionMarchaTipoPK)) {
            return false;
        }
        TiempoEstacionMarchaTipoPK other = (TiempoEstacionMarchaTipoPK) object;
        if (this.idPkEstacion != other.idPkEstacion) {
            return false;
        }
        if (this.idMarchaTipo != other.idMarchaTipo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.TiempoEstacionMarchaTipoPK[ idPkEstacion=" + idPkEstacion + ", idMarchaTipo=" + idMarchaTipo + " ]";
    }
    
}
