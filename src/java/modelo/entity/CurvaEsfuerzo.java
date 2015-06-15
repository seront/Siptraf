/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entity;

import java.io.Serializable;
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
@Table(name = "curva_esfuerzo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CurvaEsfuerzo.findAll", query = "SELECT c FROM CurvaEsfuerzo c"),
    @NamedQuery(name = "CurvaEsfuerzo.findByIdMaterialRodante", query = "SELECT c FROM CurvaEsfuerzo c WHERE c.curvaEsfuerzoPK.idMaterialRodante = :idMaterialRodante ORDER BY c.curvaEsfuerzoPK.idVelocidadCurvaEsfuerzo ASC"),
    @NamedQuery(name = "CurvaEsfuerzo.findByIdVelocidadCurvaEsfuerzo", query = "SELECT c FROM CurvaEsfuerzo c WHERE c.curvaEsfuerzoPK.idVelocidadCurvaEsfuerzo = :idVelocidadCurvaEsfuerzo"),
    @NamedQuery(name = "CurvaEsfuerzo.findByEsfuerzoTraccion", query = "SELECT c FROM CurvaEsfuerzo c WHERE c.esfuerzoTraccion = :esfuerzoTraccion"),
    @NamedQuery(name = "CurvaEsfuerzo.findByEsfuerzoFrenado", query = "SELECT c FROM CurvaEsfuerzo c WHERE c.esfuerzoFrenado = :esfuerzoFrenado")})
public class CurvaEsfuerzo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CurvaEsfuerzoPK curvaEsfuerzoPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "esfuerzo_traccion")
    private Double esfuerzoTraccion;
    @Column(name = "esfuerzo_frenado")
    private Double esfuerzoFrenado;
    @JoinColumn(name = "id_material_rodante", referencedColumnName = "id_material_rodante", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MaterialRodante materialRodante;

    public CurvaEsfuerzo() {
    }

    public CurvaEsfuerzo(CurvaEsfuerzoPK curvaEsfuerzoPK) {
        this.curvaEsfuerzoPK = curvaEsfuerzoPK;
    }

    public CurvaEsfuerzo(int idMaterialRodante, double idVelocidadCurvaEsfuerzo) {
        this.curvaEsfuerzoPK = new CurvaEsfuerzoPK(idMaterialRodante, idVelocidadCurvaEsfuerzo);
    }

    public CurvaEsfuerzoPK getCurvaEsfuerzoPK() {
        return curvaEsfuerzoPK;
    }

    public void setCurvaEsfuerzoPK(CurvaEsfuerzoPK curvaEsfuerzoPK) {
        this.curvaEsfuerzoPK = curvaEsfuerzoPK;
    }

    public Double getEsfuerzoTraccion() {
        return esfuerzoTraccion;
    }

    public void setEsfuerzoTraccion(Double esfuerzoTraccion) {
        this.esfuerzoTraccion = esfuerzoTraccion;
    }

    public Double getEsfuerzoFrenado() {
        return esfuerzoFrenado;
    }

    public void setEsfuerzoFrenado(Double esfuerzoFrenado) {
        this.esfuerzoFrenado = esfuerzoFrenado;
    }

    public MaterialRodante getMaterialRodante() {
        return materialRodante;
    }

    public void setMaterialRodante(MaterialRodante materialRodante) {
        this.materialRodante = materialRodante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (curvaEsfuerzoPK != null ? curvaEsfuerzoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CurvaEsfuerzo)) {
            return false;
        }
        CurvaEsfuerzo other = (CurvaEsfuerzo) object;
        if ((this.curvaEsfuerzoPK == null && other.curvaEsfuerzoPK != null) || (this.curvaEsfuerzoPK != null && !this.curvaEsfuerzoPK.equals(other.curvaEsfuerzoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.CurvaEsfuerzo[ curvaEsfuerzoPK=" + curvaEsfuerzoPK + " ]";
    }
    
}
