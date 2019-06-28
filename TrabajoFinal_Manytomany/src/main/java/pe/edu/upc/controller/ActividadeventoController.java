package pe.edu.upc.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Actividadevento;
import pe.edu.upc.service.IActividadService;
import pe.edu.upc.service.IActividadeventoService;
import pe.edu.upc.service.IEventoService;

@Controller
@SessionAttributes("actividadevento")
@RequestMapping("/actividadeventos")
public class ActividadeventoController {

	@Autowired
	private IActividadeventoService aService;
	@Autowired
	private IEventoService eService;
	@Autowired
	private IActividadService acService;
	
	@GetMapping("/bienvenido")
	public String bienvenido(Model model) {
		return "bienvenido";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO"})
	@GetMapping("/nuevo")
	public String nuevaActividadevento(Model model) {
		model.addAttribute("actividadevento", new Actividadevento());
		model.addAttribute("listaActividadeventos", aService.listar());
		model.addAttribute("listaEventos", eService.listar());
		model.addAttribute("listaActividades", acService.listar());
		return "actividadevento/actividadevento";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@PostMapping("/guardar")
	public String guardarActividadevento(@Valid Actividadevento actividadevento, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listaActividadeventos", aService.listar());
			return "/actividadevento/actividadevento";
		} else {
			aService.insertar(actividadevento);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
			return "redirect:/actividadeventos/listar";
		}
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@GetMapping("/listar")
	public String listarActividadeventos(Model model) {
		try {
			model.addAttribute("actividadevento", new Actividadevento());

			model.addAttribute("listaActividadeventos", aService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/actividadevento/listaActividadevento";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@GetMapping("/detalle/{id}")
	public String detailsActividadevento(@PathVariable(value = "id") int idAct, Model model) {
		try {
			Optional<Actividadevento> actividadevento = aService.listarId(idAct);

			if (!actividadevento.isPresent()) {
				model.addAttribute("info", "El item seleccionado no existe");
				return "redirect:/actividadeventos/listar";
			} else {
				model.addAttribute("actividadevento", actividadevento.get());

			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/actividadevento/actividadevento";
	}

	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				aService.eliminar(id);
				model.put("mensaje", "Se elimino correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar el item");
		}
		model.put("listaActividadeventos", aService.listar());

		return "redirect:/actividadeventos/listar";
	}

	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Actividadevento actividadevento) throws ParseException {

		List<Actividadevento> listaActividadeventos;

		listaActividadeventos = aService.buscarActividad(actividadevento.getDescripcion());

		if (listaActividadeventos.isEmpty()) {
			listaActividadeventos = aService.buscarEvento(actividadevento.getDescripcion());
		}

		if (listaActividadeventos.isEmpty()) {
			model.put("mensaje", "No se encontro item");
		}
		model.put("listaActividadeventos", listaActividadeventos);
		return "actividadevento/listaActividadevento";

	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Actividadevento> objActeve = aService.listarId(id);

		if (objActeve == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un error");
			return "redirect:/actividadeventos/listar";
		} else {
			model.addAttribute("listaActividades", acService.listar());
			model.addAttribute("listaEventos", eService.listar());
			model.addAttribute("actividadevento", objActeve.get());
			return "actividadevento/actividadevento";
		}
	}
	
}
