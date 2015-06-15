/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kelvins Insua
 */
@Entity
@Table(name = "linea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Linea.findAll", query = "SELECT l FROM Linea l"),
    @NamedQuery(name = "Linea.findByIdLinea", query = "SELECT l FROM Linea l WHERE l.idLinea = :idLinea"),
    @NamedQuery(name = "Linea.listarId", query = "SELECT l.idLinea FROM Linea l ORDER BY l.idLinea ASC"),
    @NamedQuery(name = "Linea.findByPkInicial", query = "SELECT l FROM Linea l WHERE l.pkInicial = :pkInicial"),
    @NamedQuery(name = "Linea.findByPkFinal", query = "SELECT l FROM Linea l WHERE l.pkFinal = :pkFinal"),
    @NamedQuery(name = "Linea.findByTrocha", query = "SELECT l FROM Linea l WHERE l.trocha = :trocha"),
    @NamedQuery(name = "Linea.findByNombreLinea", query = "SELECT l FROM Linea l WHERE l.nombreLinea = :nombreLinea"),
    @NamedQuery(name = "Linea.findByTipoLinea", query = "SELECT l FROM Linea l WHERE l.tipoLinea = :tipoLinea")})
public class Linea implements Serializable {
     @OneToMany(cascade = CascadeType.ALL, mappedBy = "linea")
    private List<Afluencia> afluenciaList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_linea")
    private Integer idLinea;
    @Basic(optional = false)
    @Column(name = "pk_inicial")
    private double pkInicial;
    @Basic(optional = false)
    @Column(name = "pk_final")
    private double pkFinal;
    @Basic(optional = false)
    @Column(name = "trocha")
    private double trocha;
    @Basic(optional = false)
    @Column(name = "velocidad_linea")
    private double velocidadLinea;
    @Basic(optional = false)
    @Column(name = "nombre_linea")
    private String nombreLinea;
    @Basic(optional = false)
    @Column(name = "tipo_linea")
    private boolean tipoLinea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "linea")
    private List<Restriccion> restriccionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "linea")
    private List<Segmento> segmentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "linea")
    private List<Estacion> estacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "linea")
    private List<CircuitoVia> circuitoViaList;

    public Linea() {
    }

    public Linea(Integer idLinea) {
        this.idLinea = idLinea;
    }

    public Linea(Integer idLinea, double pkInicial, double pkFinal, double trocha, String nombreLinea, boolean tipoLinea, double velocidadLinea) {
        this.idLinea = idLinea;
        this.pkInicial = pkInicial;
        this.pkFinal = pkFinal;
        this.trocha = trocha;
        this.nombreLinea = nombreLinea;
        this.tipoLinea = tipoLinea;
        this.velocidadLinea =velocidadLinea;
    }

    public Integer getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Integer idLinea) {
        this.idLinea = idLinea;
    }

    public double getPkInicial() {
        return pkInicial;
    }

    public void setPkInicial(double pkInicial) {
        this.pkInicial = pkInicial;
    }

    public double getPkFinal() {
        return pkFinal;
    }

    public void setPkFinal(double pkFinal) {
        this.pkFinal = pkFinal;
    }

    public double getTrocha() {
        return trocha;
    }

    public void setTrocha(double trocha) {
        this.trocha = trocha;
    }

    public String getNombreLinea() {
        return nombreLinea;
    }

    public void setNombreLinea(String nombreLinea) {
        this.nombreLinea = nombreLinea;
    }

    public boolean getTipoLinea() {
        return tipoLinea;
    }

    public void setTipoLinea(boolean tipoLinea) {
        this.tipoLinea = tipoLinea;
    }

    public double getVelocidadLinea() {
        return velocidadLinea;
    }

    public void setVelocidadLinea(double velocidadLinea) {
        this.velocidadLinea = velocidadLinea;
    }
    

    @XmlTransient
    public List<Restriccion> getRestriccionList() {
        return restriccionList;
    }

    public void setRestriccionList(List<Restriccion> restriccionList) {
        this.restriccionList = restriccionList;
    }

    @XmlTransient
    public List<Segmento> getSegmentoList() {
        return segmentoList;
    }

    public void setSegmentoList(List<Segmento> segmentoList) {
        this.segmentoList = segmentoList;
    }

    @XmlTransient
    public List<Estacion> getEstacionList() {
        return estacionList;
    }

    public void setEstacionList(List<Estacion> estacionList) {
        this.estacionList = estacionList;
    }

    @XmlTransient
    public List<CircuitoVia> getCircuitoViaList() {
        return circuitoViaList;
    }

    public void setCircuitoViaList(List<CircuitoVia> circuitoViaList) {
        this.circuitoViaList = circuitoViaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLinea != null ? idLinea.hashCode() : 0);
        return hash;
    }
    
    
    @XmlTransient
    public List<Afluencia> getAfluenciaList() {
        return afluenciaList;
    }

    public void setAfluenciaList(List<Afluencia> afluenciaList) {
        this.afluenciaList = afluenciaList;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Linea)) {
            return false;
        }
        Linea other = (Linea) object;
        if ((this.idLinea == null && other.idLinea != null) || (this.idLinea != null && !this.idLinea.equals(other.idLinea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.Linea[ idLinea=" + idLinea + " ]";
    }
    
}
