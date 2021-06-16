# Examen MeLi
## _Challenge mutantes_

Para ejecutar el API REST y detectar si una secuencia de ADN es de un mutante, se debe hacer una petición <b>POST</b> a la siguiente url :

https://psysdqzaai.execute-api.us-east-2.amazonaws.com/test/mutant

La petición se realiza sin ninun parametro se seguridad, en el cuerpo del mensaje se debe enviar un Json con la siguiente estructura.
```sh
{"dna": ["GTACG","CGATG","TTATG","AGATG","CAATG"]}
```
Hay un set de pruebas que puede ser importado a Postman en el siguiente enlace :

https://github.com/jeysongallego/examenmeli/blob/main/TEST%20MUTANTES.postman_collection.json

Todas las peticiones de cadenas ADN válidas son almacenada en una BD DynamoBD, con el respectivo atributo si es o no mutante.

Se puede ver una estadistica de la cantidad de ADNs mutantes vs total de ADN invocando un servicio GET en la siguiente url :

https://psysdqzaai.execute-api.us-east-2.amazonaws.com/test/stats

Fin.