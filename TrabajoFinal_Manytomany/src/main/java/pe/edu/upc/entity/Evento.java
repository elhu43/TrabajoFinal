package pe.edu.upc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "eventos")
public class Evento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEvento;

	@NotNull(message = "Ingrese el nombre del evento")
	@Column(name = "nombreEvento", nullable = false, length = 30, unique = true)
	private String nombreEvento;

	@NotNull(message = "La fecha es obligatoria")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;

	@Min(0)
	@Max(200)
	@Column(name = "maximoParticipantes", nullable = false)
	private int maximoParticipantes;
	
	@Min(0)
	@Max(200)
	@Column(name = "cantidadAsistentes", nullable = false)
	private int cantidadAsistentes;

	@ManyToOne
	@JoinColumn(name = "idLocal")
	private Local local;

	@ManyToOne
	@JoinColumn(name = "idLinea")
	private Linea linea;

	private String foto;

	public Evento(int idEvento, String nombreEvento, Date fecha, int maximoParticipantes, int cantidadAsistentes,
			Local local, Linea linea, String foto) {
		super();
		this.idEvento = idEvento;
		this.nombreEvento = nombreEvento;
		this.fecha = fecha;
		this.maximoParticipantes = maximoParticipantes;
		this.cantidadAsistentes = cantidadAsistentes;
		this.local = local;
		this.linea = linea;
		this.foto = foto;
	}

	public Evento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getMaximoParticipantes() {
		return maximoParticipantes;
	}

	public void setMaximoParticipantes(int maximoParticipantes) {
		this.maximoParticipantes = maximoParticipantes;
	}

	public int getCantidadAsistentes() {
		return cantidadAsistentes;
	}

	public void setCantidadAsistentes(int cantidadAsistentes) {
		this.cantidadAsistentes = cantidadAsistentes;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idEvento;
		return result;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (idEvento != other.idEvento)
			return false;
		return true;
	}

}
