--------------------------------------------------------
--  DDL for Sequence CSVST_AMBITO_APLI_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_AMBITO_APLI_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 2 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_AMBITOS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_AMBITOS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 2 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_APLICACIONES_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_APLICACIONES_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_AUDIT_CONSULTAS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_AUDIT_CONSULTAS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 38065 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_AUDITORIA_PARAM_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_AUDITORIA_PARAM_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 98579 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_DOCUMENTO_APLIC_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_DOCUMENTO_APLIC_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 3562 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_DOCUMENTO_ENI_ORGANO_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_DOCUMENTO_ENI_ORGANO_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 2942 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_DOCUMENTO_ENI_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_DOCUMENTO_ENI_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_DOCUMENTO_NIF_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_DOCUMENTO_NIF_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 2632 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_DOCUMENTO_RESTRIC_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_DOCUMENTO_RESTRIC_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 6044 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_DOCUMENTO_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_DOCUMENTO_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_DOCUMENTO_TIPOID_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_DOCUMENTO_TIPOID_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 3016 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_OBJETO_CMIS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_OBJETO_CMIS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 177 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_PROP_OBJ_CMIS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_PROP_OBJ_CMIS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_PROP_TIPO_CMIS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_PROP_TIPO_CMIS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_TIPO_CMI_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_TIPO_CMI_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_TIPO_IDENTIFICACION_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_TIPO_IDENTIFICACION_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 17 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_UNIDAD_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_UNIDAD_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 191339 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CSVST_USER_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "CSVST_USER_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 16 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table CSVST_APLICACIONES
--------------------------------------------------------

  CREATE TABLE "CSVST_APLICACIONES" 
   (	"ID" NUMBER(11,0), 
	"ID_APLICACION" VARCHAR2(255 CHAR), 
	"DESCRIPCION" VARCHAR2(255 CHAR), 
	"PASSWORD_HASH" VARCHAR2(255 CHAR), 
	"ADMINISTRADOR" NUMBER(1,0) DEFAULT '0', 
	"NOMBRE_RESPONSABLE" VARCHAR2(255 CHAR), 
	"EMAIL_RESPONSABLE" VARCHAR2(255 CHAR), 
	"VALIDAR_DOC_ENI" NUMBER(1,0) DEFAULT '1', 
	"ACTIVO" NUMBER(1,0) DEFAULT '1', 
	"TELEFONO_RESPONSABLE" VARCHAR2(100 CHAR), 
	"SERIAL_NUMBER_CERTIFICADO" VARCHAR2(255 CHAR), 
	"CREATED_BY" NUMBER(11,0), 
	"CREATED_AT" TIMESTAMP (6) DEFAULT (SYSTIMESTAMP), 
	"MODIFIED_BY" NUMBER(11,0), 
	"MODIFIED_AT" TIMESTAMP (6), 
	"ID_UNIDAD" NUMBER(11,0), 
	"LECTURA_CMIS" NUMBER(1,0) DEFAULT 0, 
	"ESCRITURA_CMIS" NUMBER(1,0) DEFAULT 0, 
	"PERMISO_LECTURA" NUMBER(1,0) DEFAULT 1, 
	"DOCUMENTOS_PDF_Y_ENI" NUMBER(1,0) DEFAULT 0, 
	"ID_APLICACION_PUBLICO" VARCHAR2(255 CHAR)
   );

   COMMENT ON COLUMN "CSVST_APLICACIONES"."PERMISO_LECTURA" IS '1-Documentos creados por el usuario., 2-Todos los documentos por CSV, 3-Todos los documentos';
--------------------------------------------------------
--  DDL for Table CSVST_AUDITORIA_CONSULTAS
--------------------------------------------------------

  CREATE TABLE "CSVST_AUDITORIA_CONSULTAS" 
   (	"ID" NUMBER(11,0), 
	"FECHA" TIMESTAMP (6) DEFAULT NULL, 
	"NIF" VARCHAR2(10 BYTE), 
	"CODIGO_UNIDAD" VARCHAR2(40 BYTE), 
	"CSV" VARCHAR2(50 BYTE), 
	"ID_ENI" VARCHAR2(255 BYTE), 
	"TIPO_IDENTIFICACION" NUMBER(11,0), 
	"ID_DOCUMENTO" NUMBER(11,0), 
	"ID_APLICACION" NUMBER(11,0), 
	"ID_APLICACION_TXT" VARCHAR2(255 BYTE), 
	"OPERACION" VARCHAR2(50 BYTE), 
	"REQUEST" CLOB
   ) 
   LOB ("REQUEST") STORE AS SECUREFILE (CACHE NOLOGGING );
--------------------------------------------------------
--  DDL for Table CSVST_AUDITORIA_PARAM
--------------------------------------------------------

  CREATE TABLE "CSVST_AUDITORIA_PARAM" 
   (	"ID" NUMBER(11,0), 
	"ID_AUDITORIA" NUMBER(11,0), 
	"PARAMETRO" VARCHAR2(300 CHAR), 
	"VALOR" VARCHAR2(300 CHAR)
   ); 
--------------------------------------------------------
--  DDL for Table CSVST_DOCUMENTO
--------------------------------------------------------

  CREATE TABLE "CSVST_DOCUMENTO" 
   (	"ID" NUMBER(11,0), 
	"ID_UNIDAD_ORGANICA" NUMBER(11,0), 
	"CSV" VARCHAR2(50 BYTE), 
	"TIPO_MIME" VARCHAR2(255 BYTE) DEFAULT NULL, 
	"RUTA_FICHERO" VARCHAR2(250 BYTE), 
	"CREATED_AT" TIMESTAMP (6) DEFAULT (SYSTIMESTAMP), 
	"ID_ENI" VARCHAR2(255 BYTE), 
	"UUID" VARCHAR2(50 BYTE), 
	"CREATED_BY" NUMBER(11,0), 
	"MODIFIED_BY" NUMBER(11,0), 
	"MODIFIED_AT" TIMESTAMP (6), 
	"NOMBRE" VARCHAR2(100 BYTE), 
	"TAMANIO_FICHERO" NUMBER DEFAULT (-1), 
	"CONTENIDO_POR_REF" VARCHAR2(1 BYTE) DEFAULT 0, 
	"TIPO_PERMISO" NUMBER(11,0)
   );

   COMMENT ON COLUMN "CSVST_DOCUMENTO"."CONTENIDO_POR_REF" IS 'Indica si el XML tiene el contenido del fichero o una referencia al fichero que lo contiene';
   COMMENT ON COLUMN "CSVST_DOCUMENTO"."TIPO_PERMISO" IS '1-PUBLICO, 2-PRIVADO, 3-RESTRINGIDO';
--------------------------------------------------------
--  DDL for Table CSVST_DOCUMENTO_APLICACION
--------------------------------------------------------

  CREATE TABLE "CSVST_DOCUMENTO_APLICACION" 
   (	"ID" NUMBER(11,0), 
	"ID_DOCUMENTO" NUMBER(11,0), 
	"ID_APLICACION" NUMBER(11,0)
   );
--------------------------------------------------------
--  DDL for Table CSVST_DOCUMENTO_ENI
--------------------------------------------------------

  CREATE TABLE "CSVST_DOCUMENTO_ENI" 
   (	"ID" NUMBER(11,0), 
	"ID_DOCUMENTO" NUMBER(11,0), 
	"IDENTIFICADOR" VARCHAR2(255 BYTE), 
	"VERSION_NTI" VARCHAR2(255 BYTE), 
	"FECHA_CAPTURA" TIMESTAMP (6) DEFAULT '01/01/9999 00:00:00,000000', 
	"ORIGEN" NUMBER(1,0) DEFAULT '0', 
	"NOMBRE_FORMATO" VARCHAR2(255 BYTE), 
	"TIPO_DOCUMENTAL" VARCHAR2(255 BYTE), 
	"ESTADO_ELABORACION" VARCHAR2(255 BYTE), 
	"REFERENCIA" VARCHAR2(255 BYTE), 
	"CREATED_AT" TIMESTAMP (6) DEFAULT (SYSTIMESTAMP), 
	"FECHA_BAJA" TIMESTAMP (6), 
	"VALOR_BINARIO" NUMBER(1,0) DEFAULT 1, 
	"IDENTIFICADOR_DOCUMENTO_ORIGEN" VARCHAR2(255 BYTE), 
	"IDENTIFICADOR_EN_DOCUMENTO" VARCHAR2(255 BYTE)
   );
--------------------------------------------------------
--  DDL for Table CSVST_DOCUMENTO_ENI_ORGANO
--------------------------------------------------------

  CREATE TABLE "CSVST_DOCUMENTO_ENI_ORGANO" 
   (	"ID" NUMBER(11,0), 
	"ID_DOCUMENTO_ENI" NUMBER(11,0), 
	"ORGANO" VARCHAR2(255 BYTE)
   );
