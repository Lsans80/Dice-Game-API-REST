## Objetivos de este Sprint:
- JPA.
- MySQL.
- MongoDB.
- Spring Security.
- JWT.
- Testing.

# Nivel 1

El juego de dados se juega con dos dados. En caso de que el resultado de la suma de ambos dados sea 7, la partida será ganada, si no es perdida. Un jugador/a puede ver un listado de todas las tiradas que ha realizado y el porcentaje de éxito.

Para poder jugar al juego y realizar una tirada, un usuario debe registrarse con un nombre no repetido. Al crearse, se le asigna un identificador numérico único y una fecha de registro. Si el usuario así lo desea, puedes no añadir ningún nombre y se llamará “ANÓNIMO”. Puede haber más de un jugador “ANÓNIMO”.
Cada jugador puede ver un listado de todas las tiradas que ha hecho, con el valor de cada dado y si se ha ganado o no la partida. Además, puede saber su porcentaje de éxito por todas las tiradas que ha realizado.

No se puede eliminar una partida en concreto, pero sí se puede eliminar todo el listado de tiradas por un jugador/a.

El software debe permitir listar a todos los jugadores/as que hay en el sistema, el porcentaje de éxito de cada jugador/a y el porcentaje de éxito medio de todos los jugadores/as en el sistema.

El software debe respetar los principales patrones de diseño.

Tienes que tener en cuenta los siguientes detalles de construcción:

### URL's:

POST: /players: crea un jugador/a.

PUT /players: modifica el nombre del jugador/a.

POST /players/{id}/games/ : un jugador/a específico realiza un tirón de los dados.

DELETE /players/{id}/games: elimina las tiradas del jugador/a.

GET /players/: devuelve el listado de todos los jugadores/as del sistema con su porcentaje medio de éxitos.

GET /players/{id}/games: devuelve el listado de jugadas por un jugador/a.

GET /players/ranking: devuelve el ranking medio de todos los jugadores/as del sistema. Es decir, el porcentaje medio de logros.

GET /players/ranking/loser: devuelve al jugador/a con peor porcentaje de éxito.

GET /players/ranking/winner: devuelve al jugador con peor porcentaje de éxito.

### Fase 1
Persistencia: utiliza como base de datos MySQL.

### Fase 2
Cambia todo lo que necesites y utiliza MongoDB para persistir los datos.

### Fase 3
Añade seguridad: incluye autenticación por JWT en todos los accesos a las URL's del microservicio.

# Nivel 2

Añade tests unitarios, de componente y de integración al proyecto con librerías jUnit, AssertJ o Hamcrest.

Añade Mocks al testing del proyecto (Mockito) y Contract Tests (https://docs.pact.io/)

## Recursos utilizados:

- Random code. YouTube. How to Call a REST API using WebClient in Spring Boot, 7 Marzo 2023, https://www.youtube.com/watch?v=RHZgllzbjVQ. Consultado el 28 de Enero de 2024.

- Coding with John. How To Call a REST API In Java - Simple Tutorial, 6 jun 2022, https://www.youtube.com/watch?v=9oq7Y8n1t00. Consultado el 28 de Enero de 2024.

- JAAX. YouTube. Spring Security | Implementando Spring Security + JWT, 2 de Agosto 2023, https://www.youtube.com/watch?v=KBvBY5qyfEM. Consultado el 6 de Febrero del 2024.

- stackoverflow. 19 Julio 2023, https://stackoverflow.com/questions/76725471/swagger-ui-is-not-working-on-spring-boot-3-with-spring-security-and-jwt-authoriz. Consultado el 13 de Febrero del 2024.
