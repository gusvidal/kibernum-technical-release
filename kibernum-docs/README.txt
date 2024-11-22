Para probar el desarrollo, es necesario seguir los siguientes pasos:

Pd. El token tiene una duracion de 5 minutos.

1.- Crear un usuario en el sistema, para esto, se debe ingresar al siguiente endpoint:

[POST] http://localhost:8082/api/auth/registerAdmin
Con el siguiente json
{
	"username": "admin",
	"password": "Admin1234#",
	"email": "admin_mail@gmail.com",
	"phones": [
		{
			"number": "2211574",
			"citycode": "65",
			"countrycode": "56"
		},
		{
			"number": "42874817",
			"citycode": "9",
			"countrycode": "56"
		}
	]
}

Esto creará un usuario del tipo ADMIN

2.- Loguear al usuario recién creado, por ejemplo el usuario "gvidal" creado anteriormente
El endpoint es:  [POST]  http://localhost:8082/api/auth/login
el json a utilizar es:
{
	"username": "admin",
	"password": "Admin1234#"
}
Esto devuelve un json de la siguiente manera:
{
	"id": 1,
	"created": "2024/11/11 12:41:15",
	"modified": null,
	"lastLogin": "2024/11/11 12:42:28",
	"accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndmlkYWwiLCJpYXQiOjE3MzEzMzk3NDgsImV4cCI6MTczMTM0MDA0OH0.qGRn68-MSPfadxLb8zuEPGu8uQHyUFtQ9tRk8ShcHFAN3e-FOZsr-cY_iYkUA131a5L8NMC8YdvAlflxkid7-A",
	"active": true
}

Una vez logueado será posible consultar los demás endpoint, ya que esán configurados con un usuario del tipo ADMIN  

3.- Pedir libros en biblioteca

[POST] http://localhost:8082/api/library/reserva
el json a utilizar es:
{
	"dni": "13.824.785-6",
	"bookCode": "INF0005",
	"startDate": "11/11/2024",
	"endDate": "15/11/2024"
}

4.- Devolver libro en biblioteca

[DELETE] http://localhost:8082/api/library/eliminar
el json a utilizar es:
{
	"dni": "13.824.785-6",
	"bookCode": "INF0005"
}

5.- Listado de libros pedidos por un rut (dni)

[GET] http://localhost:8082/api/library/list-dni/19.786.776-9

6.- Listado de peticiones de un libro en particular

[GET] http://localhost:8082/api/library/list-code/INF0005

7.- Crear/registrar un nuevo libro en el sistema

[POST] http://localhost:8082/api/book/save
{
  "code": "CALC001",
  "title": "Calculo Diferencial e integral",
  "author": "Edwin J. Purcell",
  "copies": "10"
}

8.- Eliminar un libro de la base de datos

[DELETE]http://localhost:8082/api/book/delete/INF0001

9.- Listar todos los libros presentes en el sistema

[GET] http://localhost:8082/api/book/list

10.- Actualizar/modificar un libro en el sistema

[PUT] http://localhost:8082/api/book/update

{
	"bookId": "5",
	"code": "INF0005",
	"title": "Programación de servicios y procesos",
	"author": "Carlos Alberto Cortijo Bon",
	"copies": "200"
}


Se adjunta una colección "Insomnia_2024-11-22.json" para probar directamente en
insomnia o postman.

El acceso a la base de datos h2 es mediante la url:
http://localhost:8082/h2-console
usuario: sa
sin password

Para acceder a la documentación de swagger, es preciso ingresar a la siguiente url
http://localhost:8082/doc/swagger-ui/index.html


También está se incluyen las configuraciones necesarias para ejecutar el la aplicación con mysql, donde
no es necesario crear base de datos ni tablas, dado que la aplicación está configurada para hacerlo en 
forma automatica.
