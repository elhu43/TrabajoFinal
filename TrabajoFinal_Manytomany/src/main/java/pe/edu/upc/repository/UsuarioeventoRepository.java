package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Usuarioevento;

@Repository
public interface UsuarioeventoRepository extends JpaRepository<Usuarioevento, Integer>{

	@Query("from Usuarioevento u where u.evento.nombreEvento like %?1%")
	public List<Usuarioevento> findByNombreEvento(String nombreEvento);
	
	@Query("select u from Usuarioevento u where u.usuario.nombreUsuario like %?1%")
	public List<Usuarioevento> findByNombreUsuario(String nombreUsuario);
}
