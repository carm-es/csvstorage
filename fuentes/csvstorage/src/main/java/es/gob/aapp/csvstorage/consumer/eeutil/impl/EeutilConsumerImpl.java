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

package es.gob.aapp.csvstorage.consumer.eeutil.impl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.MTOMFeature;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.client.ws.eeutil.misc.EeUtilValidacionENIServiceMtom;
import es.gob.aapp.csvstorage.client.ws.eeutil.misc.EeUtilValidacionENIServiceMtomImplService;
import es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.ApplicationLogin;
import es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.DocumentoEntradaMtom;
import es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.RespuestaValidacionENI;
import es.gob.aapp.csvstorage.client.ws.eeutil.misc.model.Validaciones;
import es.gob.aapp.csvstorage.consumer.eeutil.EeutilConsumer;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;



// TODO: Auto-generated Javadoc
/**
 * Implementación de la clase consumidora de los servicios web de EEUTIL.
 * 
 * @author serena.plaza
 * 
 */

/**
 * @author eSoluzion
 * @param <T>
 *
 */
@Service
@PropertySource("file:${csvstorage.config.path}/eeutil.properties")
public class EeutilConsumerImpl implements EeutilConsumer {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(EeutilConsumerImpl.class);

  /** Objeto para inyectar los servicios de descarga de unidades. */
  private EeUtilValidacionENIServiceMtom eeUtilValidacionENIService;
  /** The ruta temp. */
  @Value("${eeutil.misc.ws.ruta}")
  private String rutaEeutilMisc;

  /** The path descompresion. */
  @Value("${eeutil.misc.ws.idaplicacion}")
  private String idaplicacion;

  /** The path descompresion. */
  @Value("${eeutil.misc.ws.password}")
  private String password;

  /**
   * Configure.
   */
  private void configure() {
    LOG.info("Inicio de configuración. ");

    if (eeUtilValidacionENIService == null) {
      URL url = null;
      try {
        url = new URL(rutaEeutilMisc);


        EeUtilValidacionENIServiceMtomImplService service1 =
            new EeUtilValidacionENIServiceMtomImplService(url);
        eeUtilValidacionENIService =
            service1.getEeUtilValidacionENIServiceMtomImplPort(new MTOMFeature());

        // disableChunking(ClientProxy.getClient(eeUtilValidacionENIService));

        BindingProvider bp = (BindingProvider) eeUtilValidacionENIService;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url.toExternalForm());
      } catch (MalformedURLException e) {
        LOG.error("No se puede crear la URL del servicio de DIR3 " + url, e);
      } catch (Exception e) {
        LOG.error("Se ha producido un error al crear el servicio " + url, e);
        throw e;
      }
    }

    LOG.info("Fin de configuración. ");
  }

  /**
   * Método que settea las credenciales para consumir el servicio en el objeto WSCredential. Las
   * obtiene de querydocument.properties
   * 
   * @return WSCredential
   */
  private ApplicationLogin obtieneCredenciales() {
    ApplicationLogin credential = new ApplicationLogin();

    credential.setIdaplicacion(idaplicacion);
    credential.setPassword(password);


    return credential;
  }

  public RespuestaValidacionENI validarDocumentoENI(DocumentoEntradaMtom documentoEntradaMtom)
      throws ConsumerWSException {
    LOG.info("Inicio de validarDocumentoENI. ");
    configure();
    Validaciones validaciones = new Validaciones();
    validaciones.setValidaSchema(true);
    validaciones.setValidaDir3(true);
    validaciones.setValidaSIA(false);
    validaciones.setValidaFirma(false);

    RespuestaValidacionENI respuestaValidacionENI;
    try {
      respuestaValidacionENI = eeUtilValidacionENIService.validarDocumentoENI(obtieneCredenciales(),
          documentoEntradaMtom, null, validaciones);
    } catch (Exception e) {
      LOG.error("Se ha producido un error al llamar al servicio de validación ENI", e);
      throw new ConsumerWSException(
          "Se ha producido un error al llamar al servicio de validación ENI", e);
    }
    LOG.info("Fin de validarDocumentoENI. ");
    return respuestaValidacionENI;

  }

  // protected void disableChunking(Client client) {
  // HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
  // HTTPClientPolicy policy = httpConduit.getClient();
  // policy.setAllowChunking(false);
  // policy.setChunkingThreshold(0);
  // }

}
