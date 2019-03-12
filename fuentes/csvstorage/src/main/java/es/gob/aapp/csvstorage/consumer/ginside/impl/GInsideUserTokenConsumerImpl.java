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

package es.gob.aapp.csvstorage.consumer.ginside.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.client.ws.ginside.GInsideUserTokenWS;
import es.gob.aapp.csvstorage.client.ws.ginside.GInsideUserTokenWebService;
import es.gob.aapp.csvstorage.client.ws.ginside.InsideWSException;
import es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.conversion.TipoDocumentoConversionInside;
import es.gob.aapp.csvstorage.client.ws.ginside.model.documentoe.file.DocumentoEniFileInside;
import es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.documentoe.TipoDocumentoValidacionInside;
import es.gob.aapp.csvstorage.client.ws.ginside.model.validacion.documentoe.resultados.TipoResultadoValidacionDocumentoInside;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;
import es.gob.aapp.csvstorage.consumer.ginside.GInsideUserTokenConsumer;

/**
 * Implementación de la clase consumidora de los servicios web de DIR3.
 * 
 * @author carlos.munoz1
 * 
 */
@Service
@PropertySource("file:${csvstorage.config.path}/ginside.properties")
public class GInsideUserTokenConsumerImpl implements GInsideUserTokenConsumer {

  /**
   * Valor del properties de la URL del WSDL.
   */
  @Value("${ginside.ws.usertoken.url.wsdl}")
  private String urlWsdl;

  /**
   * Valor del idapplication de eeutilUtilFirma.
   */
  @Value("${ginside.ws.idaplicacion}")
  private String idapplication;

  /**
   * Valor del password de eeutilUtilFirma.
   */
  @Value("${ginside.ws.password}")
  private String password;

  protected static final Log LOG = LogFactory.getLog(GInsideUserTokenConsumerImpl.class);

  private GInsideUserTokenWebService ginSideUserTokenWebService = null;

  /**
   * Método para configurar el servicio de descarga de unidades orgánicas.
   * 
   */
  public void configure() throws InsideWSException {

    LOG.info("Inicio de configuraci�n. ");
    if (ginSideUserTokenWebService == null) {


      try {
        URL url = new URL(urlWsdl);
        LOG.info("URL de Endpoint... " + urlWsdl);
        GInsideUserTokenWS service1 = new GInsideUserTokenWS(url);
        ginSideUserTokenWebService = service1.getGInsideUserTokenWSPort();

        obtieneCredenciales();


        // BindingProvider bp = (BindingProvider) ginSideWebService;
        // bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
        // urlWsdl);

        // Muestra los mensajes SOAP de las llamadas a los endpoins

        // BindingProvider bindingProvider = (BindingProvider) ginSideWebService;
        // Binding binding = bindingProvider.getBinding();
        // List<Handler> handlerChain = binding.getHandlerChain();
        // handlerChain.add(new ImprimirPeticionSoap());
        // binding.setHandlerChain(handlerChain);


      } catch (MalformedURLException e) {
        LOG.error("Se ha producido un error al conectar al servicio: " + urlWsdl);
        throw new InsideWSException(e.getMessage());
      }
    }
    LOG.info("Fin de configuraci�n. ");
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

    Client c = ClientProxy.getClient(ginSideUserTokenWebService);
    Endpoint cxfEndpoint = c.getEndpoint();

    cxfEndpoint.getOutInterceptors().add(wssOut);

  }

  @Override
  public TipoResultadoValidacionDocumentoInside validarDocumentoEni(
      TipoDocumentoValidacionInside documento) throws ConsumerWSException {
    TipoResultadoValidacionDocumentoInside response = null;

    try {
      configure();
      response = ginSideUserTokenWebService.validarDocumentoEniFile(documento);

    } catch (InsideWSException e) {
      LOG.error(
          "Se ha producido un error al validarDocumentoEni: " + e.getFaultInfo().getDescripcion());
      throw new ConsumerWSException(e.getFaultInfo().getDescripcion());
    }

    return response;
  }

  public DocumentoEniFileInside convertirDocumentoAEni(TipoDocumentoConversionInside documento,
      byte[] contenido, boolean firmar) throws ConsumerWSException {

    DocumentoEniFileInside documentoEniFileInside = null;
    try {
      configure();
      documentoEniFileInside =
          ginSideUserTokenWebService.convertirDocumentoAEni(documento, contenido, firmar);

    } catch (InsideWSException e) {
      LOG.error("Se ha producido un error al convertirDocumentoAEni: "
          + e.getFaultInfo().getDescripcion());
      throw new ConsumerWSException(e.getFaultInfo().getDescripcion());
    }
    return documentoEniFileInside;
  }

}
