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
@Table(name = "material_rodante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MaterialRodante.findAll", query = "SELECT m FROM MaterialRodante m"),
     @NamedQuery(name = "MaterialRodante.listarIdMR", query = "SELECT m.idMaterialRodante FROM MaterialRodante m ORDER BY m.idMaterialRodante ASC"),
    @NamedQuery(name = "MaterialRodante.findByIdMaterialRodante", query = "SELECT m FROM MaterialRodante m WHERE m.idMaterialRodante = :idMaterialRodante"),
    @NamedQuery(name = "MaterialRodante.findByNombreMaterialRodante", query = "SELECT m FROM MaterialRodante m WHERE m.nombreMaterialRodante = :nombreMaterialRodante"),
    @NamedQuery(name = "MaterialRodante.findByTipo", query = "SELECT m FROM MaterialRodante m WHERE m.tipo = :tipo"),
    @NamedQuery(name = "MaterialRodante.findBySubTipo", query = "SELECT m FROM MaterialRodante m WHERE m.subTipo = :subTipo"),
    @NamedQuery(name = "MaterialRodante.findByNumeroVagones", query = "SELECT m FROM MaterialRodante m WHERE m.numeroVagones = :numeroVagones"),
    @NamedQuery(name = "MaterialRodante.findByCapacidadPasajeros", query = "SELECT m FROM MaterialRodante m WHERE m.capacidadPasajeros = :capacidadPasajeros"),
    @NamedQuery(name = "MaterialRodante.findByMasa", query = "SELECT m FROM MaterialRodante m WHERE m.masa = :masa"),
    @NamedQuery(name = "MaterialRodante.findByAceleracionMax", query = "SELECT m FROM MaterialRodante m WHERE m.aceleracionMax = :aceleracionMax"),
    @NamedQuery(name = "MaterialRodante.findByDesaceleracionMax", query = "SELECT m FROM MaterialRodante m WHERE m.desaceleracionMax = :desaceleracionMax"),
    @NamedQuery(name = "MaterialRodante.findByVelocidadDisenio", query = "SELECT m FROM MaterialRodante m WHERE m.velocidadDisenio = :velocidadDisenio"),
    @NamedQuery(name = "MaterialRodante.findByVelocidadOperativa", query = "SELECT m FROM MaterialRodante m WHERE m.velocidadOperativa = :velocidadOperativa"),
    @NamedQuery(name = "MaterialRodante.findByNumeroEjes", query = "SELECT m FROM MaterialRodante m WHERE m.numeroEjes = :numeroEjes")})
