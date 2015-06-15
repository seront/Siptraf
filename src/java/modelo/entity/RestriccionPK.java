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
public class RestriccionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_linea")
    private int idLinea;
    @Basic(optional = false)
    @Column(name = "id_restriccion")
    private int idRestriccion;

    public RestriccionPK() {
    }

    public RestriccionPK(int idLinea, int idRestriccion) {
        this.idLinea = idLinea;
        this.idRestriccion = idRestriccion;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public int getIdRestriccion() {
        return idRestriccion;
    }

    public void setIdRestriccion(int idRestriccion) {
        this.idRestriccion = idRestriccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idLinea;
        hash += (int) idRestriccion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RestriccionPK)) {
            return false;
        }
        RestriccionPK other = (RestriccionPK) object;
        if (this.idLinea != other.idLinea) {
            return false;
        }
        if (this.idRestriccion != other.idRestriccion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.RestriccionPK[ idLinea=" + idLinea + ", idRestriccion=" + idRestriccion + " ]";
    }
    
}
