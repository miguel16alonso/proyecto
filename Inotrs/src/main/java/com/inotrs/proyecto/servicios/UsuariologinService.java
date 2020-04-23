/*package com.inotrs.proyecto.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inotrs.proyecto.modelo.Usuariologin;
import com.inotrs.proyecto.repositorios.UsuariologinRepository;

@Service
public class UsuariologinService {

	@Autowired
	UsuariologinRepository repositorio;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	
	public Usuariologin registrar(Usuariologin u) {
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		return repositorio.save(u);
	}
	
	public Usuariologin findById(long id) {
		return repositorio.findById(id).orElse(null);
	}
	
	public Usuariologin buscarPorEmail(String email) {
		return repositorio.findFirstByEmail(email);
	}
}

create table usuariologin(
    id BIGINT NOT NULL,
    nombre VARCHAR(255),
    apellidos VARCHAR(255),
    avatar VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
   	primary key (id)
);

*/




