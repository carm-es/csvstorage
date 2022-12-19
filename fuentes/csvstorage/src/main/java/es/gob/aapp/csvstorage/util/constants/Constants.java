/*
 * Copyright (C) 2012-13 MINHAP, Gobierno de España This program is licensed and may be used,
 * modified and redistributed under the terms of the European Public License (EUPL), either version
 * 1.1 or (at your option) any later version as soon as they are approved by the European
 * Commission. Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * more details. You should have received a copy of the EUPL1.1 license along with this program; if
 * not, you may find it at http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 */

package es.gob.aapp.csvstorage.util.constants;

import java.nio.charset.Charset;

/**
 * Clase de constantes de la aplicacion.
 * 
 * @author carlos.munoz1
 * 
 */
public class Constants {

  /** Formato ficheros ZIP. */
  public static final String FORMAT_ZIP = ".zip";

  /** Separardor de datos para toString(). */
  public static final String SEPARADOR_DATOS = ",";

  /** Formato de fecha del dir3. */
  public static final String FORMATO_FECHA = "dd/MM/yyyy";

  /**
   * r Niveles para mostrar mensajes por pantalla a los usuarios.
   */
  public static final int MESSAGE_LEVEL_SUCCESS = 1;
  public static final int MESSAGE_LEVEL_INFO = 2;
  public static final int MESSAGE_LEVEL_WARNING = 3;
  public static final int MESSAGE_LEVEL_ERROR = 4;

  /** M�ximo n�mero de registros a devolver en autocomplete. */
  public static int MAX_RESULTS_AUTOCOMPLETE = 10;

  /**
   * C�digos de respuesta del servicio saveDocument.
   */
  public static final int SAVE_DOCUMENT_SUCCESS = 0;
  public static final int SAVE_DOCUMENT_ALREADY_EXIST = 1;
  public static final int SAVE_DOCUMENT_ERROR = -10;
  public static final int SAVE_DOCUMENTENI_VAL_ERROR = 3;

  /**
   * Descripciones de respuesta del servicio saveDocument.
   */
  public static final String SAVE_DOCUMENT_SUCCESS_DESC = "save.document.success";
  public static final String SAVE_DOCUMENT_ALREADY_EXIST_DESC = "save.document.already.exist";
  public static final String SAVE_DOCUMENT_ERROR_DESC = "save.document.error";
  public static final String SAVE_DOCUMENT_ERROR_MIMETYPE = "save.document.mimetype";
  public static final String SAVE_DOCUMENTENI_VAL_ERROR_DESC = "save.documentENI.validation.error";
  public static final String SAVE_DOCUMENT_ERROR_MIMETYPE_PDF_CSV =
      "save.document.mimetype.pdf.csv";
  public static final String SAVE_DOCUMENTENI_ERROR_ID_ORIGIN_DESC =
      "save.documentENI.idorigin.error";
  public static final String SAVE_DOCUMENT_ERROR_PDF_O_ENI = "save.document.error.pdfOEni";
  public static final String SAVE_DOCUMENT_RESTRICTION_APP_SUCCESS_DESC =
      "save.document.restriction.app.success";
  public static final String SAVE_DOCUMENT_REVOKE_APP_DOC_NOT_EXISTS_DESC =
      "save.document.revoke.app.doc.not.exists";
  public static final String SAVE_DOCUMENT_REVOKE_APP_NOT_PERMISSION_DESC =
      "save.document.revoke.app.not.permission";
  public static final String SAVE_DOCUMENT_REVOKE_APP_SUCCESS_DESC =
      "save.document.revoke.app.success";

  /**
   * C�digos de respuesta del servicio deleteDocument.
   */
  public static final int DELETE_DOCUMENT_SUCCESS = 0;
  public static final int DELETE_DOCUMENT_NOT_EXIST = 1;
  public static final int DELETE_DOCUMENT_ERROR = -10;

  /**
   * Descripciones de respuesta del servicio deleteDocument.
   */
  public static final String DELETE_DOCUMENT_SUCCESS_DESC = "delete.document.success";
  public static final String DELETE_DOCUMENT_NOT_EXIST_DESC = "delete.document.not.exist";
  public static final String DELETE_DOCUMENT_NOT_PERMISSION_DESC = "delete.document.not.permission";
  public static final String DELETE_DOCUMENT_ERROR_DESC = "delete.document.error";
  public static final String DELETE_DOCUMENT_APP_RESTRICTION_DESC =
      "delete.document.application.restriction";

