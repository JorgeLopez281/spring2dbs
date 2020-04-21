package com.spring2dbs.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring2dbs.entity.ClientEntitySQL;
import com.spring2dbs.mapper.MapperFindAgeClientEntity;


@Repository
public interface IClientSQLRepository extends JpaRepository<ClientEntitySQL, Long>{
	
	//SELECT c.* FROM client c WHERE c.age = ?1;
	
	@Query(value = "SELECT c.* FROM client c WHERE c.age = ?1", nativeQuery = true)
	public List<ClientEntitySQL> findSameAgeSQL(Long age);
	
	@Query(value = "SELECT c.* FROM client c WHERE c.age > ?1", nativeQuery = true)
	public List<ClientEntitySQL> findHigherAgeSQL(Long age);
	
	@Query(value = "SELECT c.* FROM client c WHERE c.age < ?1", nativeQuery = true)
	public List<ClientEntitySQL> findLessAgeSQL(Long age);

}
