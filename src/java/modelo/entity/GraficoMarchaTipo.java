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
@Table(name = "grafico_marcha_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GraficoMarchaTipo.findAll", query = "SELECT g FROM GraficoMarchaTipo g"),
    @NamedQuery(name = "GraficoMarchaTipo.findByIdGraficoMarchaTipo", query = "SELECT g FROM GraficoMarchaTipo g WHERE g.graficoMarchaTipoPK.idGraficoMarchaTipo = :idGraficoMarchaTipo"),
    @NamedQuery(name = "GraficoMarchaTipo.findByIdMarchaTipo", query = "SELECT g FROM GraficoMarchaTipo g WHERE g.graficoMarchaTipoPK.idMarchaTipo = :idMarchaTipo"),
    @NamedQuery(name = "GraficoMarchaTipo.findByProgresiva", query = "SELECT g FROM GraficoMarchaTipo g WHERE g.progresiva = :progresiva"),
    @NamedQuery(name = "GraficoMarchaTipo.findByVelocidad", query = "SELECT g FROM GraficoMarchaTipo g WHERE g.velocidad = :velocidad"),
    @NamedQuery(name = "GraficoMarchaTipo.findByTiempo", query = "SELECT g FROM GraficoMarchaTipo g WHERE g.tiempo = :tiempo")})
public class GraficoMarchaTipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GraficoMarchaTipoPK graficoMarchaTipoPK;
    @Basic(optional = false)
    @Column(name = "progresiva")
    private double progresiva;
    @Basic(optional = false)
    @Column(name = "tiempo")
    private String tiempo;
    @Basic(optional = false)
    @Column(name = "velocidad")
    private double velocidad;
    @JoinColumn(name = "id_marcha_tipo", referencedColumnName = "id_marcha_tipo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MarchaTipo marchaTipo;

    public GraficoMarchaTipo() {
    }

    public GraficoMarchaTipo(GraficoMarchaTipoPK graficoMarchaTipoPK) {
        this.graficoMarchaTipoPK = graficoMarchaTipoPK;
    }

    public GraficoMarchaTipo(GraficoMarchaTipoPK graficoMarchaTipoPK, double progresiva, double velocidad,String tiempo) {
        this.graficoMarchaTipoPK = graficoMarchaTipoPK;
        this.progresiva = progresiva;
        this.velocidad = velocidad;
        this.tiempo=tiempo;
    }

    public GraficoMarchaTipo(int idGraficoMarchaTipo, int idMarchaTipo) {
        this.graficoMarchaTipoPK = new GraficoMarchaTipoPK(idGraficoMarchaTipo, idMarchaTipo);
    }

    public GraficoMarchaTipoPK getGraficoMarchaTipoPK() {
        return graficoMarchaTipoPK;
    }

    public void setGraficoMarchaTipoPK(GraficoMarchaTipoPK graficoMarchaTipoPK) {
        this.graficoMarchaTipoPK = graficoMarchaTipoPK;
    }

    public double getProgresiva() {
        return progresiva;
    }

    public void setProgresiva(double progresiva) {
        this.progresiva = progresiva;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public MarchaTipo getMarchaTipo() {
        return marchaTipo;
    }

    public void setMarchaTipo(MarchaTipo marchaTipo) {
        this.marchaTipo = marchaTipo;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (graficoMarchaTipoPK != null ? graficoMarchaTipoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GraficoMarchaTipo)) {
            return false;
        }
        GraficoMarchaTipo other = (GraficoMarchaTipo) object;
        if ((this.graficoMarchaTipoPK == null && other.graficoMarchaTipoPK != null) || (this.graficoMarchaTipoPK != null && !this.graficoMarchaTipoPK.equals(other.graficoMarchaTipoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.GraficoMarchaTipo[ graficoMarchaTipoPK=" + graficoMarchaTipoPK + " ]";
    }
    
}
