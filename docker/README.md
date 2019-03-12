
CsvStorage en Docker
=====================

Este directorio permite construir un contenedor Docker desde el que
ejecutar la aplicación CsvStorage, y así probar la funcionalidad de la aplicación, basándose en la documentación oficial
[manual instalacion csvstorage distribuible v1.2.3.docx](../resources/manual instalacion csvstorage distribuible v1.2.3.docx).


El fichero **Dockerfile** está basado en [tomcat/8.5/jre8-slim](https://github.com/docker-library/tomcat/tree/master/8.5/jre8-slim), al que se ha añadido el war y la configuración necesaria para poder ejecutar la aplicación CsvStorage.


### Cómo usar esta imagen

Primero genere la imagen:
```
# export BUILD=1.2.3
# export BUILD=1.2.3-CARM.1-SNAPSHOT
export BUILD=latest
docker build --build-arg VERSION=$BUILD  -t carm-es/csvstorage:$BUILD .
```

Este comando construirá un contenedor docker en local, con la versión del proyecto que se le indique en la variable ```BUILD```. De esta forma, se podrá testear cualquier versión que previamente se haya publicado en la rama [mvn-repo](https://github.com/carm-es/csvstorage/tree/mvn-repo/es/gob/aapp/csvstorage)


Luego, podrá ejecutarla mediante:

```
docker run -p 9110:8080 -i --name csvstorage carm-es/csvstorage:$BUILD
```

...donde ```9110``` será el puerto local que se mapeará al que escucha el Tomcat del contenedor. Se accederá a la aplicación mediante la URL [http://localhost:9110/csvstorage](http://localhost:9110/csvstorage), con el usuario por defecto ```admin``` y contraseña ```admin```. También podrá acceder a los servicios Web a través de la URL [http://localhost:9110/csvstorage/services](http://localhost:9110/csvstorage/services) y a la documentación de estos servicios desde la Web de https://administracionelectronica.gob.es/ctt/inside/descargas

Todos los ficheros relativos a csvstorage se encontrarán bajo el directorio ```/home/aapp/csvstorage/```:

* ```config```: Ficheros de configuración de la aplicación
* ```logs```: Logs de la aplicación
* ```filerepo/docs```: Directorio donde almacenar los documentos que se añadan a través de los servicios Web.


De esta forma, podrá ejecutar el contenedor para usar una configuración previa mapeando el volumen ```/home/aapp/csvstorage/config```:

```
docker run -p 9110:8080 -i -v DIR_CONFIG_LOCAL:/home/aapp/csvstorage/config --name csvstorage carm-es/csvstorage:$BUILD
```

... o incluso redirigir el repositorio de documentos a un volumen NFS:

```
docker run -p 9110:8080 -i -v PUNTO_MONTAJE:/home/aapp/csvstorage/filerepo/docs --name csvstorage carm-es/csvstorage:$BUILD
```

### Configuración

La configuración de la aplicación se almacena bajo el directorio ```/home/aapp/csvstorage/config```, y destacan tres ficheros:

* ```datasource.properties``` con la configuración de cómo conectar al servidor de base de datos Oracle, que previamente deberíamos haber preparado e inicializado con los scripts [resources/scripts_bbdd/](../resources/scripts_bbdd/). Se ha preparado un contenedor Docker con OracleXE que podrá usar si usted no dispone de un servidor propio.

* ```dir3.properties``` con la configuración del acceso a los servicios de consulta y sincronización de DIR3 (requerirá que haya  [solicitado acceso](https://ssweb.seap.minhap.es/ayuda/faq/DIR/77) ). 

* ```eeutil.properties``` con la configuración del acceso a los servicios de consulta y sincronización de DIR3 (requerirá que haya  [solicitado acceso](https://github.com/carm-es/inside/#dependencia-del-componente-eeutils) ). 

Por defecto, la imagen docker no tendrá accesos preconfigurados a los servicios del estado (dir3 y eeutils) e intentará conectar a una instancia OracleXE en local, que a continuación se describe como ejecutar. 



## Contenedor OracleXE 

Con el objetivo de poder probar CsvStorage sin disponer de un servidor Oracle 11g, se ha preparado una imagen docker que ejecutará una instancia de OracleXE y la prepará para almacenar los datos de la aplicación.

Para poder usarla, primero necesitará generar la imagen:
```
docker build -f Dockerfile.oracle -t oracle-11g .
```

Este comando construirá un contenedor docker en local, e inicializará el esquema ```csvstorage``` y las credenciales de acceso.


Luego, podrá ejecutar la instancia de OracleXE mediante:

```
docker run -p 1521:1521 -e TZ=Europe/Madrid --name csvstorage-oracle -d oracle-11g
```


Podrá conectar a esta instancia *(credenciales según [orangehrm/docker-oracle-xe-11g](https://github.com/orangehrm/docker-oracle-xe-11g))*:
```
hostname: localhost
port: 1521
sid: xe
username: system
password: oracle
```

Contraseñas:

* para `SYS` y `SYSTEM`: `oracle`
* para `root`: `admin`
* para el usuario de aplicación `csvstorage`: `supersecreto`


Si desea conectar desde un cliente Oracle (*toad, sqlplus, etc*) deberá añadir a su fichero `$TNS_ADMIN/tnsnames.ora` 
la cadena de conexión a CSVSTORAGE:

```
CSVSTORAGE = (DESCRIPTION =
    (ADDRESS = (PROTOCOL = TCP)(HOST = *DIR_IP*)(PORT = *PUERTO*))
    (CONNECT_DATA =
      (SERVER = DEDICATED)
      (SERVICE_NAME = XE)
    )
  )
```

..donde...

* `*DIR_IP*` será la dirección IP del equipo que ejecuta el contenedor para Oracle *(ejemplos: 127.0.0.1, 192.168.1.20, etc)*
* `*PUERTO*` será el puerto TCP que mapeó al 1521 al lanza el comando `docker run -p *PUERTO*:1521 ` 



