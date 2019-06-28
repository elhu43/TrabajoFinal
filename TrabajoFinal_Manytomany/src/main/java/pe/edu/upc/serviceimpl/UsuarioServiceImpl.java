package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Usuario;
import pe.edu.upc.repository.UsuarioRepository;
import pe.edu.upc.service.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Autowired
	private UsuarioRepository uR;
	
	@Override
	@Transactional
	public boolean insertar(Usuario usuario) {
		Usuario usuarios = uR.save(usuario);
		if(usuarios == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	@Transactional
	public boolean modificar(Usuario usuario) {
		boolean flag = false;
		try {
			uR.save(usuario);
			flag = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return flag;
	}

	@Override
	@Transactional
	public void eliminar(long id) {
		uR.deleteById(id);
	}

	@Override
	public List<Usuario> listar() {
		return uR.findAll();
	}

	@Override
	public List<Usuario> buscarNombre(String nombreUsuario) {
		return uR.findByNombre(nombreUsuario);
	}

	@Override
	public Usuario buscarNombreUsuario(String username) {
		return uR.findByUsername(username);
	}

	@Override
	public Optional<Usuario> listarId(long id) {
		return uR.findById(id);
	}

}
