/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kelvins Insua
 */
@Entity
@Table(name = "marcha_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarchaTipo.findAll", query = "SELECT m FROM MarchaTipo m"),
    @NamedQuery(name = "MarchaTipo.findByIdMarchaTipo", query = "SELECT m FROM MarchaTipo m WHERE m.idMarchaTipo = :idMarchaTipo"),
    @NamedQuery(name = "MarchaTipo.findByNombreMarchaTipo", query = "SELECT m FROM MarchaTipo m WHERE m.nombreMarchaTipo = :nombreMarchaTipo"),
    @NamedQuery(name = "MarchaTipo.listarMTAsc", query = "SELECT m FROM MarchaTipo m WHERE (SELECT e.estacionPK.idPkEstacion FROM Estacion e WHERE e.nombreEstacion = m.nombreEstacionInicial) < (SELECT e.estacionPK.idPkEstacion FROM Estacion e WHERE e.nombreEstacion = m.nombreEstacionFinal) AND m.linea = (SELECT l.nombreLinea FROM Linea l WHERE l.idLinea= :idLinea)"),
    @NamedQuery(name = "MarchaTipo.listarMTDesc", query = "SELECT m FROM MarchaTipo m WHERE (SELECT e.estacionPK.idPkEstacion FROM Estacion e WHERE e.nombreEstacion = m.nombreEstacionInicial) > (SELECT e.estacionPK.idPkEstacion FROM Estacion e WHERE e.nombreEstacion = m.nombreEstacionFinal) AND m.linea = (SELECT l.nombreLinea FROM Linea l WHERE l.idLinea= :idLinea)"),
    @NamedQuery(name = "MarchaTipo.findByVelocidadMarchaTipo", query = "SELECT m FROM MarchaTipo m WHERE m.velocidadMarchaTipo = :velocidadMarchaTipo"),
    @NamedQuery(name = "MarchaTipo.listarIdMarchas", query = "SELECT m.idMarchaTipo FROM MarchaTipo m ORDER BY m.idMarchaTipo ASC"),
    @NamedQuery(name = "MarchaTipo.findByLinea", query = "SELECT m FROM MarchaTipo m WHERE m.linea = :linea"),
    @NamedQuery(name = "MarchaTipo.findByMaterialRodante", query = "SELECT m FROM MarchaTipo m WHERE m.materialRodante = :materialRodante"),
    @NamedQuery(name = "MarchaTipo.findByNombreEstacionInicial", query = "SELECT m FROM MarchaTipo m WHERE m.nombreEstacionInicial = :nombreEstacionInicial"),
    @NamedQuery(name = "MarchaTipo.findByNombreEstacionFinal", query = "SELECT m FROM MarchaTipo m WHERE m.nombreEstacionFinal = :nombreEstacionFinal"),
    @NamedQuery(name = "MarchaTipo.findByTiempoTotal", query = "SELECT m FROM MarchaTipo m WHERE m.tiempoTotal = :tiempoTotal")})
public class MarchaTipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_marcha_tipo")
    private Integer idMarchaTipo;
    @Basic(optional = false)
    @Column(name = "nombre_marcha_tipo")
    private String nombreMarchaTipo;
    @Basic(optional = false)
    @Column(name = "velocidad_marcha_tipo")
    private double velocidadMarchaTipo;
    @Basic(optional = false)
    @Column(name = "carga_marcha")
    private double cargaMarcha;
    @Basic(optional = false)
    @Column(name = "sistema_proteccion")
    private boolean sistemaProteccion;
    @Basic(optional = false)
    @Column(name = "sentido")
    private boolean sentido;
    @Basic(optional = false)
    @Column(name = "linea")
    private String linea;
    @Basic(optional = false)
    @Column(name = "material_rodante")
    private String materialRodante;
    @Basic(optional = false)
    @Column(name = "nombre_estacion_inicial")
    private String nombreEstacionInicial;
    @Basic(optional = false)
    @Column(name = "nombre_estacion_final")
    private String nombreEstacionFinal;
    @Basic(optional = false)
    @Column(name = "tiempo_total")
    @Temporal(TemporalType.TIME)
    private Date tiempoTotal;
    @Basic(optional = false)
    @Column(name = "tiempo_minimo")
    @Temporal(TemporalType.TIME)
    private Date tiempoMinimo;
    //agregado el 25/03/2015
//    @Basic(optional = false)
    @Column(name = "prog_estacion_inicial")
    private double progEstacionInicial;
