package com.fletrax.tracking.commonpackage.configuration.redis;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

        @Bean
        public RedisCacheConfiguration cacheConfiguration() {
                return RedisCacheConfiguration.defaultCacheConfig()
                                // .entryTtl(Duration.ofMinutes(30))
                                .entryTtl(Duration.ofMinutes(5))
                                .disableCachingNullValues()
                                .serializeValuesWith(SerializationPair
                                                .fromSerializer(new GenericJackson2JsonRedisSerializer()));
        }
}
