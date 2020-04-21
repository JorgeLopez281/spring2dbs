package com.spring2dbs.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring2dbs.entity.ClientEntityNoSQL;

@Repository
public interface IClientNoSQLRepository extends MongoRepository<ClientEntityNoSQL, Long>{
	
	/**
	 * findById o DeleteById, no funcionan por que en Mongo cuando se crea un documento, el id es
	 * asignado por el sistema automaticamente. 
	 */
	
	//Buscar en la DB de Mongo un Cliente por Numero de Identificacion
	public ClientEntityNoSQL findByNumIdentification(Long id);
	
	//Eliminar un cliente de la DB de Monto por NUmero de Identificacion
	public void deleteByNumIdentification(Long id);
	
}
