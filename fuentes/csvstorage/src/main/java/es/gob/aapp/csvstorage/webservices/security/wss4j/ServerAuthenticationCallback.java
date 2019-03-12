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

package es.gob.aapp.csvstorage.webservices.security.wss4j;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;
import es.gob.aapp.csvstorage.model.object.application.ApplicationObject;
import es.gob.aapp.csvstorage.services.business.application.ApplicationBusinessService;



public class ServerAuthenticationCallback implements CallbackHandler {

  protected static final Log logger = LogFactory.getLog(ServerAuthenticationCallback.class);

  @Autowired
  private ApplicationBusinessService applicationBusinessService;

  @Override
  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    try {
      logger.debug("Inicio ServerCallback");

      for (int i = 0; i < callbacks.length; i++) {
        WSPasswordCallback pwcb = (WSPasswordCallback) callbacks[i];
        String id = pwcb.getIdentifier();
        int usage = pwcb.getUsage();

        switch (usage) {
          case WSPasswordCallback.UNKNOWN:
            logger.debug("ServerCallback UNKNOWN");
            break;
          case WSPasswordCallback.DECRYPT:
            logger.debug("ServerCallback DECRYPT");
            break;
          case WSPasswordCallback.SIGNATURE:
            logger.debug("ServerCallback SIGNATURE");
            break;
          case WSPasswordCallback.USERNAME_TOKEN:
            logger.debug("ServerCallback USERNAME_TOKEN");
            ApplicationObject app = applicationBusinessService.findByIdAplicacion(id);
            pwcb.setPassword(app.getPassword());
            break;
        }
      }

      logger.debug("Fin ServerCallback");
    } catch (Exception e) {
      logger.error("Error realizando comprobación de credenciales");
    }
  }
}
