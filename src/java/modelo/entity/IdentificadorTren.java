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
@Table(name = "identificador_tren")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IdentificadorTren.findAll", query = "SELECT i FROM IdentificadorTren i"),
    @NamedQuery(name = "IdentificadorTren.findByIdIdentificadorTren", query = "SELECT i FROM IdentificadorTren i WHERE i.idIdentificadorTren = :idIdentificadorTren"),
    @NamedQuery(name = "IdentificadorTren.findByCategoria", query = "SELECT i FROM IdentificadorTren i WHERE i.categoria = :categoria"),
    @NamedQuery(name = "IdentificadorTren.findByServicio", query = "SELECT i FROM IdentificadorTren i WHERE i.categoria = 'Servicio que presta el tren'"),
    @NamedQuery(name = "IdentificadorTren.findByCategoriaIdentificacion", query = "SELECT i FROM IdentificadorTren i WHERE i.categoria = 'Categoria de identificacion'"),
    @NamedQuery(name = "IdentificadorTren.findByCRT", query = "SELECT i FROM IdentificadorTren i WHERE i.categoria = 'Centro de regulacion de trafico'"),
    @NamedQuery(name = "IdentificadorTren.findByEmpresaPropietaria", query = "SELECT i FROM IdentificadorTren i WHERE i.categoria = 'Empresa propietaria'"),
    @NamedQuery(name = "IdentificadorTren.findByValor", query = "SELECT i FROM IdentificadorTren i WHERE i.valor = :valor"),
    @NamedQuery(name = "IdentificadorTren.findByDescripcion", query = "SELECT i FROM IdentificadorTren i WHERE i.descripcion = :descripcion")})
public class IdentificadorTren implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_identificador_tren")
    private Integer idIdentificadorTren;
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "valor")
    private String valor;
    @Column(name = "descripcion")
    private String descripcion;

    public IdentificadorTren() {
    }

    public IdentificadorTren(Integer idIdentificadorTren) {
        this.idIdentificadorTren = idIdentificadorTren;
    }

    public Integer getIdIdentificadorTren() {
        return idIdentificadorTren;
    }

    public void setIdIdentificadorTren(Integer idIdentificadorTren) {
        this.idIdentificadorTren = idIdentificadorTren;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIdentificadorTren != null ? idIdentificadorTren.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IdentificadorTren)) {
            return false;
        }
        IdentificadorTren other = (IdentificadorTren) object;
        if ((this.idIdentificadorTren == null && other.idIdentificadorTren != null) || (this.idIdentificadorTren != null && !this.idIdentificadorTren.equals(other.idIdentificadorTren))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entity.IdentificadorTren[ idIdentificadorTren=" + idIdentificadorTren + " ]";
    }
    
}
