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

package es.gob.aapp.csvstorage.client.ws.ginside;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.0.1 2017-01-25T16:10:19.300+01:00 Generated source
 * version: 3.0.1
 * 
 */
@WebService(targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
    name = "GInsideUserTokenMtomWebService")
@XmlSeeAlso({
    es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.documentoenifile.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.expedientee.resultados.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.eni.documentoe.contenido.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.eni.documentoe.documentoe.mtom.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.expedientee.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.eni.expedientee.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.eni.documentoe.metadatos.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.eni.expedientee.indicee.contenido.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.eni.expedientee.indicee.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.eni.firma.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.mtom.file.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.conversion.mtom.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.eni.expedientee.mtom.ObjectFactory.class, ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.documentoe.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.expedientee.conversion.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.expedientee.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.eni.documentoe.contenido.mtom.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.documentoe.mtom.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.eni.expedientee.metadatos.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.visualizacion.documentoe.mtom.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.expedientee.expedienteenifile.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.eni.credential.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.eni.documentoe.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.documentoe.resultados.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.expedientee.mtom.file.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.expedientee.mtom.ObjectFactory.class,
    es.gob.aapp.csvstorage.client.ws.ginside.model.metadatosadicionales.ObjectFactory.class})
public interface GInsideUserTokenMtomWebService {

