# Buymesth
Applicación de compras con propósitos educativos realizada en Java8 con SpringBoot y Angular


## Herramientas y tecnologías utilizadas
El proyecto se ha dessarrollado con el IDE IntelliJ usando las siguientes tecnologías:
#### Back
* Java 8
* SpringBoot
* SpringSecurity
* Maven
* Mapstruct
* Lombok
* Swagger
* Hateoas
* PostgreSQL

#### Front
* Angular 6
* NgPrime

## Proceso de desarrollo
Se comenzó desarrollando el back-end como una API Restful. Se puede hacer una distinción de 3 capas en el back:
  * Controller: punto de entrada a los distintos servicios.
  * Service: lugar donde ocurre toda la lógica de negocio.
  * Repository: puente entre la base de datos y la aplicación.
 
 Se creó también un controlador genérico para las distintas excepciones de forma que los mensajes sean amigables para el cliente.
  
Una vez definidos los endpoints, se continuó desarrollando el front-end como el cliente que consumiría la API anteriormente creada.

## Demostración

[BACK-END](https://buymesth-back.herokuapp.com/swagger-ui.html)

[FRONT-END](https://buymesth.herokuapp.com)
