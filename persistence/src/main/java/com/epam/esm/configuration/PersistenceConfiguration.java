package com.epam.esm.configuration;

import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dao.mapper.TagMapper;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:database/mysql_dev.properties")
public class PersistenceConfiguration {

//    @Bean
//    @Profile("dev")
//    public static PropertySourcesPlaceholderConfigurer properties(){
//        PropertySourcesPlaceholderConfigurer pspc
//                = new PropertySourcesPlaceholderConfigurer();
//        Resource[] resources = new ClassPathResource[ ]
//                { new ClassPathResource( ) };
//        pspc.setLocations( resources );
//        pspc.setIgnoreUnresolvablePlaceholders( true );
//        return pspc;
//    }



    @Value("${db.driver_class_name}")
    String DB_DRIVER;
    @Value("${db.url}")
    String DB_URL;
    @Value("${db.username}")
    String DB_USERNAME;
    @Value("${db.password}")
    String DB_PASSWORD;
    @Value("${db.pool_initial_size}")
    int DB_POOL_SIZE;
    @Value("${db.pool_max_total}")
    int DB_POOL_MAX_SIZE;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setInitialSize(DB_POOL_SIZE);
        dataSource.setMaxTotal(DB_POOL_MAX_SIZE);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public GiftCertificateMapper giftCertificateMapper() {
        return new GiftCertificateMapper();
    }

    @Bean
    public TagMapper tagMapper() {
        return new TagMapper();
    }

}