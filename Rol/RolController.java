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

import pe.edu.upc.entity.Rol;
import pe.edu.upc.service.IRolService;
import pe.edu.upc.service.IUsuarioService;

@Controller
@SessionAttributes("rol")
@RequestMapping("/roles")
public class RolController {

	@Autowired
	private IRolService rService;
	@Autowired
	private IUsuarioService uService;
	
	@GetMapping("/bienvenido")
	public String bienvenido(Model model) {
		return "bienvenido";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/nuevo")
	public String nuevoRol(Model model) {
		model.addAttribute("rol", new Rol());
		model.addAttribute("listaRoles", uService.listar());
		model.addAttribute("listaUsuarios", uService.listar());
		return "rol/rol";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/guardar")
	public String guardarRol(@Valid Rol rol, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listaUsuarios", uService.listar());
			return "/rol/rol";
		} else {
			boolean flag = rService.insertar(rol);
			if(flag){
				model.addAttribute("listaRoles", rService.listar());
				return "/rol/listaRol";
			} else {
				model.addAttribute("mensaje", "Ya existe el rol");
				return "/rol/rol";
			}
		}
		
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/listar")
	public String listarRoles(Model model) {
		try {
			model.addAttribute("rol", new Rol());
			model.addAttribute("listaRoles", rService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/rol/listaRol";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/detalle/{id}")
	public String detailsRol(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Rol> rol = rService.listarId(id);
			if (!rol.isPresent()) {
				model.addAttribute("info", "El rol no existe");
				return "redirect:/roles/listar";
			} else {
				model.addAttribute("rol", rol.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/rol/rol";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				rService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar el rol");
		}
		model.put("listaRoles", rService.listar());

		return "redirect:/roles/listar";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Rol rol) {

		List<Rol> listaRoles;

		rol.setId(rol.getId());
		listaRoles = rService.buscarRol(rol.getId());

		if (listaRoles.isEmpty()) {
			listaRoles = rService.buscarRolUsuario(rol.getAuthority());
		}
		
		if (listaRoles.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaRoles", listaRoles);
		return "rol/listaRol";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Rol> objRol = rService.listarId(id);

		if (objRol == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un error");
			return "redirect:/roles/listar";
		} else {
			model.addAttribute("listaUsuarios", uService.listar());
			model.addAttribute("rol", objRol.get());
			return "rol/rol";
		}
	}
	
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Rol> rol = rService.listarId(id);
		if (rol == null) {
			flash.addFlashAttribute("error", "El Rol no existe en la base de datos");
			return "redirect:/roles/listar";
		}

		model.put("rol", rol.get());

		return "rol/ver";
	}
}