--------------------------------------------------------
--  DDL for Table CSVST_DOCUMENTO_NIF
--------------------------------------------------------

  CREATE TABLE "CSVST_DOCUMENTO_NIF" 
   (	"ID" NUMBER(11,0), 
	"ID_DOCUMENTO" NUMBER(11,0), 
	"NIF" VARCHAR2(10 BYTE)
   );
--------------------------------------------------------
--  DDL for Table CSVST_DOCUMENTO_RESTRICCION
--------------------------------------------------------

  CREATE TABLE "CSVST_DOCUMENTO_RESTRICCION" 
   (	"ID" NUMBER(11,0), 
	"ID_DOCUMENTO" NUMBER(11,0), 
	"ID_RESTRICCION" NUMBER(11,0)
   );
--------------------------------------------------------
--  DDL for Table CSVST_DOCUMENTO_TIPO_ID
--------------------------------------------------------

  CREATE TABLE "CSVST_DOCUMENTO_TIPO_ID" 
   (	"ID" NUMBER(11,0), 
	"ID_DOCUMENTO" NUMBER(11,0), 
	"ID_TIPO_IDENTIFICACION" NUMBER(11,0)
   ); 
--------------------------------------------------------
--  DDL for Table CSVST_OBJETO_CMIS
--------------------------------------------------------

  CREATE TABLE "CSVST_OBJETO_CMIS" 
   (	"ID" NUMBER(11,0), 
	"UUID" VARCHAR2(50 BYTE), 
	"FICHERO" VARCHAR2(250 BYTE), 
	"TIPO" VARCHAR2(50 BYTE), 
	"TIPO_PADRE" VARCHAR2(50 BYTE) DEFAULT 'cmis:folder'
   );
--------------------------------------------------------
--  DDL for Table CSVST_PROP_ADIC_OBJETO_CMIS
--------------------------------------------------------

  CREATE TABLE "CSVST_PROP_ADIC_OBJETO_CMIS" 
   (	"ID" NUMBER(11,0), 
	"UUID" VARCHAR2(50 BYTE), 
	"ID_PROPIEDAD" NUMBER(11,0), 
	"VALOR" VARCHAR2(200 BYTE)
   ); 
--------------------------------------------------------
--  DDL for Table CSVST_PROP_ADIC_TIPO_CMIS
--------------------------------------------------------

  CREATE TABLE "CSVST_PROP_ADIC_TIPO_CMIS" 
   (	"ID" NUMBER(11,0), 
	"ID_TIPO" NUMBER(11,0), 
	"ID_PROPIEDAD" VARCHAR2(50 BYTE), 
	"NOMBRE" VARCHAR2(100 BYTE), 
	"DESCRIPCION" VARCHAR2(100 BYTE), 
	"TIPO_DATO" VARCHAR2(50 BYTE), 
	"CARDINALIDAD" VARCHAR2(50 BYTE), 
	"MODIFICABLE" VARCHAR2(50 BYTE), 
	"HEREDADO" NUMBER(1,0), 
	"REQUERIDO" NUMBER(1,0), 
	"CONSULTABLE" NUMBER(1,0), 
	"ORDENABLE" NUMBER(1,0)
   ) ;
--------------------------------------------------------
--  DDL for Table CSVST_RESTRICCION
--------------------------------------------------------

  CREATE TABLE "CSVST_RESTRICCION" 
   (	"ID" NUMBER(11,0), 
	"TIPO_TERMISO" VARCHAR2(50 CHAR), 
	"DESCRIPCION" VARCHAR2(100 CHAR)
   );
--------------------------------------------------------
--  DDL for Table CSVST_TIPO_CMIS
--------------------------------------------------------

  CREATE TABLE "CSVST_TIPO_CMIS" 
   (	"ID" NUMBER(11,0), 
	"ID_TIPO" VARCHAR2(50 BYTE), 
	"TIPO_BASE" VARCHAR2(50 BYTE), 
	"VERSION" VARCHAR2(10 BYTE), 
	"NOMBRE" VARCHAR2(100 BYTE), 
	"DESCRIPCION" VARCHAR2(100 BYTE)
   ) ; 
--------------------------------------------------------
--  DDL for Table CSVST_TIPO_IDENTIFICACION
--------------------------------------------------------

  CREATE TABLE "CSVST_TIPO_IDENTIFICACION" 
   (	"ID" NUMBER(11,0), 
	"ID_TIPO" VARCHAR2(50 BYTE), 
	"DESCRIPCION" VARCHAR2(100 BYTE)
   );
--------------------------------------------------------
--  DDL for Table CSVST_TIPO_PERMISO_DOCUMENTO
--------------------------------------------------------

  CREATE TABLE "CSVST_TIPO_PERMISO_DOCUMENTO" 
   (	"ID" NUMBER(11,0), 
	"TIPO_TERMISO" VARCHAR2(50 BYTE), 
	"DESCRIPCION" VARCHAR2(100 BYTE)
   );
--------------------------------------------------------
--  DDL for Table CSVST_UNIDAD_ORGANICA
--------------------------------------------------------

  CREATE TABLE "CSVST_UNIDAD_ORGANICA" 
   (	"ID" NUMBER(11,0), 
	"UNIDAD_ORGANICA" VARCHAR2(40 BYTE), 
	"CODIGO_SIA" VARCHAR2(255 BYTE), 
	"AMBITO" VARCHAR2(3 BYTE), 
	"NOMBRE_UNIDAD_ORGANICA" VARCHAR2(255 BYTE), 
	"ESTADO" VARCHAR2(1 BYTE), 
	"NIVEL_ADMINISTRACION" NUMBER(1,0), 
	"NIVEL_JERARQUICO" NUMBER(2,0), 
	"CODIGO_UNIDAD_SUPERIOR" VARCHAR2(10 BYTE), 
	"NOMBRE_UNIDAD_SUPERIOR" VARCHAR2(255 BYTE), 
	"CODIGO_UNIDAD_RAIZ" VARCHAR2(10 BYTE), 
	"NOMBRE_UNIDAD_RAIZ" VARCHAR2(255 BYTE), 
	"FECHA_ALTA_OFICIAL" TIMESTAMP (6), 
	"FECHA_BAJA_OFICIAL" TIMESTAMP (6), 
	"FECHA_EXTINCION" TIMESTAMP (6), 
	"FECHA_ANULACION" TIMESTAMP (6), 
	"FECHA_CREACION" TIMESTAMP (6) DEFAULT CURRENT_TIMESTAMP, 
	"CREATED_AT" TIMESTAMP (6)
   ) ;
--------------------------------------------------------
--  DDL for Table CSVST_USUARIOS
--------------------------------------------------------

  CREATE TABLE "CSVST_USUARIOS" 
   (	"ID" NUMBER(11,0), 
	"USUARIO" VARCHAR2(255 CHAR), 
	"PASSWORD" VARCHAR2(255 CHAR), 
	"ADMINISTRADOR" NUMBER(1,0) DEFAULT '0'
   ) ;
--------------------------------------------------------
--  DDL for Table QRTZ_BLOB_TRIGGERS
--------------------------------------------------------

  CREATE TABLE "QRTZ_BLOB_TRIGGERS" 
   (	"SCHED_NAME" VARCHAR2(120 BYTE), 
	"TRIGGER_NAME" VARCHAR2(200 BYTE), 
	"TRIGGER_GROUP" VARCHAR2(200 BYTE), 
	"BLOB_DATA" BLOB
   ) 
   LOB ("BLOB_DATA") STORE AS SECUREFILE (CACHE NOLOGGING );
--------------------------------------------------------
--  DDL for Table QRTZ_CALENDARS
--------------------------------------------------------

  CREATE TABLE "QRTZ_CALENDARS" 
   (	"SCHED_NAME" VARCHAR2(120 BYTE), 
	"CALENDAR_NAME" VARCHAR2(200 BYTE), 
	"CALENDAR" BLOB
   ) 
   LOB ("CALENDAR") STORE AS SECUREFILE (CACHE NOLOGGING ); 
--------------------------------------------------------
--  DDL for Table QRTZ_CRON_TRIGGERS
--------------------------------------------------------

  CREATE TABLE "QRTZ_CRON_TRIGGERS" 
   (	"SCHED_NAME" VARCHAR2(120 BYTE), 
	"TRIGGER_NAME" VARCHAR2(200 BYTE), 
	"TRIGGER_GROUP" VARCHAR2(200 BYTE), 
	"CRON_EXPRESSION" VARCHAR2(120 BYTE), 
	"TIME_ZONE_ID" VARCHAR2(80 BYTE)
   );
--------------------------------------------------------
--  DDL for Table QRTZ_FIRED_TRIGGERS
--------------------------------------------------------

  CREATE TABLE "QRTZ_FIRED_TRIGGERS" 
   (	"SCHED_NAME" VARCHAR2(120 BYTE), 
	"ENTRY_ID" VARCHAR2(95 BYTE), 
	"TRIGGER_NAME" VARCHAR2(200 BYTE), 
	"TRIGGER_GROUP" VARCHAR2(200 BYTE), 
	"INSTANCE_NAME" VARCHAR2(200 BYTE), 
	"FIRED_TIME" NUMBER(13,0), 
	"SCHED_TIME" NUMBER(13,0), 
	"PRIORITY" NUMBER(13,0), 
	"STATE" VARCHAR2(16 BYTE), 
	"JOB_NAME" VARCHAR2(200 BYTE), 
	"JOB_GROUP" VARCHAR2(200 BYTE), 
	"IS_NONCONCURRENT" VARCHAR2(1 BYTE), 
	"REQUESTS_RECOVERY" VARCHAR2(1 BYTE)
   );
