package com.inotrs.proyecto.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.inotrs.proyecto.modelo.Categoria;

public interface ICategoriaService {
	
	public List<Categoria> findAll();
	public Categoria save(Categoria categoria);
	public Categoria findById(Long id);
	public Categoria delete(Categoria categoria);
	public Page<Categoria> findAllPaginated(Pageable pageable);

	

}
