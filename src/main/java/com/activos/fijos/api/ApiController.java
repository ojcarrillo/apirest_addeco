package com.activos.fijos.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.activos.fijos.entities.ActivoFijo;
import com.activos.fijos.repositories.ActivosFijosRepository;
import com.activos.fijos.repositories.ActivosFijosRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path = "/activos/fijos/api")
public class ApiController {

	/* repositorio para la interfaz del CRUD */
	@Autowired
	ActivosFijosRepository activoFijoRepo;

	/* repositorio para consultas especializadas */
	@Autowired
	ActivosFijosRepositoryImpl activoFijoRepoImpl;

	/* objeto para validar los constraints */
	@Autowired
	Validator validator;

	/**
	 * metodo de prueba del API
	 * 
	 * @return
	 */
	@RequestMapping(value = "/prueba/")
	public ResponseEntity<String> prueba() {
		return new ResponseEntity<>("{ prueba: 'hola'}", HttpStatus.OK);
	}

	/**
	 * metodo para obtener todos los activos fijos almacenados
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAll/")
	public ResponseEntity<Object> getAll() {
		List<ActivoFijo> rta = activoFijoRepo.findAll();
		if (rta.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"msg\": \"la consulta no arroja resultados\"}");
		} else {
			return new ResponseEntity<>(rta, HttpStatus.OK);
		}
	}

	/**
	 * metodo para guardar (almacenar/modificar) un activo fijo
	 * 
	 * @param activo
	 * @return
	 */
	@RequestMapping(value = "/save/", method = RequestMethod.POST, consumes = { "application/JSON",
			"application/XML" }, produces = { "application/JSON",
					"application/XML" }, headers = { "content-type=application/json" })
	public ResponseEntity<Object> save(@RequestBody ActivoFijo activo) {
		try {
			/* valida los campos */
			Set<ConstraintViolation<ActivoFijo>> validations = validator.validate(activo);
			if (!validations.isEmpty()) {
				Map<String, String> messages = new HashMap<>();
				for (ConstraintViolation v : validations) {
					messages.put(v.getPropertyPath().toString(), v.getMessage());
				}
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("{ \"msg\": \"datos incompletos en el registro para almacenar\",\"errors\":"
								+ new ObjectMapper().writeValueAsString(messages) + "}");
			}

			/*
			 * valida que la fecha de baja no sea anterior a la fecha de compra
			 */
			if (activo.getFechaCompra() != null && activo.getFechaBaja() != null
					&& activo.getFechaBaja().before(activo.getFechaCompra())) {
				return new ResponseEntity<>(
						"{ \"msg\": \"la fecha de baja no puede ser inferior a la fecha de compra\"}",
						HttpStatus.NOT_FOUND);
			}

			/* guarda el registro del activo fijo */
			activoFijoRepo.save(activo);
			return new ResponseEntity<>("{ \"msg\": \"registro almacenado correctamente\"}", HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					"{ \"msg\": \"error al momento de almacenar el registro\", \"errors: {" + e.getMessage() + "}\" }");
		}
	}

	/**
	 * metodo de consulta para buscar por los campos tipo, fecha de compra y
	 * serial
	 * 
	 * @param activo
	 * @return
	 */
	@RequestMapping(value = "/query/", method = RequestMethod.POST, consumes = { "application/JSON",
			"application/XML" }, produces = { "application/JSON",
					"application/XML" }, headers = { "content-type=application/json" })
	public ResponseEntity<Object> findByParams(@RequestBody ActivoFijo activo) {
		List<ActivoFijo> rta = activoFijoRepoImpl.findByParams(activo);
		if (rta.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"msg\": \"la consulta no arroja resultados\"}");
		} else {
			return new ResponseEntity<>(rta, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/update/", method = RequestMethod.POST, consumes = { "application/JSON",
			"application/XML" }, produces = { "application/JSON",
					"application/XML" }, headers = { "content-type=application/json" })
	public ResponseEntity<Object> updateActivo(@RequestBody ActivoFijo activo) {
		try {
			/* valida que halla un id de activo fijo para actualizar */
			if (activo.getId() == null) {
				return new ResponseEntity<>("{ \"msg\": \"el id del activo fijo no puede ser nulo\"}",
						HttpStatus.NOT_FOUND);
			}
			/* consulta el activo fijo original */
			if (activoFijoRepo.findById(activo.getId()).isPresent()) {
				ActivoFijo activoOriginal = activoFijoRepo.findById(activo.getId()).get();
				/* actualiza los nuevos valores */
				activoOriginal.setSerial(activo.getSerial());
				activoOriginal.setFechaBaja(activo.getFechaBaja());
				/* almacena los cambios */				
				activoFijoRepo.save(activoOriginal);
				return new ResponseEntity<>("{ \"msg\": \"registro actualizado correctamente\"}", HttpStatus.OK);
			}else{
				return new ResponseEntity<>("{ \"msg\": \"el id del activo fijo no existe\"}",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{ \"msg\": \"error al momento de actualziar el registro\", \"errors: {" + e.getMessage()
							+ "}\" }");
		}
	}
}
