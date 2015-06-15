/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kelvins Insua
 */
@Entity
@Table(name = "tiempo_estacion_marcha_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findAll", query = "SELECT t FROM TiempoEstacionMarchaTipo t"),
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByIdPkEstacion", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.tiempoEstacionMarchaTipoPK.idPkEstacion = :idPkEstacion"),
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByIdMarchaTipo", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.tiempoEstacionMarchaTipoPK.idMarchaTipo = :idMarchaTipo ORDER BY t.tiempoEstacionMarchaTipoPK.idPkEstacion ASC"),
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByIdMarchaTipoAsc", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.tiempoEstacionMarchaTipoPK.idMarchaTipo = :idMarchaTipo ORDER BY t.tiempoEstacionMarchaTipoPK.idPkEstacion ASC"),
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByIdMarchaTipoDesc", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.tiempoEstacionMarchaTipoPK.idMarchaTipo = :idMarchaTipo ORDER BY t.tiempoEstacionMarchaTipoPK.idPkEstacion DESC"),
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByNombreEstacion", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.nombreEstacion = :nombreEstacion"),
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByTiempoIdeal", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.tiempoIdeal = :tiempoIdeal"),
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByTiempoMargen", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.tiempoMargen = :tiempoMargen"),
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByPKTiempoEstacion", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.tiempoEstacionMarchaTipoPK.idMarchaTipo = :idMarchaTipo AND t.tiempoEstacionMarchaTipoPK.idPkEstacion= :idPkEstacion"),
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByTiempoReal", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.tiempoReal = :tiempoReal"),
//creada el 26/03/2015 2 query
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByIdMarchaTipoSalidaLlegadaAsc", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.tiempoEstacionMarchaTipoPK.idMarchaTipo = :idMarchaTipo AND t.tiempoEstacionMarchaTipoPK.idPkEstacion>= :salida AND t.tiempoEstacionMarchaTipoPK.idPkEstacion<= :llegada ORDER BY t.tiempoEstacionMarchaTipoPK.idPkEstacion ASC"),
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByIdMarchaTipoSalidaLlegadaDesc", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.tiempoEstacionMarchaTipoPK.idMarchaTipo = :idMarchaTipo AND t.tiempoEstacionMarchaTipoPK.idPkEstacion<= :salida AND t.tiempoEstacionMarchaTipoPK.idPkEstacion>= :llegada ORDER BY t.tiempoEstacionMarchaTipoPK.idPkEstacion ASC"),
    //Creadas el 24/03 (2 query)
    //Modificado el 8-06-2015
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByParada", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.parada = :parada AND t.tiempoEstacionMarchaTipoPK.idMarchaTipo = :idMarchaTipo"),
    // Modificada el 8-06-2015
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findProgParada", query = "SELECT t.tiempoEstacionMarchaTipoPK.idPkEstacion FROM TiempoEstacionMarchaTipo t WHERE t.parada = :parada AND t.tiempoEstacionMarchaTipoPK.idMarchaTipo=:idMarchaTipo"),
    // Creado el 25/03/2015 4 query
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByProgEstacionInicial", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.tiempoEstacionMarchaTipoPK.idPkEstacion = (SELECT mt.progEstacionInicial FROM MarchaTipo mt WHERE mt.idMarchaTipo = :idMarchaTipo) AND t.tiempoEstacionMarchaTipoPK.idMarchaTipo = :idMarchaTipo"),
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findByProgEstacionFinal", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.tiempoEstacionMarchaTipoPK.idPkEstacion = (SELECT mt.progEstacionFinal FROM MarchaTipo mt WHERE mt.idMarchaTipo = :idMarchaTipo) AND t.tiempoEstacionMarchaTipoPK.idMarchaTipo = :idMarchaTipo"),
    // Modificado el 8-06-2015
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findParadasIntermediasAsc", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.parada = :parada AND t.tiempoEstacionMarchaTipoPK.idMarchaTipo=:idMarchaTipo AND t.tiempoEstacionMarchaTipoPK.idPkEstacion > :estInicio AND t.tiempoEstacionMarchaTipoPK.idPkEstacion < :estFinal ORDER BY t.tiempoEstacionMarchaTipoPK.idPkEstacion ASC"),
    @NamedQuery(name = "TiempoEstacionMarchaTipo.findParadasIntermediasDesc", query = "SELECT t FROM TiempoEstacionMarchaTipo t WHERE t.parada = :parada AND t.tiempoEstacionMarchaTipoPK.idMarchaTipo=:idMarchaTipo AND t.tiempoEstacionMarchaTipoPK.idPkEstacion < :estInicio AND t.tiempoEstacionMarchaTipoPK.idPkEstacion > :estFinal ORDER BY t.tiempoEstacionMarchaTipoPK.idPkEstacion DESC")
    })
public class TiempoEstacionMarchaTipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TiempoEstacionMarchaTipoPK tiempoEstacionMarchaTipoPK;
    @Basic(optional = false)
    @Column(name = "nombre_estacion")
    private String nombreEstacion;
    @Basic(optional = false)
    @Column(name = "tiempo_ideal")
    @Temporal(TemporalType.TIME)
    private Date tiempoIdeal;
    @Column(name = "tiempo_perdido_rest")
    @Temporal(TemporalType.TIME)
    private Date tiempoPerdidoRest;
    @Basic(optional = false)
    @Column(name = "tiempo_margen")
    @Temporal(TemporalType.TIME)
    private Date tiempoMargen;
    @Basic(optional = false)
    @Column(name = "tiempo_real")
    @Temporal(TemporalType.TIME)
    private Date tiempoReal;
    @Basic(optional = false)
    @Column(name = "tiempo_redondeo")
    @Temporal(TemporalType.TIME)
    private Date tiempoRedondeo;
    @Basic(optional = false)
    @Column(name = "tiempo_asimilado")
    @Temporal(TemporalType.TIME)
    private Date tiempoAsimilado;
    @Basic(optional = false)
    @Column(name = "parada")
    private boolean parada; 
    @Basic(optional = false)
    @Column(name = "tiempo_adicional")
    @Temporal(TemporalType.TIME)
    private Date tiempoAdicional;
    @JoinColumn(name = "id_marcha_tipo", referencedColumnName = "id_marcha_tipo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MarchaTipo marchaTipo;

    public TiempoEstacionMarchaTipo() {
    }

    public TiempoEstacionMarchaTipo(TiempoEstacionMarchaTipoPK tiempoEstacionMarchaTipoPK) {
        this.tiempoEstacionMarchaTipoPK = tiempoEstacionMarchaTipoPK;
    }

    public TiempoEstacionMarchaTipo(TiempoEstacionMarchaTipoPK tiempoEstacionMarchaTipoPK, String nombreEstacion, Date tiempoIdeal, Date tiempoMargen, Date tiempoReal, Date tiempoRedondeo,Date tiempoAsimilado, Date tiempoAdicional,boolean parada) {
        this.tiempoEstacionMarchaTipoPK = tiempoEstacionMarchaTipoPK;
        this.nombreEstacion = nombreEstacion;
        this.tiempoIdeal = tiempoIdeal;
        this.tiempoMargen = tiempoMargen;
        this.tiempoReal = tiempoReal;
        this.tiempoAdicional = tiempoReal;
        this.tiempoAsimilado = tiempoReal;
        this.tiempoAdicional = tiempoReal;
        this.parada=parada;
    }

    public TiempoEstacionMarchaTipo(double idPkEstacion, int idMarchaTipo) {
        this.tiempoEstacionMarchaTipoPK = new TiempoEstacionMarchaTipoPK(idPkEstacion, idMarchaTipo);
    }

    public TiempoEstacionMarchaTipoPK getTiempoEstacionMarchaTipoPK() {
        return tiempoEstacionMarchaTipoPK;
    }

    public void setTiempoEstacionMarchaTipoPK(TiempoEstacionMarchaTipoPK tiempoEstacionMarchaTipoPK) {
        this.tiempoEstacionMarchaTipoPK = tiempoEstacionMarchaTipoPK;
    }

    public String getNombreEstacion() {
        return nombreEstacion;
    }

    public void setNombreEstacion(String nombreEstacion) {
        this.nombreEstacion = nombreEstacion;
    }

    public boolean isParada() {
        return parada;
    }

    public void setParada(boolean parada) {
        this.parada = parada;
    }

    public Date getTiempoIdeal() {
        return tiempoIdeal;
    }

    public void setTiempoIdeal(Date tiempoIdeal) {
        this.tiempoIdeal = tiempoIdeal;
    }

    public Date getTiempoMargen() {
        return tiempoMargen;
    }

    public void setTiempoMargen(Date tiempoMargen) {
        this.tiempoMargen = tiempoMargen;
    }

    public Date getTiempoReal() {
        return tiempoReal;
    }

    public void setTiempoReal(Date tiempoReal) {
        this.tiempoReal = tiempoReal;
    }

    public MarchaTipo getMarchaTipo() {
        return marchaTipo;
    }

    public void setMarchaTipo(MarchaTipo marchaTipo) {
        this.marchaTipo = marchaTipo;
    }

    public Date getTiempoRedondeo() {
        return tiempoRedondeo;
    }

    public void setTiempoRedondeo(Date tiempoRedondeo) {
        this.tiempoRedondeo = tiempoRedondeo;
    }

    public Date getTiempoPerdidoRest() {
        return tiempoPerdidoRest;
    }

    public void setTiempoPerdidoRest(Date tiempoPerdidoRest) {
        this.tiempoPerdidoRest = tiempoPerdidoRest;
    }

    public Date getTiempoAsimilado() {
        return tiempoAsimilado;
    }

    public void setTiempoAsimilado(Date tiempoAsimilado) {
        this.tiempoAsimilado = tiempoAsimilado;
    }

    public Date getTiempoAdicional() {
        return tiempoAdicional;
    }

    public void setTiempoAdicional(Date tiempoAdicional) {
        this.tiempoAdicional = tiempoAdicional;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tiempoEstacionMarchaTipoPK != null ? tiempoEstacionMarchaTipoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiempoEstacionMarchaTipo)) {
            return false;
        }
        TiempoEstacionMarchaTipo other = (TiempoEstacionMarchaTipo) object;
        if ((this.tiempoEstacionMarchaTipoPK == null && other.tiempoEstacionMarchaTipoPK != null) || (this.tiempoEstacionMarchaTipoPK != null && !this.tiempoEstacionMarchaTipoPK.equals(other.tiempoEstacionMarchaTipoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.TiempoEstacionMarchaTipo[ tiempoEstacionMarchaTipoPK=" + tiempoEstacionMarchaTipoPK + " ]";
    }
    
}
