package com.inotrs.proyecto.modelo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("PERSONA")
public class Usuario extends Persona{
	
	private String despacho;
	private String puesto;


}
