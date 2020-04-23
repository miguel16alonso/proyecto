package com.inotrs.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inotrs.proyecto.modelo.Edificio;
import com.inotrs.proyecto.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{
	
	public List<Usuario> findByEdificio(Edificio edificio);

	@Query("select p from Usuario p where p.edificio.id = ?1")
	public List<Usuario> findByEdificioId(Long edificioId);
	
	@Query("select count(p) from Usuario p where p.edificio = ?1")
	public int findNumUsuariossByEdificio(Edificio edificio);
	
	@Query("select p from Usuario p where p.producto.id = ?1")
	public List<Usuario> findByProductoId(String productoId);
	
	List<Usuario> findByNombreContainsIgnoreCaseOrApellidosContainsIgnoreCaseOrTelefonoContainsIgnoreCase(String nombre, String apellidos, String telefono);
	
}