--------------------------------------------------------
--  DDL for Table QRTZ_JOB_DETAILS
--------------------------------------------------------

  CREATE TABLE "QRTZ_JOB_DETAILS" 
   (	"SCHED_NAME" VARCHAR2(120 BYTE), 
	"JOB_NAME" VARCHAR2(200 BYTE), 
	"JOB_GROUP" VARCHAR2(200 BYTE), 
	"DESCRIPTION" VARCHAR2(250 BYTE), 
	"JOB_CLASS_NAME" VARCHAR2(250 BYTE), 
	"IS_DURABLE" VARCHAR2(1 BYTE), 
	"IS_NONCONCURRENT" VARCHAR2(1 BYTE), 
	"IS_UPDATE_DATA" VARCHAR2(1 BYTE), 
	"REQUESTS_RECOVERY" VARCHAR2(1 BYTE), 
	"JOB_DATA" BLOB
   )
   LOB ("JOB_DATA") STORE AS SECUREFILE (CACHE NOLOGGING ); 
--------------------------------------------------------
--  DDL for Table QRTZ_LOCKS
--------------------------------------------------------

  CREATE TABLE "QRTZ_LOCKS" 
   (	"SCHED_NAME" VARCHAR2(120 BYTE), 
	"LOCK_NAME" VARCHAR2(40 BYTE)
   );
--------------------------------------------------------
--  DDL for Table QRTZ_PAUSED_TRIGGER_GRPS
--------------------------------------------------------

  CREATE TABLE "QRTZ_PAUSED_TRIGGER_GRPS" 
   (	"SCHED_NAME" VARCHAR2(120 BYTE), 
	"TRIGGER_GROUP" VARCHAR2(200 BYTE)
   );
--------------------------------------------------------
--  DDL for Table QRTZ_SCHEDULER_STATE
--------------------------------------------------------

  CREATE TABLE "QRTZ_SCHEDULER_STATE" 
   (	"SCHED_NAME" VARCHAR2(120 BYTE), 
	"INSTANCE_NAME" VARCHAR2(200 BYTE), 
	"LAST_CHECKIN_TIME" NUMBER(13,0), 
	"CHECKIN_INTERVAL" NUMBER(13,0)
   );
--------------------------------------------------------
--  DDL for Table QRTZ_SIMPLE_TRIGGERS
--------------------------------------------------------

  CREATE TABLE "QRTZ_SIMPLE_TRIGGERS" 
   (	"SCHED_NAME" VARCHAR2(120 BYTE), 
	"TRIGGER_NAME" VARCHAR2(200 BYTE), 
	"TRIGGER_GROUP" VARCHAR2(200 BYTE), 
	"REPEAT_COUNT" NUMBER(7,0), 
	"REPEAT_INTERVAL" NUMBER(12,0), 
	"TIMES_TRIGGERED" NUMBER(10,0)
   );
--------------------------------------------------------
--  DDL for Table QRTZ_SIMPROP_TRIGGERS
--------------------------------------------------------

  CREATE TABLE "QRTZ_SIMPROP_TRIGGERS" 
   (	"SCHED_NAME" VARCHAR2(120 BYTE), 
	"TRIGGER_NAME" VARCHAR2(200 BYTE), 
	"TRIGGER_GROUP" VARCHAR2(200 BYTE), 
	"STR_PROP_1" VARCHAR2(512 BYTE), 
	"STR_PROP_2" VARCHAR2(512 BYTE), 
	"STR_PROP_3" VARCHAR2(512 BYTE), 
	"INT_PROP_1" NUMBER(10,0), 
	"INT_PROP_2" NUMBER(10,0), 
	"LONG_PROP_1" NUMBER(13,0), 
	"LONG_PROP_2" NUMBER(13,0), 
	"DEC_PROP_1" NUMBER(13,4), 
	"DEC_PROP_2" NUMBER(13,4), 
	"BOOL_PROP_1" VARCHAR2(1 BYTE), 
	"BOOL_PROP_2" VARCHAR2(1 BYTE)
   );
--------------------------------------------------------
--  DDL for Table QRTZ_TRIGGERS
--------------------------------------------------------

  CREATE TABLE "QRTZ_TRIGGERS" 
   (	"SCHED_NAME" VARCHAR2(120 BYTE), 
	"TRIGGER_NAME" VARCHAR2(200 BYTE), 
	"TRIGGER_GROUP" VARCHAR2(200 BYTE), 
	"JOB_NAME" VARCHAR2(200 BYTE), 
	"JOB_GROUP" VARCHAR2(200 BYTE), 
	"DESCRIPTION" VARCHAR2(250 BYTE), 
	"NEXT_FIRE_TIME" NUMBER(13,0), 
	"PREV_FIRE_TIME" NUMBER(13,0), 
	"PRIORITY" NUMBER(13,0), 
	"TRIGGER_STATE" VARCHAR2(16 BYTE), 
	"TRIGGER_TYPE" VARCHAR2(8 BYTE), 
	"START_TIME" NUMBER(13,0), 
	"END_TIME" NUMBER(13,0), 
	"CALENDAR_NAME" VARCHAR2(200 BYTE), 
	"MISFIRE_INSTR" NUMBER(2,0), 
	"JOB_DATA" BLOB
   )
   LOB ("JOB_DATA") STORE AS SECUREFILE (CACHE NOLOGGING );
--------------------------------------------------------
--  DDL for View V_APLICACIONES_ST
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW "V_APLICACIONES_ST" ("ID_BD", "APLICACION", "DESCRIPCION", "ADMINISTRADOR", "ACTIVO") AS 
  SELECT
    ID                                            AS ID_BD,
    ID_APLICACION                                 AS APLICACION,
    DESCRIPCION                                   AS DESCRIPCION,
    DECODE(ADMINISTRADOR, 0, 'NO', 1, 'SI', 'NO') AS ADMINISTRADOR,
    ACTIVO					      AS ACTIVO
FROM
    CSVST_APLICACIONES
WHERE
    ID_APLICACION NOT IN ( 'CSVBROKER',
                          'csvstorageadmin' )
;
--------------------------------------------------------
--  DDL for View V_AUDITORIA_ST
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW "V_AUDITORIA_ST" ("ID_AUDITORIA", "FECHA", "OPERACION", "ID_DOCUMENTO", "CSV", "ID_ENI", "ID_APLICACION", "ID_APLICACION_TXT", "PARAMETROS") AS 
  select au.ID id_auditoria, au.FECHA, au.OPERACION, au.ID_DOCUMENTO, doc.csv, doc.id_eni, au.ID_APLICACION, au.ID_APLICACION_TXT, 
    listagg((case when aup.parametro is null then null else aup.PARAMETRO || '=' || aup.VALOR end), '; ')
    within group (ORDER BY aup.id asc) "PARAMETROS"
from csvst_auditoria_param aup right outer join csvst_auditoria_consultas au
    on aup.id_auditoria = au.id left outer join csvst_documento doc
    on au.id_documento = doc.id
group by au.ID, au.FECHA, au.OPERACION, au.ID_DOCUMENTO, doc.csv, doc.id_eni, au.ID_APLICACION, au.ID_APLICACION_TXT
ORDER BY au.ID desc, au.FECHA, au.OPERACION, au.ID_DOCUMENTO, doc.csv, doc.id_eni, au.ID_APLICACION, au.ID_APLICACION_TXT
;
--------------------------------------------------------
--  DDL for View V_DOCUMENTOS_ALMACENADOS_ST
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW "V_DOCUMENTOS_ALMACENADOS_ST" ("ID_DOCUMENTO_BD", "IDENTIFICADOR_DOCUMENTO", "NOMBRE_FORMATO", "FECHA_CREACION", "UNIDAD_ORGANICA", "CODIGO_SIA", "AMBITO") AS 
  SELECT
    D.ID               AS ID_DOCUMENTO_BD,
    DECODE (D.CSV, D.CSV, D.ID_ENI) AS IDENTIFICADOR_DOCUMENTO,
    D.TIPO_MIME        AS NOMBRE_FORMATO,
    D.CREATED_AT       AS FECHA_CREACION,
    UO.UNIDAD_ORGANICA AS UNIDAD_ORGANICA,
    UO.CODIGO_SIA      AS CODIGO_SIA,
    UO.AMBITO          AS AMBITO
FROM
    CSVST_DOCUMENTO D,
    CSVST_UNIDAD_ORGANICA UO
