package com.spring2dbs.service;

import java.util.List;

import com.spring2dbs.entity.ClientEntityNoSQL;

public interface IClientNoSQLService {
	
	public void saveClientNoSQL(ClientEntityNoSQL client);
	
	public void deleteClient(ClientEntityNoSQL client);

	public void deleteAllClient();
	
	public ClientEntityNoSQL findBynumIdentification(Long id);
	
	public List<ClientEntityNoSQL> findAll();
	
	public ClientEntityNoSQL updateClientNoSQL(ClientEntityNoSQL clientNoSQL);

}
