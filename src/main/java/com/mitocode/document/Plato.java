package com.mitocode.document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Documento Plato")
@Document(collection = "platos")
public class Plato {

	@Id
	private String id;
	
	@ApiModelProperty(value = "Longitud minima debe ser 3")
	@NotEmpty
	@Size(min = 3)
	@Field(name = "nombre")
	private String nombre;
	
	@ApiModelProperty(value = "Valor minimo 1, Valor maximo 100")
	@NotNull
	@Max(100)
	@Min(1)
	private double precio;
	
	@NotNull
	private boolean estado;

	public Plato() {
		
	}

	public Plato(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
}
