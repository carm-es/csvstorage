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

package es.gob.aapp.csvstorage.webservices.document.impl;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.soap.MTOM;
import es.gob.aapp.csvstorage.services.business.document.GetDocumentBusinessService;
import es.gob.aapp.csvstorage.webservices.document.model.*;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.DocumentoMtomStreamingResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import es.gob.aapp.csvstorage.services.business.document.SaveDocumentBusinessService;
import es.gob.aapp.csvstorage.webservices.document.BIGFILEStreamingService;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.GuardarDocumentoMtomStreamingRequest;

// El mensaje MIME se analiza de forma eager y no lazy (parseEagerly)
// Los archivos adjuntos de menos de 4 MB se guardan en la memoria (memoryThreshold)
@MTOM
@WebService(serviceName = "BIGFILEStreamingService", portName = "BIGFILEStreamingServicePort",
    targetNamespace = "urn:es:gob:aapp:csvstorage:webservices:document:v1.0",
    endpointInterface = "es.gob.aapp.csvstorage.webservices.document.BIGFILEStreamingService")
@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.BARE, use = Use.LITERAL)
public class BIGFILEStreamingServiceImpl implements BIGFILEStreamingService {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(BIGFILEStreamingServiceImpl.class);


  /** Inyeccion de los servicios business de almacenamiento de documentos. */
  @Autowired
  private SaveDocumentBusinessService saveDocumentBusinessService;

  @Autowired
  private GetDocumentBusinessService getDocumentBusinessService;


  @Override
  public GuardarDocumentoUuidResponse guardarDocumentoStreaming(WSCredential info,
      GuardarDocumentoMtomStreamingRequest guardarDocumento) throws CSVStorageException {
    LOG.debug("Entramos en guardarDocumentoStreaming. Request: " + guardarDocumento.toString());
    GuardarDocumentoUuidResponse response =
        saveDocumentBusinessService.guardarStreaming(guardarDocumento);
    LOG.debug("Salimos de guardarDocumentoStreaming.");

    return response;

  }

  @Override
  public DocumentoMtomStreamingResponse obtenerDocumentoStreaming(WSCredential info,
      ObtenerDocumentoRequest obtenerDocumento) throws CSVStorageException {


    LOG.debug("Entramos en obtenerDocumentoStreaming. Request: " + obtenerDocumento.toString());

    DocumentoMtomStreamingResponse response =
        getDocumentBusinessService.obtenerDocumentoStreaming(obtenerDocumento);
    LOG.debug("Salimos de obtenerDocumentoStreaming.");


    return response;
  }

  @Override
  public DocumentoMtomStreamingResponse obtenerInfoContenidoStreaming(WSCredential info,
      String uuid) throws CSVStorageException {

    LOG.debug("Entramos en obtenerInfoContenidoStreaming. Request: " + uuid);

    DocumentoMtomStreamingResponse response =
        getDocumentBusinessService.obtenerInfoContenidoStreaming(uuid);
    LOG.debug("Salimos de obtenerDocumentoStreaming.");


    return response;
  }



}
