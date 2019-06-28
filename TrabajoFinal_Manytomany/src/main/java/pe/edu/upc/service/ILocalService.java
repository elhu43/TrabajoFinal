package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Local;


public interface ILocalService {
	public Integer insertar(Local local);
	public void modificar(Local local);
	public void eliminar(int idLocal);
	Optional<Local> listarId(int idLocal);
	List<Local> listar();
	List<Local> buscarNombre(String nombreLocal);

}
