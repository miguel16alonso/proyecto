package com.inotrs.proyecto.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Incidencia {
	@Id
	private String id;
	private String descripcion;
	private LocalDate fecha_Inicio;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fecha_Fin;
	@Enumerated(EnumType.STRING)
	private Prioridad prioridad;
	@Enumerated(EnumType.STRING)
	private Estado estado;
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	@OneToOne(cascade=CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name="login")
	private Usuario usuario;
	@OneToOne(cascade=CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name="login_Tecnico")
	private Tecnico tecnico;
	
	
}
