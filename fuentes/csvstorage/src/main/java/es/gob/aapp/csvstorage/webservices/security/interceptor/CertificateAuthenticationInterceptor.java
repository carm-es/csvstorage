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

import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.List;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.wss4j.dom.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class CertificateAuthenticationInterceptor extends AbstractPhaseInterceptor<SoapMessage>
    implements InitializingBean {

  private static final Logger LOG =
      LoggerFactory.getLogger(CertificateAuthenticationInterceptor.class);

  private ApplicationBusinessService applicationBusinessService;

  private AuthenticationManager authenticationManager;

  public CertificateAuthenticationInterceptor() {
    super(Phase.PRE_INVOKE);
  }

  /**
   * @param applicationBusinessService the applicationBusinessService to set
   */
  public void setApplicationBusinessService(ApplicationBusinessService applicationBusinessService) {
    this.applicationBusinessService = applicationBusinessService;
  }

  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  public void afterPropertiesSet() throws Exception {
    if (authenticationManager == null) {
      throw new IllegalStateException("No authentication manager has been configured");
    }
  }

  public void handleMessage(SoapMessage message) throws Fault {

    SecurityContext securityContext = SecurityContextHolder.getContext();

    List<WSHandlerResult> results =
        CastUtils.cast((List<?>) message.get(WSHandlerConstants.RECV_RESULTS));

    for (WSHandlerResult wshr : results) {

      for (WSSecurityEngineResult wsser : wshr.getResults()) {

        X509Certificate publicKey =
            (X509Certificate) wsser.get(WSSecurityEngineResult.TAG_X509_CERTIFICATE);

        List<ApplicationObject> applicationObjects = null;
        try {
          applicationObjects = applicationBusinessService
              .findBySerialNumber(publicKey.getSerialNumber().toString(16).toUpperCase());
        } catch (ServiceException e) {
          LOG.error("El certificado no asociado con ninguna aplicación");
        }

        if (applicationObjects == null || applicationObjects.size() == 0) {
          securityContext.setAuthentication(null);
          throw new SoapFault("El certificado no asociado con ninguna aplicación",
              message.getVersion().getSender());
        } else if (applicationObjects.size() > 1) {
          securityContext.setAuthentication(null);
          throw new SoapFault("Existe más de una aplicación asociada a este certificado",
              message.getVersion().getSender());
        } else {
          ApplicationObject app = applicationObjects.get(0);
          Authentication authentication =
              new UsernamePasswordAuthenticationToken(app.getIdAplicacion(), app.getPassword());

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
      LOG.error("Error salvando la request: {}", e.getMessage(), e);

    }
  }
}
