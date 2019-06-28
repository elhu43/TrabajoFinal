package pe.edu.upc.controller;

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

import pe.edu.upc.entity.Actividad;
import pe.edu.upc.service.IActividadService;

@Controller
@SessionAttributes("actividad")
@RequestMapping("/actividades")
public class ActividadController {

	@Autowired
	private IActividadService aService;

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}

	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@GetMapping("/nuevo")
	public String nuevaActividad(Model model) {
		model.addAttribute("actividad", new Actividad());
		return "actividad/actividad";
	}

	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@PostMapping("/guardar")
	public String guardarActividad(@Valid Actividad actividad, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "/actividad/actividad";
		} else {
			int rpta = aService.insertar(actividad);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe la actividad");
				return "/actividad/actividad";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listaActividades", aService.listar());
		return "/actividad/listaActividad";
	}

	@GetMapping("/listar")
	public String listarACtividades(Model model) {
		try {
			model.addAttribute("actividad", new Actividad());
			model.addAttribute("listaActividades", aService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/actividad/listaActividad";
	}

	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@GetMapping("/detalle/{id}")
	public String detailsActividad(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Actividad> actividad = aService.listarId(id);
			if (!actividad.isPresent()) {
				model.addAttribute("info", "La actividad no existe");
				return "redirect:/actividades/listar";
			} else {
				model.addAttribute("actividad", actividad.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/actividad/actividad";
	}

	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				aService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar la actividad");
		}
		model.put("listaActividades", aService.listar());

		return "redirect:/actividades/listar";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Actividad actividad) {

		List<Actividad> listaActividades;

		actividad.setNombreActividad(actividad.getNombreActividad());
		listaActividades = aService.buscarNombre(actividad.getNombreActividad());

		if (listaActividades.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaActividades", listaActividades);
		return "actividad/listaActividad";

	}
}
