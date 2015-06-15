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
public class PreCirculacionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_pre_circulacion")
    private int idPreCirculacion;
    @Basic(optional = false)
    @Column(name = "id_ruta")
    private int idRuta;
    @Basic(optional = false)
    @Column(name = "id_programacion_horaria")
    private int idProgramacionHoraria;

    public PreCirculacionPK() {
    }

    public PreCirculacionPK(int idPreCirculacion, int idRuta, int idProgramacionHoraria) {
        this.idPreCirculacion = idPreCirculacion;
        this.idRuta = idRuta;
        this.idProgramacionHoraria = idProgramacionHoraria;
    }

    public int getIdPreCirculacion() {
        return idPreCirculacion;
    }

    public void setIdPreCirculacion(int idPreCirculacion) {
        this.idPreCirculacion = idPreCirculacion;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getIdProgramacionHoraria() {
        return idProgramacionHoraria;
    }

    public void setIdProgramacionHoraria(int idProgramacionHoraria) {
        this.idProgramacionHoraria = idProgramacionHoraria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPreCirculacion;
        hash += (int) idRuta;
        hash += (int) idProgramacionHoraria;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreCirculacionPK)) {
            return false;
        }
        PreCirculacionPK other = (PreCirculacionPK) object;
        if (this.idPreCirculacion != other.idPreCirculacion) {
            return false;
        }
        if (this.idRuta != other.idRuta) {
            return false;
        }
        if (this.idProgramacionHoraria != other.idProgramacionHoraria) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.PreCirculacionPK[ idPreCirculacion=" + idPreCirculacion + ", idRuta=" + idRuta + ", idProgramacionHoraria=" + idProgramacionHoraria + " ]";
    }
    
}
