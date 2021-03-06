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

package es.gob.aapp.csvstorage.client.ws.eeutil.misc;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ObjectFactory;

/**
 * This class was generated by Apache CXF 3.0.1 2017-01-26T09:25:11.819+01:00 Generated source
 * version: 3.0.1
 * 
 */
@WebService(targetNamespace = "http://service.ws.inside.dsic.mpt.es/",
    name = "EeUtilValidacionENIServiceMtom")
@XmlSeeAlso({ObjectFactory.class})
public interface EeUtilValidacionENIServiceMtom {

  @WebResult(name = "Resultado", targetNamespace = "")
  @RequestWrapper(localName = "validarFirmaExpedienteENI",
      targetNamespace = "http://service.ws.inside.dsic.mpt.es/",
      className = "es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ValidarFirmaExpedienteENI")
  @WebMethod(action = "urn:validarFirmaExpedienteENI")
  @ResponseWrapper(localName = "validarFirmaExpedienteENIResponse",
      targetNamespace = "http://service.ws.inside.dsic.mpt.es/",
      className = "es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ValidarFirmaExpedienteENIResponse")
  public es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.RespuestaValidacionENI validarFirmaExpedienteENI(
      @WebParam(name = "aplicacionInfo",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ApplicationLogin aplicacionInfo,
      @WebParam(name = "DocumentoEntradaMtom",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.DocumentoEntradaMtom documentoEntradaMtom);

  @WebResult(name = "Resultado", targetNamespace = "")
  @RequestWrapper(localName = "validarExpedienteENI",
      targetNamespace = "http://service.ws.inside.dsic.mpt.es/",
      className = "es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ValidarExpedienteENI")
  @WebMethod(action = "urn:validarExpedienteENI")
  @ResponseWrapper(localName = "validarExpedienteENIResponse",
      targetNamespace = "http://service.ws.inside.dsic.mpt.es/",
      className = "es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ValidarExpedienteENIResponse")
  public es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.RespuestaValidacionENI validarExpedienteENI(
      @WebParam(name = "aplicacionInfo",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ApplicationLogin aplicacionInfo,
      @WebParam(name = "DocumentoEntradaMtom",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.DocumentoEntradaMtom documentoEntradaMtom,
      @WebParam(name = "versionENI", targetNamespace = "") java.lang.String versionENI,
      @WebParam(name = "validaciones",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.Validaciones validaciones);

  @WebResult(name = "Resultado", targetNamespace = "")
  @RequestWrapper(localName = "validarExpedienteDocumentosENIZIP",
      targetNamespace = "http://service.ws.inside.dsic.mpt.es/",
      className = "es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ValidarExpedienteDocumentosENIZIP")
  @WebMethod(action = "urn:validarExpedienteDocumentosENIZIP")
  @ResponseWrapper(localName = "validarExpedienteDocumentosENIZIPResponse",
      targetNamespace = "http://service.ws.inside.dsic.mpt.es/",
      className = "es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ValidarExpedienteDocumentosENIZIPResponse")
  public es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.RespuestaValidacionENI validarExpedienteDocumentosENIZIP(
      @WebParam(name = "aplicacionInfo",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ApplicationLogin aplicacionInfo,
      @WebParam(name = "expedienteENIConDocumentosENI",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.DocumentoEntradaMtom expedienteENIConDocumentosENI,
      @WebParam(name = "versionENI", targetNamespace = "") java.lang.String versionENI,
      @WebParam(name = "validacionesExpediente",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.Validaciones validacionesExpediente,
      @WebParam(name = "validacionesDocumentos",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.Validaciones validacionesDocumentos);

  @WebResult(name = "Resultado", targetNamespace = "")
  @RequestWrapper(localName = "validarDocumentoENI",
      targetNamespace = "http://service.ws.inside.dsic.mpt.es/",
      className = "es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ValidarDocumentoENI")
  @WebMethod(action = "urn:validarDocumentoENI")
  @ResponseWrapper(localName = "validarDocumentoENIResponse",
      targetNamespace = "http://service.ws.inside.dsic.mpt.es/",
      className = "es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ValidarDocumentoENIResponse")
  public es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.RespuestaValidacionENI validarDocumentoENI(
      @WebParam(name = "aplicacionInfo",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ApplicationLogin aplicacionInfo,
      @WebParam(name = "DocumentoEntradaMtom",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.DocumentoEntradaMtom documentoEntradaMtom,
      @WebParam(name = "versionENI", targetNamespace = "") java.lang.String versionENI,
      @WebParam(name = "validaciones",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.Validaciones validaciones);

  @WebResult(name = "Resultado", targetNamespace = "")
  @RequestWrapper(localName = "validarFirmaDocumentoENI",
      targetNamespace = "http://service.ws.inside.dsic.mpt.es/",
      className = "es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ValidarFirmaDocumentoENI")
  @WebMethod(action = "urn:validarFirmaDocumentoENI")
  @ResponseWrapper(localName = "validarFirmaDocumentoENIResponse",
      targetNamespace = "http://service.ws.inside.dsic.mpt.es/",
      className = "es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ValidarFirmaDocumentoENIResponse")
  public es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.RespuestaValidacionENI validarFirmaDocumentoENI(
      @WebParam(name = "aplicacionInfo",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ApplicationLogin aplicacionInfo,
      @WebParam(name = "DocumentoEntradaMtom",
          targetNamespace = "") es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.DocumentoEntradaMtom documentoEntradaMtom);
}