WHERE D.ID_UNIDAD_ORGANICA = UO.ID
;
--------------------------------------------------------
--  DDL for View V_DOCUMENTOS_POR_DIR3
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW "V_DOCUMENTOS_POR_DIR3" ("UNIDAD_ORGANICA", "NUMERO_DOCUMENTOS", "ESPACIO_ALMACENADO") AS 
  SELECT
    UO.UNIDAD_ORGANICA              AS UNIDAD_ORGANICA,
    COUNT(D.ID)                     AS NUMERO_DOCUMENTOS,
    SUM(D.TAMANIO_FICHERO)          AS ESPACIO_ALMACENADO
FROM
    CSVST_DOCUMENTO D,
    CSVST_UNIDAD_ORGANICA UO
WHERE
    D.ID_UNIDAD_ORGANICA = UO.ID
GROUP BY UNIDAD_ORGANICA
;
--------------------------------------------------------
--  DDL for View V_ORGANISMOS_PRODUCTORES_ST
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW "V_ORGANISMOS_PRODUCTORES_ST" ("UNIDAD_ORGANICA", "CODIGO_SIA", "AMBITO", "NUM_DOCUMENTOS") AS 
  SELECT
    UO.UNIDAD_ORGANICA AS UNIDAD_ORGANICA,
    UO.CODIGO_SIA      AS CODIGO_SIA,
    UO.AMBITO          AS AMBITO,
    COUNT(*)           AS NUM_DOCUMENTOS
FROM
    CSVST_DOCUMENTO_ENI D,
    CSVST_DOCUMENTO_ENI_ORGANO DEO,
    CSVST_UNIDAD_ORGANICA UO
WHERE
    D.ID_DOCUMENTO = DEO.ID_DOCUMENTO_ENI
AND DEO.ORGANO = UO.UNIDAD_ORGANICA
AND D.FECHA_BAJA IS NULL
GROUP BY
    UO.UNIDAD_ORGANICA,
    UO.CODIGO_SIA,
    UO.AMBITO
;
--------------------------------------------------------
--  DDL for View V_ORGANISMOS_ST
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW "V_ORGANISMOS_ST" ("CODIGO_UNIDAD_ORGANICA", "NOMBRE_UNIDAD_ORGANICA", "CODIGO_UNIDAD_SUPERIOR", "NOMBRE_UNIDAD_SUPERIOR", "CODIGO_UNIDAD_RAIZ", "NOMBRE_UNIDAD_RAIZ", "FECHA_ALTA", "NUM_APLICACIONES") AS 
  SELECT
    U.UNIDAD_ORGANICA        AS CODIGO_UNIDAD_ORGANICA,
    U.NOMBRE_UNIDAD_ORGANICA AS NOMBRE_UNIDAD_ORGANICA,
    U.CODIGO_UNIDAD_SUPERIOR AS CODIGO_UNIDAD_SUPERIOR,
    U.NOMBRE_UNIDAD_SUPERIOR AS NOMBRE_UNIDAD_SUPERIOR,
    U.CODIGO_UNIDAD_RAIZ     AS CODIGO_UNIDAD_RAIZ,
    U.NOMBRE_UNIDAD_RAIZ     AS NOMBRE_UNIDAD_RAIZ,
    U.FECHA_CREACION         AS FECHA_ALTA,
    COUNT(*)                 AS NUM_APLICACIONES
FROM
    CSVST_APLICACIONES A,
    CSVST_UNIDAD_ORGANICA U
WHERE
    A.ID_UNIDAD = U.ID
GROUP BY
    U.UNIDAD_ORGANICA,
    U.NOMBRE_UNIDAD_ORGANICA,
    U.CODIGO_UNIDAD_SUPERIOR,
    U.NOMBRE_UNIDAD_SUPERIOR,
    U.CODIGO_UNIDAD_RAIZ,
    U.NOMBRE_UNIDAD_RAIZ,
    U.FECHA_CREACION
;
--------------------------------------------------------
--  DDL for View V_REFERENCIAS_APP_ST
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW "V_REFERENCIAS_APP_ST" ("ID", "ID_DOCUMENTO", "TIPO", "CSV", "ID_ENI", "ID_UNIDAD_DOCUMENTO", "DIR3_DOCUMENTO", "DIR3_DOCUMENTO_NOMBRE", "ID_APLICACION", "ID_APLICACION_PUBLICO", "ID_UNIDAD_APLICACION", "DIR3_APLICACION", "DIR3_APLICACION_NOMBRE") AS 
  select sys_guid() id, d.id id_documento, 'EMISOR' TIPO, d.csv, d.id_eni, u1.ID id_unidad_documento, u1.UNIDAD_ORGANICA DIR3_DOCUMENTO, 
        u1.NOMBRE_UNIDAD_ORGANICA DIR3_DOCUMENTO_NOMBRE, ap.ID_APLICACION, ap.ID_APLICACION_PUBLICO, 
        u2.ID id_unidad_aplicacion, u2.UNIDAD_ORGANICA DIR3_APLICACION, u2.NOMBRE_UNIDAD_ORGANICA DIR3_APLICACION_NOMBRE
from csvst_documento d 
    inner join csvst_unidad_organica u1 on d.id_unidad_organicA = u1.id
    inner join csvst_aplicaciones ap on d.CREATED_BY = ap.id
    left outer join csvst_unidad_organica u2 on ap.ID_UNIDAD = u2.ID
union
select sys_guid() id, d.id id_documento, 'RECEPTOR' TIPO,  d.csv, d.id_eni, u1.ID id_unidad_documento, u1.UNIDAD_ORGANICA DIR3_DOCUMENTO,
         u1.NOMBRE_UNIDAD_ORGANICA DIR3_DOCUMENTO_NOMBRE, ap.ID_APLICACION, ap.ID_APLICACION_PUBLICO, 
         u2.ID id_unidad_aplicacion, u2.UNIDAD_ORGANICA DIR3_APLICACION, u2.NOMBRE_UNIDAD_ORGANICA DIR3_APLICACION_NOMBRE
from csvst_documento d 
    inner join csvst_unidad_organica u1 on d.id_unidad_organicA = u1.id
    inner join CSVST_DOCUMENTO_APLICACION da on da.ID_DOCUMENTO = d.ID
    inner join CSVST_APLICACIONES ap on da.id_aplicacion = ap.id
    left outer join csvst_unidad_organica u2 on ap.ID_UNIDAD = u2.ID
