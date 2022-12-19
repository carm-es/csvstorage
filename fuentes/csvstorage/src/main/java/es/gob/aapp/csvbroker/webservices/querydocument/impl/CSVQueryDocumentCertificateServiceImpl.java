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

package es.gob.aapp.csvbroker.webservices.querydocument.impl;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import es.gob.aapp.csvbroker.webservices.querydocument.CSVQueryDocumentCertificateService;
import es.gob.aapp.csvbroker.webservices.querydocument.CSVQueryDocumentException;
import es.gob.aapp.csvbroker.webservices.querydocument.CsvQueryDocumentSecurityWSS;
import es.gob.aapp.csvbroker.webservices.querydocument.CsvQueryDocumentSecurityWSSResponse;
import es.gob.aapp.csvbroker.webservices.querydocument.CsvQueryDocumentWSS;
import es.gob.aapp.csvbroker.webservices.querydocument.CsvQueryDocumentWSSResponse;
import es.gob.aapp.csvbroker.webservices.querydocument.model.v1.CSVQueryDocumentResponse;
import es.gob.aapp.csvbroker.webservices.querydocument.model.v1.CSVQueryDocumentSecurityRequest;
import es.gob.aapp.csvbroker.webservices.querydocument.model.v1.CSVQueryDocumentSecurityResponse;
import es.gob.aapp.csvbroker.webservices.querydocument.model.v1.DocumentResponse;
import es.gob.aapp.csvbroker.webservices.querydocument.model.v1.DocumentUrlResponse;
import es.gob.aapp.csvstorage.model.object.document.DocumentObject;
import es.gob.aapp.csvstorage.services.business.document.GetDocumentBusinessService;
import es.gob.aapp.csvstorage.util.MimeType;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.util.constants.IdentificationType;
import es.gob.aapp.csvstorage.webservices.document.model.CSVStorageException;
import es.gob.aapp.csvstorage.webservices.document.model.ContenidoInfo;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoResponse;

/**
 * Implemetación del servicio CSVQueryDocumentCertificateService
 * 
 * @author carlos.munoz1
 *
 */
@WebService(serviceName = "CSVQueryDocumentCertificateService",
    portName = "CSVQueryDocumentCertificateServicePort",
    targetNamespace = "urn:es:gob:aapp:csvbroker:webservices:querydocument:v1.0",
    endpointInterface = "es.gob.aapp.csvbroker.webservices.querydocument.CSVQueryDocumentCertificateService")
@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.BARE, use = Use.LITERAL)
public class CSVQueryDocumentCertificateServiceImpl implements CSVQueryDocumentCertificateService {

  /** Logger de la clase. */
  private static final Logger LOG =
      LogManager.getLogger(CSVQueryDocumentCertificateServiceImpl.class);

  private static final String FILE_NAME = "documento";

  @Autowired
  private GetDocumentBusinessService getDocumentBusinessService;

