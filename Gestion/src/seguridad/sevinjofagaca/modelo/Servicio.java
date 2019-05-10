/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridad.sevinjofagaca.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jofa7
 * @author sevin
 */
@Entity
@Table(name = "servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicio.findAll", query = "SELECT s FROM Servicio s")
    , @NamedQuery(name = "Servicio.findById", query = "SELECT s FROM Servicio s WHERE s.id = :id")
    , @NamedQuery(name = "Servicio.findByNombre", query = "SELECT s FROM Servicio s WHERE s.nombre = :nombre")
    , @NamedQuery(name = "Servicio.findByTipoServicio", query = "SELECT s FROM Servicio s WHERE s.tipoServicio = :tipoServicio")
    , @NamedQuery(name = "Servicio.findByPrecio", query = "SELECT s FROM Servicio s WHERE s.precio = :precio")
    , @NamedQuery(name = "Servicio.findByPlazoPago", query = "SELECT s FROM Servicio s WHERE s.plazoPago = :plazoPago")
    , @NamedQuery(name = "Servicio.findByIdUser", query = "SELECT s FROM Servicio s WHERE s.idUser = :idUser")})
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "tipoServicio")
    private String tipoServicio;
    @Basic(optional = false)
    @Column(name = "precio")
    private int precio;
    @Basic(optional = false)
    @Column(name = "plazoPago")
    private String plazoPago;
    @Basic(optional = false)
    @Column(name = "id_user")
    private int idUser;


    @JoinColumn(name = "us", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    public Usuarios us;


    public Servicio() {
    }

    public Servicio(Integer id) {
        this.id = id;
    }

    public Servicio(Integer id, String nombre, String tipoServicio, int precio, String plazoPago, int idUser) {
        this.id = id;
        this.nombre = nombre;
        this.tipoServicio = tipoServicio;
        this.precio = precio;
        this.plazoPago = plazoPago;
        this.idUser = idUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getPlazoPago() {
        return plazoPago;
    }

    public void setPlazoPago(String plazoPago) {
        this.plazoPago = plazoPago;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicio)) {
            return false;
        }
        Servicio other = (Servicio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "seguridad.sevinjofagaca.modelo.Servicio[ id=" + id + " ]";
    }
    
}
