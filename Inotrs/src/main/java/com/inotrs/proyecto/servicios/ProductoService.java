package com.inotrs.proyecto.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inotrs.proyecto.modelo.Categoria;
import com.inotrs.proyecto.modelo.Edificio;
import com.inotrs.proyecto.modelo.Producto;
import com.inotrs.proyecto.repositorios.ProductoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ProductoService implements IProductoService {

	@Autowired ProductoRepository productoRepositorio;
	
	public List<Producto> findAll() {
		return (List<Producto>) productoRepositorio.findAll();
	}
	
	public Page<Producto> findAllPaginated(Pageable pageable) {
		return productoRepositorio.findAll(pageable);
	}
	
	public List<Producto> findAllByCategoria(Categoria categoria) {
		return productoRepositorio.findByCategoria(categoria);
	}
	
	public List<Producto> findAllByCategoria(Long categoriaId) {
		return productoRepositorio.findByCategoriaId(categoriaId);
	}
	
	public Producto findById(String id) {
		return productoRepositorio.findById(id).orElse(null);
	}
	
	public Producto save(Producto producto) {
		return productoRepositorio.save(producto);
	}
	
	public Producto delete(Producto producto) {
		Producto result = findById(producto.getId());
		productoRepositorio.delete(result);
		return result;
	}
	
	public int numeroProductosCategoria(Categoria categoria) {
		return productoRepositorio.findNumProductosByCategoria(categoria);
	}

	@Override
	public int numeroProductosEdificio(Edificio edificio) {
		return productoRepositorio.findNumProductosByEdificio(edificio);
	}

	@Override
	public List<Producto> findAllByEdificio(Edificio edificio) {
		return productoRepositorio.findByEdificio(edificio);
	}

	@Override
	public List<Producto> findAllByEdificio(Long edificioId) {
		return productoRepositorio.findByEdificioId(edificioId);
	}
	
}
