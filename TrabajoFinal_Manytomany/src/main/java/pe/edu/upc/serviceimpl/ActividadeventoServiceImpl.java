package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Actividadevento;
import pe.edu.upc.repository.ActividadeventoRepository;
import pe.edu.upc.service.IActividadeventoService;

@Service
public class ActividadeventoServiceImpl implements IActividadeventoService{

	@Autowired
	private ActividadeventoRepository aR;
	
	@Override
	@Transactional
	public void insertar(Actividadevento actividadevento) {
		aR.save(actividadevento);		
	}

	@Override
	@Transactional
	public void modificar(Actividadevento actividadevento) {
		aR.save(actividadevento);	
	}

	@Override
	@Transactional
	public void eliminar(int idAct) {
		aR.deleteById(idAct);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Actividadevento> listarId(int idAct) {
		return aR.findById(idAct);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Actividadevento> listar() {
		return aR.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Actividadevento> buscarActividad(String nombreActividad) {
		return aR.findByNombreActividad(nombreActividad);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Actividadevento> buscarEvento(String nombreEvento) {
		return aR.findByNombreEvento(nombreEvento);
	}

}
