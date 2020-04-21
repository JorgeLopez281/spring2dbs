package com.spring2dbs.service;

import java.util.List;

import com.spring2dbs.entity.ClientEntitySQL;
import com.spring2dbs.mapper.MapperClientEntity;
import com.spring2dbs.mapper.MapperFindAgeClientEntity;

public interface IClientSQLService {
	
	public void saveClientSQL(ClientEntitySQL client);
	
	public void deleteClient(ClientEntitySQL client);

	public void deleteAllClient();
	
	public ClientEntitySQL findBynumIdentification(Long id);
	
	public List<ClientEntitySQL> findAll();
	
	public ClientEntitySQL updateClientSQL(ClientEntitySQL clientSQL);
	
	public List<ClientEntitySQL> findSameAgeClientSQL(MapperFindAgeClientEntity mapperAge);
	
	public List<ClientEntitySQL> findHigherAgeClientSQL(MapperFindAgeClientEntity mapperAge);
	
	public List<ClientEntitySQL> findLessAgeClientSQL(MapperFindAgeClientEntity mapperAge);

}
