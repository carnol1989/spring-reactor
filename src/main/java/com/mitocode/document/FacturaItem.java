package com.mitocode.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

//@Document(collection = "facturas_items")
public class FacturaItem {

	@Id
	private String id;
	
	private Integer cantidad;
	
	@DBRef
	private Plato plato;

	public FacturaItem() {
		
	}

	public FacturaItem(String id, Integer cantidad, Plato plato) {
		this.id = id;
		this.cantidad = cantidad;
		this.plato = plato;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Plato getPlato() {
		return plato;
	}

	public void setPlato(Plato plato) {
		this.plato = plato;
	}
	
	public Double calcularMonto() {
		return cantidad.doubleValue() * plato.getPrecio();
	}
	
}
