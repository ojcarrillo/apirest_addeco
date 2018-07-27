package com.activos.fijos.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.activos.fijos.entities.enumerations.EstadoEnum;

/**
 * entidad para la persistencia del activo fijo
 * 
 * @author ojcarrillo
 *
 */
@Entity
public class ActivoFijo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;

	@Column(length = 256)
	@NotNull
	@NotBlank
	private String nombre;

	@Column(length = 1024)
	private String descripcion;

	@Column(length = 20)
	@NotNull
	@NotBlank
	private String tipo;

	@Column(length = 20)
	private String serial;

	@Column(length = 20)
	private String numInventario;

	@Column
	private Double peso;

	@Column
	private Double alto;

	@Column
	private Double ancho;

	@Column
	private Double largo;

	@Column
	@NotNull
	private Double valorCompra;

	@Column
	@NotNull
	@Temporal(TemporalType.DATE)
	@Past
	private Date fechaCompra;

	@Column
	@Temporal(TemporalType.DATE)
	private Date fechaBaja;

	@Enumerated(EnumType.STRING)
	private EstadoEnum estadoActual; // * (activo, dado de baja, en
											// reparación,
	// disponible, asignado)

	@Column(length = 20)
	private String color;

	public ActivoFijo() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getNumInventario() {
		return numInventario;
	}

	public void setNumInventario(String numInventario) {
		this.numInventario = numInventario;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getAlto() {
		return alto;
	}

	public void setAlto(Double alto) {
		this.alto = alto;
	}

	public Double getAncho() {
		return ancho;
	}

	public void setAncho(Double ancho) {
		this.ancho = ancho;
	}

	public Double getLargo() {
		return largo;
	}

	public void setLargo(Double largo) {
		this.largo = largo;
	}

	public Double getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(Double valorCompra) {
		this.valorCompra = valorCompra;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public EstadoEnum getEstadoActual() {
		return estadoActual;
	}

	public void setEstadoActual(EstadoEnum estadoActual) {
		this.estadoActual = estadoActual;
	}

}
