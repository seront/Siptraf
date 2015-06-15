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
public class GraficoMarchaTipoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_grafico_marcha_tipo")
    private int idGraficoMarchaTipo;
    @Basic(optional = false)
    @Column(name = "id_marcha_tipo")
    private int idMarchaTipo;

    public GraficoMarchaTipoPK() {
    }

    public GraficoMarchaTipoPK(int idGraficoMarchaTipo, int idMarchaTipo) {
        this.idGraficoMarchaTipo = idGraficoMarchaTipo;
        this.idMarchaTipo = idMarchaTipo;
    }

    public int getIdGraficoMarchaTipo() {
        return idGraficoMarchaTipo;
    }

    public void setIdGraficoMarchaTipo(int idGraficoMarchaTipo) {
        this.idGraficoMarchaTipo = idGraficoMarchaTipo;
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
        hash += (int) idGraficoMarchaTipo;
        hash += (int) idMarchaTipo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GraficoMarchaTipoPK)) {
            return false;
        }
        GraficoMarchaTipoPK other = (GraficoMarchaTipoPK) object;
        if (this.idGraficoMarchaTipo != other.idGraficoMarchaTipo) {
            return false;
        }
        if (this.idMarchaTipo != other.idMarchaTipo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.GraficoMarchaTipoPK[ idGraficoMarchaTipo=" + idGraficoMarchaTipo + ", idMarchaTipo=" + idMarchaTipo + " ]";
    }
    
}
