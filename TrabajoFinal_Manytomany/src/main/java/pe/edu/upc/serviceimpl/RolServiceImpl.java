package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Rol;
import pe.edu.upc.repository.RolRepository;
import pe.edu.upc.service.IRolService;

@Service
public class RolServiceImpl implements IRolService{

	@Autowired
	private RolRepository rR;
	
	@Override
	@Transactional
	public boolean insertar(Rol rol) {
		Rol roles = rR.save(rol);
		if(roles == null) {
			return false;
		} else {
			return true;
		}		
	}

	@Override
	@Transactional
	public boolean modificar(Rol rol) {
		boolean flag = false;
		try {
			rR.save(rol);
			flag = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return flag;
	}

	@Override
	@Transactional
	public void eliminar(long id) {
		rR.deleteById(id);
	}

	@Override
	public List<Rol> listar() {
		return rR.findAll();
	}

	@Override
	public List<Rol> buscarRol(long id) {
		return rR.buscarRol(id);
	}

	@Override
	public Optional<Rol> listarId(long id) {
		return rR.findById(id);
	}

	@Override
	public List<Rol> buscarRolUsuario(String rolUsuario) {
		return rR.buscarRolUsuario(rolUsuario);
	}

}
