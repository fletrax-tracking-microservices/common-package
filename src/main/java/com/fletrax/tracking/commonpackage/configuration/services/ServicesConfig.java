package com.fletrax.tracking.commonpackage.configuration.services;

import com.fletrax.tracking.commonpackage.client.DeviceClientCommon;
import com.fletrax.tracking.commonpackage.client.DocumentClientCommon;
import com.fletrax.tracking.commonpackage.client.GeofenceClientCommon;
import com.fletrax.tracking.commonpackage.client.StatisticClientCommon;
import com.fletrax.tracking.commonpackage.client.UserClientCommon;
import com.fletrax.tracking.commonpackage.client.VehicleClientCommon;
import com.fletrax.tracking.commonpackage.configuration.ListsConfig;
import com.fletrax.tracking.commonpackage.configuration.SkynasaProperties;
import com.fletrax.tracking.commonpackage.utils.services.DeviceServiceCommon;
import com.fletrax.tracking.commonpackage.utils.services.DocumentServiceCommon;
import com.fletrax.tracking.commonpackage.utils.services.GeofenceServiceCommon;
import com.fletrax.tracking.commonpackage.utils.services.ListService;
import com.fletrax.tracking.commonpackage.utils.services.SkynasaService;
import com.fletrax.tracking.commonpackage.utils.services.StatisticServiceCommon;
import com.fletrax.tracking.commonpackage.utils.services.UserServiceCommon;
import com.fletrax.tracking.commonpackage.utils.services.VehicleServiceCommon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServicesConfig {

    @Bean
    public UserServiceCommon userServiceCommon(UserClientCommon userClient) {
        return new UserServiceCommon(userClient);
    }

    @Bean
    public DeviceServiceCommon deviceServiceCommon(DeviceClientCommon deviceClient) {
        return new DeviceServiceCommon(deviceClient);
    }

    @Bean
    public VehicleServiceCommon vehicleServiceCommon(VehicleClientCommon vehicleClient) {
        return new VehicleServiceCommon(vehicleClient);
    }

    @Bean
    public GeofenceServiceCommon geofenceServiceCommon(GeofenceClientCommon geofenceClient) {
        return new GeofenceServiceCommon(geofenceClient);
    }

    @Bean
    public SkynasaService skynasaService(
            RestTemplate restTemplate, SkynasaProperties skynasaProperties) {
        return new SkynasaService(restTemplate, skynasaProperties.getBaseUrl(), skynasaProperties.getLoginUrl(),
                skynasaProperties.getUsername(), skynasaProperties.getPassword(), skynasaProperties.getToken());
    }

    @Bean
    public ListService listService(ListsConfig listsConfig) {
        return new ListService(listsConfig);
    }

    @Bean
    public DocumentServiceCommon documentServiceCommon(DocumentClientCommon documentClient) {
        return new DocumentServiceCommon(documentClient);
    }

    @Bean
    public StatisticServiceCommon statisticsServiceCommon(StatisticClientCommon statisticsClient) {
        return new StatisticServiceCommon(statisticsClient);
    }
}
