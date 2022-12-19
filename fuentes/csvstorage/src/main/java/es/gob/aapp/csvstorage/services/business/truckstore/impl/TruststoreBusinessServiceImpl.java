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

package es.gob.aapp.csvstorage.services.business.truckstore.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import es.gob.aapp.csvstorage.model.object.truststore.TruststoreObject;
import es.gob.aapp.csvstorage.services.business.truckstore.TruststoreBusinessService;
import es.gob.aapp.csvstorage.services.exception.ServiceException;
import es.gob.aapp.csvstorage.webservices.document.model.CSVStorageException;

/**
 * Implementacion business.
 * 
 * @author carlos.munoz1
 * 
 */

@Service("certBusinessService")
@PropertySource("file:${csvstorage.config.path}/ws-security.properties")
public class TruststoreBusinessServiceImpl implements TruststoreBusinessService {

  private static final Logger LOG = LogManager.getLogger(TruststoreBusinessServiceImpl.class);

  @Value("${org.apache.wss4j.crypto.merlin.truststore.file}")
  private String trustStoreFile;

  @Value("${org.apache.wss4j.crypto.merlin.truststore.password}")
  private String trustStorePassword;

  @Value("${org.apache.wss4j.crypto.merlin.truststore.type}")
  private String trustStoreType;

  @PostConstruct
  public void configTruststore() {
    setProperties();
  }

  void setProperties() {
    System.setProperty("javax.net.ssl.trustStore", trustStoreFile);
    System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
    System.setProperty("javax.net.ssl.trustStoreType", trustStoreType);
  }

  public String getTrustStoreFile() {
    return trustStoreFile;
  }


  public String getTrustStorePassword() {
    return trustStorePassword;
  }


  public String getTrustStoreType() {
    return trustStoreType;
  }

  @Override
  public List<String> findAll() throws ServiceException {

    List<String> listaCerts = new ArrayList<String>();

    FileInputStream is = null;
    try {
      /* your cert path */
      is = new FileInputStream(trustStoreFile);

      KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());

      keystore.load(is, trustStorePassword.toCharArray());

      listaCerts = Collections.list(keystore.aliases());

    } catch (FileNotFoundException e) {
      new CSVStorageException("No se ha encontrado el fichero jks");
    } catch (KeyStoreException e) {
      new CSVStorageException("Se ha producido un error al actualizar el almacen de firmas");
    } catch (NoSuchAlgorithmException e) {
      new CSVStorageException("Se ha producido un error con la password");
    } catch (CertificateException e) {
      new CSVStorageException("Se ha producido un error al actualizar el almacen de firmas");
    } catch (IOException e) {
      new CSVStorageException("Se ha producido un error al modificar el fichero jks");
    } finally {
      try {
        if (is != null) {
          is.close();
        }
      } catch (IOException e) {
        new CSVStorageException("Se ha producido un error al modificar el fichero jks");
      }
    }

