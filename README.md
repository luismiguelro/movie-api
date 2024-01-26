# API de Películas con Spring Boot
<img src="https://img.icons8.com/color/96/000000/spring-logo.png" width="96" height="96"/> <img src="https://fly.io/static/images/brand/logo-portrait-light.svg" width="96" height="96"/>

Esta es una API de películas desarrollada con Spring Boot y desplegada en [fly.io](https://fly.io). La API permite consumir información sobre películas y añadir reseñas. Además, puedes obtener el tráiler de una película en particular.

## Endpoints

- **Obtener todas las películas**
  - Método: GET
  - URL: `https://api-movies.fly.dev/api/v1/movies`

- **Obtener información de una película por ID**
  - Método: GET
  - URL: `https://api-movies.fly.dev/api/v1/movies/{id}`

- **Añadir una reseña a una película**
  - Método: POST
  - URL: `https://api-movies.fly.dev/api/v1/reviews`
  - Body:
    ```json
    {
        "reviewBody": "Tu reseña aquí",
        "imdbId": "Tu ID de IMDb aquí"
    }
    ```

- **Obtener todas las reseñas de una película por ID**
  - Método: GET
  - URL: `https://api-movies.fly.dev/api/v1/reviews/{movieId}`
  - 
## Despliegue en fly.io

La aplicación está desplegada en [fly.io](https://fly.io), una plataforma confiable para desplegar aplicaciones. Puedes acceder a la API en [https://api-movies.fly.dev](https://api-movies.fly.dev).

<img src="https://fly.io/static/images/brand/logo-landscape.svg" width="96" height="96"/>

## Base de Datos

La base de datos de esta aplicación está alojada en [MongoDB Atlas](https://www.mongodb.com/cloud/atlas), un servicio de base de datos en la nube. 

## Contribuciones

¡Las contribuciones son bienvenidas! Si encuentras algún problema o tienes ideas para mejorar la API, por favor, abre un issue o una pull request.

## Licencia

Este proyecto está bajo la Licencia MIT - consulta el archivo [LICENSE](LICENSE) para más detalles.
