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

package es.gob.aapp.csvstorage.webservices.security.interceptor;

import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import es.gob.aapp.csvstorage.webservices.document.model.WSCredential;

/**
 * Interceptor para peticiones a los web services.
 * 
 * @author carlos.munoz1
 * 
 */
public class WebServiceAuthenticationInterceptor extends AbstractPhaseInterceptor<SoapMessage>
    implements InitializingBean {

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(WebServiceAuthenticationInterceptor.class);

  private AuthenticationManager authenticationManager;

  public WebServiceAuthenticationInterceptor() {
    super(Phase.PRE_INVOKE);
  }

  public void afterPropertiesSet() throws Exception {
    if (authenticationManager == null) {
      throw new IllegalStateException("No authentication manager has been configured. ");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
   */
  public void handleMessage(SoapMessage message) throws Fault {

    LOG.info("Entramos en interceptor de WS. ");

    MessageContentsList inObjects = MessageContentsList.getContentsList(message);

    Authentication authentication = null;

    for (Object obj : inObjects) {
      // WSCredential del servicio web CSVValidationService
      if (obj instanceof WSCredential) {
        LOG.info("Se intenta acceder a CSVDocumentService. ");
        WSCredential info = (WSCredential) obj;
        authentication =
            new UsernamePasswordAuthenticationToken(info.getIdaplicacion(), info.getPassword());
      } else if (obj instanceof es.gob.aapp.csvstorage.webservices.documentmtom.model.WSCredential) {
        LOG.info("Se intenta acceder a CSVDocumentMtomService. ");
        es.gob.aapp.csvstorage.webservices.documentmtom.model.WSCredential info =
            (es.gob.aapp.csvstorage.webservices.documentmtom.model.WSCredential) obj;
        authentication =
            new UsernamePasswordAuthenticationToken(info.getIdaplicacion(), info.getPassword());
      } else if (obj instanceof es.gob.aapp.csvbroker.webservices.querydocument.model.v1.WSCredential) {
        LOG.info("Se intenta acceder a CSVQueryDocumentService. ");
        es.gob.aapp.csvbroker.webservices.querydocument.model.v1.WSCredential info =
            (es.gob.aapp.csvbroker.webservices.querydocument.model.v1.WSCredential) obj;
        authentication =
            new UsernamePasswordAuthenticationToken(info.getIdaplicacion(), info.getPassword());
      } else if (obj instanceof es.gob.aapp.csvstorage.webservices.administration.model.WSCredential) {
        LOG.info("Se intenta acceder a AdminService. ");
        es.gob.aapp.csvstorage.webservices.administration.model.WSCredential info =
            (es.gob.aapp.csvstorage.webservices.administration.model.WSCredential) obj;
        authentication =
            new UsernamePasswordAuthenticationToken(info.getIdaplicacion(), info.getPassword());
      }
    }

    SecurityContext securityContext = SecurityContextHolder.getContext();

    try {
      authentication = authenticationManager.authenticate(authentication);
      message.getExchange().put(Authentication.class, authentication);

      securityContext.setAuthentication(authentication);

    } catch (AuthenticationException ex) {
      LOG.error("Credenciales Erroneas. Se lanza SoapFault con mensaje. ");
      securityContext.setAuthentication(null);
      throw new SoapFault("Credenciales Erroneas. compruebe id de aplicaci�n y password", ex,
          message.getVersion().getSender());
    }

  }

  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

}
