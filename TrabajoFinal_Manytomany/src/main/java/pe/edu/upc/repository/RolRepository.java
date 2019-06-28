package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{
	@Query("from Rol r where r.user_id = id")
	List<Rol> buscarRol(@Param("id") long id);
	
	@Query("from Rol r where r.authority = authority")
	List<Rol> buscarRolUsuario(@Param("authority") String authority);
}
