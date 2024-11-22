Para probar el desarrollo, es necesario seguir los siguientes pasos:
1.- Crear un usuario en el sistema, para esto, se debe ingresar al siguiente endpoint:
[POST] http://localhost:8082/api/auth/registerAdmin
Con el siguiente json
{
	"username": "gvidal",
	"password": "GVidal980#",
	"email": "gvidal@gmail.com",
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

o bien 

[POST] http://localhost:8082/api/auth/registerUser
Con el siguiente json
{
	"username": "user",
	"password": "GVidal980#",
	"email": "gvidalUser@gmail.com",
	"phones": [
		{
			"number": "2211525",
			"citycode": "65",
			"countrycode": "56"
		},
		{
			"number": "44558899",
			"citycode": "9",
			"countrycode": "56"
		}
	]
}
Esto creará un usuario del tipo USER

2.- Loguear al usuario recién creado, por ejemplo el usuario "gvidal" creado anteriormente
El endpoint es:  [POST]  http://localhost:8082/api/auth/login
el json a utilizar es:
{
	"username": "gvidal",
	"password": "GVidal980#"
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

3.- Finalmente, para probar la autorizacion de JWT, ingresamos al siguiente endpoint
[GET]: http://localhost:8082/api/auth/lista
y en la pestaña de authorización pegamos el token devuelto como "accessToken" del punto anteriormente
Para usuario del tipo ADMIN se despliega una lista como todos los usuarios presentes en el sistema, 
de la siguiente forma:
[
	{
		"idUsuario": 1,
		"username": "gvidal",
		"password": "$2a$10$ArAFntDiX.O3EcC1CGMB5.M.lm9z.rHX.fJUslYsKPS6BtF1JDJaW",
		"email": "gvidal@gmail.com",
		"created": "2024/11/11 16:28:41",
		"modified": null,
		"lastLogin": "2024/11/11 16:28:50",
		"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndmlkYWwiLCJpYXQiOjE3MzEzNTMzMzAsImV4cCI6MTczMTM1MzYzMH0.Aitt5EO6TZIo-ud3PL_N6mUxIVzUxjKKQ_FDkSgR7BZNK82HuPkTFCI1sbhp3NnF_CSnsAS_IAPLt4f9tTsSEw",
		"isactive": true,
		"roles": [
			{
				"idRole": 1,
				"name": "ADMIN"
			}
		],
		"phones": [
			{
				"idPhone": 1,
				"number": 2211574,
				"citycode": 65,
				"countrycode": 56
			},
			{
				"idPhone": 2,
				"number": 42874817,
				"citycode": 9,
				"countrycode": 56
			}
		]
	}
]

en cambio para los usuarios del tipo USER, el acceso no está autorizado.

Se adjunta una colección "nisum-technical-test-Insomnia_2024-11-11.json" para probar directamente en
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