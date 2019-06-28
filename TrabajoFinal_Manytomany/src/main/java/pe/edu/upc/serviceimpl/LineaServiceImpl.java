package pe.edu.upc.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Linea;
import pe.edu.upc.repository.LineaRepository;
import pe.edu.upc.service.ILineaService;

@Service
public class LineaServiceImpl implements ILineaService{

	@Autowired
	private LineaRepository lR;
	
	@Override
	@Transactional
	public Integer insertar(Linea linea) {
		int rpta = lR.buscarNombreLinea(linea.getNombreLinea());
		if(rpta==0) {
			lR.save(linea);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void modificar(Linea linea) {
		lR.save(linea);
	}

	@Override
	@Transactional
	public void eliminar(int idLinea) {
		lR.deleteById(idLinea);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Linea> listarId(int idLinea) {
		return lR.findById(idLinea);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Linea> listar() {
		return lR.findAll(Sort.by(Sort.Direction.ASC, "nombreLinea"));
	}

	@Override
	@Transactional(readOnly=true)
	public List<Linea> buscarNombre(String nombreLinea) {
		return lR.findByNombreLinea(nombreLinea);
	}

}
