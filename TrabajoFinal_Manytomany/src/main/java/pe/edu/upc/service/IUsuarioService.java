package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Usuario;



public interface IUsuarioService {
	
public boolean insertar(Usuario usuario);
	
	public boolean modificar(Usuario usuario);
	
	public void eliminar(long id);
	
	List<Usuario> listar();
	
	List<Usuario> buscarNombre(String nombreUsuario);
	
	Usuario buscarNombreUsuario(String username);
	
	Optional<Usuario> listarId(long id);
}
