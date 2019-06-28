package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Usuarioevento;

public interface IUsuarioeventoService {

public void insertar(Usuarioevento usuarioevento);
	
	public void modificar(Usuarioevento usuarioevento);

	public void eliminar(int idUsu);
	
	Optional<Usuarioevento> listarId(int idUsu);
	
	List<Usuarioevento> listar();
	
	List<Usuarioevento> buscarUsuario(String nombreUsuario);
	
	List<Usuarioevento> buscarEvento(String nombreEvento);
}
