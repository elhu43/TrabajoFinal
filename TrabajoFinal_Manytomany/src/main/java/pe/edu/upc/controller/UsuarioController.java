package pe.edu.upc.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import pe.edu.upc.entity.Usuario;
import pe.edu.upc.service.IUsuarioService;

@Controller
@SessionAttributes("usuario")
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService uService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/bienvenido")
	public String bienvenido(Model model) {
		return "bienvenido";
	}
	
	@GetMapping("/nuevo")
	public String nuevoUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/usuario";
	}
	
	@PostMapping("/guardar")
	public String guardarUsuario(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "/usuario/usuario";
		} else {
			for (int i = 0; i < 1; i++) {
				usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
				System.out.println(usuario.getPassword());
			}
			boolean flag = uService.insertar(usuario);
			if(flag){
				model.addAttribute("listaUsuarios", uService.listar());
				return "/usuario/listaUsuario";
			} else {
				model.addAttribute("mensaje", "Ya existe el usuario");
				return "/usuario/usuario";
			}
		}
		
	}
	
	@GetMapping("/listar")
	public String listarUsuarios(Model model) {
		try {
			model.addAttribute("usuario", new Usuario());
			model.addAttribute("listaUsuarios", uService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/usuario/listaUsuario";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/detalle/{id}")
	public String detailsUsuario(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Usuario> usuario = uService.listarId(id);
			if (!usuario.isPresent()) {
				model.addAttribute("info", "El usuario no existe");
				return "redirect:/usuarios/listar";
			} else {
				model.addAttribute("usuario", usuario.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/usuario/usuario";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				uService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar el usuario");
		}
		model.put("listaUsuarios", uService.listar());

		return "redirect:/usuarios/listar";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Usuario usuario) {

		List<Usuario> listaUsuarios;

		usuario.setNombreUsuario(usuario.getNombreUsuario());
		listaUsuarios = uService.buscarNombre(usuario.getNombreUsuario());

		if (listaUsuarios.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaUsuarios", listaUsuarios);
		return "usuario/listaUsuario";

	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Usuario> objUsu = uService.listarId(id);

		if (objUsu == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un error");
			return "redirect:/usuarios/listar";
		} else {
			model.addAttribute("usuario", objUsu.get());
			return "usuario/usuario";
		}
	}

	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Usuario> usuario = uService.listarId(id);
		if (usuario == null) {
			flash.addFlashAttribute("error", "El Usuario no existe en la base de datos");
			return "redirect:/usuarios/listar";
		}

		model.put("usuario", usuario.get());

		return "usuario/ver";
	}
	
}