  @Override
  public CsvQueryDocumentWSSResponse csvQueryDocument(CsvQueryDocumentWSS csvQueryDocumentRequest)
      throws CSVQueryDocumentException {
    CsvQueryDocumentWSSResponse csvQueryDocumentWSSResponse = new CsvQueryDocumentWSSResponse();
    CSVQueryDocumentResponse csvQueryDocumentResponse = new CSVQueryDocumentResponse();
    LOG.info("Entra en csvQueryDocument");

    try {

      DocumentObject documentObject = new DocumentObject();
      BeanUtils.copyProperties(csvQueryDocumentRequest.getQueryDocumentRequest(), documentObject);
      documentObject.setCalledFrom(Constants.CALLED_FROM_QUERY_SERVICE);
      documentObject.setParametrosAuditoria(
          csvQueryDocumentRequest.getQueryDocumentRequest().parametrosAuditoria());

      if (csvQueryDocumentRequest.getQueryDocumentRequest().getRecuperacionOriginal() != null) {
        documentObject.setRecuperacionOriginal(
            csvQueryDocumentRequest.getQueryDocumentRequest().getRecuperacionOriginal().value());
      }

      if (csvQueryDocumentRequest.getQueryDocumentRequest().getDocumentoEni() != null) {
        documentObject.setDocumentoEni(
            csvQueryDocumentRequest.getQueryDocumentRequest().getDocumentoEni().value());
      }

      ObtenerDocumentoResponse obtenerDocumentoResponse =
          (ObtenerDocumentoResponse) getDocumentBusinessService.obtener(documentObject,
              ObtenerDocumentoResponse.class, null);
      if (obtenerDocumentoResponse != null
          && obtenerDocumentoResponse.getDocumentoResponse() != null) {


        DocumentResponse doc = new DocumentResponse();
        if (String.valueOf(Constants.QUERY_DOCUMENT_EXIST)
            .equals(obtenerDocumentoResponse.getDocumentoResponse().getCodigo())) {
          ContenidoInfo contenidoInfo =
              obtenerDocumentoResponse.getDocumentoResponse().getContenido();

          doc.setContent(contenidoInfo.getContenido());
          doc.setMime(contenidoInfo.getTipoMIME());

          String name = FILE_NAME;
          if (contenidoInfo.getTipoMIME() != null) {
            String extension =
                MimeType.getInstance().extractExtensionFromMIMEType(contenidoInfo.getTipoMIME());
            if (extension != null) {
              name += "." + extension;
            }
            doc.setName(name);
          }
          csvQueryDocumentResponse.setDocumentResponse(doc);
          csvQueryDocumentResponse.setCode(String.valueOf(Constants.VALIDATION_OK));
          csvQueryDocumentResponse.setDescription(Constants.VALIDATION_OK_DESC);
        } else if (String.valueOf(Constants.QUERY_DOCUMENT_ERROR)
            .equals(obtenerDocumentoResponse.getDocumentoResponse().getCodigo())) {
          csvQueryDocumentResponse.setCode(String.valueOf(Constants.INTERNAL_SERVICE_ERROR));
          csvQueryDocumentResponse.setDescription(Constants.INTERNAL_SERVICE_ERROR_DESC);
        } else {
          csvQueryDocumentResponse.setCode(String.valueOf(Constants.CSV_NOT_FOUND));
          csvQueryDocumentResponse.setDescription(Constants.CSV_NOT_FOUND_DESC);
        }

      }
    } catch (CSVStorageException e) {
      LOG.error("Se ha producido un error en el metodo csvQueryDocument", e);
      csvQueryDocumentResponse.setCode(String.valueOf(Constants.INTERNAL_SERVICE_ERROR));
      csvQueryDocumentResponse.setDescription(Constants.INTERNAL_SERVICE_ERROR_DESC);
    }

    csvQueryDocumentWSSResponse.setQueryDocumentResponse(csvQueryDocumentResponse);

    LOG.info("Sale de csvQueryDocument");
    return csvQueryDocumentWSSResponse;
  }

