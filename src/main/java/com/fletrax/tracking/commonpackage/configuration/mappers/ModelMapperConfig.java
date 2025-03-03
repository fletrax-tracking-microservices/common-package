package com.fletrax.tracking.commonpackage.configuration.mappers;

import com.fletrax.tracking.commonpackage.utils.mappers.ModelMapperManager;
import com.fletrax.tracking.commonpackage.utils.mappers.ModelMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig
{
    @Bean
    public ModelMapper getModelMapper()
    {
        return new ModelMapper();
    }

    @Bean
    public ModelMapperService getModelMapperService(ModelMapper mapper)
    {
        return new ModelMapperManager(mapper);
    }
}