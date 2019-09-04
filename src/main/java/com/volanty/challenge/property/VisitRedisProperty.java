package com.volanty.challenge.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.redis.visit")
public class VisitRedisProperty extends RedisProperty {

}
