package com.inotrs.proyecto.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inotrs.proyecto.modelo.Edificio;
import com.inotrs.proyecto.modelo.Tecnico;
import com.inotrs.proyecto.modelo.Usuario;
import com.inotrs.proyecto.repositorios.TecnicoRepository;

@Service
public class TecnicoService implements ITecnicoService {

	@Autowired TecnicoRepository tecnicoRepositorio;
	
	@Override
	public List<Tecnico> findAll() {
		return (List<Tecnico>) tecnicoRepositorio.findAll();
	}

	@Override
	public Tecnico findById(String id) {
		return tecnicoRepositorio.findById(id).orElse(null);
	}

	@Override
	public Tecnico save(Tecnico tecnico) {
		return tecnicoRepositorio.save(tecnico);
	}

	@Override
	public Tecnico delete(Tecnico tecnico) {
		Tecnico result = findById(tecnico.getLogin());
		tecnicoRepositorio.delete(result);
		return result;
	}

	@Override
	public int numeroTecnicosEdificio(Edificio edificio) {
		return tecnicoRepositorio.findNumTecnicosByEdificio(edificio);
	}

	@Override
	public List<Tecnico> findAllByEdificio(Edificio edificio) {
		return tecnicoRepositorio.findByEdificio(edificio);
	}

	@Override
	public List<Tecnico> findAllByEdificio(Long edificioId) {
		return tecnicoRepositorio.findByEdificioId(edificioId);
	}

	@Override
	public List<Tecnico> findAllByProducto(String productoId) {
		return tecnicoRepositorio.findByProductoId(productoId);
	}
	
	public List<Tecnico> buscador(String cadena) {
		 return tecnicoRepositorio.findByNombreContainsIgnoreCaseOrApellidosContainsIgnoreCaseOrTelefonoContainsIgnoreCase(cadena, cadena, cadena);
	}

}