  /**
   * C�digos de respuesta del servicio queryDocument.
   */
  public static final int QUERY_DOCUMENT_EXIST = 0;
  public static final int QUERY_DOCUMENT_NOT_EXIST = 1;
  public static final int QUERY_DOCUMENT_NOT_PERMISSION = 2;
  public static final int QUERY_DOCUMENT_ERROR = -10;


  /**
   * Descripciones de respuesta del servicio queryDocument.
   */
  public static final String QUERY_DOCUMENT_EXIST_DESC = "query.document.exist";
  public static final String QUERY_DOCUMENT_NOT_EXIST_DESC = "query.document.not.exist";
  public static final String QUERY_DOCUMENT_ERROR_DESC = "query.document.error";

  public static final String MODIFY_DOCUMENT_ERROR_EXIST_DESC = "modify.document.error.exist";
  public static final int MODIFY_DOCUMENT_ERROR_EXIST = 4;

  public static final String DOCUMENT_NOT_PERMISSION_DESC = "document.not.permission";

  public static final int CONVERT_DOCUMENT_ENI_SUCCESS = 0;
  public static final int CONVERT_DOCUMENT_ENI_ALREADY_EXIST = 1;
  public static final int CONVERT_DOCUMENT_ENI_INVALID = 2;


  public static final String CONVERT_DOCUMENT_ENI_SUCCESS_DESC = "convert.documentEni.success";
  public static final String CONVERT_DOCUMENT_ENI_ALREADY_EXIST_DESC =
      "convert.documentEni.already.exist";
  public static final String CONVERT_DOCUMENT_ENI_ALREADY_EXIST_ID_ENI_DESC =
      "convert.documentEni.already.exist.idEni";
  public static final String CONVERT_DOCUMENT_ENI_ERROR_DESC = "convert.documentEni.error";
  public static final String CONVERT_DOCUMENT_ENI_ERROR_ID_INVALID_DESC =
      "convert.documentEni.error.idInvalid";
  public static final String CONVERT_DOCUMENT_ENI_ERROR_CSV_INVALID_DESC =
      "convert.documentEni.error.csvInvalid";


  // Mensajes para el CSVQueryDocument
  public static final int VALIDATION_OK = 0;
  public static final int WAIT_RESPONSE = 1;
  public static final int CSV_NOT_FOUND = 2;
  public static final int UNIT_LIST_RESPONSE = 3;
  public static final int INTERNAL_SERVICE_ERROR = 500;

  public static final String VALIDATION_OK_DESC =
      "La operaci\u00F3n se ha realizado con \u00E9xito.";
  public static final String WAIT_RESPONSE_DESC =
      "El documento no puede recuperarse. Puede consultarse pasado un tiempo.";
  public static final String CSV_NOT_FOUND_DESC = "CSV no encontrado.";
  public static final String UNIT_LIST_RESPONSE_DESC =
      "Se devuelve una lista de organismos que pueden contener el documento.";
  public static final String INTERNAL_SERVICE_ERROR_DESC =
      "Ha ocurrido un error interno en la aplicaci\u00F3n. ";

  /**
   * Otros mensajes de error
   */

  public static final int MANDATORY_FIELDS = 2;
  public static final String MANDATORY_FIELDS_DESC = "mandatory.fields";

  public static final int MANDATORY_FIELD_DIR3 = 2;
  public static final String MANDATORY_FIELD_DIR3_DESC = "empty.fields.dir3";

  public static final int EMPTY_FIELDS_CSV_IDENI = 2;
  public static final String EMPTY_FIELDS_CSV_IDENI_DESC = "empty.fields.csv.ideni";

  public static final int RETURN_MULTIPLE_RESULTS = 2;
  public static final String RETURN_MULTIPLE_RESULTS_DESC = "return.multiple.results";

  public static final int ORGANIC_UNIT_NOT_EXIST = 4;
  public static final String ORGANIC_UNIT_NOT_EXIST_DESC = "organic.unit.not.exist";

  public static final int CONTENT_NOT_EXIST = 7;
  public static final String CONTENT_NOT_EXIST_DESC = "content.not.exist";

  public static final int NOT_SIGNED_DOCUMENT = 5;
  public static final String NOT_SIGNED_DOCUMENT_DESC = "not.signed.document";

