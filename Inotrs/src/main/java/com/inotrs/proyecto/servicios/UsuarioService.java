package com.inotrs.proyecto.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inotrs.proyecto.modelo.Edificio;
import com.inotrs.proyecto.modelo.Producto;
import com.inotrs.proyecto.modelo.Usuario;
import com.inotrs.proyecto.repositorios.UsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService {

	@Autowired UsuarioRepository usuarioRepositorio;
	
	@Override
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioRepositorio.findAll();
	}

	public Page<Usuario> findAllPaginated(Pageable pageable) {
		return usuarioRepositorio.findAll(pageable);
	}
	@Override
	public Usuario findById(String id) {
		return usuarioRepositorio.findById(id).orElse(null);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepositorio.save(usuario);
	}

	@Override
	public Usuario delete(Usuario usuario) {
		Usuario result = findById(usuario.getLogin());
		usuarioRepositorio.delete(result);
		return result;
	}

	@Override
	public int numeroUsuariosEdificio(Edificio edificio) {
		return usuarioRepositorio.findNumUsuariossByEdificio(edificio);
	}

	@Override
	public List<Usuario> findAllByEdificio(Edificio edificio) {
		return usuarioRepositorio.findByEdificio(edificio);
	}

	@Override
	public List<Usuario> findAllByEdificio(Long edificioId) {
		return usuarioRepositorio.findByEdificioId(edificioId);
	}

	@Override
	public List<Usuario> findAllByProducto(String productoId) {
		return usuarioRepositorio.findByProductoId(productoId);
	}

	public List<Usuario> buscador(String cadena) {
		 return usuarioRepositorio.findByNombreContainsIgnoreCaseOrApellidosContainsIgnoreCaseOrTelefonoContainsIgnoreCase(cadena, cadena, cadena);
	}
}
