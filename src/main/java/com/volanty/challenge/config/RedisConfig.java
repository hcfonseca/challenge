package com.volanty.challenge.config;

import com.volanty.challenge.property.InspectionRedisProperty;
import com.volanty.challenge.property.RedisProperty;
import com.volanty.challenge.property.VisitRedisProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ComponentScan("com.volanty")
public class RedisConfig {

    @Autowired
    private VisitRedisProperty visitRedisProperty;

    @Autowired
    private InspectionRedisProperty inspectionRedisProperty;


    @Bean
    @Primary
    @Qualifier("visitRedisConnectionFactory")
    public RedisConnectionFactory visitRedisConnectionFactory(VisitRedisProperty visitRedisProperty) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();

        configuration.setDatabase(visitRedisProperty.getDatabase());
        configuration.setHostName(visitRedisProperty.getHost());
        configuration.setPort(visitRedisProperty.getPort());

	    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration);
	    return connectionFactory;
    }

    @Bean
    @Qualifier("inspectionRedisConnectionFactorys")
    public RedisConnectionFactory inspectionRedisConnectionFactory(InspectionRedisProperty inspectionRedisProperty) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();

        configuration.setDatabase(inspectionRedisProperty.getDatabase());
        configuration.setHostName(inspectionRedisProperty.getHost());
        configuration.setPort(inspectionRedisProperty.getPort());

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration);
        return connectionFactory;
    }


    @Bean(name = "visitRedisTemplate")
    public StringRedisTemplate visitStringRedisTemplate(@Qualifier("visitRedisConnectionFactory") RedisConnectionFactory cf) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(cf);
        return stringRedisTemplate;
    }

    @Bean(name = "inspectionRedisTemplate")
    public StringRedisTemplate inspectionStringRedisTemplate(@Qualifier("inspectionRedisConnectionFactory") RedisConnectionFactory cf) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(cf);
        return stringRedisTemplate;
    }

    private void setSerializer(RedisTemplate<String, String> template) {
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    }
}