  public static final int SCOPE_APPLICATION_ERROR = 6;
  public static final String SCOPE_APPLICATION_ERROR_DESC = "scope.application.error";

  public static final int EMPTY_NIFS_ERROR = 2;
  public static final String EMPTY_NIFS_ERROR_DESC = "empty.nifs.error";

  public static final int ERROR_NIFS_ERROR = 2;
  public static final String ERROR_NIFS_ERROR_DESC = "error.nifs.error";

  public static final int EMPTY_REST_ERROR = 2;
  public static final String EMPTY_REST_ERROR_DESC = "empty.rest.error";
  public static final String EMPTY_IDS_ERROR_DESC = "empty.ids.error";

  public static final int EMPTY_FIELDS_APPLICATIONS_QUERY = 2;
  public static final String EMPTY_FIELDS_APPLICATIONS_QUERY_DES = "empty.fields.applications.list";

  public static final int NOT_EXISTS_APPLICATIONS_QUERY = 8;
  public static final String NOT_EXISTS_APPLICATIONS_QUERY_DES = "not_exist_applications_list";

  public static final String MALFORMED_RESTRICTION_NIF = "malformed.restriction.by.nif";
  public static final String MALFORMED_RESTRICTION_ID = "malformed.restriction.by.id";
  public static final String MALFORMED_RESTRICTION_APP = "malformed.restriction.by.app";

  /**
   * Mensajes del guardado de Aplicaciones
   */

  public static final String GUARDADO_SUCCESFULLY = "save.app.success";
  public static final String PERMISOS_REJECT = "permission.app.reject";
  public static final String DELETE_SUCCESFULLY = "delete.app.success";
  public static final String DELETE_APP_NOTEXIST = "delete.app.notexist";


  /**
   * Nombre de la tarea de eliminaci�n de documentos
   */
  public static final String JOB_DELETE_DOCUMENTS = "DELETE_DOCUMENTS";
  public static final String JOB_DELETE_DOCUMENTS_CRON_EXPRESSION =
      "job.delete.documents.cron.expression";

  public static String WS_FORMATO_FECHA = FORMATO_FECHA;
  public static String LINE_SEPARATOR = System.getProperty("line.separator");
  public static String FILE_SEPARATOR = System.getProperty("file.separator");

  // etiquetas
  public static String ETIQUETA_ORGANISMO = "Organismo";
  public static String ETIQUETA_DATOS_IDENTIFICATIVOS = "Datos_Identificativos";
  public static String ETIQUETA_DATOS_DEPENDENCIA = "Datos_Dependencia";
  public static String ETIQUETA_DATOS_VIGENCIA = "Datos_Vigencia";
  public static String ETIQUETA_CODIGO_UNIDAD_ORGANICA = "Codigo_Unidad_Organica";
  public static String ETIQUETA_NOMBRE_UNIDAD_ORGANICA = "Nombre_Unidad_Organica";
  public static String ETIQUETA_NIVEL_ADMINISTRACION = "Nivel_Administracion";
  public static String ETIQUETA_INDICADOR_ENTIDAD_DERECHO_PUBLICO =
      "Indicador_Entidad_Derecho_Publico";
  public static String ETIQUETA_CODIGO_EXTERNO = "Codigo_Externo";
  public static String ETIQUETA_CODIGO_UNIDAD_SUPERIOR_JERARQUICA =
      "Codigo_Unidad_Superior_Jerarquica";
  public static String ETIQUETA_DENOMINACION_UNIDAD_SUPERIOR_JERARQUICA =
      "Denominacion_Unidad_Superior_Jerarquica";
  public static String ETIQUETA_CODIGO_UNIDAD_ORGANICA_RAIZ = "Codigo_Unidad_Organica_Raiz";
  public static String ETIQUETA_DENOMINACION_UNIDAD_ORGANICA_RAIZ =
      "Denominacion_Unidad_Organica_Raiz";
  public static String ETIQUETA_CODIGO_UNIDAD_RAIZ_ENTIDAD_DERECHO_PUBLICO =
      "Codigo_Unidad_Raiz_Entidad_Derecho_Publico";
  public static String ETIQUETA_DENOMINACION_UNIDAD_RAIZ_ENTIDAD_DERECHO_PUBLICO =
      "Denominacion_Unidad_Raiz_Entidad_Derecho_Publico";
  public static String ETIQUETA_NIVEL_JERARQUICO = "Nivel_Jerarquico";
  public static String ETIQUETA_ESTADO = "Estado";
  public static String ETIQUETA_FECHA_ALTA = "Fecha_Alta";
  public static String ETIQUETA_FECHA_BAJA = "Fecha_Baja";
  public static String ETIQUETA_FECHA_ANULACION = "Fecha_Anulacion";
  public static String ETIQUETA_FECHA_EXTINCION = "Fecha_Extincion";

