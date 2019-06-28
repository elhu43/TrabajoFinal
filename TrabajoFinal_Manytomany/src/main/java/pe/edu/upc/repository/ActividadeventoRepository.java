package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Actividadevento;

@Repository
public interface ActividadeventoRepository extends JpaRepository<Actividadevento, Integer>{

	@Query("from Actividadevento a where a.evento.nombreEvento like %?1%")
	public List<Actividadevento> findByNombreEvento(String nombreEvento);
	
	@Query("select a from Actividadevento a where a.actividad.nombreActividad like %?1%")
	public List<Actividadevento> findByNombreActividad(String nombreActividad);
	
}