public class MaterialRodante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_material_rodante")
    private Integer idMaterialRodante;
    @Column(name = "capacidad_motriz")
    private Integer capacidadMotriz;
    @Column(name = "capacidad_remolque")
    private Integer capacidadRemolque;
    @Column(name = "numero_remolque")
    private Integer numeroRemolque;
    @Column(name = "numero_motriz")
    private Integer numeroMotriz;
    @Basic(optional = false)
    @Column(name = "nombre_material_rodante")
    private String nombreMaterialRodante;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "sub_tipo")
    private String subTipo;
    @Column(name = "alto_x_ancho_remolque")
    private String altoXAnchoRemolque;
    @Column(name = "alto_x_ancho_motriz")
    private String altoXAnchoMotriz;
    @Column(name = "frenado_descripcion")
    private String frenadoDescripcion;
    @Column(name = "presion_trabajo")
    private String presionTrabajo;
    @Basic(optional = false)
    @Column(name = "numero_vagones")
    private int numeroVagones;
    @Basic(optional = false)
    @Column(name = "capacidad_pasajeros")
    private double capacidadPasajeros;
    @Basic(optional = false)
    @Column(name = "masa")
    private double masa;
    @Column(name = "longitud_total")
    private double longitudTotal;
    @Column(name = "longitud_remolque")
    private double longitudRemolque;
    @Column(name = "longitud_motriz")
    private double longitudMotriz;
    @Column(name = "masa_motriz")
    private double masaMotriz;
    @Column(name = "masa_remolque")
    private double masaRemolque;
    @Column(name = "voltaje")
    private double voltaje;
    @Column(name = "voltaje_bateria")
    private double voltajeBateria;
    @Column(name = "desaceleracion_emergencia")
    private double desaceleracionEmergencia;
    @Basic(optional = false)
    @Column(name = "aceleracion_max")
    private double aceleracionMax;
    @Basic(optional = false)
    @Column(name = "desaceleracion_max")
    private double desaceleracionMax;
    @Basic(optional = false)
    @Column(name = "velocidad_disenio")
    private double velocidadDisenio;
    @Basic(optional = false)
    @Column(name = "velocidad_operativa")
    private double velocidadOperativa;
    @Basic(optional = false)
    @Column(name = "numero_ejes")
    private int numeroEjes;
    @Basic(optional = false)
    @Column(name = "carga_maxima")
    private double cargaMaxima;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialRodante")
    private List<CurvaEsfuerzo> curvaEsfuerzoList;

    public MaterialRodante() {
    }

    public MaterialRodante(Integer idMaterialRodante) {
        this.idMaterialRodante = idMaterialRodante;
    }

    public MaterialRodante(Integer idMaterialRodante, String nombreMaterialRodante, String tipo, String subTipo, int numeroVagones, double capacidadPasajeros, double masa, double aceleracionMax, double desaceleracionMax, double velocidadDisenio, double velocidadOperativa, int numeroEjes, int cargaMaxima) {
        this.idMaterialRodante = idMaterialRodante;
        this.nombreMaterialRodante = nombreMaterialRodante;
        this.tipo = tipo;
        this.subTipo = subTipo;
        this.numeroVagones = numeroVagones;
        this.capacidadPasajeros = capacidadPasajeros;
        this.masa = masa;
        this.aceleracionMax = aceleracionMax;
        this.desaceleracionMax = desaceleracionMax;
        this.velocidadDisenio = velocidadDisenio;
        this.velocidadOperativa = velocidadOperativa;
        this.numeroEjes = numeroEjes;
        this.cargaMaxima = cargaMaxima;
    }

    public Integer getIdMaterialRodante() {
        return idMaterialRodante;
    }

    public void setIdMaterialRodante(Integer idMaterialRodante) {
        this.idMaterialRodante = idMaterialRodante;
    }

    public String getNombreMaterialRodante() {
        return nombreMaterialRodante;
    }

    public void setNombreMaterialRodante(String nombreMaterialRodante) {
        this.nombreMaterialRodante = nombreMaterialRodante;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(String subTipo) {
        this.subTipo = subTipo;
    }

    public int getNumeroVagones() {
        return numeroVagones;
    }

    public void setNumeroVagones(int numeroVagones) {
        this.numeroVagones = numeroVagones;
    }

    public double getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    public void setCapacidadPasajeros(double capacidadPasajeros) {
        this.capacidadPasajeros = capacidadPasajeros;
    }

    public double getMasa() {
        return masa;
    }

    public void setMasa(double masa) {
        this.masa = masa;
    }

    public Integer getCapacidadMotriz() {
        return capacidadMotriz;
    }

    public void setCapacidadMotriz(Integer capacidadMotriz) {
        this.capacidadMotriz = capacidadMotriz;
    }

    public Integer getCapacidadRemolque() {
        return capacidadRemolque;
    }

    public void setCapacidadRemolque(Integer capacidadRemolque) {
        this.capacidadRemolque = capacidadRemolque;
    }

    public Integer getNumeroRemolque() {
        return numeroRemolque;
    }

    public void setNumeroRemolque(Integer numeroRemolque) {
        this.numeroRemolque = numeroRemolque;
    }

    public Integer getNumeroMotriz() {
        return numeroMotriz;
    }

    public void setNumeroMotriz(Integer numeroMotriz) {
        this.numeroMotriz = numeroMotriz;
    }

    public String getAltoXAnchoRemolque() {
        return altoXAnchoRemolque;
    }

    public void setAltoXAnchoRemolque(String altoXAnchoRemolque) {
        this.altoXAnchoRemolque = altoXAnchoRemolque;
    }

    public String getAltoXAnchoMotriz() {
        return altoXAnchoMotriz;
    }

    public void setAltoXAnchoMotriz(String altoXAnchoMotriz) {
        this.altoXAnchoMotriz = altoXAnchoMotriz;
    }

    public String getFrenadoDescripcion() {
        return frenadoDescripcion;
    }

    public void setFrenadoDescripcion(String frenadoDescripcion) {
        this.frenadoDescripcion = frenadoDescripcion;
    }

    public String getPresionTrabajo() {
        return presionTrabajo;
    }

    public void setPresionTrabajo(String presionTrabajo) {
        this.presionTrabajo = presionTrabajo;
    }

    public double getLongitudTotal() {
        return longitudTotal;
    }

    public void setLongitudTotal(double longitudTotal) {
        this.longitudTotal = longitudTotal;
    }

    public double getLongitudRemolque() {
        return longitudRemolque;
    }

    public void setLongitudRemolque(double longitudRemolque) {
        this.longitudRemolque = longitudRemolque;
    }

    public double getLongitudMotriz() {
        return longitudMotriz;
    }

    public void setLongitudMotriz(double longitudMotriz) {
        this.longitudMotriz = longitudMotriz;
    }

    public double getMasaMotriz() {
        return masaMotriz;
    }

    public void setMasaMotriz(double masaMotriz) {
        this.masaMotriz = masaMotriz;
    }

    public double getMasaRemolque() {
        return masaRemolque;
    }

    public void setMasaRemolque(double masaRemolque) {
        this.masaRemolque = masaRemolque;
    }

  
    public double getVoltaje() {
        return voltaje;
    }

    public void setVoltaje(double voltaje) {
        this.voltaje = voltaje;
    }

    public double getVoltajeBateria() {
        return voltajeBateria;
    }

    public void setVoltajeBateria(double voltajeBateria) {
        this.voltajeBateria = voltajeBateria;
    }

    public double getDesaceleracionEmergencia() {
        return desaceleracionEmergencia;
    }

    public void setDesaceleracionEmergencia(double desaceleracionEmergencia) {
        this.desaceleracionEmergencia = desaceleracionEmergencia;
    }
    
    public double getAceleracionMax() {
        return aceleracionMax;
    }

    public void setAceleracionMax(double aceleracionMax) {
        this.aceleracionMax = aceleracionMax;
    }

    public double getDesaceleracionMax() {
        return desaceleracionMax;
    }

    public void setDesaceleracionMax(double desaceleracionMax) {
        this.desaceleracionMax = desaceleracionMax;
    }

    public double getVelocidadDisenio() {
        return velocidadDisenio;
    }

    public void setVelocidadDisenio(double velocidadDisenio) {
        this.velocidadDisenio = velocidadDisenio;
    }

    public double getVelocidadOperativa() {
        return velocidadOperativa;
    }

    public void setVelocidadOperativa(double velocidadOperativa) {
        this.velocidadOperativa = velocidadOperativa;
    }

    public int getNumeroEjes() {
        return numeroEjes;
    }

    public void setNumeroEjes(int numeroEjes) {
        this.numeroEjes = numeroEjes;
    }

    @XmlTransient
    public List<CurvaEsfuerzo> getCurvaEsfuerzoList() {
        return curvaEsfuerzoList;
    }

    public void setCurvaEsfuerzoList(List<CurvaEsfuerzo> curvaEsfuerzoList) {
        this.curvaEsfuerzoList = curvaEsfuerzoList;
    }

    public double getCargaMaxima() {
        return cargaMaxima;
    }

    public void setCargaMaxima(double cargaMaxima) {
        this.cargaMaxima = cargaMaxima;
    }

    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMaterialRodante != null ? idMaterialRodante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialRodante)) {
            return false;
        }
        MaterialRodante other = (MaterialRodante) object;
        if ((this.idMaterialRodante == null && other.idMaterialRodante != null) || (this.idMaterialRodante != null && !this.idMaterialRodante.equals(other.idMaterialRodante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.MaterialRodante[ idMaterialRodante=" + idMaterialRodante + " ]";
    }
    
}
