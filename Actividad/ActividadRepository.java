package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Actividad;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {

	@Query("select a from Actividad a where a.nombreActividad like %:nombreActividad%")
	public List<Actividad> findByNombreActividad(String nombreActividad);

	@Query("select count(a.nombreActividad) from Actividad a where a.nombreActividad =:nombreActividad")
	public int buscarNombreActividad(@Param("nombreActividad") String nombreActividad);

}
