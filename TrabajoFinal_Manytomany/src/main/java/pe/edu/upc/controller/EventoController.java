package pe.edu.upc.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Evento;
import pe.edu.upc.service.IEventoService;
import pe.edu.upc.service.ILineaService;
import pe.edu.upc.service.ILocalService;
import pe.edu.upc.service.IUploadFileService;

@Controller
@SessionAttributes("evento")
@RequestMapping("/eventos")
public class EventoController {

	@Autowired
	private IEventoService eService;
	@Autowired
	private ILocalService lService;
	@Autowired
	private ILineaService liService;
	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping("/bienvenido")
	public String bienvenido(Model model) {
		return "bienvenido";
	}

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/nuevo")
	public String nuevoEvento(Model model) {
		model.addAttribute("evento", new Evento());
		model.addAttribute("listaEventos", eService.listar());
		model.addAttribute("listaLocales", lService.listar());
		model.addAttribute("listaLineas", liService.listar());
		return "evento/evento";
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/guardar")
	public String guardarEvento(@Valid Evento evento, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listaLocales", lService.listar());
			model.addAttribute("listaLineas", liService.listar());
			return "/evento/evento";
		} else {
			if (!foto.isEmpty()) {

				if (evento.getIdEvento() > 0 && evento.getFoto() != null && evento.getFoto().length() > 0) {

					uploadFileService.delete(evento.getFoto());
				}

				String uniqueFilename = null;
				try {
					uniqueFilename = uploadFileService.copy(foto);
				} catch (IOException e) {
					e.printStackTrace();
				}

				flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
				evento.setFoto(uniqueFilename);
			}
			int rpta = eService.insertar(evento);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/evento/evento";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}

		model.addAttribute("listaEventos", eService.listar());
		return "/evento/listaEvento";

	}

	@GetMapping("/listar")
	public String listarEventos(Model model) {
		try {
			model.addAttribute("evento", new Evento());

			model.addAttribute("listaEventos", eService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/evento/listaEvento";
	}

	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@GetMapping("/detalle/{id}")
	public String detailsEvento(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Evento> evento = eService.listarId(id);

			if (!evento.isPresent()) {
				model.addAttribute("info", "El evento no existe");
				return "redirect:/eventos/listar";
			} else {
				model.addAttribute("evento", evento.get());

			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/evento/evento";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				eService.eliminar(id);
				model.put("mensaje", "Se elimino correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar el evento");
		}
		model.put("listaEventos", eService.listar());

		return "redirect:/eventos/listar";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Evento evento) throws ParseException {

		List<Evento> listaEventos;

		evento.setNombreEvento(evento.getNombreEvento());
		listaEventos = eService.buscar(evento.getNombreEvento());

		if (listaEventos.isEmpty()) {
			listaEventos = eService.buscarLocal(evento.getNombreEvento());
		}

		if (listaEventos.isEmpty()) {
			listaEventos = eService.buscarLinea(evento.getNombreEvento());
		}

		if (listaEventos.isEmpty()) {
			model.put("mensaje", "No se encontro evento");
		}
		model.put("listaEventos", listaEventos);
		return "evento/listaEvento";

	}

	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Evento> evento = eService.listarId(id);
		if (evento == null) {
			flash.addFlashAttribute("error", "El Evento no existe en la base de datos");
			return "redirect:/eventos/listar";
		}

		model.put("evento", evento.get());

		return "evento/ver";
	}

	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Evento> objEve = eService.listarId(id);

		if (objEve == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un error");
			return "redirect:/eventos/listar";
		} else {
			model.addAttribute("listaLocales", lService.listar());
			model.addAttribute("listaLineas", liService.listar());

			model.addAttribute("evento", objEve.get());
			return "evento/evento";
		}
	}
	

}
