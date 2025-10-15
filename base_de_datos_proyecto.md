Estructura de base de datos de la aplicacion:

Table paises {
  id serial [pk]
  nombre varchar(100) [not null, unique]
  codigo_iso varchar(10) [unique]
  continente varchar(50)
}

Table usuarios {
  id serial [pk]
  nombre varchar(100) [not null]
  email varchar(150) [not null, unique]
  password_hash varchar(255)
  foto_perfil text
  pais_id int
}

Table recetas {
  id serial [pk]
  titulo varchar(150) [not null]
  descripcion text
  duracion_minutos int
  dificultad varchar(50) 
  valoracion float 
  foto_url text
  estado varchar(20)
  fecha_publicacion timestamp
  pais_id int
  usuario_id int 
}

Table ingredientes {
  id serial [pk]
  nombre varchar(100) [not null, unique]
  descripcion text
}

Table receta_ingrediente {
  id serial [pk]
  receta_id int 
  ingrediente_id int 
  cantidad varchar(100)
  unidad varchar(50)
}

Table pasos_receta {
  id serial [pk]
  receta_id int 
  orden int [not null]
  descripcion text [not null]
  foto_url text
}

Table comentarios_receta {
  id serial [pk]
  receta_id int 
  usuario_id int
  comentario text [not null]
  fecha timestamp
}