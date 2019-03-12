#bigDataTransfer-1.0.jar
#Debe instalarse esta librería en el Maven Local con el siguiente comando, sustituyendo el parámetro -Dfile por la ruta donde se encuentra guardado el jar.

mvn install:install-file -DgroupId=es.gob.aapp.eeutil -DartifactId=bigDataTransfer -Dversion=1.0 -Dpackaging=jar -Dfile=/ruta/del/fichero/bigDataTransfer-1.0.jar  -DgeneratePom=true