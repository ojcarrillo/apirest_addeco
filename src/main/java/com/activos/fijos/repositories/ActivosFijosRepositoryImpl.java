package com.activos.fijos.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.activos.fijos.entities.ActivoFijo;

/**
 * clase para la elaboracion de consultas personalizadas a los activos fijos
 * 
 * @author ojcarrillo
 *
 */
@Repository
public class ActivosFijosRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	/**
	 * metodo para la busqueda de activos fijos segun los parametros dados,
	 * implementa 'ands' para cada parametro mediante criteria
	 * 
	 * @param activo
	 * @return
	 */
	public List<ActivoFijo> findByParams(ActivoFijo activo) {
		/* define el objeto criteria para la consulta */
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ActivoFijo> q = cb.createQuery(ActivoFijo.class);
		Root<ActivoFijo> root = q.from(ActivoFijo.class);

		/* prepara la lista de parametros para la consulta */
		List<Predicate> predicates = new ArrayList<>();
		if (activo.getSerial() != null) {
			predicates.add(cb.equal(root.get("serial"), activo.getSerial()));
		}
		if (activo.getFechaCompra() != null) {
			predicates.add(cb.equal(root.get("fechaCompra"), activo.getFechaCompra()));
		}
		if (activo.getTipo() != null) {
			predicates.add(cb.equal(root.get("tipo"), activo.getTipo()));
		}
		/* asigna los parametros como conjunciones (and) */
		q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		/* ejecuta la consulta y devuelve los resultados */
		return em.createQuery(q).getResultList();
	}
}
