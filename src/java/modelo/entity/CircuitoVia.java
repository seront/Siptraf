/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kelvins Insua
 */
@Entity
@Table(name = "circuito_via")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CircuitoVia.findAll", query = "SELECT c FROM CircuitoVia c"),
    @NamedQuery(name = "CircuitoVia.findByCircuitoViaPK", query = "SELECT c FROM CircuitoVia c WHERE c.circuitoViaPK.idLinea=:idLinea AND c.circuitoViaPK.idPkInicialCircuito=:idPkInicialCircuito"),    
    @NamedQuery(name = "CircuitoVia.findByIdPkInicialCircuito", query = "SELECT c FROM CircuitoVia c WHERE c.circuitoViaPK.idPkInicialCircuito = :idPkInicialCircuito"),
    @NamedQuery(name = "CircuitoVia.findByPkFinalCircuito", query = "SELECT c FROM CircuitoVia c WHERE c.pkFinalCircuito = :pkFinalCircuito"),
    @NamedQuery(name = "CircuitoVia.findByIdLinea", query = "SELECT c FROM CircuitoVia c WHERE c.circuitoViaPK.idLinea = :idLinea")})
public class CircuitoVia implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CircuitoViaPK circuitoViaPK;
    @Basic(optional = false)
    @Column(name = "pk_final_circuito")
    private double pkFinalCircuito;
    @JoinColumn(name = "id_linea", referencedColumnName = "id_linea", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Linea linea;

    public CircuitoVia() {
    }

    public CircuitoVia(CircuitoViaPK circuitoViaPK) {
        this.circuitoViaPK = circuitoViaPK;
    }

    public CircuitoVia(CircuitoViaPK circuitoViaPK, double pkFinalCircuito) {
        this.circuitoViaPK = circuitoViaPK;
        this.pkFinalCircuito = pkFinalCircuito;
    }

    public CircuitoVia(double idPkInicialCircuito, int idLinea) {
        this.circuitoViaPK = new CircuitoViaPK(idPkInicialCircuito, idLinea);
    }

    public CircuitoViaPK getCircuitoViaPK() {
        return circuitoViaPK;
    }

    public void setCircuitoViaPK(CircuitoViaPK circuitoViaPK) {
        this.circuitoViaPK = circuitoViaPK;
    }

    public double getPkFinalCircuito() {
        return pkFinalCircuito;
    }

    public void setPkFinalCircuito(double pkFinalCircuito) {
        this.pkFinalCircuito = pkFinalCircuito;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (circuitoViaPK != null ? circuitoViaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CircuitoVia)) {
            return false;
        }
        CircuitoVia other = (CircuitoVia) object;
        if ((this.circuitoViaPK == null && other.circuitoViaPK != null) || (this.circuitoViaPK != null && !this.circuitoViaPK.equals(other.circuitoViaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.CircuitoVia[ circuitoViaPK=" + circuitoViaPK + " ]";
    }
    
}
