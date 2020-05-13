package com.inotrs.proyecto.controladores.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.inotrs.proyecto.modelo.Prioridad;
import com.inotrs.proyecto.modelo.Producto;
import com.inotrs.proyecto.modelo.Tecnico;
import com.inotrs.proyecto.modelo.Usuario;
import com.inotrs.proyecto.servicios.IncidenciaService;
import com.inotrs.proyecto.servicios.TecnicoService;
import com.inotrs.proyecto.servicios.UsuarioService;

@Controller
@RequestMapping("/admin/incidencia")
public class IncidenciaController {

	@Autowired IncidenciaService incidenciaService;
	
	@Autowired UsuarioService usuarioService;
	@Autowired TecnicoService tecnicoService;
	
	/*
	@GetMapping("/")
	public String index(Model model, Pageable pageable) {
		model.addAttribute("incidencias", incidenciaService.findAllPaginated(pageable));
		return "admin/list-incidencia";
	}
*/
	
	@GetMapping("/")
	public String index(Model model, @RequestParam(name="q", required=false) String query) {
		ArrayList<Incidencia> resultado = new ArrayList<Incidencia>();
		
		if(query == null) 
			resultado=(ArrayList<Incidencia>) incidenciaService.findAll();
		else {
			Incidencia i=incidenciaService.findById(query);
			resultado.add(i);
		}
		model.addAttribute("incidencias", resultado);
		return "admin/list-incidencia";
	}
	
	@GetMapping("/nueva")
	public String nuevaIncidencia(Model model) {
		model.addAttribute("incidencia", new Incidencia());
		model.addAttribute("usuarios", usuarioService.findAll());
		
		return "admin/form-incidencia";
	}

	@PostMapping("/nueva/submit")
	public String submitNuevoIncidencia(Incidencia incidencia, Model model) {
		
		String id="";
		LocalDate n =LocalDate.now();
		int numero = (int) (Math.random() * 10000);
		id=id+numero+n.getYear()+n.getMonthValue()+n.getDayOfMonth();
		incidencia.setId(id);
		incidencia.setFecha_Inicio(n);
		
		//Fecha resolucion si es alta-1dias media-3dias baja-5dias
		
		
		if(incidencia.getPrioridad().equals(Prioridad.ALTA)) {
			incidencia.setFecha_Fin(n.plusDays(1));
		}else if(incidencia.getPrioridad().equals(Prioridad.MEDIA)) {
			incidencia.setFecha_Fin(n.plusDays(3));
		}else if(incidencia.getPrioridad().equals(Prioridad.BAJA)) {
			incidencia.setFecha_Fin(n.plusDays(5));
		}
		
		ArrayList<Tecnico> tecnicosEdificio=(ArrayList<Tecnico>) tecnicoService.findAllByEdificio(incidencia.getUsuario().getEdificio());
		Tecnico tec=new Tecnico();
		
		if(tecnicosEdificio == null || tecnicosEdificio.size() == 0) {
			
			ArrayList<Tecnico> tecnicos=(ArrayList<Tecnico>) tecnicoService.findAll();
			
			tec=tecnicos.get(0);
			for(Tecnico tecnico: tecnicos)
				if(tecnico.getIncidencias()<tec.getIncidencias())
					tec=tecnico;
			tec.setIncidencias(tec.getIncidencias()+1);
			
		}else {
			
			tec=tecnicosEdificio.get(0);
			for(Tecnico tecnico: tecnicosEdificio)
				if(tecnico.getIncidencias()<tec.getIncidencias())
					tec=tecnico;
			
			tec.setIncidencias(tec.getIncidencias()+1);
			
		}
		
		incidencia.setTecnico(tec);
		
		incidenciaService.save(incidencia);
		return "redirect:/admin/incidencia/";

	}
	
	@GetMapping("/editar/{id}")
	public String editarIncidencia(@PathVariable("id") String id, Model model) {

		Incidencia incidencia = incidenciaService.findById(id);

		if (incidencia != null) {
			model.addAttribute("incidencia", incidencia);
			//model.addAttribute("usuarios", usuarioService.findAll());

			return "admin/form-incidencia";
		} else {
			return "redirect:/admin/incidencia/";
		}
	}
	
	
	@GetMapping("/borrar/{id}")
	public String borrarIncidencia(@PathVariable("id") String id, Model model) {

		Incidencia incidencia = incidenciaService.findById(id);

		if (incidencia != null) {
			Tecnico tec=incidencia.getTecnico();
			tec.setIncidencias(tec.getIncidencias()-1);
			
			incidenciaService.delete(incidencia);
		}

		return "redirect:/admin/incidencia/";

	}
	
	@GetMapping("/incidencia/{id}")
	public String showDetails(@PathVariable("id") String id, Model model) {
		Incidencia p = incidenciaService.findById(id);
		if (p != null) {
			model.addAttribute("incidencia", p);
			return "admin/one-incidencia";
		}
		
		return "redirect:/";
		
	}
}