  public static String FIRMAS = "_FIRMAS";

  public static class ConstantesDir3Consumer {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String FIELD_SEPARATOR = ",";
    public static final String ETIQUETA_ORGANISMO = "Organismo";
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String RUTA_CONFIG_LOG = "log4j2.xml";


    // etiquetas
    public static final String ETIQUETA_DATOS_IDENTIFICATIVOS = "Datos_Identificativos";
    public static final String ETIQUETA_DATOS_DEPENDENCIA = "Datos_Dependencia";
    public static final String ETIQUETA_DATOS_VIGENCIA = "Datos_Vigencia";
    public static final String ETIQUETA_CODIGO_UNIDAD_ORGANICA = "Codigo_Unidad_Organica";
    public static final String ETIQUETA_NOMBRE_UNIDAD_ORGANICA = "Nombre_Unidad_Organica";
    public static final String ETIQUETA_NIVEL_ADMINISTRACION = "Nivel_Administracion";
    public static final String ETIQUETA_INDICADOR_ENTIDAD_DERECHO_PUBLICO =
        "Indicador_Entidad_Derecho_Publico";
    public static final String ETIQUETA_CODIGO_EXTERNO = "Codigo_Externo";
    public static final String ETIQUETA_CODIGO_UNIDAD_SUPERIOR_JERARQUICA =
        "Codigo_Unidad_Superior_Jerarquica";
    public static final String ETIQUETA_DENOMINACION_UNIDAD_SUPERIOR_JERARQUICA =
        "Denominacion_Unidad_Superior_Jerarquica";
    public static final String ETIQUETA_CODIGO_UNIDAD_ORGANICA_RAIZ = "Codigo_Unidad_Organica_Raiz";
    public static final String ETIQUETA_DENOMINACION_UNIDAD_ORGANICA_RAIZ =
        "Denominacion_Unidad_Organica_Raiz";
    public static final String ETIQUETA_CODIGO_UNIDAD_RAIZ_ENTIDAD_DERECHO_PUBLICO =
        "Codigo_Unidad_Raiz_Entidad_Derecho_Publico";
    public static final String ETIQUETA_DENOMINACION_UNIDAD_RAIZ_ENTIDAD_DERECHO_PUBLICO =
        "Denominacion_Unidad_Raiz_Entidad_Derecho_Publico";
    public static final String ETIQUETA_NIVEL_JERARQUICO = "Nivel_Jerarquico";
    public static final String ETIQUETA_ESTADO = "Estado";
    public static final String ETIQUETA_FECHA_ALTA = "Fecha_Alta";
    public static final String ETIQUETA_FECHA_BAJA = "Fecha_Baja";
    public static final String ETIQUETA_FECHA_ANULACION = "Fecha_Anulacion";
    public static final String ETIQUETA_FECHA_EXTINCION = "Fecha_Extincion";

    public static final String VALOR_SI = "S";
    public static final String VALOR_NO = "N";
    public static final String FORMATO_FECHA = "yyyy-MM-dd";

    public static final String WS_FORMATO_FECHA = "dd/MM/yyyy";

  }

  public static class UnidadOrganica {

