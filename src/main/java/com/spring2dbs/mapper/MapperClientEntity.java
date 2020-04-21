package com.spring2dbs.mapper;

import javax.validation.constraints.NotNull;

public class MapperClientEntity {

	@NotNull
	private Long numIdentification;
	
	private String typeIdentification;
	private String name;
	private String surnames;
	private Long age;
	private String birthCity;
	private String picture;

	public MapperClientEntity(Long numIdentification, String typeIdentification, String name, String surnames,
			Long age, String birthCity, String picture) {
		this.numIdentification = numIdentification;
		this.typeIdentification = typeIdentification;
		this.name = name;
		this.surnames = surnames;
		this.age = age;
		this.birthCity = birthCity;
		this.picture = picture;
	}

	public MapperClientEntity() {
	}

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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}
