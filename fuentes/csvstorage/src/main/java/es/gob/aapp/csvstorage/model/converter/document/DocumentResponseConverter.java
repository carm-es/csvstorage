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

package es.gob.aapp.csvstorage.model.converter.document;

import java.io.IOException;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import es.gob.aapp.csvstorage.webservices.documentmtom.model.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.TipoDocumento;
import es.gob.aapp.csvstorage.client.ws.eni.documentoe.documentoe.mtom.TipoDocumentoMtom;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniEntity;
import es.gob.aapp.csvstorage.dao.entity.document.DocumentEniOrganoEntity;
import es.gob.aapp.csvstorage.model.object.document.DocumentEniResponseObject;
import es.gob.aapp.csvstorage.model.object.document.DocumentResponseObject;
import es.gob.aapp.csvstorage.webservices.document.model.CSVStorageException;
import es.gob.aapp.csvstorage.webservices.document.model.ContenidoInfo;
import es.gob.aapp.csvstorage.webservices.document.model.ContenidoInfoBigData;
import es.gob.aapp.csvstorage.webservices.document.model.ConvertirDocumentoEniResponse;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoBigDataResponse;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoEniResponse;
import es.gob.aapp.csvstorage.webservices.document.model.DocumentoResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoBigDataResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoEniResponse;
import es.gob.aapp.csvstorage.webservices.document.model.ObtenerDocumentoResponse;
import es.gob.aapp.eeutil.bigDataTransfer.exception.BigDataTransferException;
import es.gob.aapp.eeutil.bigDataTransfer.service.BigDataTransferService;

/**
 * Clase converter para documentos.
 * 
 * @author carlos.munoz1
 * 
 */
public abstract class DocumentResponseConverter {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(DocumentResponseConverter.class);

  public static Object convertObjectToDocumentoResponse(
      DocumentResponseObject documentResponseObject, DataHandler content, String mimeType,
      Class<?> className, BigDataTransferService bigDataTransferService, String path, String url)
      throws CSVStorageException {

    Object response;
    // Obtenemos los parametros dependiendo de si el contenido
    // es de tipo ContenidoInfo o ContenidoInfoMtom
    if (className.isAssignableFrom(ObtenerDocumentoMtomResponse.class)) {
      response = new ObtenerDocumentoMtomResponse();
      DocumentoMtomResponse documentoResponse = new DocumentoMtomResponse();
      documentoResponse.setCodigo(documentResponseObject.getCodigo());
      documentoResponse.setDescripcion(documentResponseObject.getDescripcion());

      if (content != null) {
        DataSource source = content.getDataSource();

        ContenidoInfoMtom contInfo = new ContenidoInfoMtom();
        contInfo.setContenido(new DataHandler(source));
        contInfo.setTipoMIME(mimeType);

        documentoResponse.setContenido(contInfo);
      }

      ((ObtenerDocumentoMtomResponse) response).setDocumentoMtomResponse(documentoResponse);

    } else if (className.isAssignableFrom(ObtenerDocumentoMtomStreamingResponse.class)) {

      response = new ObtenerDocumentoMtomStreamingResponse();
      DocumentoMtomStreamingResponse documentoStreamingResponse =
          new DocumentoMtomStreamingResponse();
      documentoStreamingResponse.setCodigo(documentResponseObject.getCodigo());
      documentoStreamingResponse.setDescripcion(documentResponseObject.getDescripcion());

      if (content != null) {
        DataSource source = content.getDataSource();

        ContenidoInfoMtom contInfo = new ContenidoInfoMtom();
        contInfo.setContenido(new DataHandler(source));
        contInfo.setTipoMIME(mimeType);

        documentoStreamingResponse.setContenido(contInfo);
      }

      ((ObtenerDocumentoMtomStreamingResponse) response)
          .setDocumentoMtomResponse(documentoStreamingResponse);

    } else if (className.isAssignableFrom(ObtenerDocumentoResponse.class)) {
      response = new ObtenerDocumentoResponse();
      DocumentoResponse documentoResponse = new DocumentoResponse();
      documentoResponse.setCodigo(documentResponseObject.getCodigo());
      documentoResponse.setDescripcion(documentResponseObject.getDescripcion());

      if (content != null) {
        ContenidoInfo contInfo = new ContenidoInfo();
        try {
          byte[] contentByte = IOUtils.toByteArray(content.getInputStream());

          contInfo.setContenido(contentByte);
          contInfo.setTipoMIME(mimeType);
        } catch (IOException e) {
          LOG.error("Error al obtener el contenido");
        }

        documentoResponse.setContenido(contInfo);
      }

      ((ObtenerDocumentoResponse) response).setDocumentoResponse(documentoResponse);
    } else if (className.isAssignableFrom(ObtenerDocumentoBigDataResponse.class)) {
      response = new ObtenerDocumentoBigDataResponse();
      DocumentoBigDataResponse documentoResponse = new DocumentoBigDataResponse();
      documentoResponse.setCodigo(documentResponseObject.getCodigo());
      documentoResponse.setDescripcion(documentResponseObject.getDescripcion());

      if (content != null) {
        ContenidoInfoBigData contInfo = new ContenidoInfoBigData();

        try {
          List<String> listFiles = bigDataTransferService.sendFile(path, url);
          contInfo.setFiles(listFiles);
          contInfo.setTipoMIME(mimeType);
        } catch (BigDataTransferException e) {
          LOG.error("BigData: Error al obtener el contenido", e);
        }

        documentoResponse.setContenido(contInfo);
      }

      ((ObtenerDocumentoBigDataResponse) response).setDocumentoResponse(documentoResponse);
    } else {
      throw new CSVStorageException("No es un tipo de respuesta válido");
    }

    return response;
  }

