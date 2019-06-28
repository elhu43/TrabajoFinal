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

import pe.edu.upc.entity.Local;
import pe.edu.upc.service.ILocalService;

@Controller
@SessionAttributes("local")
@RequestMapping("/locales")
public class LocalController {
	@Autowired
	private ILocalService lService;
	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}
	@Secured("ROLE_ADMIN")
	@GetMapping("/nuevo")
	public String nuevoLocal(Model model) {
		model.addAttribute("local", new Local());
		return "local/local";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/guardar")
	public String guardarLocal(@Valid Local local, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "/local/local";
		} else {
			int rpta = lService.insertar(local);
			if(rpta > 0){
				model.addAttribute("mensaje", "Ya existe el local");
				return "/local/local";
			} else {
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
			}
		}
		model.addAttribute("listaLocales", lService.listar());
		return "/local/listaLocal";
	}

	@GetMapping("/listar")
	public String listarLocales(Model model) {
		try {
			model.addAttribute("local", new Local());
			model.addAttribute("listaLocales", lService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/local/listaLocal";
	}
	
	@GetMapping("/detalle/{id}")
	public String detailsLocal(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Local> local = lService.listarId(id);
			if (!local.isPresent()) {
				model.addAttribute("info", "El local no existe");
				return "redirect:/locales/listar";
			} else {
				model.addAttribute("local", local.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/local/local";
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
			model.put("mensaje", "No se puede eliminar el local");
		}
		model.put("listaLocales", lService.listar());

		return "redirect:/locales/listar";
	}
	
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Local local) {

		List<Local> listaLocales;

		local.setNombreLocal(local.getNombreLocal());
		listaLocales = lService.buscarNombre(local.getNombreLocal());

		if (listaLocales.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaLocales", listaLocales);
		return "local/listaLocal";

	}
	
}
