-- Script BD DDL para índices, mejoraron mucho la migración a partir de los 3 millones de documentos (los tiempos de carga empezaron a ser muy altos)
-- Aplicaciones CSVSTORAGE (el de INSIDE) y CSVSTORAGEGEN (el GENeral para Consejerías, e.g. IMAS y EXPERDOC) se trata de homogeneizar los tres entornos y entre los dos CSVSTORAGE y CSVSTORAGEGEN

	-- CSVSTORAGE
	
		-- PRO DES PRU Primero se lanzó en producción y posteriormente lo hemos normalizado y están creados también en el entorno de desarrollo y de pruebas
		CREATE INDEX "NDX_CSVST_DOCUMENTO_CSV" ON "CSVST_DOCUMENTO" ("CSV") COMPUTE STATISTICS;
		CREATE UNIQUE INDEX "NDX_CSVST_DOCUMENTO_IDENI" ON "CSVST_DOCUMENTO" ("ID_ENI" ASC) COMPUTE STATISTICS;
		CREATE INDEX "NDX_CSVST_DOCUMENTO_UORGANICA" ON "CSVST_DOCUMENTO" ("ID_UNIDAD_ORGANICA") COMPUTE STATISTICS;
		CREATE INDEX "NDX_CSVST_UNIDAD_ORGANICA" ON "CSVST_UNIDAD_ORGANICA" ("UNIDAD_ORGANICA") COMPUTE STATISTICS;

		-- PRO DES PRU Resumen de lo que hemos añadido. En el entorno de producción de la aplicación CSVSTORAGE se crearon primero, y después en desarrollo y en pruebas
		SELECT table_name, index_name, uniqueness FROM user_indexes WHERE table_name IN ('CSVST_DOCUMENTO', 'CSVST_UNIDAD_ORGANICA') AND index_name NOT LIKE 'PK_%';
		TABLE_NAME				INDEX_NAME						UNIQUENES
		CSVST_DOCUMENTO			NDX_CSVST_DOCUMENTO_CSV			NONUNIQUE
		CSVST_DOCUMENTO			NDX_CSVST_DOCUMENTO_IDENI		UNIQUE
		CSVST_DOCUMENTO			NDX_CSVST_DOCUMENTO_UORGANICA	NONUNIQUE
		CSVST_UNIDAD_ORGANICA	NDX_CSVST_UNIDAD_ORGANICA		NONUNIQUE

	-- CSVSTORAGEGEN
	
		-- No hay entorno de desarrollo ni de pruebas. En el entorno de producción de la aplicación CSVSTORAGE se crearon dos índices
		-- Email a Jesús (los crea él) En tu entorno de producción (en el usuario de base de datos CSVSTORAGEGEN) tienes que lanzar la creación de índices (yo no tengo acceso a tu usuario):

		-- CSVSTORAGEGEN PRO tenía creados estos índices
		CREATE INDEX "NDX_CSVST_DOCUMENTO_CSV" ON "CSVST_DOCUMENTO" ("CSV") COMPUTE STATISTICS;
		CREATE INDEX "NDX_CSVST_DOCUMENTO_IDENI" ON "CSVST_DOCUMENTO" ("ID_ENI") COMPUTE STATISTICS;

		-- Y posteriormente volvemos a crear uno de ellos y dos más. 
		-- Se trata de homogeneizar CSVSTORAGEGEN con el que tenemos nosotros CSVSTORAGE. No debería afectar a la funcionalidad.
		
		-- El NDX_CSVST_DOCUMENTO_IDENI lo eliminamos y lo volvemos a crear así:
		DROP INDEX NDX_CSVST_DOCUMENTO_IDENI;
		CREATE UNIQUE INDEX "NDX_CSVST_DOCUMENTO_IDENI" ON "CSVST_DOCUMENTO" ("ID_ENI" ASC) COMPUTE STATISTICS;

		-- Además faltan dos índices más que mejoran los tiempos de respuesta:
		CREATE INDEX "NDX_CSVST_DOCUMENTO_UORGANICA" ON "CSVST_DOCUMENTO" ("ID_UNIDAD_ORGANICA") COMPUTE STATISTICS;
		CREATE INDEX "NDX_CSVST_UNIDAD_ORGANICA" ON "CSVST_UNIDAD_ORGANICA" ("UNIDAD_ORGANICA") COMPUTE STATISTICS;

