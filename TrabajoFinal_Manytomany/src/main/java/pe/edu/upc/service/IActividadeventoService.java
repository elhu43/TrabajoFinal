package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Actividadevento;

public interface IActividadeventoService {
	
	public void insertar(Actividadevento actividadevento);
	
	public void modificar(Actividadevento actividadevento);

	public void eliminar(int idAct);
	
	Optional<Actividadevento> listarId(int idAct);
	
	List<Actividadevento> listar();
	
	List<Actividadevento> buscarActividad(String nombreActividad);
	
	List<Actividadevento> buscarEvento(String nombreEvento);
	
}
