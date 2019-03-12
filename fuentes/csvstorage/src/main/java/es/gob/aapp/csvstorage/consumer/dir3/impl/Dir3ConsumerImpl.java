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

package es.gob.aapp.csvstorage.consumer.dir3.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.xml.bind.JAXBException;
import javax.xml.ws.BindingProvider;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.client.ws.dir3.FormatoFichero;
import es.gob.aapp.csvstorage.client.ws.dir3.ObjectFactory;
import es.gob.aapp.csvstorage.client.ws.dir3.RespuestaWS;
import es.gob.aapp.csvstorage.client.ws.dir3.SD01UNDescargaUnidades;
import es.gob.aapp.csvstorage.client.ws.dir3.SD01UNDescargaUnidadesService;
import es.gob.aapp.csvstorage.client.ws.dir3.TipoConsultaUO;
import es.gob.aapp.csvstorage.client.ws.dir3.UnidadesWs;
import es.gob.aapp.csvstorage.consumer.dir3.Dir3Consumer;
import es.gob.aapp.csvstorage.consumer.dir3.model.ObjectFactoryUnidades;
import es.gob.aapp.csvstorage.consumer.dir3.model.Unidades;
import es.gob.aapp.csvstorage.consumer.exception.ConsumerWSException;
import es.gob.aapp.csvstorage.dao.entity.unit.UnitEntity;
import es.gob.aapp.csvstorage.dao.repository.BaseRepository;
import es.gob.aapp.csvstorage.dao.repository.impl.BaseRepositoryImpl;
import es.gob.aapp.csvstorage.util.constants.Constants;
import es.gob.aapp.csvstorage.util.file.FileUtil;
import es.gob.aapp.csvstorage.util.xml.JAXBMarshaller;
import reactor.util.IoUtils;


// TODO: Auto-generated Javadoc
/**
 * Implementación de la clase consumidora de los servicios web de DIR3.
 * 
 * @author serena.plaza
 * 
 */

/**
 * @author eSoluzion
 *
 */
@Service
@PropertySource("file:${csvstorage.config.path}/dir3.properties")
public class Dir3ConsumerImpl implements Dir3Consumer {

  /** Logger de la clase. */
  private static final Logger LOG = Logger.getLogger(Dir3ConsumerImpl.class);
  public static final String DIR3_WS_RUTA_DESCARGA_ZIP = "dir3.ws.ruta.descarga.zip";
  public static final String ERROR_AL_LLAMAR_AL_SERVICIO_RESPUESTA_NULA =
      "Error al llamar al servicio. Respuesta nula. ";

  /** The entity manager. */
  @Autowired
  EntityManager entityManager;

  /** Contexto para properties. */
  @Autowired
  private ApplicationContext context;

  /** Objeto para inyectar los servicios de descarga de unidades. */
  private SD01UNDescargaUnidades descargaUnidadesService;

  /** The unit repository. */
  private BaseRepository<UnitEntity, Long> unitRepository;

  /**
   * ObjectFactory para crear instancias de objetos del cliente del servicio web dir3.
   */
  private static final ObjectFactory OBJECT_FACTORY_DIR3 = new ObjectFactory();

  /**
   * ObjectFactory para crear instancias de objetos para los datos de las unidades.
   */
  private static final ObjectFactoryUnidades OBJECT_FACTORY_UNIDADES = new ObjectFactoryUnidades();

  /**
   * Cadena con el nombre que vamos a dar al fichero ZIP descargado del ws.
   */
  private static final String NOMBRE_FICHERO_ZIP = "descargaUnidadesDir3" + Constants.FORMAT_ZIP;

  /** Arrays de niveles de adminsitracion de dir3. */
  public static final String[] NIVELES_DIR3_ADMINISTRACION =
      {"1", "2", "3", "4", "5", "7", "8", "9"};

  /** The ruta temp. */
  @Value("${dir3.ws.ruta.descarga.zip}")
  private String rutaTemp;

