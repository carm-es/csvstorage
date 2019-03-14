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

import java.util.Properties;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Clase de configuración de base de datos y acceso.
 * 
 * @author carlos.munoz1
 *
 */
@PropertySource("file:${csvstorage.config.path}/datasource.properties")
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

  @Value("${database.jndi}")
  private String databaseJndi;

  @Value("${database.driver}")
  private String databaseDriver;

  @Value("${database.url}")
  private String databaseUrl;

  @Value("${database.username}")
  private String databaseUsername;

  @Value("${database.password}")
  private String databasePassword;

  @Value("${jpa.generate-ddl}")
  private String jpaGenerateDdl;

  @Value("${jpa.hibernate.ddl-auto}")
  private String jpaHibernateDdlAuto;

  @Value("${jpa.show-sql}")
  private String jpaShowSql;

  @Value("${jpa.database-platform}")
  private String jpaDatabasePlatform;

  @Value("${jpa.database}")
  private String jpaDatabase;


  /**
   * Bean datasource
   *
   * @return dataSource
   */
  @Bean
  public DataSource dataSource() {
    // FIX #11 (https://github.com/carm-es/csvstorage/issues/11)
    // FIX #2 (https://gitlab.carm.es/SIAC/PAECARM/csvstorage/issues/2)
    try {
      return (DataSource) new InitialContext().lookup(databaseJndi);
    } catch (Exception x) {
      // Ignorar la excepción y salir según comportamiento original
    }
    return DataSourceBuilder.create().driverClassName(databaseDriver).username(databaseUsername)
        .password(databasePassword).url(databaseUrl).build();
  }

  /**
   * Bean JpaVendorAdapter
   * 
   * @return JpaVendorAdapter
   */
  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(Boolean.parseBoolean(jpaGenerateDdl));
    vendorAdapter.setShowSql(Boolean.parseBoolean(jpaShowSql));
    vendorAdapter.setDatabasePlatform(jpaDatabasePlatform);
    vendorAdapter.setDatabase(Database.valueOf(jpaDatabase));
    return vendorAdapter;
  }


  /**
   * Bean sessionFactory
   * 
   * @return LocalSessionFactoryBean
   */
  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    // hibernateProperties - Propiedades para hibernate, propiedades para usar sequencias oracle
    sessionFactory.setHibernateProperties(new Properties() {
      private static final long serialVersionUID = 1L;
      {
        setProperty("hibernate.jdbc.use_get_generated_keys", Boolean.TRUE.toString());
      }
    });
    return sessionFactory;
  }

}
