package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Evento;
import pe.edu.upc.repository.EventoRepository;
import pe.edu.upc.service.IEventoService;

@Service
public class EventoServiceImpl implements IEventoService{

	@Autowired
	private EventoRepository eR;
	
	@Override
	@Transactional
	public Integer insertar(Evento evento) {
		int rpta = eR.buscarNombreEvento(evento.getNombreEvento());
		if(rpta==0) {
			eR.save(evento);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void modificar(Evento evento) {
		eR.save(evento);		
	}

	@Override
	@Transactional
	public void eliminar(int idEvento) {
		eR.deleteById(idEvento);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Evento> listarId(int idEvento) {
		return eR.findById(idEvento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Evento> listar() {
		return eR.findAll(Sort.by(Sort.Direction.ASC, "nombreEvento"));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Evento> buscar(String nombreEvento) {
		return eR.findByNombreEvento(nombreEvento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Evento> buscarLocal(String nombreLocal) {
		return eR.buscarLocal(nombreLocal);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Evento> buscarLinea(String nombreLinea) {
		return eR.buscarLinea(nombreLinea);
	}

}
