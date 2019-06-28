package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Sort;//
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Local;
import pe.edu.upc.repository.LocalRepository;
import pe.edu.upc.service.ILocalService;

@Service
public class LocalServiceImpl implements ILocalService{

	@Autowired
	private LocalRepository lR;
	
	@Override
	@Transactional
	public Integer insertar(Local local) {
		int rpta = lR.buscarNombreLocal(local.getNombreLocal());
		if(rpta==0) {
			lR.save(local);
		}
		return rpta;
	}
	
	@Override
	@Transactional
	public void modificar(Local local) {
		lR.save(local);
	}

	@Override
	@Transactional
	public void eliminar(int idLocal) {
		lR.deleteById(idLocal);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Local> listarId(int idLocal) {
		return lR.findById(idLocal);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Local> listar() {
		return lR.findAll(Sort.by(Sort.Direction.ASC, "nombreLocal"));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Local> buscarNombre(String nombreLocal) {
		return lR.findByNombreLocal(nombreLocal);
	}

}
