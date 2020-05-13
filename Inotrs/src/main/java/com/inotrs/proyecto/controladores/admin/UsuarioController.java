package com.inotrs.proyecto.controladores.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inotrs.proyecto.modelo.Incidencia;
import com.inotrs.proyecto.modelo.Producto;
import com.inotrs.proyecto.modelo.Tecnico;
import com.inotrs.proyecto.modelo.Usuario;
import com.inotrs.proyecto.servicios.EdificioService;
import com.inotrs.proyecto.servicios.IncidenciaService;
import com.inotrs.proyecto.servicios.ProductoService;
import com.inotrs.proyecto.servicios.UsuarioService;

@Controller
@RequestMapping("/admin/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EdificioService edificioService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private IncidenciaService incidenciaService;
	
	@GetMapping("/")
	public String index(Model model, @RequestParam(name="q", required=false) String query) {
		List<Usuario> resultado = (query == null) ? usuarioService.findAll() : usuarioService.buscador(query);
		model.addAttribute("usuarios", resultado);
		return "admin/list-usuario";
	}

	@GetMapping("/nuevo")
	public String nuevaUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("edificios", edificioService.findAll());
		
		ArrayList<Producto> productos=(ArrayList<Producto>) productoService.findAll();
		ArrayList<Producto> productosStock=new ArrayList<Producto>();
		
		for(Producto producto: productos)
			if(producto.getStock()>0)
				productosStock.add(producto);
		
		model.addAttribute("productos", productosStock);
		return "admin/form-usuario";
	}

	@PostMapping("/nuevo/submit")
	public String submitNuevoUsuario(Usuario usuario, Model model) {
		
		Producto p=usuario.getProducto();
		p.setStock(p.getStock()-1);
		usuario.setProducto(p);
		
		usuarioService.save(usuario);
		return "redirect:/admin/usuario/";

	}
	
	@GetMapping("/editar/{login}")
	public String editarUsuario(@PathVariable("login") String login, Model model) {

		Usuario usuario = usuarioService.findById(login);

		if (usuario != null) {
			model.addAttribute("usuario", usuario);
			model.addAttribute("edificios", edificioService.findAll());
			
			ArrayList<Producto> productos=(ArrayList<Producto>) productoService.findAll();
			ArrayList<Producto> productosStock=new ArrayList<Producto>();
			
			for(Producto producto: productos)
				if(producto.getStock()>0)
					productosStock.add(producto);
			
			model.addAttribute("productos", productosStock);
			return "admin/form-usuario";
		} else {
			return "redirect:/admin/usuario/";
		}
	}
	
	
	@GetMapping("/borrar/{login}")
	public String borrarUsuario(@PathVariable("login") String login, Model model) {

		Usuario usuario = usuarioService.findById(login);

		if (usuario != null) {
			Producto p=usuario.getProducto();
			p.setStock(p.getStock()+1);
			usuario.setProducto(p);
			usuarioService.delete(usuario);
		}

		return "redirect:/admin/usuario/";

	}
	
	@GetMapping("/usuario/{login}")
	public String showDetails(@PathVariable("login") String login, Model model) {
		Usuario p = usuarioService.findById(login);
		ArrayList<Incidencia> incidencias = new ArrayList<Incidencia>();
		incidencias=(ArrayList<Incidencia>) incidenciaService.findAllByUsuario(login);
		if (p != null) {
			model.addAttribute("usuario", p);
			model.addAttribute("incidencias", incidencias);
			return "admin/one-usuario";
		}
		
		return "redirect:/";
		
	}


}
