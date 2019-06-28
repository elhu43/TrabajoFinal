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

import pe.edu.upc.entity.Usuarioevento;
import pe.edu.upc.service.IEventoService;
import pe.edu.upc.service.IUsuarioService;
import pe.edu.upc.service.IUsuarioeventoService;

@Controller
@SessionAttributes("usuarioevento")
@RequestMapping("/usuarioeventos")
public class UsuarioeventoController {

	@Autowired
	private IUsuarioeventoService uService;
	@Autowired
	private IEventoService eService;
	@Autowired
	private IUsuarioService usService;
	
	@GetMapping("/bienvenido")
	public String bienvenido(Model model) {
		return "bienvenido";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO"})
	@GetMapping("/nuevo")
	public String nuevoUsuarioevento(Model model) {
		model.addAttribute("usuarioevento", new Usuarioevento());
		model.addAttribute("listaUsuarioeventos", uService.listar());
		model.addAttribute("listaEventos", eService.listar());
		model.addAttribute("listaUsuarios", usService.listar());
		return "usuarioevento/usuarioevento";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@PostMapping("/guardar")
	public String guardarUsuarioevento(@Valid Usuarioevento usuarioevento, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listaUsuarioeventos", uService.listar());
			return "/usuarioevento/usuarioevento";
		} else {
			uService.insertar(usuarioevento);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
			return "redirect:/usuarioeventos/listar";
		}
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@GetMapping("/listar")
	public String listarUsuarioeventos(Model model) {
		try {
			model.addAttribute("usuarioevento", new Usuarioevento());

			model.addAttribute("listaUsuarioeventos", uService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/usuarioevento/listaUsuarioevento";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@GetMapping("/detalle/{id}")
	public String detailsUsuarioevento(@PathVariable(value = "id") int idUsu, Model model) {
		try {
			Optional<Usuarioevento> usuarioevento = uService.listarId(idUsu);

			if (!usuarioevento.isPresent()) {
				model.addAttribute("info", "El item seleccionado no existe");
				return "redirect:/usuarioeventos/listar";
			} else {
				model.addAttribute("usuarioevento", usuarioevento.get());

			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/usuarioevento/usuarioevento";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				uService.eliminar(id);
				model.put("mensaje", "Se elimino correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar el item");
		}
		model.put("listaUsuarioeventos", uService.listar());

		return "redirect:/usuarioeventos/listar";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Usuarioevento usuarioevento) throws ParseException {

		List<Usuarioevento> listaUsuarioeventos;

		listaUsuarioeventos = uService.buscarUsuario(usuarioevento.getDescripcion());

		if (listaUsuarioeventos.isEmpty()) {
			listaUsuarioeventos = uService.buscarEvento(usuarioevento.getDescripcion());
		}

		if (listaUsuarioeventos.isEmpty()) {
			model.put("mensaje", "No se encontro item");
		}
		model.put("listaUsuarioeventos", listaUsuarioeventos);
		return "usuarioevento/listaUsuarioevento";

	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ENCARGADO" })
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Usuarioevento> objUsueve = uService.listarId(id);

		if (objUsueve == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un error");
			return "redirect:/usuarioeventos/listar";
		} else {
			model.addAttribute("listaUsuarios", usService.listar());
			model.addAttribute("listaEventos", eService.listar());
			model.addAttribute("usuarioevento", objUsueve.get());
			return "usuarioevento/usuarioevento";
		}
	}

}