    public static String ETIQUETA_DATOS_IDENTIFICATIVOS = "Datos_Identificativos";
    public static String ETIQUETA_DATOS_DEPENDENCIA = "Datos_Dependencia";
    public static String ETIQUETA_DATOS_VIGENCIA = "Datos_Vigencia";
    public static String ETIQUETA_CODIGO_UNIDAD_ORGANICA = "Codigo_Unidad_Organica";
    public static String ETIQUETA_NOMBRE_UNIDAD_ORGANICA = "Nombre_Unidad_Organica";
    public static String ETIQUETA_NIVEL_ADMINISTRACION = "Nivel_Administracion";
    public static String ETIQUETA_INDICADOR_ENTIDAD_DERECHO_PUBLICO =
        "Indicador_Entidad_Derecho_Publico";
    public static String ETIQUETA_CODIGO_EXTERNO = "Codigo_Externo";
    public static String ETIQUETA_CODIGO_UNIDAD_SUPERIOR_JERARQUICA =
        "Codigo_Unidad_Superior_Jerarquica";
    public static String ETIQUETA_DENOMINACION_UNIDAD_SUPERIOR_JERARQUICA =
        "Denominacion_Unidad_Superior_Jerarquica";
    public static String ETIQUETA_CODIGO_UNIDAD_ORGANICA_RAIZ = "Codigo_Unidad_Organica_Raiz";
    public static String ETIQUETA_DENOMINACION_UNIDAD_ORGANICA_RAIZ =
        "Denominacion_Unidad_Organica_Raiz";
    public static String ETIQUETA_CODIGO_UNIDAD_RAIZ_ENTIDAD_DERECHO_PUBLICO =
        "Codigo_Unidad_Raiz_Entidad_Derecho_Publico";
    public static String ETIQUETA_DENOMINACION_UNIDAD_RAIZ_ENTIDAD_DERECHO_PUBLICO =
        "Denominacion_Unidad_Raiz_Entidad_Derecho_Publico";
    public static String ETIQUETA_NIVEL_JERARQUICO = "Nivel_Jerarquico";
    public static String ETIQUETA_ESTADO = "Estado";
    public static String ETIQUETA_FECHA_ALTA = "Fecha_Alta";
    public static String ETIQUETA_FECHA_BAJA = "Fecha_Baja";
    public static String ETIQUETA_FECHA_ANULACION = "Fecha_Anulacion";
    public static String ETIQUETA_FECHA_EXTINCION = "Fecha_Extincion";

    public static String LINE_SEPARATOR = System.getProperty("line.separator");
    public static String FIELD_SEPARATOR = ",";
    public static String ETIQUETA_ORGANISMO = "Organismo";
    public static String FILE_SEPARATOR = System.getProperty("file.separator");
    public static String RUTA_CONFIG_LOG = "log4j2.xml";

    public static String VALOR_SI = "S";
    public static String VALOR_NO = "N";
    public static String FORMATO_FECHA = "yyyy-MM-dd";

    public static String WS_FORMATO_FECHA = "dd/MM/yyyy";


  }

  public static class CMIS {
    public static final String PROPERTY_DIR3 = "csvstorage:dir3";
    public static final String PROPERTY_ID_ENI = "csvstorage:id_eni";
    public static final String PROPERTY_CSV = "csvstorage:csv";
  }

  public static final String REGULACION_CSV = "BOE-A-2014-3729";

  public static final String EEUTIL_CODIGO_000 = ".000]";

  public static final String GUION = "-";

  public static final String MIME_TYPE_PDF = "application/pdf";
  public static final String[] MIME_TYPE_ENI = {"application/xml", "text/xml"};
  public static final String[] MIME_TYPE_ENI_PDF = {"application/xml", "text/xml", MIME_TYPE_PDF};

  public static final String EXTENSION_BACKUP = "_backup";

  public static final String SCAN_RESULT_FOLDER = "SCAN_RESULT";
  public static final String BIG_FILE_NAME = "_BF";
  public static final String REF_FILE = "ref:";

  public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

  /**
   * Se especifica el origen de las consultas para obtener un documento
   */
  public static final String CALLED_FROM_DOCUMENT_SERVICE = "DocumentService";
  public static final String CALLED_FROM_QUERY_SERVICE = "QueryDocumnetService";
  public static final String CALLED_FROM_QUERY_SECURITY_SERVICE = "QueryDocumnetSecurityService";
  public static final String CALLED_FROM_GUARDAR_DOCUMENTO = "guardarDocumento";
  public static final String CALLED_FROM_GUARDAR_DOCUMENTO_ENI = "guardarDocumentoEni";
  public static final String CALLED_FROM_CONVERTIR_A_ENI = "convertirDocumentoAEni";

  /**
   * Carácteres separadores para generar el listado con los parámetros de auditoría
   */
  public static final String AUDIT_SEPARATOR_ROW = "$$";
  public static final String AUDIT_SEPARATOR_VALUE = "¿¿";
}
