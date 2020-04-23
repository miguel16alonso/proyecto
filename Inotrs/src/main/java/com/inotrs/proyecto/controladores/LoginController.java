/*package com.inotrs.proyecto.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.inotrs.proyecto.modelo.Usuariologin;
import com.inotrs.proyecto.servicios.UsuariologinService;

@Controller
public class LoginController {
	
	@Autowired
	UsuariologinService usuarioServicio;
	
	@GetMapping("/")
	public String welcome() {
		return "redirect:/index";
	}
	
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("usuario", new Usuariologin());
		return "login";
	}
	
	
	@PostMapping("/register")
	public String register(@ModelAttribute Usuariologin usuario) {		
		usuarioServicio.registrar(usuario);
		return "redirect:/login";
	}

}*/
