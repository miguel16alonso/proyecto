package com.inotrs.proyecto.controladores.tecnico;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inotrs.proyecto.modelo.Producto;
import com.inotrs.proyecto.servicios.CategoriaService;
import com.inotrs.proyecto.servicios.EdificioService;
import com.inotrs.proyecto.servicios.ProductoService;
import com.inotrs.proyecto.servicios.TecnicoService;
import com.inotrs.proyecto.servicios.UsuarioService;

@Controller
@RequestMapping("/tecnico/producto")
public class ProductoControllerTecnico {
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private EdificioService edificioService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	
	@GetMapping("/")
	public String index(Model model, Pageable pageable) {
		model.addAttribute("productos", productoService.findAllPaginated(pageable));
		return "tecnico/list-producto";
	}
	
	@GetMapping("/nuevo")
	public String nuevaProducto(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("categorias", categoriaService.findAll());
		model.addAttribute("edificios", edificioService.findAll());
		return "tecnico/form-producto";
	}
	
	
	@PostMapping("/nuevo/submit")
	public String submitNuevoProducto(@Valid Producto producto, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categorias", categoriaService.findAll());
			model.addAttribute("edificios", edificioService.findAll());
			return "tecnico/form-producto";
		} else {
			producto.setStock(producto.getStock()+producto.getCantidad());
			producto.setCantidad(0);
			productoService.save(producto);
			return "redirect:/tecnico/producto/";

		}

	}
	
	@GetMapping("/editar/{id}")
	public String editarProducto(@PathVariable("id") String id, Model model) {

		Producto producto = productoService.findById(id);
		

		if (producto != null) {
			producto.setCantidad(0);
			model.addAttribute("producto", producto);
			model.addAttribute("categorias", categoriaService.findAll());
			model.addAttribute("edificios", edificioService.findAll());
			return "tecnico/form-producto";
		} else {
			return "redirect:/tecnico/producto/";
		}
	}
	
	@GetMapping("/product/{id}")
	public String showDetails(@PathVariable("id") String id, Model model) {
		Producto p = productoService.findById(id);
		if (p != null) {
			model.addAttribute("producto", p);
			model.addAttribute("usuarios", usuarioService.findAllByProducto(id));
			model.addAttribute("tecnicos", tecnicoService.findAllByProducto(id));
			return "tecnico/one-producto";
		}
		
		return "redirect:/";
		
	}


}
