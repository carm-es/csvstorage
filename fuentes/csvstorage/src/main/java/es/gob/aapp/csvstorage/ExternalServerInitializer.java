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

package es.gob.aapp.csvstorage;

import java.util.Arrays;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import es.gob.aapp.csvstorage.configuration.Configuration;


/**
 * Esta clase es necesaria para que el WAR pueda desplegarse en un servidor independiente (no
 * embebido).
 *
 */
public class ExternalServerInitializer extends SpringBootServletInitializer {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(ExternalServerInitializer.class);

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

    LOG.info("********** ExternalServerInitializer ***************");
    Configuration.init();

    return application.sources(Application.class);
  }

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    WebApplicationContext rootAppContext = createRootApplicationContext(servletContext);
    if (rootAppContext != null) {
      servletContext.addListener(new ContextLoaderListener(rootAppContext) {
        @Override
        public void contextInitialized(ServletContextEvent event) {
          // no-op because the application context is already initialized
        }
      });

    } else {
      this.logger.debug("No ContextLoaderListener registered, as "
          + "createRootApplicationContext() did not " + "return an application context");
    }

    LOG.info("****** Aplicacion iniciada **********");

    LOG.debug("Let's inspect the beans provided by Spring Boot:");
    String[] beanNames = rootAppContext.getBeanDefinitionNames();
    Arrays.sort(beanNames);
    for (String beanName : beanNames) {
      LOG.debug(beanName);
    }
  }

}
