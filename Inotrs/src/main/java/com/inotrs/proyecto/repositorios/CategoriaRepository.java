package com.inotrs.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inotrs.proyecto.modelo.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	

}
