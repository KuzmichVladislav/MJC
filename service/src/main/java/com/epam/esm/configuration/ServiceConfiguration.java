package com.epam.esm.configuration;

import com.epam.esm.dto.mapper.GiftCertificateDtoMapper;
import com.epam.esm.dto.mapper.TagDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.epam.esm")
public class ServiceConfiguration {

    @Bean
    public GiftCertificateDtoMapper giftCertificateDtoMapper() {
        return new GiftCertificateDtoMapper();
    }

    @Bean
    public TagDtoMapper tagDtoMapper() {
        return new TagDtoMapper();
    }
}