order by 2 desc, 3
;
REM INSERTING into CSVST_RESTRICCION
SET DEFINE OFF;
Insert into CSVST_RESTRICCION (ID,TIPO_TERMISO,DESCRIPCION) values ('4','RESTRINGIDO_APP','Restringido por aplicaciones');
Insert into CSVST_RESTRICCION (ID,TIPO_TERMISO,DESCRIPCION) values ('1','RESTRINGIDO_ID','Restringido por identificacion');
Insert into CSVST_RESTRICCION (ID,TIPO_TERMISO,DESCRIPCION) values ('2','RESTRINGIDO_NIF','Restringido por NIF');
Insert into CSVST_RESTRICCION (ID,TIPO_TERMISO,DESCRIPCION) values ('3','RESTRINGIDO_PUB','Restringido a empleados publicos');
REM INSERTING into CSVST_TIPO_CMIS
SET DEFINE OFF;
REM INSERTING into CSVST_TIPO_IDENTIFICACION
SET DEFINE OFF;
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('1','CLAVE_PERM','Clave');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('2','PIN24','PIN24');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('3','DNIE','DNI-E');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('4','PF_2CA','Persona Fisica 2CA');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('5','PJ_2CA','Persona Juridica 2CA');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('6','COMPONENTESSL','Componente SSL');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('7','SEDE_ELECTRONICA','Sede Electronica');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('8','SELLO_ORGANO','Sello Organo');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('9','EMPLEADO_PUBLICO','Empleado publico');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('10','ENTIDAD_NO_PERSONA_JURIDICA','Entidad No Persona Juridica');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('11','EMPLEADO_PUBLICO_PSEUD','Empleado Publico Pseudonimo');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('12','CUALIFICADO_SELLO_ENTIDAD','Cualificado Sello Entidad');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('13','CUALIFICADO_AUTENTICACION','Cualificado Autenticacion');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('14','CUALIFICADO_SELLO_TIEMPO','Cualificado Sello Tiempo');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('15','REPRESENTACION_PJ','Representacion Persona Juridica');
Insert into CSVST_TIPO_IDENTIFICACION (ID,ID_TIPO,DESCRIPCION) values ('16','REPRESENTACION_ENTIDAD_SIN_PF','Representacion Entidad sin Persona Fisica');
REM INSERTING into CSVST_TIPO_PERMISO_DOCUMENTO
SET DEFINE OFF;
Insert into CSVST_TIPO_PERMISO_DOCUMENTO (ID,TIPO_TERMISO,DESCRIPCION) values ('1','PUBLICO','Publico');
Insert into CSVST_TIPO_PERMISO_DOCUMENTO (ID,TIPO_TERMISO,DESCRIPCION) values ('2','PRIVADO','Privado');
Insert into CSVST_TIPO_PERMISO_DOCUMENTO (ID,TIPO_TERMISO,DESCRIPCION) values ('3','RESTRINGIDO','Restringido');
REM INSERTING into QRTZ_BLOB_TRIGGERS
SET DEFINE OFF;
REM INSERTING into QRTZ_CALENDARS
SET DEFINE OFF;
REM INSERTING into QRTZ_CRON_TRIGGERS
SET DEFINE OFF;
Insert into QRTZ_CRON_TRIGGERS (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,CRON_EXPRESSION,TIME_ZONE_ID) values ('schedulerFactoryBean','triggerunidadesorganicas','unidaddir3','0 0 4 ? * MON 2049','Europe/Paris');
REM INSERTING into QRTZ_FIRED_TRIGGERS
SET DEFINE OFF;
REM INSERTING into QRTZ_JOB_DETAILS
SET DEFINE OFF;
Insert into QRTZ_JOB_DETAILS (SCHED_NAME,JOB_NAME,JOB_GROUP,DESCRIPTION,JOB_CLASS_NAME,IS_DURABLE,IS_NONCONCURRENT,IS_UPDATE_DATA,REQUESTS_RECOVERY) values ('schedulerFactoryBean','triggerunidadesorganicas','unidaddir3',null,'es.gob.aapp.csvstorage.job.OrganicUnitJob','1','0','0','0');
REM INSERTING into QRTZ_LOCKS
SET DEFINE OFF;
Insert into QRTZ_LOCKS (SCHED_NAME,LOCK_NAME) values ('schedulerFactoryBean','STATE_ACCESS');
Insert into QRTZ_LOCKS (SCHED_NAME,LOCK_NAME) values ('schedulerFactoryBean','TRIGGER_ACCESS');
REM INSERTING into QRTZ_PAUSED_TRIGGER_GRPS
SET DEFINE OFF;
REM INSERTING into QRTZ_SCHEDULER_STATE
SET DEFINE OFF;
Insert into QRTZ_SCHEDULER_STATE (SCHED_NAME,INSTANCE_NAME,LAST_CHECKIN_TIME,CHECKIN_INTERVAL) values ('schedulerFactoryBean','sacaedesse01.redsara.es1538988662306','1540215656364','20000');
REM INSERTING into QRTZ_SIMPLE_TRIGGERS
SET DEFINE OFF;
REM INSERTING into QRTZ_SIMPROP_TRIGGERS
SET DEFINE OFF;
REM INSERTING into QRTZ_TRIGGERS
SET DEFINE OFF;
Insert into QRTZ_TRIGGERS (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,JOB_NAME,JOB_GROUP,DESCRIPTION,NEXT_FIRE_TIME,PREV_FIRE_TIME,PRIORITY,TRIGGER_STATE,TRIGGER_TYPE,START_TIME,END_TIME,CALENDAR_NAME,MISFIRE_INSTR) values ('schedulerFactoryBean','triggerunidadesorganicas','unidaddir3','triggerunidadesorganicas','unidaddir3',null,'1540782000000','-1','0','WAITING','CRON','1540207986000','0',null,'0');
--------------------------------------------------------
--  DDL for Index CSVST_AUDITORIA_PARAMS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CSVST_AUDITORIA_PARAMS_PK" ON "CSVST_AUDITORIA_PARAM" ("ID") COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index CSVST_OBJETO_CMIS_IX1
--------------------------------------------------------

  CREATE UNIQUE INDEX "CSVST_OBJETO_CMIS_IX1" ON "CSVST_OBJETO_CMIS" ("UUID") COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index CSVST_TIPO_CMIS_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "CSVST_TIPO_CMIS_UK1" ON "CSVST_TIPO_CMIS" ("ID_TIPO", "VERSION") COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index CSVST_USER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CSVST_USER_PK" ON "CSVST_USUARIOS" ("ID") COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index DOC_ORG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "DOC_ORG_PK" ON "CSVST_DOCUMENTO_ENI_ORGANO" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_FT_INST_JOB_REQ_RCVRY
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_FT_INST_JOB_REQ_RCVRY" ON "QRTZ_FIRED_TRIGGERS" ("SCHED_NAME", "INSTANCE_NAME", "REQUESTS_RECOVERY")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_FT_J_G
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_FT_J_G" ON "QRTZ_FIRED_TRIGGERS" ("SCHED_NAME", "JOB_NAME", "JOB_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_FT_JG
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_FT_JG" ON "QRTZ_FIRED_TRIGGERS" ("SCHED_NAME", "JOB_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_FT_T_G
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_FT_T_G" ON "QRTZ_FIRED_TRIGGERS" ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_FT_TG
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_FT_TG" ON "QRTZ_FIRED_TRIGGERS" ("SCHED_NAME", "TRIGGER_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_FT_TRIG_INST_NAME
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_FT_TRIG_INST_NAME" ON "QRTZ_FIRED_TRIGGERS" ("SCHED_NAME", "INSTANCE_NAME")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_J_GRP
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_J_GRP" ON "QRTZ_JOB_DETAILS" ("SCHED_NAME", "JOB_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_J_REQ_RECOVERY
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_J_REQ_RECOVERY" ON "QRTZ_JOB_DETAILS" ("SCHED_NAME", "REQUESTS_RECOVERY")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_T_C
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_T_C" ON "QRTZ_TRIGGERS" ("SCHED_NAME", "CALENDAR_NAME")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_T_G
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_T_G" ON "QRTZ_TRIGGERS" ("SCHED_NAME", "TRIGGER_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_T_J
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_T_J" ON "QRTZ_TRIGGERS" ("SCHED_NAME", "JOB_NAME", "JOB_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_T_JG
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_T_JG" ON "QRTZ_TRIGGERS" ("SCHED_NAME", "JOB_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_T_NEXT_FIRE_TIME
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_T_NEXT_FIRE_TIME" ON "QRTZ_TRIGGERS" ("SCHED_NAME", "NEXT_FIRE_TIME")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_T_NFT_MISFIRE
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_T_NFT_MISFIRE" ON "QRTZ_TRIGGERS" ("SCHED_NAME", "MISFIRE_INSTR", "NEXT_FIRE_TIME")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_T_NFT_ST
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_T_NFT_ST" ON "QRTZ_TRIGGERS" ("SCHED_NAME", "TRIGGER_STATE", "NEXT_FIRE_TIME")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_T_NFT_ST_MISFIRE
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_T_NFT_ST_MISFIRE" ON "QRTZ_TRIGGERS" ("SCHED_NAME", "MISFIRE_INSTR", "NEXT_FIRE_TIME", "TRIGGER_STATE")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_T_NFT_ST_MISFIRE_GRP
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_T_NFT_ST_MISFIRE_GRP" ON "QRTZ_TRIGGERS" ("SCHED_NAME", "MISFIRE_INSTR", "NEXT_FIRE_TIME", "TRIGGER_GROUP", "TRIGGER_STATE")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_T_N_G_STATE
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_T_N_G_STATE" ON "QRTZ_TRIGGERS" ("SCHED_NAME", "TRIGGER_GROUP", "TRIGGER_STATE")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_T_N_STATE
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_T_N_STATE" ON "QRTZ_TRIGGERS" ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP", "TRIGGER_STATE")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index IDX_QRTZ_T_STATE
--------------------------------------------------------

  CREATE INDEX "IDX_QRTZ_T_STATE" ON "QRTZ_TRIGGERS" ("SCHED_NAME", "TRIGGER_STATE")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index PF_CSVST_OBJETO_CMIS
