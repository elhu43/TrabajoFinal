package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Linea;

@Repository
public interface LineaRepository extends JpaRepository<Linea, Integer>{

	@Query("select l from Linea l where l.nombreLinea like %:nombreLinea%")
	public List<Linea> findByNombreLinea(String nombreLinea);
	
	@Query("select count(li.nombreLinea) from Linea li where li.nombreLinea =:nombreLinea")
	public int buscarNombreLinea (@Param("nombreLinea")String nombreLinea);

}
