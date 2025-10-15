Esta es la estructura del proyecto:

src/
 └── main/java/com/tuapp/recetas/
      ├── controller/       → Controladores REST
      ├── service/          → Lógica de negocio
      ├── repository/       → Interfaces JPA
      ├── model/            → Entidades (basadas en el MER)
      ├── dto/              → Objetos de transferencia de datos
      ├── security/         → Configuración JWT + filtros
      └── RecetasApplication.java
