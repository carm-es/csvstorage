
# CSV Storage
Proyecto para la instalación y mantenimiento evolutivo de la versión distribuible de CSV Storage (Almacenamiento de CSVs).

Se parte de la versión distribuible que se ofrece en el área de descargas de la Suite Inside del Centro de Transferencia Tecnológica: https://administracionelectronica.gob.es/ctt/inside



### Requisitos
Para trabajar con el código fuente necesitarás: 

* Java 1.7 o superior
* Maven 3.3.9 o superior

### Obtener el código fuente
Clona el repositorio de código fuente utilizando git:

```
git clone https://github.com/carm-es/csvstorage.git
```

O crea tu propio `Fork` a fin de reincorporar tus cambios al repositorio utilizando un `Pull Request` [leer más](https://help.github.com/articles/fork-a-repo). 


### Compilar el proyecto
Para compilar el proyecto ejecuta los siguientes comandos en el directorio donde descargarte los fuentes:

```
cd csvstorage
mvn package 
```

La aplicación web final la encontrarás en `fuentes/csvstorage/target/csvstorage.war`



