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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Linea;
import pe.edu.upc.service.ILineaService;

@Controller
@SessionAttributes("linea")
@RequestMapping("/lineas")
public class LineaController {
	@Autowired
	private ILineaService lService;
	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}
	@Secured("ROLE_ADMIN")
	@GetMapping("/nuevo")
	public String nuevaLinea(Model model) {
		model.addAttribute("linea", new Linea());
		return "linea/linea";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/guardar")
	public String guardarLinea(@Valid Linea linea, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "/linea/linea";
		} else {
			int rpta = lService.insertar(linea);
			if(rpta > 0){
				model.addAttribute("mensaje", "Ya existe la linea");
				return "/linea/linea";
			} else {
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
			}
		}
		model.addAttribute("listaLineas", lService.listar());
		return "/linea/listaLinea";
	}

	
	@GetMapping("/listar")
	public String listarLineas(Model model) {
		try {
			model.addAttribute("linea", new Linea());
			model.addAttribute("listaLineas", lService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/linea/listaLinea";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })	
	@GetMapping("/detalle/{id}")
	public String detailsLinea(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Linea> linea = lService.listarId(id);
			if (!linea.isPresent()) {
				model.addAttribute("info", "La linea no existe");
				return "redirect:/lineas/listar";
			} else {
				model.addAttribute("linea", linea.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/linea/linea";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				lService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar la linea");
		}
		model.put("listaLineas", lService.listar());

		return "redirect:/lineas/listar";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Linea linea) {

		List<Linea> listaLineas;

		linea.setNombreLinea(linea.getNombreLinea());
		listaLineas = lService.buscarNombre(linea.getNombreLinea());

		if (listaLineas.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaLineas", listaLineas);
		return "linea/listaLinea";

	}
	
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Linea> linea = lService.listarId(id);
		if (linea == null) {
			flash.addFlashAttribute("error", "La linea no existe en la base de datos");
			return "redirect:/lineas/listar";
		}

		model.put("linea", linea.get());

		return "linea/ver";
	}
}
