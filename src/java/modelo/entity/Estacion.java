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
@Table(name = "estacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estacion.findAll", query = "SELECT e FROM Estacion e"),
    @NamedQuery(name = "Estacion.findByEstacionesParaMTAscendente", query = "SELECT e FROM Estacion e WHERE e.estacionPK.idPkEstacion >= :progEstInicio AND e.estacionPK.idPkEstacion <= :progEstFinal AND e.estacionPK.idLinea= :idLinea ORDER BY e.estacionPK.idPkEstacion ASC"),
    @NamedQuery(name = "Estacion.findByEstacionesParaMTAscendenteConParada", query = "SELECT e FROM Estacion e WHERE e.estacionPK.idPkEstacion > :progEstInicio AND e.estacionPK.idPkEstacion < :progEstFinal AND e.estacionPK.idLinea= :idLinea ORDER BY e.estacionPK.idPkEstacion ASC"),
    @NamedQuery(name = "Estacion.findByEstacionesParaMTDescendente", query = "SELECT e FROM Estacion e WHERE e.estacionPK.idPkEstacion <= :progEstInicio AND e.estacionPK.idPkEstacion >= :progEstFinal AND e.estacionPK.idLinea= :idLinea ORDER BY e.estacionPK.idPkEstacion DESC"),
    @NamedQuery(name = "Estacion.findByEstacionesParaMTDescendenteConParada", query = "SELECT e FROM Estacion e WHERE e.estacionPK.idPkEstacion < :progEstInicio AND e.estacionPK.idPkEstacion > :progEstFinal AND e.estacionPK.idLinea= :idLinea ORDER BY e.estacionPK.idPkEstacion DESC"),
    @NamedQuery(name = "Estacion.findByEstacionPK", query = "SELECT e FROM Estacion e WHERE e.estacionPK.idLinea= :idLinea AND e.estacionPK.idPkEstacion= :idPkEstacion"),
    @NamedQuery(name = "Estacion.findByIdLineaAscendente", query = "SELECT e FROM Estacion e WHERE e.estacionPK.idLinea = :idLinea ORDER BY e.estacionPK.idPkEstacion ASC"),
    @NamedQuery(name = "Estacion.findByIdLineaDescendente", query = "SELECT e FROM Estacion e WHERE e.estacionPK.idLinea = :idLinea ORDER BY e.estacionPK.idPkEstacion DESC"),
    @NamedQuery(name = "Estacion.findByIdLinea", query = "SELECT e FROM Estacion e WHERE e.estacionPK.idLinea = :idLinea"),
    @NamedQuery(name = "Estacion.findByIdPkEstacion", query = "SELECT e FROM Estacion e WHERE e.estacionPK.idPkEstacion = :idPkEstacion"),
    @NamedQuery(name = "Estacion.findByNombreEstacion", query = "SELECT e FROM Estacion e WHERE e.nombreEstacion = :nombreEstacion")})
public class Estacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EstacionPK estacionPK;
    @Basic(optional = false)
    @Column(name = "nombre_estacion")
    private String nombreEstacion;
    @Basic(optional = false)
    @Column(name = "abrev_estacion")
    private String abrevEstacion;
    @JoinColumn(name = "id_linea", referencedColumnName = "id_linea", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Linea linea;

    public Estacion() {
    }

    public Estacion(EstacionPK estacionPK) {
        this.estacionPK = estacionPK;
    }

    public Estacion(EstacionPK estacionPK, String nombreEstacion,String abrevEstacion) {
        this.estacionPK = estacionPK;
        this.nombreEstacion = nombreEstacion;
        this.abrevEstacion = abrevEstacion;
    }

    public Estacion(int idLinea, double idPkEstacion) {
        this.estacionPK = new EstacionPK(idLinea, idPkEstacion);
    }

    public EstacionPK getEstacionPK() {
        return estacionPK;
    }

    public void setEstacionPK(EstacionPK estacionPK) {
        this.estacionPK = estacionPK;
    }

    public String getNombreEstacion() {
        return nombreEstacion;
    }

    public void setNombreEstacion(String nombreEstacion) {
        this.nombreEstacion = nombreEstacion;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }

    public String getAbrevEstacion() {
        return abrevEstacion;
    }

    public void setAbrevEstacion(String abrevEstacion) {
        this.abrevEstacion = abrevEstacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estacionPK != null ? estacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estacion)) {
            return false;
        }
        Estacion other = (Estacion) object;
        if ((this.estacionPK == null && other.estacionPK != null) || (this.estacionPK != null && !this.estacionPK.equals(other.estacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.Estacion[ estacionPK=" + estacionPK + " ]";
    }
    
}
