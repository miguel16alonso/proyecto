package com.inotrs.proyecto.servicios;

import java.util.List;

import com.inotrs.proyecto.modelo.Categoria;
import com.inotrs.proyecto.modelo.Edificio;
import com.inotrs.proyecto.modelo.Producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductoService {

	public List<Producto> findAll();
	public List<Producto> findAllByCategoria(Categoria categoria);
	public List<Producto> findAllByCategoria(Long categoriaId);
	public Producto findById(String id);
	public Producto save(Producto producto);
	public Producto delete(Producto producto);
	public int numeroProductosCategoria(Categoria categoria);
	public int numeroProductosEdificio(Edificio edificio);
	public List<Producto> findAllByEdificio(Edificio edificio);
	public List<Producto> findAllByEdificio(Long edificioId);
	public Page<Producto> findAllPaginated(Pageable pageable);
}
