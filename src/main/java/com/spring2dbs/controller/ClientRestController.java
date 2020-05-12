package com.spring2dbs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spring2dbs.entity.ClientEntityNoSQL;
import com.spring2dbs.entity.ClientEntitySQL;
import com.spring2dbs.mapper.MapperClientEntity;
import com.spring2dbs.mapper.MapperFindAgeClientEntity;
import com.spring2dbs.service.IClientNoSQLService;
import com.spring2dbs.service.IClientSQLService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping("/spring2db")
public class ClientRestController {
	
	@Autowired
	private IClientNoSQLService iClientNoSQLService;
	@Autowired
	private IClientSQLService iClientSQLService;
	
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	/**
	 * 1. Funcion Post encargada de guardar un cliente en las dos Bases de Datos.
	 * 
	 * @param client
	 * @return
	 */
	@PostMapping("/saveClient")
	@ApiOperation("Metodo Post encargado de Guardar un solo cliente en la Base de Datos MySQL y MongoDB segun la distribucion especificada.")
	public ResponseEntity<?> addClient(@RequestBody MapperClientEntity client){
		
		/**
		 * Debido a que con save se puede guardar y actualizar un registro en DB SQL se realiza validacion
		 * para garantizar que si el Front envia un registro ya existente no se actualice se agrega la siguiente
		 * validacion.
		 */
		if (iClientNoSQLService.findBynumIdentification(client.getNumIdentification()) == null &&
				iClientSQLService.findBynumIdentification(client.getNumIdentification()) == null) {
			
			ClientEntityNoSQL clientEntityNoSQL = new ClientEntityNoSQL();
			ClientEntitySQL clientEntitySQL = new ClientEntitySQL();
			
			clientEntitySQL.setName(client.getName());
			clientEntitySQL.setSurnames(client.getSurnames());
			clientEntitySQL.setNumIdentification(client.getNumIdentification());
			clientEntitySQL.setTypeIdentification(client.getTypeIdentification());
			clientEntitySQL.setAge(client.getAge());
			clientEntitySQL.setBirthCity(client.getBirthCity());
			
			clientEntityNoSQL.setNumIdentification(client.getNumIdentification());
			clientEntityNoSQL.setPicture(client.getPicture());
			
			iClientNoSQLService.saveClientNoSQL(clientEntityNoSQL);
			iClientSQLService.saveClientSQL(clientEntitySQL);
			
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		
	}
	
	/**
	 * 2. Funcion Delete encargada de eliminar un usuario dependiendo de su numero de Identificacion.
	 * 
	 * @param client
	 * @return
	 */
	@DeleteMapping("/deleteClient")
	@ApiOperation("Metodo Delete encargado de Eliminar un solo cliente de la Base de Datos MySQL y MongoDB.")
	public ResponseEntity<?> deleteClient(@RequestBody MapperClientEntity client){
		
		/**
		 * Se debe tener cuidado con mapear los campos obligatorios o los campos que se requiere para
		 * hacer la operacion, en este caso el delete se realiza por Numero de Identificacion y es el unico
		 * campo obligatorio en la entidad.
		 */
		ClientEntitySQL clientEntitySQL = iClientSQLService.findBynumIdentification(client.getNumIdentification());
		ClientEntityNoSQL clientEntityNoSQL = iClientNoSQLService.findBynumIdentification(client.getNumIdentification());
		
		if (clientEntitySQL != null && clientEntityNoSQL != null) {
			iClientNoSQLService.deleteClient(clientEntityNoSQL);
			iClientSQLService.deleteClient(clientEntitySQL);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 3. Funcion Delete encargada de eliminar todos los registros de las DBs
	 * 
	 * @return
	 */
	@DeleteMapping("/deleteAllClients")
	@ApiOperation("Metodo Delete encargado de Eliminar todos los clientes de la Base de Datos MySQL y MongoDB.")
	public ResponseEntity<?> deleteAll(){
		iClientNoSQLService.deleteAllClient();
		iClientSQLService.deleteAllClient();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/**
	 * 4. Funcion Get encargada de traer toda la informacion de los registros dependiendo
	 * si el numero de Identificacion coinciden en las dos BDs
	 * 
	 * @return
	 */
	@GetMapping("/getAllClients")
	@ApiOperation("Metodo Get encargado de recuperar todos los clientes de la Base de Datos MySQL y MongoDB.")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getAllUsers(){
		List<ClientEntityNoSQL> clientEntityNoSqlList = iClientNoSQLService.findAll();
		List<ClientEntitySQL> clientEntitySqlList = iClientSQLService.findAll();
		List<MapperClientEntity> mapperClientEntityList = new ArrayList<>();
		
		/**
		 * Se realizan 3 validaciones, que las listas no esten nulas, que su tamaño sea diferente de 0, 
		 * por ultimo se valida que el tamaño de las listas sea el mismo para evitar inconsistencias
		 */
		if (clientEntityNoSqlList != null && clientEntitySqlList != null) {
			if (clientEntityNoSqlList.size() != 0 && clientEntitySqlList.size() != 0) {
				if (clientEntityNoSqlList.size() == clientEntitySqlList.size()) {
					
					/**
					 * Se recorren las dos listas (NoSQL y SQL) con el fin de buscar los datos iguales por 
					 * numero de identificacion, posterior se crea un objeto de tipo MapperClientEntity el cual
					 * se utilizara para Setear todos los valores, se creo una lista mapperClientEntityList que 
					 * es la que va a guardar toda la informacion y se le enviara al Front.
					 */
					for (ClientEntitySQL listClientSQLEntity : clientEntitySqlList) {
						for (ClientEntityNoSQL listClientNoSQLEntity : clientEntityNoSqlList) {
							if (listClientSQLEntity.getNumIdentification().equals(listClientNoSQLEntity.getNumIdentification())) {
								
								MapperClientEntity listMapperClientEntity = new MapperClientEntity();
								
								listMapperClientEntity.setNumIdentification(listClientSQLEntity.getNumIdentification());
								listMapperClientEntity.setTypeIdentification(listClientSQLEntity.getTypeIdentification());
								listMapperClientEntity.setName(listClientSQLEntity.getName());
								listMapperClientEntity.setSurnames(listClientSQLEntity.getSurnames());
								listMapperClientEntity.setAge(listClientSQLEntity.getAge());
								listMapperClientEntity.setBirthCity(listClientSQLEntity.getBirthCity());
								listMapperClientEntity.setPicture(listClientNoSQLEntity.getPicture());
								
								mapperClientEntityList.add(listMapperClientEntity);
							}
						}
					}
					return new ResponseEntity<>(mapperClientEntityList , HttpStatus.OK);
				}else {
					return new ResponseEntity<Void>(HttpStatus.CONFLICT);
				}
			}else {
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 5. Funcion Post para buscar un cliente en las DBs.
	 * 
	 * @param client
	 * @return
	 */
	@PostMapping("/getClientOne")
	@ApiOperation("Metodo Post encargado de Recuperar un solo cliente de la Base de Datos MySQL y MongoDB, por medio de su Numero de Identificacion.")
	public ResponseEntity<?> getClientOne(@RequestBody MapperClientEntity client){
		
		ClientEntitySQL clientEntitySQL = iClientSQLService.findBynumIdentification(client.getNumIdentification());
		ClientEntityNoSQL clientEntityNoSQL = iClientNoSQLService.findBynumIdentification(client.getNumIdentification());
		
		if (clientEntitySQL != null && clientEntityNoSQL != null) {
			
			MapperClientEntity clientMapper = new MapperClientEntity();
			
			clientMapper.setAge(clientEntitySQL.getAge());
			clientMapper.setBirthCity(clientEntitySQL.getBirthCity());
			clientMapper.setName(clientEntitySQL.getName());
			clientMapper.setNumIdentification(clientEntitySQL.getNumIdentification());
			clientMapper.setPicture(clientEntityNoSQL.getPicture());
			clientMapper.setSurnames(clientEntitySQL.getSurnames());
			clientMapper.setTypeIdentification(clientEntitySQL.getTypeIdentification());
			
			return new ResponseEntity<>(clientMapper, HttpStatus.OK);
			
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 6. Funcion Put para realizar la actualizacion de un cliente en las DBs a partir de su Numero de Identificacion
	 * 
	 * @param client
	 * @return
	 */
	@PutMapping("/updateClient")
	@ApiOperation("Metodo Put encargado de actualizar la informacion de un cliente en la Base de Datos MySQL y MongoDB.")
	public ResponseEntity<?> updateClient(@RequestBody MapperClientEntity client){
		
		ClientEntitySQL clientEntitySQLDB = iClientSQLService.findBynumIdentification(client.getNumIdentification());
		ClientEntityNoSQL clientEntityNoSQLDB = iClientNoSQLService.findBynumIdentification(client.getNumIdentification());
		
		if (clientEntitySQLDB != null && clientEntityNoSQLDB != null) {
			
			clientEntitySQLDB.setName(client.getName());
			clientEntitySQLDB.setSurnames(client.getSurnames());
			clientEntitySQLDB.setTypeIdentification(client.getTypeIdentification());
			clientEntitySQLDB.setAge(client.getAge());
			clientEntitySQLDB.setBirthCity(client.getBirthCity());
			clientEntitySQLDB.setNumIdentification(client.getNumIdentification());
			
			clientEntityNoSQLDB.setNumIdentification(client.getNumIdentification());
			clientEntityNoSQLDB.setPicture(client.getPicture());
			
			//Como imprimir un JSON con libreria Gson de Google.
//			String jsonInString = gson.toJson(clientEntityNoSQLDB);
//          System.out.println(jsonInString);
			
			iClientNoSQLService.updateClientNoSQL(clientEntityNoSQLDB);
			iClientSQLService.updateClientSQL(clientEntitySQLDB);
			
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 7. Funcion Post para realizar la busqueda de los Clientes por Edad dependiendo de la condicion recibida del Front.
	 * 
	 * 	a. Buscar clientes por la misma edad enviada por el Front - getSameAge.
	 * 	b. Buscar clientes con edad mayor a la enviada por el Front - getHigherAge.
	 * 	c. Buscar clientes con edad menor a la enviada por el Front - getLessAge.
	 * 
	 * @param clientAge
	 * @return
	 */
	@PostMapping("/findClientByAge")
	@ApiOperation("Metodo Post encargado de Recuperar todos los clientes de la Base de Datos MySQL y MongoDB, segun los criterios de busqueda dependiendo la edad")
	public ResponseEntity<?> findClientByAge(@RequestBody MapperFindAgeClientEntity clientAge){
		List<MapperClientEntity> mapperClientEntityList = new ArrayList<>();
		boolean sameAge = clientAge.getSameAge();
		boolean higherAge = clientAge.getHigherAge();
		boolean lessAge = clientAge.getLessAge();
		
		/**
		 * Condicion enviada por el Front para saber si se busca los registros con la misma edad, este ultimo
		 * tambien lo envia el Front. 
		 */
		if (sameAge == true && higherAge == false && lessAge == false) {
			List<ClientEntitySQL> clientEntitySqlDBSameAge = iClientSQLService.findSameAgeClientSQL(clientAge);
//			String jsonInString = gson.toJson(clientEntitySqlDBSameAge);
//			System.out.println(jsonInString);
			for (ClientEntitySQL clientEntitySqlSameAgeList : clientEntitySqlDBSameAge) {
				ClientEntityNoSQL clientEntityNoSqlSameAgeDB = iClientNoSQLService.findBynumIdentification(clientEntitySqlSameAgeList.getNumIdentification());
				if (clientEntitySqlSameAgeList.getNumIdentification().equals(clientEntityNoSqlSameAgeDB.getNumIdentification())) {
					
					MapperClientEntity clientMapperSameAge = new MapperClientEntity();	
					
					clientMapperSameAge.setAge(clientEntitySqlSameAgeList.getAge());
					clientMapperSameAge.setBirthCity(clientEntitySqlSameAgeList.getBirthCity());
					clientMapperSameAge.setName(clientEntitySqlSameAgeList.getName());
					clientMapperSameAge.setNumIdentification(clientEntitySqlSameAgeList.getNumIdentification());
					clientMapperSameAge.setPicture(clientEntityNoSqlSameAgeDB.getPicture());
					clientMapperSameAge.setSurnames(clientEntitySqlSameAgeList.getSurnames());
					clientMapperSameAge.setTypeIdentification(clientEntitySqlSameAgeList.getTypeIdentification());
					
					mapperClientEntityList.add(clientMapperSameAge);
				}
			}
			return new ResponseEntity<>(mapperClientEntityList, HttpStatus.OK);
			
		}
		/**
		 * Condicion enviada por el Front para saber si se busca los registros con edad mayor a la edad enviada, este ultimo
		 * tambien lo envia el Front. 
		 */
		if (higherAge == true && sameAge == false && lessAge == false) {
			List<ClientEntitySQL> clientEntitySqlDBHigherAge = iClientSQLService.findHigherAgeClientSQL(clientAge);
//			String jsonInString = gson.toJson(clientEntitySqlDBHigherAge);
//			System.out.println(jsonInString);
			for (ClientEntitySQL clientEntitySqlHigherAgeList : clientEntitySqlDBHigherAge) {
				ClientEntityNoSQL clientEntityNoSqlHigherAgeDB = iClientNoSQLService.findBynumIdentification(clientEntitySqlHigherAgeList.getNumIdentification());
				if (clientEntitySqlHigherAgeList.getNumIdentification().equals(clientEntityNoSqlHigherAgeDB.getNumIdentification())) {
					
					MapperClientEntity clientMapperSameAge = new MapperClientEntity();	
					
					clientMapperSameAge.setAge(clientEntitySqlHigherAgeList.getAge());
					clientMapperSameAge.setBirthCity(clientEntitySqlHigherAgeList.getBirthCity());
					clientMapperSameAge.setName(clientEntitySqlHigherAgeList.getName());
					clientMapperSameAge.setNumIdentification(clientEntitySqlHigherAgeList.getNumIdentification());
					clientMapperSameAge.setPicture(clientEntityNoSqlHigherAgeDB.getPicture());
					clientMapperSameAge.setSurnames(clientEntitySqlHigherAgeList.getSurnames());
					clientMapperSameAge.setTypeIdentification(clientEntitySqlHigherAgeList.getTypeIdentification());
					
					mapperClientEntityList.add(clientMapperSameAge);
				}
			}
			return new ResponseEntity<>(mapperClientEntityList, HttpStatus.OK);
			
		}
		/**
		 * Condicion enviada por el Front para saber si se busca los registros con edad menor a la edad enviada, este ultimo
		 * tambien lo envia el Front. 
		 */
		if (lessAge == true && higherAge == false && sameAge == false) {
			List<ClientEntitySQL> clientEntitySqlDBLessAge = iClientSQLService.findLessAgeClientSQL(clientAge);
//			String jsonInString = gson.toJson(clientEntitySqlDBHigherAge);
//			System.out.println(jsonInString);
			for (ClientEntitySQL clientEntitySqlLessAgeList : clientEntitySqlDBLessAge) {
				ClientEntityNoSQL clientEntityNoSqlLessAgeDB = iClientNoSQLService.findBynumIdentification(clientEntitySqlLessAgeList.getNumIdentification());
				if (clientEntitySqlLessAgeList.getNumIdentification().equals(clientEntityNoSqlLessAgeDB.getNumIdentification())) {
					
					MapperClientEntity clientMapperSameAge = new MapperClientEntity();	
					
					clientMapperSameAge.setAge(clientEntitySqlLessAgeList.getAge());
					clientMapperSameAge.setBirthCity(clientEntitySqlLessAgeList.getBirthCity());
					clientMapperSameAge.setName(clientEntitySqlLessAgeList.getName());
					clientMapperSameAge.setNumIdentification(clientEntitySqlLessAgeList.getNumIdentification());
					clientMapperSameAge.setPicture(clientEntityNoSqlLessAgeDB.getPicture());
					clientMapperSameAge.setSurnames(clientEntitySqlLessAgeList.getSurnames());
					clientMapperSameAge.setTypeIdentification(clientEntitySqlLessAgeList.getTypeIdentification());
					
					mapperClientEntityList.add(clientMapperSameAge);
				}
			}
			return new ResponseEntity<>(mapperClientEntityList, HttpStatus.OK);
		}
		/**
		 * En el caso de que el Front envie las tres o dos condiciones en TRUE o FALSE devuelve un Conflicto.
		 */
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

}