  @WebResult(name = "expediente", targetNamespace = "")
  @RequestWrapper(localName = "convertirExpedienteAEniAutocontenido",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ConvertirExpedienteAEniAutocontenido")
  @WebMethod(action = "urn:convertirExpedienteAEniAutocontenido")
  @ResponseWrapper(localName = "convertirExpedienteAEniAutocontenidoResponse",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ConvertirExpedienteAEniAutocontenidoResponse")
  public es.gob.aapp.csvstorage.client.ws.ginside.model.expedientee.mtom.file.ExpedienteEniFileInsideMtom convertirExpedienteAEniAutocontenido(
      @WebParam(name = "expediente",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.ginside.model.expedientee.conversion.TipoExpedienteConversionInsideWSMtom expediente,
      @WebParam(name = "contenidoFirma", targetNamespace = "") java.lang.String contenidoFirma)
      throws InsideWSException;

  @WebResult(name = "expediente", targetNamespace = "")
  @RequestWrapper(localName = "convertirExpedienteAEniConMAdicionalesAutocontenido",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ConvertirExpedienteAEniConMAdicionalesAutocontenido")
  @WebMethod(action = "urn:convertirExpedienteAEniConMAdicionalesAutocontenido")
  @ResponseWrapper(localName = "convertirExpedienteAEniConMAdicionalesAutocontenidoResponse",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ConvertirExpedienteAEniConMAdicionalesAutocontenidoResponse")
  public es.gob.aapp.csvstorage.client.ws.ginside.model.expedientee.mtom.file.ExpedienteEniFileInsideConMAdicionalesMtom convertirExpedienteAEniConMAdicionalesAutocontenido(
      @WebParam(name = "expediente",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.ginside.model.expedientee.conversion.TipoExpedienteConversionInsideWSMtom expediente,
      @WebParam(name = "metadatosAdicionales",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.ginside.model.metadatosadicionales.TipoMetadatosAdicionales metadatosAdicionales,
      @WebParam(name = "contenidoFirma", targetNamespace = "") java.lang.String contenidoFirma)
      throws InsideWSException;

  @WebResult(name = "resultadoVisualizacion", targetNamespace = "")
  @RequestWrapper(localName = "visualizarDocumentoEni",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.VisualizarDocumentoEni")
  @WebMethod(action = "urn:visualizarDocumentoEni")
  @ResponseWrapper(localName = "visualizarDocumentoEniResponse",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.VisualizarDocumentoEniResponse")
  public es.gob.aapp.csvstorage.client.ws.ginside.model.visualizacion.documentoe.mtom.TipoResultadoVisualizacionDocumentoInsideMtom visualizarDocumentoEni(
      @WebParam(name = "documento",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.ginside.model.visualizacion.documentoe.mtom.TipoDocumentoVisualizacionInsideMtom documento)
      throws InsideWSException;

  @WebResult(name = "documento", targetNamespace = "")
  @RequestWrapper(localName = "convertirDocumentoAEni",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ConvertirDocumentoAEni")
  @WebMethod(action = "urn:convertirDocumentoAEni")
  @ResponseWrapper(localName = "convertirDocumentoAEniResponse",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ConvertirDocumentoAEniResponse")
  public es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.mtom.file.DocumentoEniFileInsideMtom convertirDocumentoAEni(
      @WebParam(name = "documento",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.conversion.mtom.TipoDocumentoConversionInsideMtom documento,
      @WebParam(name = "contenido", targetNamespace = "") byte[] contenido,
      @WebParam(name = "firmar", targetNamespace = "") java.lang.Boolean firmar)
      throws InsideWSException;

  @WebResult(name = "resultadoValidacion", targetNamespace = "")
  @RequestWrapper(localName = "validarDocumentoEniFile",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ValidarDocumentoEniFile")
  @WebMethod(action = "urn:validarDocumentoEniFile")
  @ResponseWrapper(localName = "validarDocumentoEniFileResponse",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ValidarDocumentoEniFileResponse")
  public es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.documentoe.resultados.TipoResultadoValidacionDocumentoInside validarDocumentoEniFile(
      @WebParam(name = "documento",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.documentoe.mtom.TipoDocumentoValidacionInsideMtom documento)
      throws InsideWSException;

  @WebResult(name = "expediente", targetNamespace = "")
  @RequestWrapper(localName = "convertirExpedienteAEni",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ConvertirExpedienteAEni")
  @WebMethod(action = "urn:convertirExpedienteAEni")
  @ResponseWrapper(localName = "convertirExpedienteAEniResponse",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ConvertirExpedienteAEniResponse")
  public es.gob.aapp.csvstorage.client.ws.ginside.model.expedientee.mtom.file.ExpedienteEniFileInsideMtom convertirExpedienteAEni(
      @WebParam(name = "expediente",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.ginside.model.expedientee.conversion.TipoExpedienteConversionInside expediente,
      @WebParam(name = "contenidoFirma", targetNamespace = "") java.lang.String contenidoFirma)
      throws InsideWSException;

  @WebResult(name = "documento", targetNamespace = "")
  @RequestWrapper(localName = "convertirDocumentoAEniConMAdicionales",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ConvertirDocumentoAEniConMAdicionales")
  @WebMethod(action = "urn:convertirDocumentoAEniConMAdicionales")
  @ResponseWrapper(localName = "convertirDocumentoAEniConMAdicionalesResponse",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ConvertirDocumentoAEniConMAdicionalesResponse")
  public es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.mtom.file.DocumentoEniFileInsideConMAdicionalesMtom convertirDocumentoAEniConMAdicionales(
      @WebParam(name = "documento",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.conversion.mtom.TipoDocumentoConversionInsideMtom documento,
      @WebParam(name = "metadatosAdicionales",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.ginside.model.metadatosadicionales.TipoMetadatosAdicionales metadatosAdicionales,
      @WebParam(name = "contenido", targetNamespace = "") byte[] contenido,
      @WebParam(name = "firmar", targetNamespace = "") java.lang.Boolean firmar)
      throws InsideWSException;

  @WebResult(name = "expediente", targetNamespace = "")
  @RequestWrapper(localName = "convertirExpedienteAEniConMAdicionales",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ConvertirExpedienteAEniConMAdicionales")
  @WebMethod(action = "urn:convertirExpedienteAEniConMAdicionales")
  @ResponseWrapper(localName = "convertirExpedienteAEniConMAdicionalesResponse",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ConvertirExpedienteAEniConMAdicionalesResponse")
  public es.gob.aapp.csvstorage.client.ws.ginside.model.expedientee.mtom.file.ExpedienteEniFileInsideConMAdicionalesMtom convertirExpedienteAEniConMAdicionales(
      @WebParam(name = "expediente",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.ginside.model.expedientee.conversion.TipoExpedienteConversionInside expediente,
      @WebParam(name = "metadatosAdicionales",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.ginside.model.metadatosadicionales.TipoMetadatosAdicionales metadatosAdicionales,
      @WebParam(name = "contenidoFirma", targetNamespace = "") java.lang.String contenidoFirma)
      throws InsideWSException;

  @WebResult(name = "resultadoValidacion", targetNamespace = "")
  @RequestWrapper(localName = "validarExpedienteEniFile",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ValidarExpedienteEniFile")
  @WebMethod(action = "urn:validarExpedienteEniFile")
  @ResponseWrapper(localName = "validarExpedienteEniFileResponse",
      targetNamespace = "https://ssweb.seap.minhap.es/Inside/XSD/v1.0/WebServiceFiles",
      className = "es.gob.aapp.csvstorage.client.ws.ginside.ValidarExpedienteEniFileResponse")
  public es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.expedientee.resultados.TipoResultadoValidacionExpedienteInside validarExpedienteEniFile(
      @WebParam(name = "expediente",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.expedientee.mtom.TipoExpedienteValidacionInsideMtom expediente)
      throws InsideWSException;
}
