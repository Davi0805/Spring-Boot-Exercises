package com.example.analysis.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.DefaultRedisTypeMapper;
import org.springframework.data.redis.core.convert.MappingRedisConverter;
import org.springframework.data.redis.core.convert.RedisTypeMapper;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
//@EnableRedisRepositories("com.example.analysis.Repository")
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration configs = new RedisStandaloneConfiguration();
        configs.setHostName(host);
        configs.setPort(port);
        return new LettuceConnectionFactory(configs);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate()
    {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setEnableTransactionSupport(true);
        template.setConnectionFactory(lettuceConnectionFactory());
        return template;
    }

    @Bean
    public RedisKeyValueTemplate redisKeyValueTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisMappingContext mappingContext = new RedisMappingContext();
        RedisTypeMapper typeMapper = new DefaultRedisTypeMapper(null);
        MappingRedisConverter converter = new MappingRedisConverter(mappingContext, null, null);
        converter.afterPropertiesSet();
        return new RedisKeyValueTemplate(new RedisKeyValueAdapter(redisTemplate), mappingContext);
    }
}
