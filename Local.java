package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "locales")
public class Local implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idLocal;

	@NotEmpty(message = "Ingresa el nombre del local")
	@Column(name = "nombreLocal", nullable = false, length = 70, unique = true)
	private String nombreLocal;

	@NotEmpty(message = "Ingresa el distrito")
	@Column(name = "distrito", nullable = false)
	private String distrito;

	@NotEmpty(message = "Ingresa la dirección")
	@Column(name = "direccion", nullable = false)
	private String direccion;

	public Local(int idLocal, String nombreLocal, String distrito, String direccion) {
		super();
		this.idLocal = idLocal;
		this.nombreLocal = nombreLocal;
		this.distrito = distrito;
		this.direccion = direccion;
	}

	public Local() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(int idLocal) {
		this.idLocal = idLocal;
	}

	public String getNombreLocal() {
		return nombreLocal;
	}

	public void setNombreLocal(String nombreLocal) {
		this.nombreLocal = nombreLocal;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idLocal;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Local other = (Local) obj;
		if (idLocal != other.idLocal)
			return false;
		return true;
	}

}