  /** The path descompresion. */
  @Value("${dir3.ws.ruta.descarga.unzip}")
  private String pathDescompresion;

  // @PostConstruct
  private void configure() {
    LOG.info("Inicio de configuración. ");

    String urlPath =
        context.getEnvironment().getProperty("dir3.ws.SD01UNDescargaUnidades.url.wsdl");
    if (descargaUnidadesService == null) {
      unitRepository = new BaseRepositoryImpl<>(UnitEntity.class, entityManager);
      try {
        URL url = new URL(urlPath);

        SD01UNDescargaUnidadesService service1 = new SD01UNDescargaUnidadesService(url);
        descargaUnidadesService = service1.getPort(SD01UNDescargaUnidades.class);

        BindingProvider bp = (BindingProvider) descargaUnidadesService;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url.toExternalForm());

      } catch (MalformedURLException e) {
        LOG.error("No se puede crear la URL del servicio de DIR3 " + urlPath, e);
      }
    }

    LOG.info("Fin de configuración. ");
  }

  public Unidades exportOrganicUnitsToXML() throws ConsumerWSException {

    LOG.info("[INI] Entramos en exportOrganicUnitsXML. ");

    Unidades listaRetorno = null;

    UnitEntity unidadOrganica = null;

    configure();

    try {
      unidadOrganica = (UnitEntity) unitRepository.findFirstResult(new UnitEntity(),
          new Sort(Sort.Direction.DESC, "fechaCreacion"));
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      LOG.error("Error al recuperar el primer resultado", e);
    }

    // 1. Creamos el objeto UnidadesWs a enviar con la fecha para saber si
    // es un volcado completo o incremenental.
    Calendar c = Calendar.getInstance();
    if (unidadOrganica != null && unidadOrganica.getFechaCreacion() != null) {
      c.setTime(unidadOrganica.getFechaCreacion());
      c.add(Calendar.DATE, 1);
    }

    LOG.info("Creamos la petici�n: el objeto UnidadesWs a enviar. ");
    UnidadesWs unidadesRequest = creaPeticionServicio(
        unidadOrganica != null && unidadOrganica.getFechaCreacion() != null ? c.getTime() : null);

    // 2. Llamada al servicio
    LOG.info("Llamamos al servicio de descarga de unidades... ");

    RespuestaWS respuesta = descargaUnidadesService.exportar(unidadesRequest);

    // 3. Procesamos la salida
    LOG.info("Procesamos la salida del servicio... ");
    if (respuesta != null) {
      LOG.info("Respuesta: " + respuesta.getCodigo() + "  " + respuesta.getDescripcion());
      if (!StringUtils.isEmpty(respuesta.getFichero())) {
        listaRetorno = procesaRespuestaServicio(respuesta);
      } else {
        LOG.error("Ha ocurrido un error. Código: " + respuesta.getCodigo() + "; Descripció�n: "
            + respuesta.getDescripcion());
        throw new ConsumerWSException("No hay nada que actualizar. Dir3 ya actualizado");
      }
    } else {
      LOG.error(ERROR_AL_LLAMAR_AL_SERVICIO_RESPUESTA_NULA);
      throw new ConsumerWSException(ERROR_AL_LLAMAR_AL_SERVICIO_RESPUESTA_NULA);
    }

    LOG.info("[FIN] Salimos de exportOrganicUnitsXML. ");

    return listaRetorno;
  }


  public Unidades exportOrganicUnitsToXML(String fecha) throws ConsumerWSException {

    LOG.info("[INI] Entramos en exportOrganicUnitsXML. ");

    Unidades listaRetorno = null;

    UnitEntity unidadOrganica = null;

    configure();

    // 1. Creamos el objeto UnidadesWs a enviar con la fecha para saber si
    // es un volcado completo o incremenental.
    Calendar c = Calendar.getInstance();
    if (fecha != null && fecha != null) {
      try {
        c.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(fecha));
      } catch (ParseException e) {
        LOG.error("Error transformando la fecha  " + e.getMessage());
      }
      // c.add(Calendar.DATE, 1);
    }
    LOG.info("Fecha: " + c.getTime());

    LOG.info("Creamos la peticion: el objeto UnidadesWs a enviar. ");
    UnidadesWs unidadesRequest = creaPeticionServicio(c.getTime());

    // 2. Llamada al servicio
    LOG.info("Llamamos al servicio de descarga de unidades... ");

    RespuestaWS respuesta = descargaUnidadesService.exportar(unidadesRequest);

    // 3. Procesamos la salida
    LOG.info("Procesamos la salida del servicio... ");
    if (respuesta != null) {
      LOG.info("Respuesta: " + respuesta.getCodigo() + "  " + respuesta.getDescripcion());
      if (!StringUtils.isEmpty(respuesta.getFichero())) {
        listaRetorno = procesaRespuestaServicio(respuesta);
      } else {
        LOG.error("Ha ocurrido un error. Código: " + respuesta.getCodigo() + "; Descripció�n: "
            + respuesta.getDescripcion());
        throw new ConsumerWSException("No hay nada que actualizar. Dir3 ya actualizado");
      }
    } else {
      LOG.error(ERROR_AL_LLAMAR_AL_SERVICIO_RESPUESTA_NULA);
      throw new ConsumerWSException(ERROR_AL_LLAMAR_AL_SERVICIO_RESPUESTA_NULA);
    }

    LOG.info("[FIN] Salimos de exportOrganicUnitsXML. ");

    return listaRetorno;
  }

  /*
   * (non-Javadoc)
   * 
   * @see es.gob.aapp.csvstorage.consumer.dir3.Dir3Consumer#exportOrganicUnitsXML()
   */
  public Unidades exportAllOrganicUnitsToXML() throws ConsumerWSException {

    LOG.info("[INI] Entramos en exportAllOrganicUnitsToXML. ");

    Unidades listaRetorno = null;

    configure();

    SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
    Date fechaInicio = new Date();
    try {
      fechaInicio = format.parse("01/01/1900");
    } catch (ParseException e) {
      LOG.error("Formato de fecha erroneo");
    }
    // 1. Creamos el objeto UnidadesWs a enviar con la fecha para saber si
    // es un volcado completo o incremenental.
    Calendar c = Calendar.getInstance();
    c.setTime(fechaInicio);
    c.add(Calendar.DATE, 1);

    LOG.info("Creamos la petición: el objeto UnidadesWs a enviar. ");
    UnidadesWs unidadesRequest = creaPeticionServicio(c.getTime());

    // 2. Llamada al servicio
    LOG.info("Llamamos al servicio de descarga de unidades... ");

    RespuestaWS respuesta = descargaUnidadesService.exportar(unidadesRequest);

    // 3. Procesamos la salida
    LOG.info("Procesamos la salida del servicio... ");
    if (respuesta != null) {
      LOG.info("Respuesta: " + respuesta.getCodigo() + "  " + respuesta.getDescripcion());
      if (!StringUtils.isEmpty(respuesta.getFichero())) {
        listaRetorno = procesaRespuestaServicio(respuesta);
      } else {
        LOG.error("Ha ocurrido un error. Código: " + respuesta.getCodigo() + "; Descripció�n: "
            + respuesta.getDescripcion());
        throw new ConsumerWSException("No hay nada que actualizar. Dir3 ya actualizado");
      }
    } else {
      LOG.error(ERROR_AL_LLAMAR_AL_SERVICIO_RESPUESTA_NULA);
      throw new ConsumerWSException(ERROR_AL_LLAMAR_AL_SERVICIO_RESPUESTA_NULA);
    }

    LOG.info("[FIN] Salimos de exportAllOrganicUnitsToXML. ");

    return listaRetorno;
  }

  /**
   * Crea peticion servicio.
   *
   * @param fecha the fecha
   * @return the unidades ws
   */
  private UnidadesWs creaPeticionServicio(Date fecha) {

    // Objeto UnidadesWs
    UnidadesWs unidadesWs = OBJECT_FACTORY_DIR3.createUnidadesWs();
    unidadesWs.setUsuario(context.getEnvironment().getProperty("dir3.ws.usuario"));
    unidadesWs.setClave(context.getEnvironment().getProperty("dir3.ws.password"));
    unidadesWs.setFormatoFichero(FormatoFichero.XML);
    unidadesWs.setTipoConsulta(TipoConsultaUO.UNIDADES);
    // Necesitamos un dato fecha para hacer incremental los datos o un
    // volcado total si no viene la fecha
    if (fecha != null) {
      SimpleDateFormat format =
          new SimpleDateFormat(Constants.ConstantesDir3Consumer.WS_FORMATO_FECHA);
      unidadesWs.setFechaInicio(format.format(fecha));
    }

    return unidadesWs;

  }// fin creaPeticionServicio

  /**
   * Metodo para procesar la salida del servicio de Descarga de Unidades del DIR3. Devuelve una
   * lista de unidades parseadas a partir del XML obtenido.
   *
   * @param respuesta the respuesta
   * @return lista de Unidades
   * @throws ConsumerWSException the consumer ws exception
   */
  private Unidades procesaRespuestaServicio(RespuestaWS respuesta) throws ConsumerWSException {

    Unidades listaRetorno;

    // 1. Obtengo array de bytes a partir de la cadena con el ZIP en base64
    LOG.info("Obtengo array de bytes a partir de la cadena con el ZIP en base64.");
    byte[] zipData = Base64.decodeBase64(respuesta.getFichero());

    // 2. Borro ficheros temporales anteriores
    LOG.info("Borrado de fichero ZIP si existe.");
    FileUtil.deleteFile(context.getEnvironment().getProperty(DIR3_WS_RUTA_DESCARGA_ZIP),
        NOMBRE_FICHERO_ZIP);

    // 3. Genero el fichero ZIP y lo guardo en ruta
    LOG.info("Genero el fichero ZIP y guardo en ruta.");
    FileOutputStream fileOut = null;
    try {
      fileOut = new FileOutputStream(
          context.getEnvironment().getProperty(DIR3_WS_RUTA_DESCARGA_ZIP) + NOMBRE_FICHERO_ZIP);
      fileOut.write(zipData);

    } catch (FileNotFoundException e) {
      LOG.error("Error al encontrar fichero.");
      throw new ConsumerWSException("Error al crear fichero.", e);
    } catch (IOException e) {
      LOG.error("Error al crear el fichero.");
      throw new ConsumerWSException("Error al crear el fichero.", e);
    } finally {
      IoUtils.closeQuietly(fileOut);
    }

    try {

      // 4. Descomprimo el fichero ZIP
      LOG.info("Descomprimo el fichero ZIP.");
      FileUtil.unZipFile(
          context.getEnvironment().getProperty(DIR3_WS_RUTA_DESCARGA_ZIP) + NOMBRE_FICHERO_ZIP,
          context.getEnvironment().getProperty("dir3.ws.ruta.descarga.unzip"));

      // 5. Proceso el XML de Unidades para convertirlo al objeto JAXB
      LOG.info("Proceso el XML de Unidades para convertirlo al objeto JAXB.");
      listaRetorno = OBJECT_FACTORY_UNIDADES.createUnidades();
      listaRetorno = JAXBMarshaller.unmarshallRootElementFich(
          context.getEnvironment().getProperty("dir3.ws.ruta.descarga.unzip")
              + context.getEnvironment().getProperty("dir3.ws.nombre.xml.unidades"),
          Unidades.class);
    } catch (JAXBException | IOException e) {
      LOG.error("Error al parsear XML a objeto JAXB.", e);
      throw new ConsumerWSException("Error al convertir XML en objeto JAXB de unidades.", e);
    }

    return listaRetorno;
  }

}
