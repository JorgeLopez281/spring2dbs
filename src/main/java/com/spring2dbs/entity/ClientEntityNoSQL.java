package com.spring2dbs.entity;


import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clients")
public class ClientEntityNoSQL implements Serializable{
	
	/*
	 * Propiedades de la entidad en MySQL: 
	 * Nombres 
	 * Apellidos 
	 * Tipo y número de identificación 
	 * Edad 
	 * Ciudad de nacimiento
	 * 
	 * Propiedades de la entidad en Mongo 
	 * Foto (Archivo base64)
	 */
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@NotNull
	@Indexed(unique = true)
	private Long numIdentification;
	
	private String picture;

	public ClientEntityNoSQL(Long numIdentification, String picture) {
		this.numIdentification = numIdentification;
		this.picture = picture;
	}

	public ClientEntityNoSQL() {
	}

	public Long getNumIdentification() {
		return numIdentification;
	}

	public void setNumIdentification(Long numIdentification) {
		this.numIdentification = numIdentification;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
