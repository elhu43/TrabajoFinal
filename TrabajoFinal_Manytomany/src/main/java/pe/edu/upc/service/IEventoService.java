package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Evento;

public interface IEventoService {

	public Integer insertar(Evento evento);
	public void modificar(Evento evento);
	public void eliminar (int idEvento);
	Optional<Evento> listarId(int idEvento);
	List<Evento> listar();
	List<Evento> buscar(String nombreEvento);
	List<Evento> buscarLocal(String nombreLocal);
	List<Evento> buscarLinea(String nombreLinea);
	
}
