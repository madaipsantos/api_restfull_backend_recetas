Endpoints REST (Controllers):

Módulo	Endpoints	Método	Descripción
Auth:	
/api/auth/register	POST	Registro de usuario
/api/auth/login		POST	Login y obtención de token JWT

Usuarios:	
/api/usuarios/{id}	GET		Perfil público del usuario
/api/usuarios/me	GET		Datos del usuario autenticado

Recetas:	
/api/recetas		GET		Listar recetas (filtros opcionales)
/api/recetas/{id}	GET		Ver detalle de una receta
/api/recetas		POST	Crear una nueva receta (usuario logueado)
/api/recetas/{id}	PUT		Editar receta propia
/api/recetas/{id}	DELETE	Eliminar receta propia

Comentarios:	
/api/recetas/{id}/comentarios	POST	Agregar comentario
/api/recetas/{id}/comentarios	GET		Ver comentarios de receta

Paises:	
/api/paises			GET		Listar países para filtro