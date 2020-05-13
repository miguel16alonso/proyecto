package com.inotrs.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.inotrs.proyecto.modelo.Edificio;
import com.inotrs.proyecto.modelo.Producto;
import com.inotrs.proyecto.modelo.Tecnico;
import com.inotrs.proyecto.modelo.Usuario;

public interface TecnicoRepository extends CrudRepository<Tecnico, String>{
	
	public List<Tecnico> findByEdificio(Edificio edificio);

	@Query("select p from Tecnico p where p.edificio.id = ?1")
	public List<Tecnico> findByEdificioId(Long edificioId);
	
	@Query("select count(p) from Tecnico p where p.edificio = ?1")
	public int findNumTecnicosByEdificio(Edificio edificio);
	
	@Query("select p from Tecnico p where p.producto.id = ?1")
	public List<Tecnico> findByProductoId(String productoId);
	
	List<Tecnico> findByNombreContainsIgnoreCaseOrApellidosContainsIgnoreCaseOrTelefonoContainsIgnoreCase(String nombre, String apellidos, String telefono);

	@Query("select count(p) from Tecnico p where p.producto = ?1")
	public int findNumTecnicoByProducto(Producto producto);
	
}
