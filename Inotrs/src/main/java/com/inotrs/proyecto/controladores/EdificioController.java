package com.inotrs.proyecto.controladores;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inotrs.proyecto.modelo.Categoria;
import com.inotrs.proyecto.modelo.Edificio;
import com.inotrs.proyecto.modelo.Tecnico;
import com.inotrs.proyecto.servicios.EdificioService;
import com.inotrs.proyecto.servicios.ProductoService;
import com.inotrs.proyecto.servicios.TecnicoService;

@Controller
@RequestMapping("/admin/edificio")
public class EdificioController {
	
	@Autowired EdificioService edificioService;
	@Autowired ProductoService productoService;
	@Autowired TecnicoService tecnicoService;
	/*	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("edificios", edificioService.findAll());

		return "admin/list-edificio";
	}
	*/
	
	@GetMapping("/")
	public String index(Model model, Pageable pageable) {
		model.addAttribute("edificios", edificioService.findAllPaginated(pageable));

		return "admin/list-edificio";
	}
			
	@GetMapping("/nueva")
	public String nuevaEdificio(Model model) {
		model.addAttribute("edificio", new Edificio());
		return "admin/form-edificio";
	}
	
	@PostMapping("/nueva/submit")
	public String submitNuevaEdificio(@Valid Edificio edificio, BindingResult bindingResult, Model model) {
		
		
		
		if (bindingResult.hasErrors()) {
			return "admin/form-edificio";
		} else {
			edificioService.save(edificio);
			return "redirect:/admin/edificio/";

		}
		
		
	}
	
	@GetMapping("/editar/{id}")
	public String editarEdificio(@PathVariable("id") Long id, Model model) {
		
		Edificio edificio = edificioService.findById(id);
		
		if (edificio != null) {
			model.addAttribute("edificio", edificio);
			return "admin/form-edificio";
		} else {
			return "redirect:/admin/edificio/";
		}
		
	}
	
	@GetMapping("/borrar/{id}")
	public String borrarEdificio(@PathVariable("id") Long id, Model model) {
		
		Edificio edificio = edificioService.findById(id);
		
		if (edificio != null) {
			
			if (productoService.numeroProductosEdificio(edificio) == 0) {
				edificioService.delete(edificio);				
			} else {
				return "redirect:/admin/edificio/?error=true";
			}
			
		} 

		return "redirect:/admin/edificio/";
		
		
	}
	
	
	@GetMapping("/tecnicoEdificio")
	public String indexo(Model model) {
		
		
		model.addAttribute("edificios", edificioService.findAll());
		
		List<Tecnico> tecnicos;
		
		tecnicos = tecnicoService.findAll();

		model.addAttribute("tecnicos", tecnicos);
		
		return "admin/list-edificio_tecnico";
	}
	
	@GetMapping("/edificiotec/{id}")
	public String showDetails(@PathVariable("id") Long id, Model model) {
		
		List<Tecnico> tecnicos;
		
		tecnicos = tecnicoService.findAllByEdificio(id);
		
		model.addAttribute("tecnicos", tecnicos);
		return "/admin/list-tecnicoedificio";
		
	}

}
