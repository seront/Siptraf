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
@Table(name = "restriccion_marcha_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RestriccionMarchaTipo.findAll", query = "SELECT r FROM RestriccionMarchaTipo r"),
    @NamedQuery(name = "RestriccionMarchaTipo.findByIdPkInicialResMt", query = "SELECT r FROM RestriccionMarchaTipo r WHERE r.restriccionMarchaTipoPK.idPkInicialResMt = :idPkInicialResMt"),
    @NamedQuery(name = "RestriccionMarchaTipo.findByIdMarchaTipo", query = "SELECT r FROM RestriccionMarchaTipo r WHERE r.restriccionMarchaTipoPK.idMarchaTipo = :idMarchaTipo"),
    @NamedQuery(name = "RestriccionMarchaTipo.findByPkFinalResMt", query = "SELECT r FROM RestriccionMarchaTipo r WHERE r.pkFinalResMt = :pkFinalResMt"),
    @NamedQuery(name = "RestriccionMarchaTipo.findByVelocidadMaxAscendente", query = "SELECT r FROM RestriccionMarchaTipo r WHERE r.velocidadMaxAscendente = :velocidadMaxAscendente"),
    @NamedQuery(name = "RestriccionMarchaTipo.findByVelocidadMaxDescendente", query = "SELECT r FROM RestriccionMarchaTipo r WHERE r.velocidadMaxDescendente = :velocidadMaxDescendente")})
public class RestriccionMarchaTipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RestriccionMarchaTipoPK restriccionMarchaTipoPK;
    @Basic(optional = false)
    @Column(name = "pk_final_res_mt")
    private double pkFinalResMt;
    @Basic(optional = false)
    @Column(name = "velocidad_max_ascendente")
    private double velocidadMaxAscendente;
    @Basic(optional = false)
    @Column(name = "velocidad_max_descendente")
    private double velocidadMaxDescendente;
    @JoinColumn(name = "id_marcha_tipo", referencedColumnName = "id_marcha_tipo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MarchaTipo marchaTipo;

    public RestriccionMarchaTipo() {
    }

    public RestriccionMarchaTipo(RestriccionMarchaTipoPK restriccionMarchaTipoPK) {
        this.restriccionMarchaTipoPK = restriccionMarchaTipoPK;
    }

    public RestriccionMarchaTipo(RestriccionMarchaTipoPK restriccionMarchaTipoPK, double pkFinalResMt, double velocidadMaxAscendente, double velocidadMaxDescendente) {
        this.restriccionMarchaTipoPK = restriccionMarchaTipoPK;
        this.pkFinalResMt = pkFinalResMt;
        this.velocidadMaxAscendente = velocidadMaxAscendente;
        this.velocidadMaxDescendente = velocidadMaxDescendente;
    }

    public RestriccionMarchaTipo(double idPkInicialResMt, int idMarchaTipo) {
        this.restriccionMarchaTipoPK = new RestriccionMarchaTipoPK(idPkInicialResMt, idMarchaTipo);
    }

    public RestriccionMarchaTipoPK getRestriccionMarchaTipoPK() {
        return restriccionMarchaTipoPK;
    }

    public void setRestriccionMarchaTipoPK(RestriccionMarchaTipoPK restriccionMarchaTipoPK) {
        this.restriccionMarchaTipoPK = restriccionMarchaTipoPK;
    }

    public double getPkFinalResMt() {
        return pkFinalResMt;
    }

    public void setPkFinalResMt(double pkFinalResMt) {
        this.pkFinalResMt = pkFinalResMt;
    }

    public double getVelocidadMaxAscendente() {
        return velocidadMaxAscendente;
    }

    public void setVelocidadMaxAscendente(double velocidadMaxAscendente) {
        this.velocidadMaxAscendente = velocidadMaxAscendente;
    }

    public double getVelocidadMaxDescendente() {
        return velocidadMaxDescendente;
    }

    public void setVelocidadMaxDescendente(double velocidadMaxDescendente) {
        this.velocidadMaxDescendente = velocidadMaxDescendente;
    }

    public MarchaTipo getMarchaTipo() {
        return marchaTipo;
    }

    public void setMarchaTipo(MarchaTipo marchaTipo) {
        this.marchaTipo = marchaTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (restriccionMarchaTipoPK != null ? restriccionMarchaTipoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RestriccionMarchaTipo)) {
            return false;
        }
        RestriccionMarchaTipo other = (RestriccionMarchaTipo) object;
        if ((this.restriccionMarchaTipoPK == null && other.restriccionMarchaTipoPK != null) || (this.restriccionMarchaTipoPK != null && !this.restriccionMarchaTipoPK.equals(other.restriccionMarchaTipoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.RestriccionMarchaTipo[ restriccionMarchaTipoPK=" + restriccionMarchaTipoPK + " ]";
    }
    
}
