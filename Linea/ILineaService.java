package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Linea;

public interface ILineaService {

	public Integer insertar(Linea linea);
	public void modificar(Linea linea);
	public void eliminar(int idLinea);
	Optional<Linea> listarId(int idLinea);
	List<Linea> listar();
	List<Linea> buscarNombre(String nombreLinea);
	
}