//    @Basic(optional = false)
    @Column(name = "prog_estacion_final")
    private double progEstacionFinal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "marchaTipo")
    private List<RestriccionMarchaTipo> restriccionMarchaTipoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "marchaTipo")
    private List<TiempoEstacionMarchaTipo> tiempoEstacionMarchaTipoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "marchaTipo")
    private List<GraficoMarchaTipo> graficoMarchaTipoList;

    public MarchaTipo() {
    }

    public MarchaTipo(Integer idMarchaTipo) {
        this.idMarchaTipo = idMarchaTipo;
    }

    public MarchaTipo(Integer idMarchaTipo, String nombreMarchaTipo, double velocidadMarchaTipo, String linea, String materialRodante, String nombreEstacionInicial, String nombreEstacionFinal, Date tiempoTotal, double cargaMarcha, boolean sistemaProteccion,boolean sentido) {
        this.idMarchaTipo = idMarchaTipo;
        this.nombreMarchaTipo = nombreMarchaTipo;
        this.velocidadMarchaTipo = velocidadMarchaTipo;
        this.linea = linea;
        this.materialRodante = materialRodante;
        this.nombreEstacionInicial = nombreEstacionInicial;
        this.nombreEstacionFinal = nombreEstacionFinal;
        this.tiempoTotal = tiempoTotal;
        this.cargaMarcha = cargaMarcha;
        this.sistemaProteccion = sistemaProteccion;
        this.sentido = sentido;
    }

    public Integer getIdMarchaTipo() {
        return idMarchaTipo;
    }

    public void setIdMarchaTipo(Integer idMarchaTipo) {
        this.idMarchaTipo = idMarchaTipo;
    }

    public String getNombreMarchaTipo() {
        return nombreMarchaTipo;
    }

    public void setNombreMarchaTipo(String nombreMarchaTipo) {
        this.nombreMarchaTipo = nombreMarchaTipo;
    }

    public double getVelocidadMarchaTipo() {
        return velocidadMarchaTipo;
    }

    public void setVelocidadMarchaTipo(double velocidadMarchaTipo) {
        this.velocidadMarchaTipo = velocidadMarchaTipo;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public Date getTiempoMinimo() {
        return tiempoMinimo;
    }

    public void setTiempoMinimo(Date tiempoMinimo) {
        this.tiempoMinimo = tiempoMinimo;
    }

    public String getMaterialRodante() {
        return materialRodante;
    }

    public void setMaterialRodante(String materialRodante) {
        this.materialRodante = materialRodante;
    }

    public String getNombreEstacionInicial() {
        return nombreEstacionInicial;
    }

    public void setNombreEstacionInicial(String nombreEstacionInicial) {
        this.nombreEstacionInicial = nombreEstacionInicial;
    }

    public String getNombreEstacionFinal() {
        return nombreEstacionFinal;
    }

    public void setNombreEstacionFinal(String nombreEstacionFinal) {
        this.nombreEstacionFinal = nombreEstacionFinal;
    }

    public Date getTiempoTotal() {
        return tiempoTotal;
    }

    public double getProgEstacionInicial() {
        return progEstacionInicial;
    }

    public void setProgEstacionInicial(double progEstacionInicial) {
        this.progEstacionInicial = progEstacionInicial;
    }

    public double getProgEstacionFinal() {
        return progEstacionFinal;
    }

    public void setProgEstacionFinal(double progEstacionFinal) {
        this.progEstacionFinal = progEstacionFinal;
    }
    

    public void setTiempoTotal(Date tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public double getCargaMarcha() {
        return cargaMarcha;
    }

    public void setCargaMarcha(double cargaMarcha) {
        this.cargaMarcha = cargaMarcha;
    }

    public boolean isSistemaProteccion() {
        return sistemaProteccion;
    }

    public void setSistemaProteccion(boolean sistemaProteccion) {
        this.sistemaProteccion = sistemaProteccion;
    }

    public boolean isSentido() {
        return sentido;
    }

    public void setSentido(boolean sentido) {
        this.sentido = sentido;
    }
    
    

    @XmlTransient
    public List<RestriccionMarchaTipo> getRestriccionMarchaTipoList() {
        return restriccionMarchaTipoList;
    }

    public void setRestriccionMarchaTipoList(List<RestriccionMarchaTipo> restriccionMarchaTipoList) {
        this.restriccionMarchaTipoList = restriccionMarchaTipoList;
    }

    @XmlTransient
    public List<TiempoEstacionMarchaTipo> getTiempoEstacionMarchaTipoList() {
        return tiempoEstacionMarchaTipoList;
    }

    public void setTiempoEstacionMarchaTipoList(List<TiempoEstacionMarchaTipo> tiempoEstacionMarchaTipoList) {
        this.tiempoEstacionMarchaTipoList = tiempoEstacionMarchaTipoList;
    }

    @XmlTransient
    public List<GraficoMarchaTipo> getGraficoMarchaTipoList() {
        return graficoMarchaTipoList;
    }

    public void setGraficoMarchaTipoList(List<GraficoMarchaTipo> graficoMarchaTipoList) {
        this.graficoMarchaTipoList = graficoMarchaTipoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMarchaTipo != null ? idMarchaTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarchaTipo)) {
            return false;
        }
        MarchaTipo other = (MarchaTipo) object;
        if ((this.idMarchaTipo == null && other.idMarchaTipo != null) || (this.idMarchaTipo != null && !this.idMarchaTipo.equals(other.idMarchaTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.MarchaTipo[ idMarchaTipo=" + idMarchaTipo + " ]";
    }
    
}
