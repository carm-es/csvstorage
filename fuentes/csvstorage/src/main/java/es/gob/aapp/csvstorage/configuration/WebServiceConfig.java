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

package es.gob.aapp.csvstorage.configuration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Clase de configuraci�n para web services.
 * 
 * @author carlos.munoz1
 *
 */
@Configuration
@ImportResource({"classpath:application-context-ws.xml"})
public class WebServiceConfig {

  @Bean
  public ServletRegistrationBean cxfServlet() {
    ServletRegistrationBean servlet = new ServletRegistrationBean(new CXFServlet(), "/services/*");
    servlet.setLoadOnStartup(1);

    return servlet;
  }

}
