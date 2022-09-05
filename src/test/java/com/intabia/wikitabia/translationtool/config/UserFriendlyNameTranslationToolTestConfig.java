package com.intabia.wikitabia.translationtool.config;

import com.intabia.wikitabia.model.util.UserFriendlyNameBeanPostProcessor;
import com.intabia.wikitabia.translationtool.testclasses.BeanWithUserFriendlyName;
import com.intabia.wikitabia.translationtool.testclasses.BeanWithoutUserFriendlyName;
import javax.sql.DataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@Profile("translation-tool-test")
public class UserFriendlyNameTranslationToolTestConfig {
  private static final String PACKAGES_TO_SCAN = "com.intabia.wikitabia.translationtool.testclasses";
  // TODO: найти способ избавиться от необходимости поднимать тестконтейнер с постгресом
  private static final String DATABASE_URL = "jdbc:tc:postgresql:14:///test-db?serverTimezone=UTC&TC_ROLE=postgres";

  @Bean
  ServletWebServerFactory servletWebServerFactory(){
    return new TomcatServletWebServerFactory();
  }

  @Bean
  BeanPostProcessor userFriendlyNameBeanPostProcessor(){
    return new UserFriendlyNameBeanPostProcessor();
  }

  @Bean
  BeanWithUserFriendlyName beanWithUserFriendlyName() {
    return new BeanWithUserFriendlyName();
  }

  @Bean
  BeanWithoutUserFriendlyName beanWithoutUserFriendlyName() {
    return new BeanWithoutUserFriendlyName();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory()
  {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan(PACKAGES_TO_SCAN);

    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);

    return em;
  }

  @Bean
  public DataSource dataSource(){
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setUrl(DATABASE_URL);
    return dataSource;
  }
}
