package com.spring2dbs.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "client")
public class ClientEntitySQL implements Serializable {

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
	@Column(length = 60, unique = true, name = "numberIdentification")
	private Long numIdentification;

	@Column(name = "typeIdentification", length = 2)
	private String typeIdentification;

	@Column(name = "name", length = 40)
	private String name;

	@Column(name = "surnames", length = 40)
	private String surnames;

	@Column(name = "age")
	private Long age;

	@Column(name = "birthCity", length = 40)
	private String birthCity;

	public Long getNumIdentification() {
		return numIdentification;
	}

	public void setNumIdentification(Long numIdentification) {
		this.numIdentification = numIdentification;
	}

	public String getTypeIdentification() {
		return typeIdentification;
	}

	public void setTypeIdentification(String typeIdentification) {
		this.typeIdentification = typeIdentification;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getBirthCity() {
		return birthCity;
	}

	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}

}
