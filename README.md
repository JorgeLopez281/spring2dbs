# spring2dbs

Api Rest SpringBoot con DBs MySQL y MongoDB

Construir API’s Restful que expongan como mínimo las operaciones CRUD de una tabla de base de datos llamada CLIENTE.

Los datos de un cliente son los siguientes:

Nombres
Apellidos
Tipo y número de identificación
Edad
Ciudad de nacimiento
Foto (Archivo base64)

Normalmente las consultas se hacen mediante el tipo y número de identificación, pero en algunos casos es necesario consultar los clientes que sean mayor o igual a determinada edad. Tener presente esta información para el diseño de la tabla donde se va a almacenar la información de los clientes de forma tal que le permitan al servicio ser lo más rápido posible.

Cada operación debe ser especificada como un servicio Restful en el controlador del microservicio desarrollado como proyecto Spring Boot. La información básica del cliente se almacenará en una base de datos relacional (MySQL) y su respectiva foto debe almacenarse en una base de datos no relacional (Mongodb) que se pueda identificar por algún campo único del registro. Todos los servicios deben permitir Cross Domain o el consumo de recursos desde un dominio diferente para que la aplicación web ya existente los pueda consumir.
