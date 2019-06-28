package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer>{

	@Query("from Evento e where e.nombreEvento like %:nombreEvento%")
	public List<Evento> findByNombreEvento(String nombreEvento);

	@Query("select e from Evento e where e.local.nombreLocal like %?1%")
	public List<Evento> buscarLocal(String nombreLocal);
	
	@Query("select e from Evento e where e.linea.nombreLinea like %?1%")
	public List<Evento> buscarLinea(String nombreLinea);
	
	@Query("select count(e.nombreEvento) from Evento e where e.nombreEvento =:nombreEvento")
	public int buscarNombreEvento (@Param("nombreEvento")String nombreEvento);
	
}
