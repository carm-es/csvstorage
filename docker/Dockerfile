# Usar la distribución oficial
# https://hub.docker.com/_/tomcat/
FROM tomcat:8.5-jre8-slim

LABEL maintainer ibarrancos <ignacio.barrancos@carm.es>

# Instalar dependencias (del sistema) 
RUN apt-get update -qq && \
    apt-get upgrade -y -q && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y bash-completion zip unzip curl wget vim locales sudo locales-all ssmtp

# Fijar locales
ENV LC_ALL es_ES.UTF-8
ENV LANG es_ES.UTF-8
ENV LANGUAGE es_ES.UTF-8

# Limpiar la caché de paquetes para liberar espacio
RUN apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Repostirio GitHub
ENV REPO carm-es/csvstorage

# Para recibir como argumento la version
ARG VERSION=latest
ENV VERS ${VERSION}

# Descargar JDBC para oracle
RUN wget -q https://raw.githubusercontent.com/${REPO}/mvn-repo/com/oracle/ojdbc6/11.2.0.4/ojdbc6-11.2.0.4.jar -O /usr/local/tomcat/lib/ojdbc6-11.2.0.4.jar

# Crear directorios que requiere CsvStorage
RUN mkdir -p /home/aapp/csvstorage/tmp/unzipped  /home/aapp/csvstorage/logs /etc/ssmtp /home/aapp/csvstorage/filerepo/docs /home/aapp/csvstorage/filerepo/cmis

# Directorio con la configuración de CsvStorage
ENV JAVA_OPTS='-Dcsvstorage.config.path=/home/aapp/csvstorage/config'

# Copiar la configuracion SMTP
COPY assets/ssmtp/* /etc/ssmtp/

# Copiar la configuración de CSVStorage
COPY assets/csvstorage-conf /home/aapp/csvstorage/config

# Descargar el WAR de la version
RUN export URL=$( wget -q https://raw.githubusercontent.com/${REPO}/mvn-repo/docker.link -O /tmp/link && \
    grep "^${VERS}#" /tmp/link  | cut -d '#' -f 2 | tail -1 ) ; \
    echo "** Se construirá la versión [${VERS}]=[$URL] **" ; \
    wget -q ${URL} -O /usr/local/tomcat/webapps/csvstorage.war

EXPOSE 8080 8009

CMD ["catalina.sh", "run"]

#
# Construir con:
#   export BUILD=1.2.3
#   export BUILD=1.2.3-CARM.1-SNAPSHOT
#   export BUILD=latest
#   docker build --build-arg VERSION=$BUILD  -t carm-es/csvstorage:$BUILD .
#
# Ejecutar interactivo (y comprobar si falla algo con):
#   docker run -p 9110:8080 -i --name csvstorage carm-es/csvstorage:$BUILD

