package com.inotrs.proyecto.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

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
public class Edificio {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue
	private Long id;
	@NotEmpty
	private String nombre;
	@NotEmpty
	private String ubicacion;

}