--------------------------------------------------------

  CREATE UNIQUE INDEX "PF_CSVST_OBJETO_CMIS" ON "CSVST_OBJETO_CMIS" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index PK_CSVBR_APLICACIONES
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CSVBR_APLICACIONES" ON "CSVST_APLICACIONES" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index PK_CSVST_DOCUMENTO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CSVST_DOCUMENTO" ON "CSVST_DOCUMENTO" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index PK_CSVST_DOCUMENTO_ENI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CSVST_DOCUMENTO_ENI" ON "CSVST_DOCUMENTO_ENI" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index PK_CSVST_UNIDAD
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CSVST_UNIDAD" ON "CSVST_UNIDAD_ORGANICA" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index QRTZ_BLOB_TRIG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "QRTZ_BLOB_TRIG_PK" ON "QRTZ_BLOB_TRIGGERS" ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index QRTZ_CALENDARS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "QRTZ_CALENDARS_PK" ON "QRTZ_CALENDARS" ("SCHED_NAME", "CALENDAR_NAME")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index QRTZ_CRON_TRIG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "QRTZ_CRON_TRIG_PK" ON "QRTZ_CRON_TRIGGERS" ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index QRTZ_FIRED_TRIGGER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "QRTZ_FIRED_TRIGGER_PK" ON "QRTZ_FIRED_TRIGGERS" ("SCHED_NAME", "ENTRY_ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index QRTZ_JOB_DETAILS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "QRTZ_JOB_DETAILS_PK" ON "QRTZ_JOB_DETAILS" ("SCHED_NAME", "JOB_NAME", "JOB_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index QRTZ_LOCKS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "QRTZ_LOCKS_PK" ON "QRTZ_LOCKS" ("SCHED_NAME", "LOCK_NAME")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index QRTZ_PAUSED_TRIG_GRPS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "QRTZ_PAUSED_TRIG_GRPS_PK" ON "QRTZ_PAUSED_TRIGGER_GRPS" ("SCHED_NAME", "TRIGGER_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index QRTZ_SCHEDULER_STATE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "QRTZ_SCHEDULER_STATE_PK" ON "QRTZ_SCHEDULER_STATE" ("SCHED_NAME", "INSTANCE_NAME")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index QRTZ_SIMPLE_TRIG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "QRTZ_SIMPLE_TRIG_PK" ON "QRTZ_SIMPLE_TRIGGERS" ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index QRTZ_SIMPROP_TRIG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "QRTZ_SIMPROP_TRIG_PK" ON "QRTZ_SIMPROP_TRIGGERS" ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index QRTZ_TRIGGERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "QRTZ_TRIGGERS_PK" ON "QRTZ_TRIGGERS" ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index SYS_C00673282
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C00673282" ON "CSVST_TIPO_CMIS" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index SYS_C00673488
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C00673488" ON "CSVST_PROP_ADIC_TIPO_CMIS" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index SYS_C00673490
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C00673490" ON "CSVST_PROP_ADIC_OBJETO_CMIS" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index SYS_C00737542
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C00737542" ON "CSVST_TIPO_IDENTIFICACION" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index SYS_C00737783
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C00737783" ON "CSVST_DOCUMENTO_NIF" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index SYS_C00737894
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C00737894" ON "CSVST_TIPO_PERMISO_DOCUMENTO" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index SYS_C00739871
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C00739871" ON "CSVST_AUDITORIA_CONSULTAS" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index SYS_C00866378
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C00866378" ON "CSVST_RESTRICCION" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index SYS_C00866380
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C00866380" ON "CSVST_DOCUMENTO_RESTRICCION" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index SYS_C00867769
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C00867769" ON "CSVST_DOCUMENTO_TIPO_ID" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index SYS_C00868095
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C00868095" ON "CSVST_DOCUMENTO_APLICACION" ("ID")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index UNIQUE_ID_APLICACION
--------------------------------------------------------

  CREATE UNIQUE INDEX "UNIQUE_ID_APLICACION" ON "CSVST_APLICACIONES" ("ID_APLICACION")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index UNIQUE_ID_APLICACION_PUBLICO
--------------------------------------------------------

  CREATE UNIQUE INDEX "UNIQUE_ID_APLICACION_PUBLICO" ON "CSVST_APLICACIONES" ("ID_APLICACION_PUBLICO")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  DDL for Index UNIQUE_VERSION_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "UNIQUE_VERSION_ID" ON "CSVST_DOCUMENTO_ENI" ("IDENTIFICADOR")  COMPUTE STATISTICS ;
--------------------------------------------------------
--  Constraints for Table CSVST_APLICACIONES
--------------------------------------------------------

  ALTER TABLE "CSVST_APLICACIONES" ADD CONSTRAINT "UNIQUE_ID_APLICACION_PUBLICO" UNIQUE ("ID_APLICACION_PUBLICO");

  ALTER TABLE "CSVST_APLICACIONES" ADD CONSTRAINT "UNIQUE_ID_APLICACION" UNIQUE ("ID_APLICACION");

  ALTER TABLE "CSVST_APLICACIONES" MODIFY ("DOCUMENTOS_PDF_Y_ENI" NOT NULL ENABLE);
  ALTER TABLE "CSVST_APLICACIONES" MODIFY ("PERMISO_LECTURA" NOT NULL ENABLE);
  ALTER TABLE "CSVST_APLICACIONES" MODIFY ("ESCRITURA_CMIS" NOT NULL ENABLE);
  ALTER TABLE "CSVST_APLICACIONES" MODIFY ("LECTURA_CMIS" NOT NULL ENABLE);
  ALTER TABLE "CSVST_APLICACIONES" MODIFY ("CREATED_BY" NOT NULL ENABLE);
  ALTER TABLE "CSVST_APLICACIONES" MODIFY ("ACTIVO" NOT NULL ENABLE);
  ALTER TABLE "CSVST_APLICACIONES" MODIFY ("VALIDAR_DOC_ENI" NOT NULL ENABLE);
  ALTER TABLE "CSVST_APLICACIONES" MODIFY ("ADMINISTRADOR" NOT NULL ENABLE);
  ALTER TABLE "CSVST_APLICACIONES" MODIFY ("PASSWORD_HASH" NOT NULL ENABLE);
  ALTER TABLE "CSVST_APLICACIONES" MODIFY ("DESCRIPCION" NOT NULL ENABLE);
  ALTER TABLE "CSVST_APLICACIONES" MODIFY ("ID_APLICACION" NOT NULL ENABLE);
  ALTER TABLE "CSVST_APLICACIONES" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "CSVST_APLICACIONES" ADD CONSTRAINT "PK_CSVBR_APLICACIONES" PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table CSVST_AUDITORIA_CONSULTAS
--------------------------------------------------------

  ALTER TABLE "CSVST_AUDITORIA_CONSULTAS" ADD PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table CSVST_AUDITORIA_PARAM
--------------------------------------------------------

  ALTER TABLE "CSVST_AUDITORIA_PARAM" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "CSVST_AUDITORIA_PARAM" ADD CONSTRAINT "CSVST_AUDITORIA_PARAMS_PK" PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table CSVST_DOCUMENTO
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO" MODIFY ("CONTENIDO_POR_REF" NOT NULL ENABLE);
  ALTER TABLE "CSVST_DOCUMENTO" MODIFY ("RUTA_FICHERO" NOT NULL ENABLE);
  ALTER TABLE "CSVST_DOCUMENTO" MODIFY ("ID_UNIDAD_ORGANICA" NOT NULL ENABLE);
  ALTER TABLE "CSVST_DOCUMENTO" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "CSVST_DOCUMENTO" ADD CONSTRAINT "PK_CSVST_DOCUMENTO" PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table CSVST_DOCUMENTO_APLICACION
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO_APLICACION" ADD PRIMARY KEY ("ID");
  ALTER TABLE "CSVST_DOCUMENTO_APLICACION" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CSVST_DOCUMENTO_ENI
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO_ENI" ADD CONSTRAINT "UNIQUE_VERSION_ID" UNIQUE ("IDENTIFICADOR");
  ALTER TABLE "CSVST_DOCUMENTO_ENI" MODIFY ("VALOR_BINARIO" NOT NULL ENABLE);
  ALTER TABLE "CSVST_DOCUMENTO_ENI" MODIFY ("ORIGEN" NOT NULL ENABLE);
  ALTER TABLE "CSVST_DOCUMENTO_ENI" MODIFY ("IDENTIFICADOR" NOT NULL ENABLE);
  ALTER TABLE "CSVST_DOCUMENTO_ENI" MODIFY ("ID_DOCUMENTO" NOT NULL ENABLE);
  ALTER TABLE "CSVST_DOCUMENTO_ENI" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "CSVST_DOCUMENTO_ENI" ADD CONSTRAINT "PK_CSVST_DOCUMENTO_ENI" PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table CSVST_DOCUMENTO_ENI_ORGANO
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO_ENI_ORGANO" MODIFY ("ORGANO" NOT NULL ENABLE);
  ALTER TABLE "CSVST_DOCUMENTO_ENI_ORGANO" MODIFY ("ID_DOCUMENTO_ENI" NOT NULL ENABLE);
  ALTER TABLE "CSVST_DOCUMENTO_ENI_ORGANO" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "CSVST_DOCUMENTO_ENI_ORGANO" ADD CONSTRAINT "DOC_ORG_PK" PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table CSVST_DOCUMENTO_NIF
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO_NIF" ADD PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table CSVST_DOCUMENTO_RESTRICCION
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO_RESTRICCION" ADD PRIMARY KEY ("ID");
  ALTER TABLE "CSVST_DOCUMENTO_RESTRICCION" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CSVST_DOCUMENTO_TIPO_ID
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO_TIPO_ID" ADD PRIMARY KEY ("ID");
  ALTER TABLE "CSVST_DOCUMENTO_TIPO_ID" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CSVST_OBJETO_CMIS
--------------------------------------------------------

  ALTER TABLE "CSVST_OBJETO_CMIS" MODIFY ("TIPO_PADRE" NOT NULL ENABLE);
  ALTER TABLE "CSVST_OBJETO_CMIS" MODIFY ("TIPO" NOT NULL ENABLE);
  ALTER TABLE "CSVST_OBJETO_CMIS" MODIFY ("FICHERO" NOT NULL ENABLE);
  ALTER TABLE "CSVST_OBJETO_CMIS" MODIFY ("UUID" NOT NULL ENABLE);
  ALTER TABLE "CSVST_OBJETO_CMIS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "CSVST_OBJETO_CMIS" ADD CONSTRAINT "PF_CSVST_OBJETO_CMIS" PRIMARY KEY ("ID");
  ALTER TABLE "CSVST_OBJETO_CMIS" ADD CONSTRAINT "CSVST_OBJETO_CMIS_IX1" UNIQUE ("UUID");
--------------------------------------------------------
--  Constraints for Table CSVST_PROP_ADIC_OBJETO_CMIS
--------------------------------------------------------

  ALTER TABLE "CSVST_PROP_ADIC_OBJETO_CMIS" ADD PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table CSVST_PROP_ADIC_TIPO_CMIS
--------------------------------------------------------

  ALTER TABLE "CSVST_PROP_ADIC_TIPO_CMIS" ADD PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table CSVST_RESTRICCION
--------------------------------------------------------

  ALTER TABLE "CSVST_RESTRICCION" ADD PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table CSVST_TIPO_CMIS
--------------------------------------------------------

  ALTER TABLE "CSVST_TIPO_CMIS" ADD PRIMARY KEY ("ID");
  ALTER TABLE "CSVST_TIPO_CMIS" ADD CONSTRAINT "CSVST_TIPO_CMIS_UK1" UNIQUE ("ID_TIPO", "VERSION");
--------------------------------------------------------
--  Constraints for Table CSVST_TIPO_IDENTIFICACION
--------------------------------------------------------

  ALTER TABLE "CSVST_TIPO_IDENTIFICACION" ADD PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table CSVST_TIPO_PERMISO_DOCUMENTO
--------------------------------------------------------

  ALTER TABLE "CSVST_TIPO_PERMISO_DOCUMENTO" ADD PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table CSVST_UNIDAD_ORGANICA
--------------------------------------------------------

  ALTER TABLE "CSVST_UNIDAD_ORGANICA" MODIFY ("UNIDAD_ORGANICA" NOT NULL ENABLE);
  ALTER TABLE "CSVST_UNIDAD_ORGANICA" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "CSVST_UNIDAD_ORGANICA" ADD CONSTRAINT "PK_CSVST_UNIDAD" PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table CSVST_USUARIOS
--------------------------------------------------------

  ALTER TABLE "CSVST_USUARIOS" MODIFY ("ADMINISTRADOR" NOT NULL ENABLE);
  ALTER TABLE "CSVST_USUARIOS" MODIFY ("PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "CSVST_USUARIOS" MODIFY ("USUARIO" NOT NULL ENABLE);
  ALTER TABLE "CSVST_USUARIOS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "CSVST_USUARIOS" ADD CONSTRAINT "CSVST_USER_PK" PRIMARY KEY ("ID");
--------------------------------------------------------
--  Constraints for Table QRTZ_BLOB_TRIGGERS
--------------------------------------------------------

  ALTER TABLE "QRTZ_BLOB_TRIGGERS" MODIFY ("TRIGGER_GROUP" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_BLOB_TRIGGERS" MODIFY ("TRIGGER_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_BLOB_TRIGGERS" MODIFY ("SCHED_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_BLOB_TRIGGERS" ADD CONSTRAINT "QRTZ_BLOB_TRIG_PK" PRIMARY KEY ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP");
--------------------------------------------------------
--  Constraints for Table QRTZ_CALENDARS
--------------------------------------------------------

  ALTER TABLE "QRTZ_CALENDARS" MODIFY ("CALENDAR" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_CALENDARS" MODIFY ("CALENDAR_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_CALENDARS" MODIFY ("SCHED_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_CALENDARS" ADD CONSTRAINT "QRTZ_CALENDARS_PK" PRIMARY KEY ("SCHED_NAME", "CALENDAR_NAME");
--------------------------------------------------------
--  Constraints for Table QRTZ_CRON_TRIGGERS
--------------------------------------------------------

  ALTER TABLE "QRTZ_CRON_TRIGGERS" MODIFY ("CRON_EXPRESSION" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_CRON_TRIGGERS" MODIFY ("TRIGGER_GROUP" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_CRON_TRIGGERS" MODIFY ("TRIGGER_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_CRON_TRIGGERS" MODIFY ("SCHED_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_CRON_TRIGGERS" ADD CONSTRAINT "QRTZ_CRON_TRIG_PK" PRIMARY KEY ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP");
--------------------------------------------------------
--  Constraints for Table QRTZ_FIRED_TRIGGERS
--------------------------------------------------------

  ALTER TABLE "QRTZ_FIRED_TRIGGERS" MODIFY ("STATE" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_FIRED_TRIGGERS" MODIFY ("PRIORITY" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_FIRED_TRIGGERS" MODIFY ("SCHED_TIME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_FIRED_TRIGGERS" MODIFY ("FIRED_TIME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_FIRED_TRIGGERS" MODIFY ("INSTANCE_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_FIRED_TRIGGERS" MODIFY ("TRIGGER_GROUP" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_FIRED_TRIGGERS" MODIFY ("TRIGGER_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_FIRED_TRIGGERS" MODIFY ("ENTRY_ID" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_FIRED_TRIGGERS" MODIFY ("SCHED_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_FIRED_TRIGGERS" ADD CONSTRAINT "QRTZ_FIRED_TRIGGER_PK" PRIMARY KEY ("SCHED_NAME", "ENTRY_ID");
--------------------------------------------------------
--  Constraints for Table QRTZ_JOB_DETAILS
--------------------------------------------------------

  ALTER TABLE "QRTZ_JOB_DETAILS" MODIFY ("REQUESTS_RECOVERY" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_JOB_DETAILS" MODIFY ("IS_UPDATE_DATA" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_JOB_DETAILS" MODIFY ("IS_NONCONCURRENT" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_JOB_DETAILS" MODIFY ("IS_DURABLE" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_JOB_DETAILS" MODIFY ("JOB_CLASS_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_JOB_DETAILS" MODIFY ("JOB_GROUP" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_JOB_DETAILS" MODIFY ("JOB_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_JOB_DETAILS" MODIFY ("SCHED_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_JOB_DETAILS" ADD CONSTRAINT "QRTZ_JOB_DETAILS_PK" PRIMARY KEY ("SCHED_NAME", "JOB_NAME", "JOB_GROUP");
--------------------------------------------------------
--  Constraints for Table QRTZ_LOCKS
--------------------------------------------------------

  ALTER TABLE "QRTZ_LOCKS" MODIFY ("LOCK_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_LOCKS" MODIFY ("SCHED_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_LOCKS" ADD CONSTRAINT "QRTZ_LOCKS_PK" PRIMARY KEY ("SCHED_NAME", "LOCK_NAME");
--------------------------------------------------------
--  Constraints for Table QRTZ_PAUSED_TRIGGER_GRPS
--------------------------------------------------------

  ALTER TABLE "QRTZ_PAUSED_TRIGGER_GRPS" MODIFY ("TRIGGER_GROUP" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_PAUSED_TRIGGER_GRPS" MODIFY ("SCHED_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_PAUSED_TRIGGER_GRPS" ADD CONSTRAINT "QRTZ_PAUSED_TRIG_GRPS_PK" PRIMARY KEY ("SCHED_NAME", "TRIGGER_GROUP");
--------------------------------------------------------
--  Constraints for Table QRTZ_SCHEDULER_STATE
--------------------------------------------------------

  ALTER TABLE "QRTZ_SCHEDULER_STATE" MODIFY ("CHECKIN_INTERVAL" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_SCHEDULER_STATE" MODIFY ("LAST_CHECKIN_TIME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_SCHEDULER_STATE" MODIFY ("INSTANCE_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_SCHEDULER_STATE" MODIFY ("SCHED_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_SCHEDULER_STATE" ADD CONSTRAINT "QRTZ_SCHEDULER_STATE_PK" PRIMARY KEY ("SCHED_NAME", "INSTANCE_NAME");
--------------------------------------------------------
--  Constraints for Table QRTZ_SIMPLE_TRIGGERS
--------------------------------------------------------

  ALTER TABLE "QRTZ_SIMPLE_TRIGGERS" MODIFY ("TIMES_TRIGGERED" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_SIMPLE_TRIGGERS" MODIFY ("REPEAT_INTERVAL" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_SIMPLE_TRIGGERS" MODIFY ("REPEAT_COUNT" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_SIMPLE_TRIGGERS" MODIFY ("TRIGGER_GROUP" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_SIMPLE_TRIGGERS" MODIFY ("TRIGGER_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_SIMPLE_TRIGGERS" MODIFY ("SCHED_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_SIMPLE_TRIGGERS" ADD CONSTRAINT "QRTZ_SIMPLE_TRIG_PK" PRIMARY KEY ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP");
--------------------------------------------------------
--  Constraints for Table QRTZ_SIMPROP_TRIGGERS
--------------------------------------------------------

  ALTER TABLE "QRTZ_SIMPROP_TRIGGERS" MODIFY ("TRIGGER_GROUP" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_SIMPROP_TRIGGERS" MODIFY ("TRIGGER_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_SIMPROP_TRIGGERS" MODIFY ("SCHED_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_SIMPROP_TRIGGERS" ADD CONSTRAINT "QRTZ_SIMPROP_TRIG_PK" PRIMARY KEY ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP");
--------------------------------------------------------
--  Constraints for Table QRTZ_TRIGGERS
--------------------------------------------------------

  ALTER TABLE "QRTZ_TRIGGERS" MODIFY ("START_TIME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_TRIGGERS" MODIFY ("TRIGGER_TYPE" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_TRIGGERS" MODIFY ("TRIGGER_STATE" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_TRIGGERS" MODIFY ("JOB_GROUP" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_TRIGGERS" MODIFY ("JOB_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_TRIGGERS" MODIFY ("TRIGGER_GROUP" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_TRIGGERS" MODIFY ("TRIGGER_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_TRIGGERS" MODIFY ("SCHED_NAME" NOT NULL ENABLE);
  ALTER TABLE "QRTZ_TRIGGERS" ADD CONSTRAINT "QRTZ_TRIGGERS_PK" PRIMARY KEY ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP");
--------------------------------------------------------
--  Ref Constraints for Table CSVST_APLICACIONES
--------------------------------------------------------

  ALTER TABLE "CSVST_APLICACIONES" ADD CONSTRAINT "CSVST_APLICACIONES_FK1" FOREIGN KEY ("CREATED_BY")
	  REFERENCES "CSVST_USUARIOS" ("ID") ENABLE;
  ALTER TABLE "CSVST_APLICACIONES" ADD CONSTRAINT "FK_ST_APLICACION_UNIDAD" FOREIGN KEY ("ID_UNIDAD")
	  REFERENCES "CSVST_UNIDAD_ORGANICA" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CSVST_AUDITORIA_CONSULTAS
--------------------------------------------------------

  ALTER TABLE "CSVST_AUDITORIA_CONSULTAS" ADD CONSTRAINT "ID_TIPO_IDEN_FK" FOREIGN KEY ("TIPO_IDENTIFICACION")
	  REFERENCES "CSVST_TIPO_IDENTIFICACION" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CSVST_AUDITORIA_PARAM
--------------------------------------------------------

  ALTER TABLE "CSVST_AUDITORIA_PARAM" ADD CONSTRAINT "FK_AUDITPARAMS_AUDIT" FOREIGN KEY ("ID_AUDITORIA")
	  REFERENCES "CSVST_AUDITORIA_CONSULTAS" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CSVST_DOCUMENTO
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO" ADD CONSTRAINT "CSVST_DOCUMENTO_APLI_FK" FOREIGN KEY ("CREATED_BY")
	  REFERENCES "CSVST_APLICACIONES" ("ID") ENABLE;
  ALTER TABLE "CSVST_DOCUMENTO" ADD CONSTRAINT "CSVST_DOCUMENTO_TIPO_PERM_FK" FOREIGN KEY ("TIPO_PERMISO")
	  REFERENCES "CSVST_TIPO_PERMISO_DOCUMENTO" ("ID") ENABLE;
  ALTER TABLE "CSVST_DOCUMENTO" ADD CONSTRAINT "PF_CSVST_DOCUMENTO_UNIDAD" FOREIGN KEY ("ID_UNIDAD_ORGANICA")
	  REFERENCES "CSVST_UNIDAD_ORGANICA" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CSVST_DOCUMENTO_APLICACION
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO_APLICACION" ADD CONSTRAINT "FK_DOCAPLICACION_APLICACION" FOREIGN KEY ("ID_APLICACION")
	  REFERENCES "CSVST_APLICACIONES" ("ID") ENABLE;
  ALTER TABLE "CSVST_DOCUMENTO_APLICACION" ADD CONSTRAINT "FK_DOCAPLICACION_DOCUMENTO" FOREIGN KEY ("ID_DOCUMENTO")
	  REFERENCES "CSVST_DOCUMENTO" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CSVST_DOCUMENTO_ENI
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO_ENI" ADD CONSTRAINT "PF_CSVST_DOCUMENTO_ENI_ID" FOREIGN KEY ("ID_DOCUMENTO")
	  REFERENCES "CSVST_DOCUMENTO" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CSVST_DOCUMENTO_ENI_ORGANO
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO_ENI_ORGANO" ADD CONSTRAINT "FK_DOCUMENTO_ENI_ORGANO_DOC" FOREIGN KEY ("ID_DOCUMENTO_ENI")
	  REFERENCES "CSVST_DOCUMENTO_ENI" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CSVST_DOCUMENTO_NIF
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO_NIF" ADD CONSTRAINT "ID_DOCUMENTO_NIF_FK" FOREIGN KEY ("ID_DOCUMENTO")
	  REFERENCES "CSVST_DOCUMENTO" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CSVST_DOCUMENTO_RESTRICCION
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO_RESTRICCION" ADD CONSTRAINT "FK_DOCRESTRIC_DOCUMENTO" FOREIGN KEY ("ID_DOCUMENTO")
	  REFERENCES "CSVST_DOCUMENTO" ("ID") ENABLE;
  ALTER TABLE "CSVST_DOCUMENTO_RESTRICCION" ADD CONSTRAINT "FK_DOCRESTRIC_RESTRICCION" FOREIGN KEY ("ID_RESTRICCION")
	  REFERENCES "CSVST_RESTRICCION" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CSVST_DOCUMENTO_TIPO_ID
--------------------------------------------------------

  ALTER TABLE "CSVST_DOCUMENTO_TIPO_ID" ADD CONSTRAINT "FK_DOCTIPOID_DOCUMENTO" FOREIGN KEY ("ID_DOCUMENTO")
	  REFERENCES "CSVST_DOCUMENTO" ("ID") ENABLE;
  ALTER TABLE "CSVST_DOCUMENTO_TIPO_ID" ADD CONSTRAINT "FK_DOCTIPOID_TIPOID" FOREIGN KEY ("ID_TIPO_IDENTIFICACION")
	  REFERENCES "CSVST_TIPO_IDENTIFICACION" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CSVST_PROP_ADIC_OBJETO_CMIS
--------------------------------------------------------

  ALTER TABLE "CSVST_PROP_ADIC_OBJETO_CMIS" ADD CONSTRAINT "CSVBR_PROP_DOC_PROP_FK" FOREIGN KEY ("ID_PROPIEDAD")
	  REFERENCES "CSVST_PROP_ADIC_TIPO_CMIS" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CSVST_PROP_ADIC_TIPO_CMIS
--------------------------------------------------------

  ALTER TABLE "CSVST_PROP_ADIC_TIPO_CMIS" ADD CONSTRAINT "CSVBR_PROP_TIPO_CMIS_FK" FOREIGN KEY ("ID_TIPO")
	  REFERENCES "CSVST_TIPO_CMIS" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table QRTZ_BLOB_TRIGGERS
--------------------------------------------------------

  ALTER TABLE "QRTZ_BLOB_TRIGGERS" ADD CONSTRAINT "QRTZ_BLOB_TRIG_TO_TRIG_FK" FOREIGN KEY ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP")
	  REFERENCES "QRTZ_TRIGGERS" ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table QRTZ_CRON_TRIGGERS
--------------------------------------------------------

  ALTER TABLE "QRTZ_CRON_TRIGGERS" ADD CONSTRAINT "QRTZ_CRON_TRIG_TO_TRIG_FK" FOREIGN KEY ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP")
	  REFERENCES "QRTZ_TRIGGERS" ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table QRTZ_SIMPLE_TRIGGERS
--------------------------------------------------------

  ALTER TABLE "QRTZ_SIMPLE_TRIGGERS" ADD CONSTRAINT "QRTZ_SIMPLE_TRIG_TO_TRIG_FK" FOREIGN KEY ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP")
	  REFERENCES "QRTZ_TRIGGERS" ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table QRTZ_SIMPROP_TRIGGERS
--------------------------------------------------------

  ALTER TABLE "QRTZ_SIMPROP_TRIGGERS" ADD CONSTRAINT "QRTZ_SIMPROP_TRIG_TO_TRIG_FK" FOREIGN KEY ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP")
	  REFERENCES "QRTZ_TRIGGERS" ("SCHED_NAME", "TRIGGER_NAME", "TRIGGER_GROUP") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table QRTZ_TRIGGERS
--------------------------------------------------------

  ALTER TABLE "QRTZ_TRIGGERS" ADD CONSTRAINT "QRTZ_TRIGGER_TO_JOBS_FK" FOREIGN KEY ("SCHED_NAME", "JOB_NAME", "JOB_GROUP")
	  REFERENCES "QRTZ_JOB_DETAILS" ("SCHED_NAME", "JOB_NAME", "JOB_GROUP") ENABLE;
--------------------------------------------------------
--  Usuario administrador: admin/admin
--------------------------------------------------------
Insert into CSVST_USUARIOS (ID,USUARIO,PASSWORD,ADMINISTRADOR) values ('1','admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','1');
