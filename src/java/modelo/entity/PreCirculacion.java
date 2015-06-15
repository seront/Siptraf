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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "pre_circulacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreCirculacion.findAll", query = "SELECT p FROM PreCirculacion p"),
    @NamedQuery(name = "PreCirculacion.buscarPorRuta", query = "SELECT p FROM PreCirculacion p WHERE p.preCirculacionPK.idProgramacionHoraria= :idProgramacionHoraria AND p.preCirculacionPK.idRuta= :idRuta"),
    @NamedQuery(name = "PreCirculacion.buscarIdPH", query = "SELECT p FROM PreCirculacion p WHERE p.ruta.rutaPK.idProgramacionHoraria= :idProgramacionHoraria AND p.preCirculacionPK.idPreCirculacion= :idPrecirculacion"),
    @NamedQuery(name = "PreCirculacion.listarId", query = "SELECT p.preCirculacionPK.idPreCirculacion FROM PreCirculacion p WHERE p.preCirculacionPK.idProgramacionHoraria = :idProgramacionHoraria ORDER BY p.preCirculacionPK.idPreCirculacion ASC"),
    @NamedQuery(name = "PreCirculacion.buscarIdMax", query = "SELECT MAX(p.preCirculacionPK.idPreCirculacion) FROM PreCirculacion p WHERE p.preCirculacionPK.idProgramacionHoraria = :idProgramacionHoraria"),
    @NamedQuery(name = "PreCirculacion.findByIdPreCirculacion", query = "SELECT p FROM PreCirculacion p WHERE p.preCirculacionPK.idPreCirculacion = :idPreCirculacion"),
    @NamedQuery(name = "PreCirculacion.findByIdRuta", query = "SELECT p FROM PreCirculacion p WHERE p.preCirculacionPK.idRuta = :idRuta"),
    @NamedQuery(name = "PreCirculacion.findByIdProgramacionHoraria", query = "SELECT p FROM PreCirculacion p WHERE p.preCirculacionPK.idProgramacionHoraria = :idProgramacionHoraria"),
    @NamedQuery(name = "PreCirculacion.findByMarchaTipo", query = "SELECT p FROM PreCirculacion p WHERE p.marchaTipo = :marchaTipo"),
    @NamedQuery(name = "PreCirculacion.findByEstacionSalida", query = "SELECT p FROM PreCirculacion p WHERE p.estacionSalida = :estacionSalida"),
    @NamedQuery(name = "PreCirculacion.findByEstacionLlegada", query = "SELECT p FROM PreCirculacion p WHERE p.estacionLlegada = :estacionLlegada"),
    @NamedQuery(name = "PreCirculacion.findByPrefijoNumeracion", query = "SELECT p FROM PreCirculacion p WHERE p.prefijoNumeracion = :prefijoNumeracion"),
    @NamedQuery(name = "PreCirculacion.findByHoraInicial", query = "SELECT p FROM PreCirculacion p WHERE p.horaInicial = :horaInicial"),
    @NamedQuery(name = "PreCirculacion.findByColor", query = "SELECT p FROM PreCirculacion p WHERE p.color = :color")})
public class PreCirculacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreCirculacionPK preCirculacionPK;
    @Basic(optional = false)
    @Column(name = "marcha_tipo")
    private int marchaTipo;
    @Basic(optional = false)
    @Column(name = "estacion_salida")
    private double estacionSalida;
    @Basic(optional = false)
    @Column(name = "estacion_llegada")
    private double estacionLlegada;
    @Basic(optional = false)
    @Column(name = "prefijo_numeracion")
    private int prefijoNumeracion;
    @Basic(optional = false)
    @Column(name = "hora_inicial")
    private double horaInicial;
    @Basic(optional = false)
    @Column(name = "color")
    private String color;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preCirculacion")
    private List<EstacionTramoParada> estacionTramoParadaList;
    @JoinColumns({
        @JoinColumn(name = "id_ruta", referencedColumnName = "id_ruta", insertable = false, updatable = false),
        @JoinColumn(name = "id_programacion_horaria", referencedColumnName = "id_programacion_horaria", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Ruta ruta;

    public PreCirculacion() {
    }

    public PreCirculacion(PreCirculacionPK preCirculacionPK) {
        this.preCirculacionPK = preCirculacionPK;
    }

    public PreCirculacion(PreCirculacionPK preCirculacionPK, int marchaTipo, double estacionSalida, double estacionLlegada, int prefijoNumeracion, double horaInicial, String color) {
        this.preCirculacionPK = preCirculacionPK;
        this.marchaTipo = marchaTipo;
        this.estacionSalida = estacionSalida;
        this.estacionLlegada = estacionLlegada;
        this.prefijoNumeracion = prefijoNumeracion;
        this.horaInicial = horaInicial;
        this.color = color;
    }

    public PreCirculacion(int idPreCirculacion, int idRuta, int idProgramacionHoraria) {
        this.preCirculacionPK = new PreCirculacionPK(idPreCirculacion, idRuta, idProgramacionHoraria);
    }

    public PreCirculacionPK getPreCirculacionPK() {
        return preCirculacionPK;
    }

    public void setPreCirculacionPK(PreCirculacionPK preCirculacionPK) {
        this.preCirculacionPK = preCirculacionPK;
    }

    public int getMarchaTipo() {
        return marchaTipo;
    }

    public void setMarchaTipo(int marchaTipo) {
        this.marchaTipo = marchaTipo;
    }

    public double getEstacionSalida() {
        return estacionSalida;
    }

    public void setEstacionSalida(double estacionSalida) {
        this.estacionSalida = estacionSalida;
    }

    public double getEstacionLlegada() {
        return estacionLlegada;
    }

    public void setEstacionLlegada(double estacionLlegada) {
        this.estacionLlegada = estacionLlegada;
    }

    public int getPrefijoNumeracion() {
        return prefijoNumeracion;
    }

    public void setPrefijoNumeracion(int prefijoNumeracion) {
        this.prefijoNumeracion = prefijoNumeracion;
    }

    public double getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(double horaInicial) {
        this.horaInicial = horaInicial;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @XmlTransient
    public List<EstacionTramoParada> getEstacionTramoParadaList() {
        return estacionTramoParadaList;
    }

    public void setEstacionTramoParadaList(List<EstacionTramoParada> estacionTramoParadaList) {
        this.estacionTramoParadaList = estacionTramoParadaList;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preCirculacionPK != null ? preCirculacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreCirculacion)) {
            return false;
        }
        PreCirculacion other = (PreCirculacion) object;
        if ((this.preCirculacionPK == null && other.preCirculacionPK != null) || (this.preCirculacionPK != null && !this.preCirculacionPK.equals(other.preCirculacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.PreCirculacion[ preCirculacionPK=" + preCirculacionPK + " ]";
    }
    
}
