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

import java.io.IOException;
import java.util.Properties;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import es.gob.aapp.csvstorage.job.OrganicUnitJob;

@Configuration
public class QuartzConfiguration {

  @Resource(name = "loadTablesProperties")
  private Properties loadTablesProperties;

  @Bean
  public Properties quartzProperties() throws IOException {
    PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
    propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
    propertiesFactoryBean.afterPropertiesSet();
    return propertiesFactoryBean.getObject();
  }

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource,
      ApplicationContext applicationContext) throws IOException {
    SchedulerFactoryBean scheduler = new SchedulerFactoryBean();

    scheduler.setOverwriteExistingJobs(true);
    scheduler.setTriggers(cronTriggerFactoryBean().getObject());
    scheduler.setDataSource(dataSource);
    scheduler.setQuartzProperties(quartzProperties());

    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    scheduler.setJobFactory(jobFactory);

    return scheduler;
  }


  @Bean
  public JobDetailFactoryBean jobDetailFactoryBean() {
    JobDetailFactoryBean factory = new JobDetailFactoryBean();

    factory.setJobClass(OrganicUnitJob.class);
    factory.setDurability(true);
    factory.setName(loadTablesProperties.getProperty("job.unidadesOrganicas.name"));
    factory.setGroup(loadTablesProperties.getProperty("job.unidadesOrganicas.group"));

    return factory;
  }

  @Bean
  public CronTriggerFactoryBean cronTriggerFactoryBean() {
    CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
    stFactory.setJobDetail(jobDetailFactoryBean().getObject());
    stFactory.setStartDelay(
        Long.valueOf(loadTablesProperties.getProperty("job.unidadesOrganicas.startDelay")));
    stFactory.setName(loadTablesProperties.getProperty("job.unidadesOrganicas.name"));
    stFactory.setGroup(loadTablesProperties.getProperty("job.unidadesOrganicas.group"));
    stFactory
        .setCronExpression(loadTablesProperties.getProperty("job.unidadesOrganicas.expression"));
    return stFactory;

  }

}
