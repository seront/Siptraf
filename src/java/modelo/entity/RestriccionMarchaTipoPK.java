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
public class RestriccionMarchaTipoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_pk_inicial_res_mt")
    private double idPkInicialResMt;
    @Basic(optional = false)
    @Column(name = "id_marcha_tipo")
    private int idMarchaTipo;

    public RestriccionMarchaTipoPK() {
    }

    public RestriccionMarchaTipoPK(double idPkInicialResMt, int idMarchaTipo) {
        this.idPkInicialResMt = idPkInicialResMt;
        this.idMarchaTipo = idMarchaTipo;
    }

    public double getIdPkInicialResMt() {
        return idPkInicialResMt;
    }

    public void setIdPkInicialResMt(double idPkInicialResMt) {
        this.idPkInicialResMt = idPkInicialResMt;
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
        hash += (int) idPkInicialResMt;
        hash += (int) idMarchaTipo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RestriccionMarchaTipoPK)) {
            return false;
        }
        RestriccionMarchaTipoPK other = (RestriccionMarchaTipoPK) object;
        if (this.idPkInicialResMt != other.idPkInicialResMt) {
            return false;
        }
        if (this.idMarchaTipo != other.idMarchaTipo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.RestriccionMarchaTipoPK[ idPkInicialResMt=" + idPkInicialResMt + ", idMarchaTipo=" + idMarchaTipo + " ]";
    }
    
}
