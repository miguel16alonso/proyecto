package com.inotrs.proyecto.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inotrs.proyecto.modelo.Edificio;
import com.inotrs.proyecto.repositorios.EdificioRepository;

@Service
public class EdificioService implements IEdificioService {

	@Autowired EdificioRepository edificioRepositorio;
	
	@Override
	public List<Edificio> findAll() {
		return (List<Edificio>) edificioRepositorio.findAll();
	}
	
	public Page<Edificio> findAllPaginated(Pageable pageable) {
		return edificioRepositorio.findAll(pageable);
	}

	@Override
	public Edificio save(Edificio edificio) {
		return edificioRepositorio.save(edificio);
	}

	@Override
	public Edificio findById(Long id) {
		return edificioRepositorio.findById(id).orElse(null);
	}

	@Override
	public Edificio delete(Edificio edificio) {
		Edificio result = findById(edificio.getId());
		edificioRepositorio.delete(result);
		return result;
	}

}
