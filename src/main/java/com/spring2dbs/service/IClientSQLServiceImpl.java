package com.spring2dbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring2dbs.entity.ClientEntitySQL;
import com.spring2dbs.mapper.MapperFindAgeClientEntity;
import com.spring2dbs.repo.IClientSQLRepository;

@Service
public class IClientSQLServiceImpl implements IClientSQLService{

	@Autowired
	private IClientSQLRepository iClientSQLRepository;
	
	@Override
	@Transactional
	public void saveClientSQL(ClientEntitySQL clientSQL) {
		iClientSQLRepository.save(clientSQL);
	}

	@Override
	public void deleteClient(ClientEntitySQL client) {
		iClientSQLRepository.deleteById(client.getNumIdentification());
	}

	@Override
	@Transactional
	public void deleteAllClient() {
		iClientSQLRepository.deleteAll();
	}

	@Override
	@Transactional(readOnly = true)
	public ClientEntitySQL findBynumIdentification(Long id) {
		return iClientSQLRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClientEntitySQL> findAll() {
		return iClientSQLRepository.findAll();
	}

	/**
	 * Metodos para realizar las busquedas por Edad.
	 */
	
	@Override
	@Transactional
	public ClientEntitySQL updateClientSQL(ClientEntitySQL client) {
		return iClientSQLRepository.save(client);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClientEntitySQL> findSameAgeClientSQL(MapperFindAgeClientEntity mapperSameAge) {
		return iClientSQLRepository.findSameAgeSQL(mapperSameAge.getAge());
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClientEntitySQL> findHigherAgeClientSQL(MapperFindAgeClientEntity mapperHigherAge) {
		return iClientSQLRepository.findHigherAgeSQL(mapperHigherAge.getAge());
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClientEntitySQL> findLessAgeClientSQL(MapperFindAgeClientEntity mapperLessAge) {
		return iClientSQLRepository.findLessAgeSQL(mapperLessAge.getAge());
	}

}
