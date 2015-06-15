/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author seront
 */
@Entity
@Table(name = "prefijo_numeracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrefijoNumeracion.findAll", query = "SELECT p FROM PrefijoNumeracion p"),
    @NamedQuery(name = "PrefijoNumeracion.findByIdPrefijo", query = "SELECT p FROM PrefijoNumeracion p WHERE p.idPrefijo = :idPrefijo"),
    @NamedQuery(name = "PrefijoNumeracion.listarIdPrefijo", query = "SELECT p.idPrefijo FROM PrefijoNumeracion p ORDER BY p.idPrefijo ASC"),
    @NamedQuery(name = "PrefijoNumeracion.findByNombre", query = "SELECT p FROM PrefijoNumeracion p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PrefijoNumeracion.findByValor", query = "SELECT p FROM PrefijoNumeracion p WHERE p.valor = :valor")})
public class PrefijoNumeracion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_prefijo")
    private Integer idPrefijo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "valor")
    private String valor;

    public PrefijoNumeracion() {
    }

    public PrefijoNumeracion(Integer idPrefijo) {
        this.idPrefijo = idPrefijo;
    }

    public Integer getIdPrefijo() {
        return idPrefijo;
    }

    public void setIdPrefijo(Integer idPrefijo) {
        this.idPrefijo = idPrefijo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrefijo != null ? idPrefijo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrefijoNumeracion)) {
            return false;
        }
        PrefijoNumeracion other = (PrefijoNumeracion) object;
        if ((this.idPrefijo == null && other.idPrefijo != null) || (this.idPrefijo != null && !this.idPrefijo.equals(other.idPrefijo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.controlBD.PrefijoNumeracion[ idPrefijo=" + idPrefijo + " ]";
    }
    
}
