package com.spring2dbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring2dbs.entity.ClientEntityNoSQL;
import com.spring2dbs.mapper.MapperClientEntity;
import com.spring2dbs.repo.IClientNoSQLRepository;

@Service
public class IClientNoSQLServiceImpl implements IClientNoSQLService{

	@Autowired
	private IClientNoSQLRepository iClientNoSQLRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	@Transactional
	public void saveClientNoSQL(ClientEntityNoSQL clientNoSQL) {
		iClientNoSQLRepository.save(clientNoSQL);
	}

	@Override
	@Transactional
	public void deleteClient(ClientEntityNoSQL client) {
		iClientNoSQLRepository.deleteByNumIdentification(client.getNumIdentification());
	}

	@Override
	@Transactional
	public void deleteAllClient() {
		iClientNoSQLRepository.deleteAll();
	}

	@Override
	@Transactional(readOnly = true)
	public ClientEntityNoSQL findBynumIdentification(Long id) {
		return iClientNoSQLRepository.findByNumIdentification(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClientEntityNoSQL> findAll() {
		return iClientNoSQLRepository.findAll();
	}

	/**
	 * query.addCriteria(Criteria.where("numIdentification").is(clientNoSQL.getNumIdentification())); -> Busca en la Collection un dato por numero de identificacion.
	 * 
	 * update.set("picture", clientNoSQL.getPicture()); -> Actualiza los datos.
	 */
	@Override
	public ClientEntityNoSQL updateClientNoSQL(ClientEntityNoSQL clientNoSQL) {		
		
		Query query = new Query();
		query.addCriteria(Criteria.where("numIdentification").is(clientNoSQL.getNumIdentification()));
        Update update = new Update();        
        update.set("picture", clientNoSQL.getPicture());
        
        return mongoTemplate.findAndModify(query, update, ClientEntityNoSQL.class);
	}

}
