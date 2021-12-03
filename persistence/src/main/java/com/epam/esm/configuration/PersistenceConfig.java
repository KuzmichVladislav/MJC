package com.epam.esm.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"com.epam.esm"})
@PropertySource("classpath:database/mysql.properties")
public class PersistenceConfig implements WebMvcConfigurer {

    @Value("${db.driverClassName}")
    String DB_DRIVER;
    @Value("${db.url}")
    String DB_URL;
    @Value("${db.username}")
    String DB_USERNAME;
    @Value("${db.password}")
    String DB_PASSWORD;
    @Value("${pool.initialSize}")
    int DB_POOL_SIZE;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setInitialSize(DB_POOL_SIZE);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}