  @Override
  public CsvQueryDocumentSecurityWSSResponse csvQueryDocumentSecurity(
      CsvQueryDocumentSecurityWSS csvQueryDocumentSecurityWSS) throws CSVQueryDocumentException {
    CsvQueryDocumentSecurityWSSResponse csvQueryDocumentWSSResponse =
        new CsvQueryDocumentSecurityWSSResponse();
    CSVQueryDocumentSecurityResponse csvQueryDocumentResponse =
        new CSVQueryDocumentSecurityResponse();
    LOG.info("Entra en csvQueryDocumentSecurity");
    CSVQueryDocumentSecurityRequest csvQueryDocumentSecurityRequest =
        csvQueryDocumentSecurityWSS.getQueryDocumentSecurityRequest();
    try {

      DocumentObject documentObject =
          createDocumentObjectFromCsvQueryDocumentSecurityRequest(csvQueryDocumentSecurityRequest);

      ObtenerDocumentoResponse obtenerDocumentoResponse =
          (ObtenerDocumentoResponse) getDocumentBusinessService.obtener(documentObject,
              ObtenerDocumentoResponse.class, null);

      if (obtenerDocumentoResponse != null
          && obtenerDocumentoResponse.getDocumentoResponse() != null) {


        DocumentUrlResponse doc = new DocumentUrlResponse();
        if (String.valueOf(Constants.QUERY_DOCUMENT_EXIST)
            .equals(obtenerDocumentoResponse.getDocumentoResponse().getCodigo())) {
          ContenidoInfo contenidoInfo =
              obtenerDocumentoResponse.getDocumentoResponse().getContenido();

          doc.setContent(contenidoInfo.getContenido());
          doc.setMime(contenidoInfo.getTipoMIME());

          String name = FILE_NAME;
          if (contenidoInfo.getTipoMIME() != null) {
            String extension =
                MimeType.getInstance().extractExtensionFromMIMEType(contenidoInfo.getTipoMIME());
            if (extension != null) {
              name += "." + extension;
            }
            doc.setName(name);
          }
          csvQueryDocumentResponse.setDocumentUrlResponse(doc);
          csvQueryDocumentResponse.setCode(String.valueOf(Constants.VALIDATION_OK));
          csvQueryDocumentResponse.setDescription(Constants.VALIDATION_OK_DESC);
        } else if (String.valueOf(Constants.QUERY_DOCUMENT_ERROR)
            .equals(obtenerDocumentoResponse.getDocumentoResponse().getCodigo())) {
          csvQueryDocumentResponse.setCode(String.valueOf(Constants.INTERNAL_SERVICE_ERROR));
          csvQueryDocumentResponse.setDescription(Constants.INTERNAL_SERVICE_ERROR_DESC);
        } else {
          csvQueryDocumentResponse.setCode(String.valueOf(Constants.CSV_NOT_FOUND));
          csvQueryDocumentResponse.setDescription(Constants.CSV_NOT_FOUND_DESC);
        }

      }
    } catch (CSVStorageException e) {
      LOG.error("Se ha producido un error en el metodo csvQueryDocument", e);
      csvQueryDocumentResponse.setCode(String.valueOf(Constants.INTERNAL_SERVICE_ERROR));
      csvQueryDocumentResponse.setDescription(Constants.INTERNAL_SERVICE_ERROR_DESC);
    }

    csvQueryDocumentWSSResponse.setQueryDocumentSecurityResponse(csvQueryDocumentResponse);

    LOG.info("Sale de csvQueryDocumentSecurity");
    return csvQueryDocumentWSSResponse;
  }

  private DocumentObject createDocumentObjectFromCsvQueryDocumentSecurityRequest(
      CSVQueryDocumentSecurityRequest csvQueryDocumentSecurityRequest) {
    DocumentObject documentObject = new DocumentObject();
    BeanUtils.copyProperties(csvQueryDocumentSecurityRequest, documentObject);
    documentObject.setCalledFrom(Constants.CALLED_FROM_QUERY_SERVICE);
    documentObject.setParametrosAuditoria(csvQueryDocumentSecurityRequest.parametrosAuditoria());

    if (csvQueryDocumentSecurityRequest.getNif() != null) {
      documentObject.getNifs().add(csvQueryDocumentSecurityRequest.getNif());
    }

    if (csvQueryDocumentSecurityRequest.getTipoIdentificacion() != null) {
      documentObject.setTipoIdentificacion(IdentificationType
          .getTypeByDescription(csvQueryDocumentSecurityRequest.getTipoIdentificacion().value()));

    }

    if (csvQueryDocumentSecurityRequest.getRecuperacionOriginal() != null) {
      documentObject.setRecuperacionOriginal(
          csvQueryDocumentSecurityRequest.getRecuperacionOriginal().value());
    }

    if (csvQueryDocumentSecurityRequest.getDocumentoEni() != null) {
      documentObject.setDocumentoEni(csvQueryDocumentSecurityRequest.getDocumentoEni().value());
    }
    return documentObject;
  }


}
