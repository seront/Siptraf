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
import javax.persistence.JoinColumns;
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
@Table(name = "estacion_tramo_parada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstacionTramoParada.findAll", query = "SELECT e FROM EstacionTramoParada e"),
    @NamedQuery(name = "EstacionTramoParada.findByIdProgramacionHoraria&&IdPreCirculacion", query = "SELECT e FROM EstacionTramoParada e WHERE e.estacionTramoParadaPK.idProgramacionHoraria = :idProgramacionHoraria AND e.estacionTramoParadaPK.idPreCirculacion = :idPreCirculacion ORDER BY e.estacionTramoParadaPK.idEstacion ASC"),
    @NamedQuery(name = "EstacionTramoParada.findByIdEstacion", query = "SELECT e FROM EstacionTramoParada e WHERE e.estacionTramoParadaPK.idEstacion = :idEstacion"),
    @NamedQuery(name = "EstacionTramoParada.findByIdPreCirculacion", query = "SELECT e FROM EstacionTramoParada e WHERE e.estacionTramoParadaPK.idPreCirculacion = :idPreCirculacion"),
    @NamedQuery(name = "EstacionTramoParada.findByIdRuta", query = "SELECT e FROM EstacionTramoParada e WHERE e.estacionTramoParadaPK.idRuta = :idRuta"),
    @NamedQuery(name = "EstacionTramoParada.findByIdProgramacionHoraria", query = "SELECT e FROM EstacionTramoParada e WHERE e.estacionTramoParadaPK.idProgramacionHoraria = :idProgramacionHoraria"),
    @NamedQuery(name = "EstacionTramoParada.findByTiempo", query = "SELECT e FROM EstacionTramoParada e WHERE e.tiempo = :tiempo"),
    @NamedQuery(name = "EstacionTramoParada.findByParada", query = "SELECT e FROM EstacionTramoParada e WHERE e.parada = :parada")})
public class EstacionTramoParada implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EstacionTramoParadaPK estacionTramoParadaPK;
    @Basic(optional = false)
    @Column(name = "tiempo")
    private int tiempo;
    @Basic(optional = false)
    @Column(name = "parada")
    private int parada;
    @JoinColumns({
        @JoinColumn(name = "id_pre_circulacion", referencedColumnName = "id_pre_circulacion", insertable = false, updatable = false),
        @JoinColumn(name = "id_ruta", referencedColumnName = "id_ruta", insertable = false, updatable = false),
        @JoinColumn(name = "id_programacion_horaria", referencedColumnName = "id_programacion_horaria", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private PreCirculacion preCirculacion;

    public EstacionTramoParada() {
    }

    public EstacionTramoParada(EstacionTramoParadaPK estacionTramoParadaPK) {
        this.estacionTramoParadaPK = estacionTramoParadaPK;
    }

    public EstacionTramoParada(EstacionTramoParadaPK estacionTramoParadaPK, int tiempo, int parada) {
        this.estacionTramoParadaPK = estacionTramoParadaPK;
        this.tiempo = tiempo;
        this.parada = parada;
    }

    public EstacionTramoParada(double idEstacion, int idPreCirculacion, int idRuta, int idProgramacionHoraria) {
        this.estacionTramoParadaPK = new EstacionTramoParadaPK(idEstacion, idPreCirculacion, idRuta, idProgramacionHoraria);
    }

    public EstacionTramoParadaPK getEstacionTramoParadaPK() {
        return estacionTramoParadaPK;
    }

    public void setEstacionTramoParadaPK(EstacionTramoParadaPK estacionTramoParadaPK) {
        this.estacionTramoParadaPK = estacionTramoParadaPK;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getParada() {
        return parada;
    }

    public void setParada(int parada) {
        this.parada = parada;
    }

    public PreCirculacion getPreCirculacion() {
        return preCirculacion;
    }

    public void setPreCirculacion(PreCirculacion preCirculacion) {
        this.preCirculacion = preCirculacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estacionTramoParadaPK != null ? estacionTramoParadaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstacionTramoParada)) {
            return false;
        }
        EstacionTramoParada other = (EstacionTramoParada) object;
        if ((this.estacionTramoParadaPK == null && other.estacionTramoParadaPK != null) || (this.estacionTramoParadaPK != null && !this.estacionTramoParadaPK.equals(other.estacionTramoParadaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.EstacionTramoParada[ estacionTramoParadaPK=" + estacionTramoParadaPK + " ]";
    }
    
}
