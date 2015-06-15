/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entity;

import java.io.Serializable;
import java.sql.Date;
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
@Table(name = "restriccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Restriccion.findAll", query = "SELECT r FROM Restriccion r"),
    @NamedQuery(name = "Restriccion.listarIdRestr", query = "SELECT r.restriccionPK.idRestriccion FROM Restriccion r WHERE r.restriccionPK.idLinea = :idLinea ORDER BY r.restriccionPK.idRestriccion ASC"),
    @NamedQuery(name="Restriccion.findByRestriccionPK", query="SELECT r FROM Restriccion r WHERE r.restriccionPK.idLinea= :idLinea AND r.restriccionPK.idRestriccion= :idRestriccion"),
    @NamedQuery(name = "Restriccion.findByIdLineaYVelocidad", query = "SELECT r FROM Restriccion r WHERE r.restriccionPK.idLinea = :idLinea AND r.velocidadMaxAscendente <= :velocidadMax AND r.velocidadMaxDescendente <= :velocidadMax"),
    @NamedQuery(name = "Restriccion.restriccionEntreEstacionesAscendente", query = "SELECT r FROM Restriccion r WHERE r.restriccionPK.idLinea = :idLinea AND :progEstacionInicial <= r.progInicio AND r.progFinal <= :progEstacionFinal AND r.velocidadMaxAscendente<:vel ORDER BY r.progInicio ASC"),
    @NamedQuery(name = "Restriccion.restriccionEntreEstacionesDescendente", query = "SELECT r FROM Restriccion r WHERE r.restriccionPK.idLinea = :idLinea AND :progEstacionInicial >= r.progFinal AND r.progInicio >= :progEstacionFinal AND r.velocidadMaxDescendente<:vel ORDER BY r.progFinal DESC"),
    @NamedQuery(name = "Restriccion.findByIdLineaAscendente", query = "SELECT r FROM Restriccion r WHERE r.restriccionPK.idLinea = :idLinea ORDER BY r.progInicio ASC"),
    @NamedQuery(name = "Restriccion.findByIdLineaAscendenteDocTren", query = "SELECT r FROM Restriccion r WHERE r.restriccionPK.idLinea = :idLinea AND r.velocidadMaxAscendente < :vel ORDER BY r.progInicio ASC"),
    @NamedQuery(name = "Restriccion.findByFechaRegistro", query = "SELECT r FROM Restriccion r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Restriccion.findByIdLineaDescendente", query = "SELECT r FROM Restriccion r WHERE r.restriccionPK.idLinea = :idLinea ORDER BY r.progInicio DESC"),
    @NamedQuery(name = "Restriccion.findByIdLineaDescendenteDocTren", query = "SELECT r FROM Restriccion r WHERE r.restriccionPK.idLinea = :idLinea AND r.velocidadMaxDescendente< :vel ORDER BY r.progInicio DESC"),
    @NamedQuery(name = "Restriccion.findByIdLinea", query = "SELECT r FROM Restriccion r WHERE r.restriccionPK.idLinea = :idLinea"),
    @NamedQuery(name = "Restriccion.findByIdRestriccion", query = "SELECT r FROM Restriccion r WHERE r.restriccionPK.idRestriccion = :idRestriccion"),
    @NamedQuery(name = "Restriccion.findByUsuario", query = "SELECT r FROM Restriccion r WHERE r.usuario = :usuario"),
    @NamedQuery(name = "Restriccion.findByVelocidadMaxAscendente", query = "SELECT r FROM Restriccion r WHERE r.velocidadMaxAscendente = :velocidadMaxAscendente"),
    @NamedQuery(name = "Restriccion.findByVelocidadMaxDescendente", query = "SELECT r FROM Restriccion r WHERE r.velocidadMaxDescendente = :velocidadMaxDescendente"),
    @NamedQuery(name = "Restriccion.findByProgInicio", query = "SELECT r FROM Restriccion r WHERE r.progInicio = :progInicio"),
    @NamedQuery(name = "Restriccion.findByProgFinal", query = "SELECT r FROM Restriccion r WHERE r.progFinal = :progFinal")})
public class Restriccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RestriccionPK restriccionPK;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "velocidad_max_ascendente")
    private Double velocidadMaxAscendente;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
    @Column(name = "velocidad_max_descendente")
    private Double velocidadMaxDescendente;
    @Basic(optional = false)
    @Column(name = "prog_inicio")
    private double progInicio;
    @Basic(optional = false)
    @Column(name = "prog_final")
    private double progFinal;
    @Column(name = "observacion")
    private String observacion;
    @JoinColumn(name = "id_linea", referencedColumnName = "id_linea", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Linea linea;

    public Restriccion() {
    }

    public Restriccion(RestriccionPK restriccionPK) {
        this.restriccionPK = restriccionPK;
    }

    public Restriccion(RestriccionPK restriccionPK, String usuario, double progInicio, double progFinal,Date fechaRegistro) {
        this.restriccionPK = restriccionPK;
        this.usuario = usuario;
        this.progInicio = progInicio;
        this.progFinal = progFinal;
        this.fechaRegistro=fechaRegistro;
    }

    public Restriccion(int idLinea, int idRestriccion) {
        this.restriccionPK = new RestriccionPK(idLinea, idRestriccion);
    }

    public RestriccionPK getRestriccionPK() {
        return restriccionPK;
    }

    public void setRestriccionPK(RestriccionPK restriccionPK) {
        this.restriccionPK = restriccionPK;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

    public double getProgInicio() {
        return progInicio;
    }

    public void setProgInicio(double progInicio) {
        this.progInicio = progInicio;
    }

    public double getProgFinal() {
        return progFinal;
    }

    public void setProgFinal(double progFinal) {
        this.progFinal = progFinal;
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
        hash += (restriccionPK != null ? restriccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Restriccion)) {
            return false;
        }
        Restriccion other = (Restriccion) object;
        if ((this.restriccionPK == null && other.restriccionPK != null) || (this.restriccionPK != null && !this.restriccionPK.equals(other.restriccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.Restriccion[ restriccionPK=" + restriccionPK + " ]";
    }
    
}
