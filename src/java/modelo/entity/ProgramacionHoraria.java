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
@Table(name = "programacion_horaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProgramacionHoraria.findAll", query = "SELECT p FROM ProgramacionHoraria p"),
    @NamedQuery(name = "ProgramacionHoraria.listarId", query = "SELECT p.idProgramacionHoraria FROM ProgramacionHoraria p ORDER BY p.idProgramacionHoraria ASC"),
    @NamedQuery(name = "ProgramacionHoraria.findByIdProgramacionHoraria", query = "SELECT p FROM ProgramacionHoraria p WHERE p.idProgramacionHoraria = :idProgramacionHoraria"),
    @NamedQuery(name = "ProgramacionHoraria.findByIdLinea", query = "SELECT p FROM ProgramacionHoraria p WHERE p.idLinea = :idLinea"),
    @NamedQuery(name = "ProgramacionHoraria.findByNombreProgramacionHoraria", query = "SELECT p FROM ProgramacionHoraria p WHERE p.nombreProgramacionHoraria = :nombreProgramacionHoraria"),
    @NamedQuery(name = "ProgramacionHoraria.findByMarchaTipoPredAsc", query = "SELECT p FROM ProgramacionHoraria p WHERE p.marchaTipoPredAsc = :marchaTipoPredAsc"),
    @NamedQuery(name = "ProgramacionHoraria.findByColorPredAsc", query = "SELECT p FROM ProgramacionHoraria p WHERE p.colorPredAsc = :colorPredAsc"),
    @NamedQuery(name = "ProgramacionHoraria.findByMarchaTipoPredDesc", query = "SELECT p FROM ProgramacionHoraria p WHERE p.marchaTipoPredDesc = :marchaTipoPredDesc"),
    @NamedQuery(name = "ProgramacionHoraria.findByColorPredDesc", query = "SELECT p FROM ProgramacionHoraria p WHERE p.colorPredDesc = :colorPredDesc")})
public class ProgramacionHoraria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_programacion_horaria")
    private Integer idProgramacionHoraria;
    @Basic(optional = false)
    @Column(name = "id_linea")
    private int idLinea;
    @Basic(optional = false)
    @Column(name = "nombre_programacion_horaria")
    private String nombreProgramacionHoraria;
    @Basic(optional = false)
    @Column(name = "marcha_tipo_pred_asc")
    private int marchaTipoPredAsc;
    @Basic(optional = false)
    @Column(name = "color_pred_asc")
    private String colorPredAsc;
    @Column(name = "marcha_tipo_pred_desc")
    private Integer marchaTipoPredDesc;
    @Column(name = "color_pred_desc")
    private String colorPredDesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programacionHoraria")
    private List<Ruta> rutaList;

    public ProgramacionHoraria() {
    }

    public ProgramacionHoraria(Integer idProgramacionHoraria) {
        this.idProgramacionHoraria = idProgramacionHoraria;
    }

    public ProgramacionHoraria(Integer idProgramacionHoraria, int idLinea, String nombreProgramacionHoraria, int marchaTipoPredAsc, String colorPredAsc,int marchaTipoPredDesc, String colorPredDesc) {
        this.idProgramacionHoraria = idProgramacionHoraria;
        this.idLinea = idLinea;
        this.nombreProgramacionHoraria = nombreProgramacionHoraria;
        this.marchaTipoPredAsc = marchaTipoPredAsc;
        this.colorPredAsc = colorPredAsc;
        this.marchaTipoPredDesc =marchaTipoPredDesc;
        this.colorPredDesc = colorPredDesc;
    }

    public Integer getIdProgramacionHoraria() {
        return idProgramacionHoraria;
    }

    public void setIdProgramacionHoraria(Integer idProgramacionHoraria) {
        this.idProgramacionHoraria = idProgramacionHoraria;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public String getNombreProgramacionHoraria() {
        return nombreProgramacionHoraria;
    }

    public void setNombreProgramacionHoraria(String nombreProgramacionHoraria) {
        this.nombreProgramacionHoraria = nombreProgramacionHoraria;
    }

    public int getMarchaTipoPredAsc() {
        return marchaTipoPredAsc;
    }

    public void setMarchaTipoPredAsc(int marchaTipoPredAsc) {
        this.marchaTipoPredAsc = marchaTipoPredAsc;
    }

    public String getColorPredAsc() {
        return colorPredAsc;
    }

    public void setColorPredAsc(String colorPredAsc) {
        this.colorPredAsc = colorPredAsc;
    }

    public Integer getMarchaTipoPredDesc() {
        return marchaTipoPredDesc;
    }

    public void setMarchaTipoPredDesc(Integer marchaTipoPredDesc) {
        this.marchaTipoPredDesc = marchaTipoPredDesc;
    }

    public String getColorPredDesc() {
        return colorPredDesc;
    }

    public void setColorPredDesc(String colorPredDesc) {
        this.colorPredDesc = colorPredDesc;
    }

    @XmlTransient
    public List<Ruta> getRutaList() {
        return rutaList;
    }

    public void setRutaList(List<Ruta> rutaList) {
        this.rutaList = rutaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProgramacionHoraria != null ? idProgramacionHoraria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramacionHoraria)) {
            return false;
        }
        ProgramacionHoraria other = (ProgramacionHoraria) object;
        if ((this.idProgramacionHoraria == null && other.idProgramacionHoraria != null) || (this.idProgramacionHoraria != null && !this.idProgramacionHoraria.equals(other.idProgramacionHoraria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.ProgramacionHoraria[ idProgramacionHoraria=" + idProgramacionHoraria + " ]";
    }
    
}
