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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxws.interceptors.HolderInInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;
import org.apache.wss4j.common.principal.WSUsernameTokenPrincipalImpl;
import org.apache.wss4j.dom.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import es.gob.aapp.csvstorage.model.object.application.ApplicationObject;
import es.gob.aapp.csvstorage.services.business.application.ApplicationBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.util.file.FileUtil;

/**
 * Interceptor para peticiones a los web services.
 * 
 */
public class UserTokenAuthenticationInterceptor extends AbstractPhaseInterceptor<Message>
    implements InitializingBean {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(UserTokenAuthenticationInterceptor.class);

  private AuthenticationManager authenticationManager;

  private es.gob.aapp.csvstorage.services.business.application.ApplicationBusinessService applicationBusinessService;

  public UserTokenAuthenticationInterceptor() {
    super(Phase.PRE_INVOKE);

    addAfter(HolderInInterceptor.class.getName());
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
  public void handleMessage(Message message) throws Fault {

    LOG.info("Entramos en interceptor de WS. ");

    QName qNampeSoap = (QName) message.get("javax.xml.ws.wsdl.operation");

    List<WSHandlerResult> results =
        CastUtils.cast((List<?>) message.get(WSHandlerConstants.RECV_RESULTS));
    SecurityContext securityContext = SecurityContextHolder.getContext();

    for (WSHandlerResult wshr : results) {
      for (WSSecurityEngineResult wsser : wshr.getResults()) {
        WSUsernameTokenPrincipalImpl principal =
            (WSUsernameTokenPrincipalImpl) wsser.get(WSSecurityEngineResult.TAG_PRINCIPAL);
        ApplicationObject applicationObject = null;
        try {
          applicationObject = applicationBusinessService.findByIdAplicacion(principal.getName());
        } catch (ServiceException e) {
          LOG.error("No existe ninguna aplicación con esta credenciales");
        }

        if (applicationObject == null) {
          securityContext.setAuthentication(null);
          throw new SoapFault("Credenciales Erroneas. compruebe identificador y password",
              qNampeSoap);
        } else {
          Authentication authentication = new UsernamePasswordAuthenticationToken(
              applicationObject.getIdAplicacion(), applicationObject.getPassword());
          authentication = authenticationManager.authenticate(authentication);
          message.getExchange().put(Authentication.class, authentication);

          securityContext.setAuthentication(authentication);
        }
      }
    }

    // Capturar petición
    byte[] xmlRequest = (byte[]) message.get("xmlRequest");
    try {
      if (xmlRequest != null) {
        // String peticion = Base64.encodeBase64String(xmlRequest);
        // LOG.info("PETICION: " + peticion);
        Calendar cal = Calendar.getInstance();
        String tmpFile = System.getProperty("java.io.tmpdir");
        if (!tmpFile.endsWith(System.getProperty("file.separator"))) {
          tmpFile += System.getProperty("file.separator");
        }

        FileUtil.saveFile(tmpFile, "peticion_" + cal.getTimeInMillis() + ".xml", xmlRequest);
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  /**
   * @param applicationBusinessService the applicationBusinessService to set
   */
  public void setApplicationBusinessService(ApplicationBusinessService applicationBusinessService) {
    this.applicationBusinessService = applicationBusinessService;
  }

}
