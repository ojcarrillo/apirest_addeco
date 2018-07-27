package com.activos.fijos.entities.enumerations;

public enum EstadoEnum {

	ACTIVO("Activo"), DEBAJA("Dado de baja"), ENREPARACION("en reparacion"), DISPONIBLE("disponible"), ASGINADO(
			"asignado");

	private String nombre;

	private EstadoEnum(String nombre) {
		this.nombre = nombre;
	}

}
