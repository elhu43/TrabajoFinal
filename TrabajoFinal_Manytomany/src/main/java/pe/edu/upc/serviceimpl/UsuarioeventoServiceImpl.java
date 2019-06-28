package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Usuarioevento;
import pe.edu.upc.repository.UsuarioeventoRepository;
import pe.edu.upc.service.IUsuarioeventoService;

@Service
public class UsuarioeventoServiceImpl implements IUsuarioeventoService{

	@Autowired
	private UsuarioeventoRepository uR;
	
	@Override
	@Transactional
	public void insertar(pe.edu.upc.entity.Usuarioevento usuarioevento) {
		uR.save(usuarioevento);
	}

	@Override
	@Transactional
	public void modificar(pe.edu.upc.entity.Usuarioevento usuarioevento) {
		uR.save(usuarioevento);
	}

	@Override
	@Transactional
	public void eliminar(int idUsu) {
		uR.deleteById(idUsu);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Usuarioevento> listarId(int idUsu) {
		return uR.findById(idUsu);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuarioevento> listar() {
		return uR.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuarioevento> buscarUsuario(String nombreUsuario) {
		return uR.findByNombreUsuario(nombreUsuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuarioevento> buscarEvento(String nombreEvento) {
		return uR.findByNombreEvento(nombreEvento);
	}

}
