# API REST con Spring - Reto Foro Alura 🌐  

## Descripción del Proyecto 📖  
Foro Alura es una plataforma donde estudiantes y profesores de Alura pueden interactuar, compartir ideas y resolver dudas relacionadas con cursos. Este proyecto consiste en una API REST desarrollada con Spring que permite gestionar tópicos del foro, incluyendo las siguientes funcionalidades:  

- Crear nuevos tópicos 🆕  
- Listar todos los tópicos disponibles 📜  
- Consultar un tópico específico 🔎  
- Actualizar información de un tópico ♻️  
- Eliminar un tópico del sistema 🗑  

## Funcionalidades Clave 🔑  
- **Autenticación Segura 🔐**: Asegura que solo usuarios autorizados puedan acceder y gestionar los recursos.  
- **Gestión Completa de Recursos**: Permite crear, consultar, modificar y eliminar:
  - Tópicos 📂  
  - Respuestas 💭  
  - Cursos 🎓  
  - Usuarios 👤  
- **Base de Datos Relacional**: Implementación con MySQL para un almacenamiento eficiente y persistente.  

## Tecnologías Utilizadas 🛠  
- **Java 17 ☕**: Para el desarrollo del backend.  
- **JPA con Hibernate 📦**: Para el mapeo objeto-relacional y gestión de persistencia.  
- **Spring Framework**:  
  - Spring Boot 🚀: Simplifica el desarrollo de aplicaciones Spring.  
  - Spring MVC 🌍: Gestión de solicitudes HTTP y retorno de respuestas en JSON.  
  - Spring Data JPA 📊: Abstracción para el acceso a datos.  
  - Spring Security 🛡️: Para autenticación y autorización.  
- **JWT (JSON Web Tokens) 🔑**: Gestión segura de sesiones.  
- **MySQL 🐬**: Base de datos relacional para almacenamiento de datos.  
- **IntelliJ IDEA 💻**: IDE utilizado para el desarrollo.  

## Configuración del Entorno ⚙️  
1. **Java JDK 17**: Asegúrate de tenerlo instalado.  
2. **MySQL**: Instala y configura el servidor MySQL.  
3. **IDE**: Usa IntelliJ IDEA (Community o Ultimate).  

## Instalación 📥  
1. Clona el repositorio:  
   ```bash  
   git clone https://github.com/Walter-Britos1/foro-hub-challenge
   cd forohub

2. Configurar la base de datos:
   
- Crear una base de datos en MySQL.
- Actualizar las credenciales en el archivo application.properties:
- spring.datasource.url=jdbc:mysql://localhost:3306/forohub
- spring.datasource.username=tu_usuario
- spring.datasource.password=tu_contraseña

3. Compilar y ejecutar la aplicación:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```


# Endpoints 🌟

| Método | Endpoint       | Descripción                                |
|--------|----------------|--------------------------------------------|
| POST   | /auth/login    | Validar las credenciales de un usuario.    |
| GET    | /users         | Recuperar el listado de usuarios.          |
| GET    | /topics        | Consultar todos los tópicos disponibles.   |
| POST   | /topics        | Registrar un nuevo tópico en el foro.      |
| GET    | /courses       | Obtener todos los cursos registrados.      |
| POST   | /responses     | Agregar una respuesta a un tópico.         |



## Contribución 🙌
¿Te gustaría contribuir? Sigue estos pasos:

- Haz un fork del repositorio.
- Crea una nueva rama para cada mejora o corrección.
- Envía un pull request con una descripción detallada de tus cambios


## 👨‍💻 Autor

Desarrollado por [Walter-Britos1](https://github.com/Walter-Britos1).
