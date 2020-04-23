package com.inotrs.proyecto.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.inotrs.proyecto.modelo.Incidencia;
import com.inotrs.proyecto.modelo.Producto;
import com.inotrs.proyecto.modelo.Usuario;

public interface IIncidenciaService {

	public List<Incidencia> findAll();
	public Incidencia findById(String id);
	public Incidencia save(Incidencia incidencia);
	public Incidencia delete(Incidencia incidencia);
	public Page<Incidencia> findAllPaginated(Pageable pageable);
	public List<Incidencia> findAllByUsuario(String usuarioId);
	public List<Incidencia> findAllByTecnico(String tecnicoId);
}
