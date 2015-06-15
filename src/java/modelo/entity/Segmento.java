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
@Table(name = "segmento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Segmento.findAll", query = "SELECT s FROM Segmento s"),
    @NamedQuery(name = "Segmento.findBySegmentoPK", query = "SELECT s FROM Segmento s WHERE s.segmentoPK.idPkInicial= :idPkInicial AND s.segmentoPK.idLinea= :idLinea"),
    @NamedQuery(name = "Segmento.findByIdLineaAscendente", query = "SELECT s FROM Segmento s WHERE s.segmentoPK.idLinea = :idLinea ORDER BY s.segmentoPK.idPkInicial ASC"),
    @NamedQuery(name = "Segmento.findByIdLineaDescendente", query = "SELECT s FROM Segmento s WHERE s.segmentoPK.idLinea = :idLinea ORDER BY s.segmentoPK.idPkInicial DESC"),
    @NamedQuery(name = "Segmento.findByIdLinea", query = "SELECT s FROM Segmento s WHERE s.segmentoPK.idLinea = :idLinea"),
    @NamedQuery(name = "Segmento.findByIdPkInicial", query = "SELECT s FROM Segmento s WHERE s.segmentoPK.idPkInicial = :idPkInicial"),
    @NamedQuery(name = "Segmento.findByPkFinal", query = "SELECT s FROM Segmento s WHERE s.pkFinal = :pkFinal"),
    @NamedQuery(name = "Segmento.findByRecta", query = "SELECT s FROM Segmento s WHERE s.recta = :recta"),
    @NamedQuery(name = "Segmento.findByRadioCurvatura", query = "SELECT s FROM Segmento s WHERE s.radioCurvatura = :radioCurvatura"),
    @NamedQuery(name = "Segmento.findByGradiente", query = "SELECT s FROM Segmento s WHERE s.gradiente = :gradiente"),
    @NamedQuery(name = "Segmento.findByRampaAscendente", query = "SELECT s FROM Segmento s WHERE s.rampaAscendente = :rampaAscendente"),
    @NamedQuery(name = "Segmento.findByRampaDescendente", query = "SELECT s FROM Segmento s WHERE s.rampaDescendente = :rampaDescendente"),
    @NamedQuery(name = "Segmento.findByVelocidadMaxAscendente", query = "SELECT s FROM Segmento s WHERE s.velocidadMaxAscendente = :velocidadMaxAscendente"),
    @NamedQuery(name = "Segmento.findByVelocidadMaxDescendente", query = "SELECT s FROM Segmento s WHERE s.velocidadMaxDescendente = :velocidadMaxDescendente")})
public class Segmento implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SegmentoPK segmentoPK;
    @Basic(optional = false)
    @Column(name = "pk_final")
    private double pkFinal;
    @Basic(optional = false)
    @Column(name = "recta")
    private boolean recta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "radio_curvatura")
    private Double radioCurvatura;
    @Basic(optional = false)
    @Column(name = "gradiente")
    private double gradiente;
    @Column(name = "rampa_ascendente")
    private Double rampaAscendente;
    @Column(name = "rampa_descendente")
    private Double rampaDescendente;
    @Column(name = "velocidad_max_ascendente")
    private Double velocidadMaxAscendente;
    @Column(name = "velocidad_max_descendente")
    private Double velocidadMaxDescendente;
    @JoinColumn(name = "id_linea", referencedColumnName = "id_linea", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Linea linea;

    public Segmento() {
    }

    public Segmento(SegmentoPK segmentoPK) {
        this.segmentoPK = segmentoPK;
    }

    public Segmento(SegmentoPK segmentoPK, double pkFinal, boolean recta, double gradiente) {
        this.segmentoPK = segmentoPK;
        this.pkFinal = pkFinal;
        this.recta = recta;
        this.gradiente = gradiente;
    }

    public Segmento(int idLinea, double idPkInicial) {
        this.segmentoPK = new SegmentoPK(idLinea, idPkInicial);
    }

    public SegmentoPK getSegmentoPK() {
        return segmentoPK;
    }

    public void setSegmentoPK(SegmentoPK segmentoPK) {
        this.segmentoPK = segmentoPK;
    }

    public double getPkFinal() {
        return pkFinal;
    }

    public void setPkFinal(double pkFinal) {
        this.pkFinal = pkFinal;
    }

    public boolean getRecta() {
        return recta;
    }

    public void setRecta(boolean recta) {
        this.recta = recta;
    }

    public Double getRadioCurvatura() {
        return radioCurvatura;
    }

    public void setRadioCurvatura(Double radioCurvatura) {
        this.radioCurvatura = radioCurvatura;
    }

    public double getGradiente() {
        return gradiente;
    }

    public void setGradiente(double gradiente) {
        this.gradiente = gradiente;
    }

    public Double getRampaAscendente() {
        return rampaAscendente;
    }

    public void setRampaAscendente(Double rampaAscendente) {
        this.rampaAscendente = rampaAscendente;
    }

    public Double getRampaDescendente() {
        return rampaDescendente;
    }

    public void setRampaDescendente(Double rampaDescendente) {
        this.rampaDescendente = rampaDescendente;
    }

    public Double getVelocidadMaxAscendente() {
        return velocidadMaxAscendente;
    }

    public void setVelocidadMaxAscendente(Double velocidadMaxAscendente) {
        this.velocidadMaxAscendente = velocidadMaxAscendente;
    }

    public Double getVelocidadMaxDescendente() {
        return velocidadMaxDescendente;
    }

    public void setVelocidadMaxDescendente(Double velocidadMaxDescendente) {
        this.velocidadMaxDescendente = velocidadMaxDescendente;
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
        hash += (segmentoPK != null ? segmentoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Segmento)) {
            return false;
        }
        Segmento other = (Segmento) object;
        if ((this.segmentoPK == null && other.segmentoPK != null) || (this.segmentoPK != null && !this.segmentoPK.equals(other.segmentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.Segmento[ segmentoPK=" + segmentoPK + " ]";
    }
    
}
