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

package es.gob.aapp.csvstorage.controller;

import es.gob.aapp.csvstorage.services.business.truckstore.TruststoreBusinessService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase controladora de aplicaciones. Es la primera pestaña de la parte de Administración.
 * 
 * @author carlos.munoz1
 * 
 */
@Controller
public class AdminInfoController {

  private static final String WAR = "csvstorage.war";

  /** Logger de la clase. */
  private static final Logger LOG = LogManager.getLogger(AdminInfoController.class);

  @Autowired
  private TruststoreBusinessService certBusinessService;


  /**
   * Inicialización del WebDataBinder.
   * 
   * @param binder
   */
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    // Trim Strings and transform an empty string into a null value.
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

  // admin/units/_info
  @RequestMapping(value = "/admin/_info", method = RequestMethod.GET)
  @Secured("ROLE_ADMIN")
  public String info(ModelMap model) {

    LOG.info("[INI] Entramos en info:");

    try {
      InetAddress address = InetAddress.getLocalHost();

      String sHostName;
      sHostName = address.getHostName();
      model.put("nombreHost", sHostName);


      // Cogemos la IP
      byte[] bIPAddress = address.getAddress();

      // IP en formato String
      String sIPAddress = "";

      for (int x = 0; x < bIPAddress.length; x++) {
        if (x > 0) {
          // A todos los numeros les anteponemos
          // un punto menos al primero
          sIPAddress += ".";
        }
        // Jugamos con los bytes y cambiamos el bit del signo
        sIPAddress += bIPAddress[x] & 255;

      }

      // Cogemos el puerto

      String port = null;

      Set<ObjectName> objectNames = null;
      try {
        objectNames = ManagementFactory.getPlatformMBeanServer().queryNames(
            new ObjectName("*:type=Connector,*"),
            Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));

        port = objectNames.iterator().next().getKeyProperty("port");

      } catch (MalformedObjectNameException e) {
        LOG.error(e.getMessage());
      }

      if (port != null) {
        model.put("ipAdress", sIPAddress + ":" + port);
      } else {
        model.put("ipAdress", sIPAddress + ": - ");
      }

      //

      String urlAbsolute = new File(".").getAbsolutePath();

      String url = StringUtils.replace(urlAbsolute, "bin/.", "webapps/");

      model.put("url", url + WAR);

      File f = new File(url + WAR);
      if (f.exists()) {
        Date d = new Date();
        d.setTime(f.lastModified());

        model.put("ficheroModi", d);
        model.put("ficheroSize", f.length() / 1024);

      }

      // Para obtener los logs.
      String urlLogs = StringUtils.replace(urlAbsolute, "bin/.", "logs/");
      LOG.info("Ruta de los logs: " + urlLogs);
      model.put("urlLogs", urlLogs);

      List<File> listaFicheros = listarFicherosPorCarpeta(new File(urlLogs));

      model.put("fichLogs", listaFicheros);

      // para obtener los certificados Truststore
      model.put("truststore", new File(certBusinessService.getTrustStoreFile()));

    } catch (UnknownHostException e) {
      LOG.error("Error: " + e.getMessage());
    }


    LOG.info("Return info.html");

    return "admin/info";
  }

  public List<File> listarFicherosPorCarpeta(File carpeta) {

    List<File> ficheros = new ArrayList<>();

    for (File ficheroEntrada : carpeta.listFiles()) {

      if (ficheroEntrada.isFile()) {
        ficheros.add(ficheroEntrada);
      }

    }

    return ficheros;
  }

  @RequestMapping(value = "/admin/_info/descarga", method = RequestMethod.GET)
  @Secured("ROLE_ADMIN")
  public ModelAndView handleRequest(@RequestParam String unPath, @RequestParam String nombreFichero,
      HttpServletRequest request, HttpServletResponse response) throws Exception {

    LOG.info("Entramos handleRequest ");

    try {
      // Suponemos que es un zip lo que se quiere descargar el usuario.
      // Aqui se hace a piñón fijo, pero podría obtenerse el fichero
      // pedido por el usuario a partir de algún parámetro del request
      // o de la URL con la que nos han llamado.

      response.setContentType("text/plain");
      response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreFichero + "\"");

      try (InputStream is = new FileInputStream(unPath)) {

        IOUtils.copy(is, response.getOutputStream());
      }

      response.flushBuffer();

    } catch (IOException ex) {
      // Sacar log de error.
      throw ex;
    }

    return null;
  }

}
