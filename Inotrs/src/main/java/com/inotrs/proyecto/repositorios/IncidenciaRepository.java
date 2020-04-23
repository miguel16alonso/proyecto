package com.inotrs.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.inotrs.proyecto.modelo.Incidencia;
import com.inotrs.proyecto.modelo.Usuario;


public interface IncidenciaRepository extends JpaRepository<Incidencia, String>{

	@Query("select p from Incidencia p where p.usuario.login = ?1")
	public List<Incidencia> findByUsuarioId(String usuarioId);
	
	@Query("select p from Incidencia p where p.tecnico.login = ?1")
	public List<Incidencia> findByTecnicoId(String tecnicoId);
}
