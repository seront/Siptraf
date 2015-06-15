/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "ruta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ruta.findAll", query = "SELECT r FROM Ruta r"),
    @NamedQuery(name = "Ruta.listarId", query = "SELECT r.rutaPK.idRuta FROM Ruta r ORDER BY r.rutaPK.idRuta ASC"),
    @NamedQuery(name = "Ruta.findByIdRuta", query = "SELECT r FROM Ruta r WHERE r.rutaPK.idRuta = :idRuta"),
    @NamedQuery(name = "Ruta.findByIdProgramacionHoraria", query = "SELECT r FROM Ruta r WHERE r.rutaPK.idProgramacionHoraria = :idProgramacionHoraria"),
    @NamedQuery(name = "Ruta.findByNombre", query = "SELECT r FROM Ruta r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Ruta.findByColor", query = "SELECT r FROM Ruta r WHERE r.color = :color")})
public class Ruta implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RutaPK rutaPK;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "color")
    private String color;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ruta")
    private List<PreCirculacion> preCirculacionList;
    @JoinColumn(name = "id_programacion_horaria", referencedColumnName = "id_programacion_horaria", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProgramacionHoraria programacionHoraria;

    public Ruta() {
    }

    public Ruta(RutaPK rutaPK) {
        this.rutaPK = rutaPK;
    }

    public Ruta(int idRuta, int idProgramacionHoraria) {
        this.rutaPK = new RutaPK(idRuta, idProgramacionHoraria);
    }

    public RutaPK getRutaPK() {
        return rutaPK;
    }

    public void setRutaPK(RutaPK rutaPK) {
        this.rutaPK = rutaPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @XmlTransient
    public List<PreCirculacion> getPreCirculacionList() {
        return preCirculacionList;
    }

    public void setPreCirculacionList(List<PreCirculacion> preCirculacionList) {
        this.preCirculacionList = preCirculacionList;
    }

    public ProgramacionHoraria getProgramacionHoraria() {
        return programacionHoraria;
    }

    public void setProgramacionHoraria(ProgramacionHoraria programacionHoraria) {
        this.programacionHoraria = programacionHoraria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rutaPK != null ? rutaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ruta)) {
            return false;
        }
        Ruta other = (Ruta) object;
        if ((this.rutaPK == null && other.rutaPK != null) || (this.rutaPK != null && !this.rutaPK.equals(other.rutaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.Ruta[ rutaPK=" + rutaPK + " ]";
    }
    
}
