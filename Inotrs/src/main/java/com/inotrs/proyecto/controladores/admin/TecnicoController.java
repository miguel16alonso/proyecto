package com.inotrs.proyecto.controladores.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.inotrs.proyecto.servicios.TecnicoService;

@Controller
@RequestMapping("/admin/tecnico")
public class TecnicoController {

	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private EdificioService edificioService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private IncidenciaService incidenciaService;
	
	/*
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("tecnicos", tecnicoService.findAll());
		return "admin/list-tecnico";
	}
*/
	
	@GetMapping("/")
	public String index(Model model, @RequestParam(name="q", required=false) String query) {
		List<Tecnico> resultado = (query == null) ? tecnicoService.findAll() : tecnicoService.buscador(query);
		model.addAttribute("tecnicos", resultado);
		return "admin/list-tecnico";
	}
	
	@GetMapping("/nuevo")
	public String nuevaTecnico(Model model) {
		model.addAttribute("tecnico", new Tecnico());
		model.addAttribute("edificios", edificioService.findAll());
		
		ArrayList<Producto> productos=(ArrayList<Producto>) productoService.findAll();
		ArrayList<Producto> productosStock=new ArrayList<Producto>();
		
		for(Producto producto: productos)
			if(producto.getStock()>0)
				productosStock.add(producto);
		
		model.addAttribute("productos", productosStock);
		return "admin/form-tecnico";
	}

	@PostMapping("/nuevo/submit")
	public String submitNuevoTecnico(Tecnico tecnico, Model model) {
		
		Producto p=tecnico.getProducto();
		p.setStock(p.getStock()-1);
		tecnico.setProducto(p);
		
		tecnicoService.save(tecnico);
		return "redirect:/admin/tecnico/";

	}
	
	@GetMapping("/editar/{login}")
	public String editarTecnico(@PathVariable("login") String login, Model model) {

		Tecnico tecnico = tecnicoService.findById(login);

		if (tecnico != null) {
			model.addAttribute("tecnico", tecnico);
			model.addAttribute("edificios", edificioService.findAll());
			
			ArrayList<Producto> productos=(ArrayList<Producto>) productoService.findAll();
			ArrayList<Producto> productosStock=new ArrayList<Producto>();
			
			for(Producto producto: productos)
				if(producto.getStock()>0)
					productosStock.add(producto);
			
			model.addAttribute("productos", productosStock);
			return "admin/form-tecnico";
		} else {
			return "redirect:/admin/tecnico/";
		}
	}
	
	
	@GetMapping("/borrar/{login}")
	public String borrarTecnico(@PathVariable("login") String login, Model model) {

		Tecnico tecnico = tecnicoService.findById(login);

		if (tecnico != null) {
			Producto p=tecnico.getProducto();
			p.setStock(p.getStock()+1);
			tecnico.setProducto(p);
			tecnicoService.delete(tecnico);
		}

		return "redirect:/admin/tecnico/";

	}
	
	@GetMapping("/tecnico/{login}")
	public String showDetails(@PathVariable("login") String login, Model model) {
		Tecnico p = tecnicoService.findById(login);
		ArrayList<Incidencia> incidencias = new ArrayList<Incidencia>();
		incidencias=(ArrayList<Incidencia>) incidenciaService.findAllByTecnico(login);
		if (p != null) {
			model.addAttribute("tecnico", p);
			model.addAttribute("incidencias", incidencias);
			return "admin/one-tecnico";
		}
		
		return "redirect:/";
		
	}

	
}
