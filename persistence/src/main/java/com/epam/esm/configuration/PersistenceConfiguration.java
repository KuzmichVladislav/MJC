package com.epam.esm.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * The Class PersistenceConfiguration contains a spring configuration for persistence.
 */
@Configuration
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
public class PersistenceConfiguration {

    @Value("${db.driver_class_name}")
    private String dbDriver;
    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.username}")
    private String dbUsername;
    @Value("${db.password}")
    private String dbPassword;
    @Value("${db.pool_initial_size}")
    private int dbPoolSize;
    @Value("${db.pool_max_total}")
    private int dbPoolMaxSize;

    /**
     * Properties dev to map the bean to the develop profile
     *
     * @return the property sources placeholder configurer
     */
    @Bean
    @Profile("dev")
    public static PropertySourcesPlaceholderConfigurer propertiesDev() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        ClassPathResource classPathResource = new ClassPathResource("database/mysql_dev.properties");
        configurer.setLocations(classPathResource);
        return configurer;
    }

    /**
     * Properties prod to map the bean to the production profile
     *
     * @return the property sources placeholder configurer
     */
    @Bean
    @Profile("prod")
    public static PropertySourcesPlaceholderConfigurer propertiesProd() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        ClassPathResource classPathResource = new ClassPathResource("database/mysql_prod.properties");
        configurer.setLocations(classPathResource);
        return configurer;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setInitialSize(dbPoolSize);
        dataSource.setMaxTotal(dbPoolMaxSize);
        return dataSource;
    }
}
