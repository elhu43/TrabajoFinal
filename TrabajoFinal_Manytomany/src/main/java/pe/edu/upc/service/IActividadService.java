package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Actividad;

public interface IActividadService {

	public Integer insertar(Actividad actividad);

	public void modificar(Actividad actividad);

	public void eliminar(int idActividad);

	Optional<Actividad> listarId(int idActividad);

	List<Actividad> listar();

	List<Actividad> buscarNombre(String nombreActividad);
}
