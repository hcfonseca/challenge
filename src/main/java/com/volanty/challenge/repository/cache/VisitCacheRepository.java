package com.volanty.challenge.repository.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class VisitCacheRepository {


	@Autowired
	@Qualifier("visitRedisTemplate")
	private StringRedisTemplate visitRedisTemplate;

	public void insertItem(String key, String value, Long timeout, TimeUnit timeUnit) {
		visitRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
	}

	public Set<String> getKeys(String pattern) {
		return visitRedisTemplate.keys(pattern);
	}

	public Boolean deleteItem(String key) {
		return visitRedisTemplate.delete(key);
	}

	public Long getExpire(String key) {
		return visitRedisTemplate.getExpire(key);
	}
}
