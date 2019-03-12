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

package es.gob.aapp.csvstorage.consumer.ginside.mtom.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.ws.soap.MTOMFeature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.client.ws.ginside.GInsideUserTokenMtomWS;
import es.gob.aapp.csvstorage.client.ws.ginside.GInsideUserTokenMtomWebService;
import es.gob.aapp.csvstorage.client.ws.ginside.InsideWSException;
import es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.conversion.mtom.TipoDocumentoConversionInsideMtom;
import es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.mtom.file.DocumentoEniFileInsideMtom;
import es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.documentoe.mtom.TipoDocumentoValidacionInsideMtom;
import es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.documentoe.resultados.TipoResultadoValidacionDocumentoInside;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;
import es.gob.aapp.csvstorage.consumer.ginside.mtom.GInsideUserTokenMtomConsumer;



/**
 * Implementaci�n de la clase consumidora de los servicios web de DIR3.
 * 
 * @author carlos.munoz1
 * 
 */
@Service
@PropertySource("file:${csvstorage.config.path}/ginside.properties")
public class GInsideUserTokenMtomConsumerImpl implements GInsideUserTokenMtomConsumer {

  /**
   * Valor del properties de la URL del WSDL.
   */
  @Value("${ginsidemtom.ws.usertoken.url.wsdl}")
  private String urlWsdl;

  /**
   * Valor del idapplication de eeutilUtilFirma.
   */
  @Value("${ginsidemtom.ws.idaplicacion}")
  private String idapplication;

  /**
   * Valor del password de eeutilUtilFirma.
   */
  @Value("${ginsidemtom.ws.password}")
  private String password;

  protected static final Log LOG = LogFactory.getLog(GInsideUserTokenMtomConsumerImpl.class);

  private GInsideUserTokenMtomWebService ginSideUserTokenMtomWebService = null;


  /**
   * M�todo para configurar el servicio de descarga de unidades org�nicas.
   * 
   */
  public void configure() throws InsideWSException {

    LOG.debug("Inicio de configuración. ");
    if (ginSideUserTokenMtomWebService == null) {
      try {
        URL url = new URL(urlWsdl);
        LOG.info("URL de Endpoint... " + urlWsdl);

        GInsideUserTokenMtomWS service1 = new GInsideUserTokenMtomWS(url);
        ginSideUserTokenMtomWebService = service1.getGInsideUserTokenMtomWSPort(new MTOMFeature());

        disableChunking(ClientProxy.getClient(ginSideUserTokenMtomWebService));

        obtieneCredenciales();

      } catch (MalformedURLException e) {
        LOG.error("Se ha producido un error al conectar al servicio: " + urlWsdl);
        throw new InsideWSException(e.getMessage());
      }
    }
    LOG.debug("Fin de configuración. ");
  }



  void obtieneCredenciales() {

    final String passwordFinal = password;

    CallbackHandler cpc = new CallbackHandler() {

      @Override
      public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        pc.setPassword(passwordFinal);

      }
    };

    Map<String, Object> outProps = new HashMap<String, Object>();
    outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
    outProps.put(WSHandlerConstants.USER, idapplication);
    outProps.put(WSHandlerConstants.PW_CALLBACK_REF, cpc);
    outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);

    WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);

    Client c = ClientProxy.getClient(ginSideUserTokenMtomWebService);
    Endpoint cxfEndpoint = c.getEndpoint();

    cxfEndpoint.getOutInterceptors().add(wssOut);

  }


  @Override
  public TipoResultadoValidacionDocumentoInside validarDocumentoEni(
      TipoDocumentoValidacionInsideMtom documento) throws ConsumerWSException {
    LOG.debug("Inicio de validarDocumentoEni. ");
    TipoResultadoValidacionDocumentoInside response = null;

    try {
      configure();
      response = ginSideUserTokenMtomWebService.validarDocumentoEniFile(documento);
    } catch (InsideWSException e) {
      LOG.error(
          "Se ha producido un error al validarDocumentoEni: " + e.getFaultInfo().getDescripcion());
      throw new ConsumerWSException(e.getFaultInfo().getDescripcion());
    }
    LOG.debug("Fin de validarDocumentoEni. ");

    return response;
  }



  @Override
  public DocumentoEniFileInsideMtom convertirDocumentoAEni(
      TipoDocumentoConversionInsideMtom documento, byte[] contenido, boolean firmar)
      throws ConsumerWSException {
    LOG.info("Inicio de convertirDocumentoAEni. ");
    DocumentoEniFileInsideMtom documentoEniFileInsideMtom = null;
    try {
      configure();
      documentoEniFileInsideMtom =
          ginSideUserTokenMtomWebService.convertirDocumentoAEni(documento, contenido, firmar);
    } catch (InsideWSException e) {
      LOG.error("Se ha producido un error al convertirDocumentoAEni: "
          + e.getFaultInfo().getDescripcion());
      throw new ConsumerWSException("Se ha producido un error al convertir el documento a ENI", e);
    }
    LOG.info("Fin de convertirDocumentoAEni. ");
    return documentoEniFileInsideMtom;
  }

  protected void disableChunking(Client client) {
    HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
    HTTPClientPolicy policy = httpConduit.getClient();
    policy.setAllowChunking(false);
    policy.setChunkingThreshold(0);
  }

}
