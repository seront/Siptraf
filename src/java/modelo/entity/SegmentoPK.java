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
public class SegmentoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_linea")
    private int idLinea;
    @Basic(optional = false)
    @Column(name = "id_pk_inicial")
    private double idPkInicial;

    public SegmentoPK() {
    }

    public SegmentoPK(int idLinea, double idPkInicial) {
        this.idLinea = idLinea;
        this.idPkInicial = idPkInicial;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public double getIdPkInicial() {
        return idPkInicial;
    }

    public void setIdPkInicial(double idPkInicial) {
        this.idPkInicial = idPkInicial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idLinea;
        hash += (int) idPkInicial;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegmentoPK)) {
            return false;
        }
        SegmentoPK other = (SegmentoPK) object;
        if (this.idLinea != other.idLinea) {
            return false;
        }
        if (this.idPkInicial != other.idPkInicial) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.SegmentoPK[ idLinea=" + idLinea + ", idPkInicial=" + idPkInicial + " ]";
    }
    
}
