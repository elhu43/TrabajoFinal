package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "actividades")
public class Actividad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idActividad;

	@NotNull(message = "Ingrese el nombre de la actividad")
	@Column(name = "nombreActividad", nullable = false, length = 30, unique = true)
	private String nombreActividad;

	@NotNull(message = "Ingrese una breve descripcion")
	@Column(name = "descripcion", nullable = false, length = 100, unique = true)
	private String descripcion;

	public int getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Actividad(int idActividad, String nombreActividad, String descripcion) {
		super();
		this.idActividad = idActividad;
		this.nombreActividad = nombreActividad;
		this.descripcion = descripcion;
	}

	public Actividad() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idActividad;
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
		Actividad other = (Actividad) obj;
		if (idActividad != other.idActividad)
			return false;
		return true;
	}

}
