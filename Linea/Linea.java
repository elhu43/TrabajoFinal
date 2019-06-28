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
@Table(name = "lineas")
public class Linea implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idLinea;

	@NotEmpty(message = "Ingrese el nombre de la línea")
	@Column(name = "nombreLinea", nullable = false, length = 50, unique = true)
	private String nombreLinea;

	@NotEmpty(message = "Ingrese la descripción de la línea")
	@Column(name = "descripcion", nullable = false, length = 90)
	private String descripcion;

	public Linea(int idLinea, String nombreLinea, String descripcion) {
		super();
		this.idLinea = idLinea;
		this.nombreLinea = nombreLinea;
		this.descripcion = descripcion;
	}

	public Linea() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}

	public String getNombreLinea() {
		return nombreLinea;
	}

	public void setNombreLinea(String nombreLinea) {
		this.nombreLinea = nombreLinea;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idLinea;
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
		Linea other = (Linea) obj;
		if (idLinea != other.idLinea)
			return false;
		return true;
	}
	
	

}
