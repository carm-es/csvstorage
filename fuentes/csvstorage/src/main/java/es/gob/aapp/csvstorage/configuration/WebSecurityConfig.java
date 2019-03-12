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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import es.gob.aapp.csvstorage.services.business.security.UserSecurityServiceImpl;



/**
 * Configuración de login por usuario y contrase�a.
 * 
 * @author carlos.munoz1
 * 
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserSecurityServiceImpl userSecurityService;


  /**
   * Configuración de permisos y página de login.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/", "/login/**", "/public/**", "/services/**", "/services11/**",
            "/cmis/atom11/**")
        .permitAll().antMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated().and()
        .formLogin().loginPage("/login").defaultSuccessUrl("/admin").permitAll().and().logout()
        .permitAll();

    http.csrf().disable();
  }

  /**
   * Configuración de los usuarios y roles de la aplicación.
   *
   * @param auth the auth
   * @throws Exception the exception
   */
  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    ShaPasswordEncoder enconderPass = new ShaPasswordEncoder(256);
    auth.userDetailsService(userSecurityService).passwordEncoder(enconderPass);
  }


  /**
   * Definición del bean para el Dialect de Thymeleaf.
   * 
   * @return
   */
  @Bean
  public SpringSecurityDialect securityDialect() {
    return new SpringSecurityDialect();
  }

}