  public static Object convertObjectToConvertirDocumentoEniResponse(
      DocumentEniResponseObject documentResponseObject, boolean isMtom) {

    Object response = null;
    // Obtenemos los parametros dependiendo de si el contenido
    // es de tipo TipoDocumento o TipoDocumentoMtom
    if (isMtom) {
      response = new ConvertirDocumentoEniMtomResponse();
      DocumentoEniMtomResponse documentObject = new DocumentoEniMtomResponse();
      documentObject.setCodigo(documentResponseObject.getCodigo());
      documentObject.setDescripcion(documentResponseObject.getDescripcion());
      documentObject.setDocumento((TipoDocumentoMtom) documentResponseObject.getDocumento());
      ((ConvertirDocumentoEniMtomResponse) response).setDocumentoEniMtomResponse(documentObject);
    } else {
      response = new ConvertirDocumentoEniResponse();
      DocumentoEniResponse documentObject = new DocumentoEniResponse();
      documentObject.setCodigo(documentResponseObject.getCodigo());
      documentObject.setDescripcion(documentResponseObject.getDescripcion());
      documentObject.setDocumento((TipoDocumento) documentResponseObject.getDocumento());
      ((ConvertirDocumentoEniResponse) response).setDocumentoEniResponse(documentObject);
    }

    return response;
  }


  public static Object convertObjectToDocumentoEniResponse(
      DocumentEniResponseObject documentEniResponseObject, DocumentEniEntity documentEniEntity,
      List<DocumentEniOrganoEntity> listOrganos, Class<?> className) throws CSVStorageException {

    Object response;
    try {
      // Obtenemos los parametros dependiendo de si el contenido
      // es de tipo TipoDocumento o TipoDocumentoMtom
      if (className.isInstance(DocumentoEniMtomResponse.class)) {
        response = new ObtenerDocumentoEniMtomResponse();
        DocumentoEniMtomResponse documentObject = new DocumentoEniMtomResponse();
        documentObject.setCodigo(documentEniResponseObject.getCodigo());
        documentObject.setDescripcion(documentEniResponseObject.getDescripcion());

        if (documentEniEntity != null) {
          TipoDocumentoMtom tipoDocumento = (TipoDocumentoMtom) DocumentConverter
              .convertDocumentoEni(documentEniEntity, listOrganos, true);
          documentObject.setDocumento(tipoDocumento);
        }

        ((ObtenerDocumentoEniMtomResponse) response).setDocumentoEniMtomResponse(documentObject);

      } else {
        response = new ObtenerDocumentoEniResponse();
        DocumentoEniResponse documentObject = new DocumentoEniResponse();
        documentObject.setCodigo(documentEniResponseObject.getCodigo());
        documentObject.setDescripcion(documentEniResponseObject.getDescripcion());

        if (documentEniEntity != null) {
          TipoDocumento tipoDocumento = (TipoDocumento) DocumentConverter
              .convertDocumentoEni(documentEniEntity, listOrganos, false);
          documentObject.setDocumento(tipoDocumento);
        }

        ((ObtenerDocumentoEniResponse) response).setDocumentoEniResponse(documentObject);
      }
    } catch (ConsumerWSException e) {
      throw new CSVStorageException("No es un tipo de respuesta válido");
    }

    return response;
  }


}
