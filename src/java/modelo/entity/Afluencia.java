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
 * @author seront
 */
@Entity
@Table(name = "afluencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Afluencia.findAll", query = "SELECT a FROM Afluencia a"),
    @NamedQuery(name = "Afluencia.findByIdLinea", query = "SELECT a FROM Afluencia a WHERE a.afluenciaPK.idLinea = :idLinea"),
    @NamedQuery(name = "Afluencia.findByAfluencia", query = "SELECT a FROM Afluencia a WHERE a.afluencia = :afluencia"),
    @NamedQuery(name = "Afluencia.findByConsolidado", query = "SELECT a FROM Afluencia a WHERE a.consolidado = :consolidado"),
    @NamedQuery(name = "Afluencia.findByIdPkEstacion", query = "SELECT a FROM Afluencia a WHERE a.afluenciaPK.idPkEstacion = :idPkEstacion"),
    // Creado el 10-06-2015
    @NamedQuery(name = "Afluencia.findByIdLineaYIdPkEstacion", query = "SELECT a FROM Afluencia a WHERE a.afluenciaPK.idLinea = :idLinea AND a.afluenciaPK.idPkEstacion = :idPkEstacion"),
    @NamedQuery(name = "Afluencia.findByIdFechaInicial", query = "SELECT a FROM Afluencia a WHERE a.afluenciaPK.idFechaInicial = :idFechaInicial"),
    @NamedQuery(name = "Afluencia.findByFechaFinal", query = "SELECT a FROM Afluencia a WHERE a.fechaFinal = :fechaFinal"),
    @NamedQuery(name = "Afluencia.listaPorDia", query = "SELECT a FROM Afluencia a WHERE a.afluenciaPK.idLinea = :idLinea AND a.afluenciaPK.idPkEstacion =:idPkEstacion AND :fechaMinima <= a.afluenciaPK.idFechaInicial AND a.afluenciaPK.idFechaInicial < :fechaMaxima ORDER BY a.afluenciaPK.idFechaInicial ASC")})
public class Afluencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AfluenciaPK afluenciaPK;
    @Column(name = "afluencia")
    private Integer afluencia;
    @Column(name = "consolidado")
    private Boolean consolidado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fecha_final")
    private Double fechaFinal;
    @JoinColumn(name = "id_linea", referencedColumnName = "id_linea", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Linea linea;

    public Afluencia() {
    }

    public Afluencia(AfluenciaPK afluenciaPK) {
        this.afluenciaPK = afluenciaPK;
    }

    public Afluencia(int idLinea, double idPkEstacion, double idFechaInicial) {
        this.afluenciaPK = new AfluenciaPK(idLinea, idPkEstacion, idFechaInicial);
    }

    public AfluenciaPK getAfluenciaPK() {
        return afluenciaPK;
    }

    public void setAfluenciaPK(AfluenciaPK afluenciaPK) {
        this.afluenciaPK = afluenciaPK;
    }

    public Integer getAfluencia() {
        return afluencia;
    }

    public void setAfluencia(Integer afluencia) {
        this.afluencia = afluencia;
    }

    public Boolean getConsolidado() {
        return consolidado;
    }

    public void setConsolidado(Boolean consolidado) {
        this.consolidado = consolidado;
    }

    public Double getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Double fechaFinal) {
        this.fechaFinal = fechaFinal;
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
        hash += (afluenciaPK != null ? afluenciaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Afluencia)) {
            return false;
        }
        Afluencia other = (Afluencia) object;
        if ((this.afluenciaPK == null && other.afluenciaPK != null) || (this.afluenciaPK != null && !this.afluenciaPK.equals(other.afluenciaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.Afluencia[ afluenciaPK=" + afluenciaPK + " ]";
    }
    
}
