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
public class CircuitoViaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_pk_inicial_circuito")
    private double idPkInicialCircuito;
    @Basic(optional = false)
    @Column(name = "id_linea")
    private int idLinea;

    public CircuitoViaPK() {
    }

    public CircuitoViaPK(double idPkInicialCircuito, int idLinea) {
        this.idPkInicialCircuito = idPkInicialCircuito;
        this.idLinea = idLinea;
    }

    public double getIdPkInicialCircuito() {
        return idPkInicialCircuito;
    }

    public void setIdPkInicialCircuito(double idPkInicialCircuito) {
        this.idPkInicialCircuito = idPkInicialCircuito;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPkInicialCircuito;
        hash += (int) idLinea;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CircuitoViaPK)) {
            return false;
        }
        CircuitoViaPK other = (CircuitoViaPK) object;
        if (this.idPkInicialCircuito != other.idPkInicialCircuito) {
            return false;
        }
        if (this.idLinea != other.idLinea) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.CircuitoViaPK[ idPkInicialCircuito=" + idPkInicialCircuito + ", idLinea=" + idLinea + " ]";
    }
    
}
