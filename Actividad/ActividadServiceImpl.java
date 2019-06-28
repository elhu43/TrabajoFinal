package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Actividad;
import pe.edu.upc.repository.ActividadRepository;
import pe.edu.upc.service.IActividadService;

@Service
public class ActividadServiceImpl implements IActividadService {

	@Autowired
	private ActividadRepository aR;

	@Override
	@Transactional
	public Integer insertar(Actividad actividad) {
		// TODO Auto-generated method stub
		int rpta = aR.buscarNombreActividad(actividad.getNombreActividad());
		if (rpta == 0) {
			aR.save(actividad);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void modificar(Actividad actividad) {
		// TODO Auto-generated method stub
		aR.save(actividad);
	}

	@Override
	public void eliminar(int idActividad) {
		// TODO Auto-generated method stub
		aR.deleteById(idActividad);
	}

	@Override
	public Optional<Actividad> listarId(int idActividad) {
		// TODO Auto-generated method stub
		return aR.findById(idActividad);
	}

	@Override
	public List<Actividad> listar() {
		// TODO Auto-generated method stub
		return aR.findAll(Sort.by(Sort.Direction.ASC, "nombreActividad"));
	}

	@Override
	public List<Actividad> buscarNombre(String nombreActividad) {
		// TODO Auto-generated method stub
		return aR.findByNombreActividad(nombreActividad);
	}

}
