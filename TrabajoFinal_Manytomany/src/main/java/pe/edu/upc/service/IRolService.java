package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Rol;



public interface IRolService {
	
	public boolean insertar(Rol rol);

	public boolean modificar(Rol rol);

	public void eliminar(long id);

	List<Rol> listar();

	List<Rol> buscarRol(long id);
	
	List<Rol> buscarRolUsuario(String rolUsuario);

	Optional<Rol> listarId(long id);
}
