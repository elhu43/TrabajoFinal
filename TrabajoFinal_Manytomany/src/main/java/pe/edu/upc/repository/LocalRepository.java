package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Local;

@Repository
public interface LocalRepository extends JpaRepository<Local, Integer>{

	@Query("select l from Local l where l.nombreLocal like %:nombreLocal%")
	public List<Local> findByNombreLocal(String nombreLocal);

	@Query("select count(l.nombreLocal) from Local l where l.nombreLocal =:nombreLocal")
	public int buscarNombreLocal (@Param("nombreLocal")String nombreLocal);

	
}
