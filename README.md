# Falabella-FIF
Api Bender Beer Master

Instalar �ste docker:

```
./mvnw install dockerfile:build
```

Una vez creada la imagen, consultar las imagenes generadas con el siguiente comando:

```
docker images
```

Correr la imagen generada:

```
docker run -p 8080:8080 -t beer
```