package com.example.orderservice.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.DefaultRedisTypeMapper;
import org.springframework.data.redis.core.convert.MappingRedisConverter;
import org.springframework.data.redis.core.convert.RedisTypeMapper;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = "com.example.orderservice.Repository")
public class RedisConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.redis")
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    public RedisKeyValueTemplate redisKeyValueTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisMappingContext mappingContext = new RedisMappingContext();
        RedisTypeMapper typeMapper = new DefaultRedisTypeMapper(null);
        MappingRedisConverter converter = new MappingRedisConverter(mappingContext, null, null);
        return new RedisKeyValueTemplate(new RedisKeyValueAdapter(redisTemplate), mappingContext);
    }
}
