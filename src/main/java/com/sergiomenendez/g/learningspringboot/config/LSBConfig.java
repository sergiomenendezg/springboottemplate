package com.sergiomenendez.g.learningspringboot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class LSBConfig {

  @Value("${spring.datasource.url}")
  private String URL = "url";
  @Value("${spring.datasource.username}")
  private String USER = "user";
  @Value("${spring.datasource.driver}")
  private String DRIVER;
  @Value("${spring.datasource.password}")
  private String PASSWORD;

  @Bean
  DataSource dataSource() {
    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
    driverManagerDataSource.setUrl(URL);
    driverManagerDataSource.setUsername(USER);
    driverManagerDataSource.setPassword(PASSWORD);
    driverManagerDataSource.setDriverClassName(DRIVER);
    return driverManagerDataSource;
  }
}