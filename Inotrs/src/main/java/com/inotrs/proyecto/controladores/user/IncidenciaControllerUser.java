package com.inotrs.proyecto.controladores.user;

import java.time.LocalDate;
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

import com.inotrs.proyecto.modelo.Estado;
import com.inotrs.proyecto.modelo.Incidencia;
import com.inotrs.proyecto.modelo.Prioridad;
import com.inotrs.proyecto.modelo.Tecnico;
import com.inotrs.proyecto.modelo.Usuario;
import com.inotrs.proyecto.servicios.IncidenciaService;
import com.inotrs.proyecto.servicios.TecnicoService;
import com.inotrs.proyecto.servicios.UsuarioService;

@Controller
@RequestMapping("/user/incidencia")
public class IncidenciaControllerUser {
	
@Autowired IncidenciaService incidenciaService;
	
	@Autowired UsuarioService usuarioService;
	@Autowired TecnicoService tecnicoService;
	
	@GetMapping("/")
	public String index(Model model, Pageable pageable) {
		model.addAttribute("incidencias", incidenciaService.findAllPaginated(pageable));
		return "user/incidencia-list";
	}
	

	@GetMapping("/nueva")
	public String nuevaIncidencia(Model model) {
		model.addAttribute("incidencia", new Incidencia());
		model.addAttribute("usuarios", usuarioService.findAll());
		
		return "user/form-incidencia";
	}

	@PostMapping("/nueva/submit")
	public String submitNuevoIncidencia(Incidencia incidencia, Model model) {
		String id="";
		LocalDate n =LocalDate.now();
		LocalDate f=LocalDate.now().plusYears(100);
		int numero = (int) (Math.random() * 10000);
		id=id+numero+n.getYear()+n.getMonthValue()+n.getDayOfMonth();
		incidencia.setId(id);
		incidencia.setFecha_Inicio(n);
		incidencia.setFecha_Fin(f);
		incidencia.setEstado(Estado.ABIERTA);
		
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
		
		String url="redirect:/user/incidencia/incidencia/"+incidencia.getId();
		
		return url;

	}
	
	
	
	@GetMapping("/incidencia/{id}")
	public String showDetails(@PathVariable("id") String id, Model model) {
		Incidencia p = incidenciaService.findById(id);
		if (p != null) {
			model.addAttribute("incidencia", p);
			return "user/incidencia-one";
		}
		
		return "redirect:/";
		
	}
	
	@GetMapping("/incidenciaUsuario/{id}")
	public String showDetails1(@PathVariable("id") String id, Model model) {
		
		List<Incidencia> incidencias;
		
		incidencias = incidenciaService.findAllByUsuario(id);
		
		model.addAttribute("incidencias", incidencias);
		return "user/incidencia-list";
		
	}
	
	@GetMapping("/inventarioUsuario/{id}")
	public String showDetails2(@PathVariable("id") String id, Model model) {
		
		Usuario p = usuarioService.findById(id);
		if (p != null) {
			model.addAttribute("usuario", p);
			return "user/usuario-one";
		}
		
		return "redirect:/";
		
	}
}


