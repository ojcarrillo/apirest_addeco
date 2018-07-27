package com.activos.fijos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.activos.fijos.entities.ActivoFijo;

/**
 * clase para el manejo del CRUD de activo fijo 
 * 
 * @author ojcarrillo
 *
 */
public interface ActivosFijosRepository extends JpaRepository<ActivoFijo, Integer> {

}
