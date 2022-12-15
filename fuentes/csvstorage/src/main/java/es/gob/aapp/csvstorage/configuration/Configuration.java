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

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase de configuracion inicial de la aplicacion.
 * 
 */
public class Configuration {

  private Configuration() {}

  /**
   * Nombre de la variable con que arranca la aplicacion, donde se guardaran todos los ficheros de
   * propiedades.
   */
  public static final String CONFIG_PATH_VAR = "csvstorage.config.path";

  /** Nombre del fichero para log4j. */
  public static final String LOG4J_FILE_NAME = "log4j2.xml";
  public static final String LOG4J_CONFIGURATION = "log4j.configuration";

  /**
   * Establece configuraci�n inicial extra de la aplicaci�n.
   */
  public static void init() {

    // Configuracion base
    if (!propertyFromSystemOrEnvironmentExists(CONFIG_PATH_VAR)) {
      throw new IllegalArgumentException(
          "*** NO SE HA ENCONTRADO VALOR PARA LA VARIABLE " + CONFIG_PATH_VAR + " ***");
    }

    // Configuraci�n de log4j
    if (!propertyFromSystemOrEnvironmentExists(LOG4J_CONFIGURATION)) {
      setPropertyToSystemAndEnvironment(LOG4J_CONFIGURATION,
          getPropertyFromSystemOrEnvironment(CONFIG_PATH_VAR) + "/" + LOG4J_FILE_NAME);
    }

    System.out
        .println(CONFIG_PATH_VAR + "' ->" + getPropertyFromSystemOrEnvironment(CONFIG_PATH_VAR));// NOSONAR
    System.out.println(
        LOG4J_CONFIGURATION + "' ->" + getPropertyFromSystemOrEnvironment(LOG4J_CONFIGURATION));// NOSONAR

  }

  /**
   * @return devulve el valor de la propiedad si la encuentra: 1� en las propiedades de sistema y
   *         sino 2� en las variables de entorno
   */
  private static String getPropertyFromSystemOrEnvironment(String name) {
    return org.apache.commons.lang.StringUtils.isEmpty(System.getProperty(name))
        ? System.getenv(name)
        : System.getProperty(name);
  }

  /**
   * Setting system and env. property
   *
   * @param name property name
   * @param value property value
   */
  private static void setPropertyToSystemAndEnvironment(String name, String value) {
    // setting system property
    System.setProperty(name, value);
    // setting env. property
    Map<String, String> newenv = new HashMap<>(System.getenv());
    newenv.put(name, value);
    setEnv(newenv);
  }

  /**
   * @return devuelve true si la propiedad existe en el entorno o en el sistema
   */
  private static boolean propertyFromSystemOrEnvironmentExists(String name) {
    return !org.apache.commons.lang.StringUtils.isEmpty(getPropertyFromSystemOrEnvironment(name));
  }

  /**
   * Hay un problama estableciendo la variable <code>CONFIG_PATH_VAR_FOR_CLAVE</code>
   *
   * @see <a href='http://stackoverflow.com/a/7201825/3407287'>How do I set environment variables
   *      from Java?</a>
   * @param newenv
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  protected static void setEnv(Map<String, String> newenv) {
    try {
      Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
      Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
      theEnvironmentField.setAccessible(true);
      Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
      env.putAll(newenv);
      Field theCaseInsensitiveEnvironmentField =
          processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
      theCaseInsensitiveEnvironmentField.setAccessible(true);
      Map<String, String> cienv =
          (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
      cienv.putAll(newenv);
    } catch (NoSuchFieldException e) {// NOSONAR
      try {
        Class[] classes = Collections.class.getDeclaredClasses();
        Map<String, String> env = System.getenv();
        for (Class cl : classes) {
          if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {// NOSONAR
            Field field = cl.getDeclaredField("m");
            field.setAccessible(true);
            Object obj = field.get(env);
            Map<String, String> map = (Map<String, String>) obj;
            map.clear();
            map.putAll(newenv);
          }
        }
      } catch (Exception e2) {
        System.err.println(e2);// NOSONAR
      }
    } catch (Exception e1) {
      System.err.println(e1);// NOSONAR
    }
  }

  public static String getVariableData(String name) {
    String retorno = System.getenv(name);
    if (org.apache.commons.lang.StringUtils.isBlank(retorno)) {
      retorno = System.getProperty(name);
    }
    return retorno;
  }

}
