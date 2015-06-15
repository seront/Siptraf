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
public class CurvaEsfuerzoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_material_rodante")
    private int idMaterialRodante;
    @Basic(optional = false)
    @Column(name = "id_velocidad_curva_esfuerzo")
    private double idVelocidadCurvaEsfuerzo;

    public CurvaEsfuerzoPK() {
    }

    public CurvaEsfuerzoPK(int idMaterialRodante, double idVelocidadCurvaEsfuerzo) {
        this.idMaterialRodante = idMaterialRodante;
        this.idVelocidadCurvaEsfuerzo = idVelocidadCurvaEsfuerzo;
    }

    public int getIdMaterialRodante() {
        return idMaterialRodante;
    }

    public void setIdMaterialRodante(int idMaterialRodante) {
        this.idMaterialRodante = idMaterialRodante;
    }

    public double getIdVelocidadCurvaEsfuerzo() {
        return idVelocidadCurvaEsfuerzo;
    }

    public void setIdVelocidadCurvaEsfuerzo(double idVelocidadCurvaEsfuerzo) {
        this.idVelocidadCurvaEsfuerzo = idVelocidadCurvaEsfuerzo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idMaterialRodante;
        hash += (int) idVelocidadCurvaEsfuerzo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CurvaEsfuerzoPK)) {
            return false;
        }
        CurvaEsfuerzoPK other = (CurvaEsfuerzoPK) object;
        if (this.idMaterialRodante != other.idMaterialRodante) {
            return false;
        }
        if (this.idVelocidadCurvaEsfuerzo != other.idVelocidadCurvaEsfuerzo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.CurvaEsfuerzoPK[ idMaterialRodante=" + idMaterialRodante + ", idVelocidadCurvaEsfuerzo=" + idVelocidadCurvaEsfuerzo + " ]";
    }
    
}