    return listaCerts;
  }


  public TruststoreObject save(TruststoreObject certificado) throws ServiceException {

    LOG.debug("[INI] Entramos en save() para guardar los datos del certificado. ");

    FileInputStream is = null;
    FileOutputStream out = null;
    try {
      /* your cert path */
      is = new FileInputStream(trustStoreFile);

      KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
      keystore.load(is, trustStorePassword.toCharArray());

      String alias = certificado.getAlias();
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      Certificate certs = cf.generateCertificate(certificado.getCertificado());

      // Add the certificate
      keystore.setCertificateEntry(alias, certs);

      // Save the new keystore contents
      out = new FileOutputStream(trustStoreFile);
      keystore.store(out, trustStorePassword.toCharArray());

      certificado
          .setSerialNumbre(((X509Certificate) certs).getSerialNumber().toString(16).toUpperCase());

    } catch (FileNotFoundException e) {
      throw new ServiceException("No se ha encontrado el fichero jks");
    } catch (KeyStoreException e) {
      throw new ServiceException("Se ha producido un error al actualizar el almacen de firmas");
    } catch (NoSuchAlgorithmException e) {
      throw new ServiceException("Se ha producido un error con la password");
    } catch (CertificateException e) {
      throw new ServiceException("Se ha producido un error al actualizar el almacen de firmas");
    } catch (IOException e) {
      throw new ServiceException("Se ha producido un error al modificar el fichero jks");
    } finally {
      try {
        if (is != null) {
          is.close();
        }
        if (out != null) {
          out.flush();
          out.close();
        }
      } catch (IOException e) {
        throw new ServiceException("Se ha producido un error al modificar el fichero jks");
      }
    }

    setProperties();

    return certificado;


  }

  @Override
  public TruststoreObject delete(TruststoreObject certificado) throws ServiceException {
    LOG.debug("[INI] Entramos en delete() para borrar los certificados. ");

    FileInputStream is = null;
    FileOutputStream out = null;
    try {
      /* your cert path */
      is = new FileInputStream(trustStoreFile);

      KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
      keystore.load(is, trustStorePassword.toCharArray());

      // Delete the certificate
      keystore.deleteEntry(certificado.getAlias());

      // Save the new keystore contents
      out = new FileOutputStream(trustStoreFile);
      keystore.store(out, trustStorePassword.toCharArray());

    } catch (FileNotFoundException e) {
      throw new ServiceException("No se ha encontrado el fichero jks");
    } catch (KeyStoreException e) {
      throw new ServiceException("Se ha producido un error al actualizar el almacen de firmas");
    } catch (NoSuchAlgorithmException e) {
      throw new ServiceException("Se ha producido un error con la password");
    } catch (CertificateException e) {
      throw new ServiceException("Se ha producido un error al actualizar el almacen de firmas");
    } catch (IOException e) {
      throw new ServiceException("Se ha producido un error al modificar el fichero jks");
    } finally {
      try {
        if (is != null) {
          is.close();
        }
        if (out != null) {
          out.close();
        }

      } catch (IOException e) {
        throw new ServiceException("Se ha producido un error al modificar el fichero jks");
      }
    }

    System.setProperty("javax.net.ssl.trustStore", trustStoreFile);
    System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
    System.setProperty("javax.net.ssl.trustStoreType", trustStoreType);

    LOG.debug("[FIN] Salimos de delete certificado. ");

    return certificado;


  }


  public TruststoreObject getDetail(String alias) throws ServiceException {

    LOG.debug("[INI] Entramos en getDetail() para obtener los datos del certificado. ");
    TruststoreObject almacenObject = null;

    FileInputStream is = null;
    try {

      /* your cert path */
      is = new FileInputStream(trustStoreFile);

      KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
      keystore.load(is, trustStorePassword.toCharArray());

      X509Certificate certificate = (X509Certificate) keystore.getCertificate(alias);

      if (certificate != null) {
        almacenObject = new TruststoreObject();
        almacenObject.setAlias(alias);
        almacenObject.setSerialNumbre(certificate.getSerialNumber().toString(16).toUpperCase());
        almacenObject.setAsunto(certificate.getSubjectX500Principal().getName());
        almacenObject.setEmisor(certificate.getIssuerX500Principal().getName());
        almacenObject.setValidoDesde(certificate.getNotBefore());
        almacenObject.setValidoHasta(certificate.getNotAfter());
      }

    } catch (FileNotFoundException e) {
      throw new ServiceException("No se ha encontrado el fichero jks");
    } catch (KeyStoreException e) {
      throw new ServiceException("Se ha producido un error al actualizar el almacen de firmas");
    } catch (NoSuchAlgorithmException e) {
      throw new ServiceException("Se ha producido un error con la password");
    } catch (CertificateException e) {
      throw new ServiceException("Se ha producido un error al actualizar el almacen de firmas");
    } catch (IOException e) {
      throw new ServiceException("Se ha producido un error al modificar el fichero jks");
    } finally {
      try {
        if (is != null) {
          is.close();
        }
      } catch (IOException e) {
        throw new ServiceException("Se ha producido un error al modificar el fichero jks");
      }
    }

    return almacenObject;


  }
}
