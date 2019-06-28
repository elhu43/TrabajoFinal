package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	@Query("select u from Usuario u where u.nombreUsuario like %:nombreUsuario%")
	public List<Usuario> findByNombre(String nombreUsuario);
	
	public Usuario findByUsername(String username);
	
	@Query("select count(u.dniUsuario) from Usuario u where u.dniUsuario =:dniUsuario")
	public int buscardni (@Param("dniUsuario")String dniUsuario);
}
