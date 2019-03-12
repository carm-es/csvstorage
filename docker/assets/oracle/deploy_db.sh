#!/bin/bash
#
#

 WorkDir="/var"
 DB="Oracle"

 # Descomprimir scripts para la base de datos
 cd $WorkDir

 # Descargar script de inicialización del esquema
 wget -q https://raw.githubusercontent.com/carm-es/csvstorage/master/resources/scripts_bbdd/BDcsvstorage.sql -O $WorkDir/BDcsvstorage.sql


 #
 # CREACION DE TABLESPACES...
 #
 #  - Crear uno para los esquemas comunes
 #  - Otro  para la entidad 000
 #  - Crear uno independiente para la auditoria (come mucho)
 #
 
 cat >$WorkDir/01-CreateTS.sql <<EOSQL

-- Para evitar que nos pregunte por los ampersand
SET escape on
SET define off

-- Evitar caducidad de contraseña
ALTER PROFILE "DEFAULT" LIMIT PASSWORD_LIFE_TIME UNLIMITED;

-- Cambiar la clave del system para que no caduque
ALTER USER system IDENTIFIED BY oracle;

-- Crear tableSpace
CREATE TABLESPACE CSVSTORAGE_DATA DATAFILE 'tbs_csvstorage_data.dbf' SIZE 512M ONLINE;

-- Crear usuarios
CREATE USER csvstorage IDENTIFIED BY supersecreto DEFAULT TABLESPACE CSVSTORAGE_DATA
  TEMPORARY TABLESPACE TEMP
      QUOTA UNLIMITED ON CSVSTORAGE_DATA ;

GRANT connect,resource TO csvstorage;
GRANT create view TO csvstorage;


exit;

EOSQL
 
 # Crear directorio de inicializacion si no existe
 mkdir -p /docker-entrypoint-initdb.d

 # Inicializacion de la Base de datos
 cat >/docker-entrypoint-initdb.d/CSVStorage.sh  <<EOF

cd $WorkDir 
echo "CREANDO TABLESPACES y USUARIOS" >&2
/u01/app/oracle/product/11.2.0/xe/bin/sqlplus -s system/oracle @01-CreateTS.sql

echo "Inicializando csvstorage" >&2
echo "exit;" | /u01/app/oracle/product/11.2.0/xe/bin/sqlplus -s csvstorage/supersecreto @BDcsvstorage.sql

 
EOF

