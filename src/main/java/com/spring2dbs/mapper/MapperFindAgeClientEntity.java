package com.spring2dbs.mapper;

public class MapperFindAgeClientEntity {

	private Long age;

	private Boolean sameAge;

	private Boolean higherAge;

	private Boolean lessAge;

	public MapperFindAgeClientEntity() {
	}

	public MapperFindAgeClientEntity(Long age, Boolean sameAge, Boolean higherAge, Boolean lessAge) {
		super();
		this.age = age;
		this.sameAge = sameAge;
		this.higherAge = higherAge;
		this.lessAge = lessAge;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Boolean getSameAge() {
		return sameAge;
	}

	public void setSameAge(Boolean sameAge) {
		this.sameAge = sameAge;
	}

	public Boolean getHigherAge() {
		return higherAge;
	}

	public void setHigherAge(Boolean higherAge) {
		this.higherAge = higherAge;
	}

	public Boolean getLessAge() {
		return lessAge;
	}

	public void setLessAge(Boolean lessAge) {
		this.lessAge = lessAge;
	}

}
