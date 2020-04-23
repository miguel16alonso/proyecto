package com.inotrs.proyecto.controladores;

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

import com.inotrs.proyecto.modelo.Categoria;
import com.inotrs.proyecto.servicios.CategoriaService;
import com.inotrs.proyecto.servicios.ProductoService;

@Controller
@RequestMapping("/admin/categoria")
public class CategoriaController {
	
	@Autowired CategoriaService categoriaService;
	@Autowired ProductoService productoService;
		
	@GetMapping("/")
	public String index(Model model, Pageable pageable) {
		model.addAttribute("categorias", categoriaService.findAllPaginated(pageable));

		return "admin/list-categoria";
	}
			
	@GetMapping("/nueva")
	public String nuevaCategoria(Model model) {
		model.addAttribute("categoria", new Categoria());
		return "admin/form-categoria";
	}
	
	@PostMapping("/nueva/submit")
	public String submitNuevaCategoria(@Valid Categoria categoria, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "admin/form-categoria";
		} else {
			categoriaService.save(categoria);
			return "redirect:/admin/categoria/";

		}
	}
	
	@GetMapping("/editar/{id}")
	public String editarCategoria(@PathVariable("id") Long id, Model model) {
		
		Categoria categoria = categoriaService.findById(id);
		
		if (categoria != null) {
			model.addAttribute("categoria", categoria);
			return "admin/form-categoria";
		} else {
			return "redirect:/admin/categoria/";
		}
		
	}
	
	@GetMapping("/borrar/{id}")
	public String borrarCategoria(@PathVariable("id") Long id, Model model) {
		
		Categoria categoria = categoriaService.findById(id);
		
		if (categoria != null) {
			
			if (productoService.numeroProductosCategoria(categoria) == 0) {
				categoriaService.delete(categoria);				
			} else {
				return "redirect:/admin/categoria/?error=true";
			}
			
		} 

		return "redirect:/admin/categoria/";
		
		
	}
	
}
