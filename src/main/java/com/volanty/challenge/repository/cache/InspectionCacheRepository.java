package com.volanty.challenge.repository.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class InspectionCacheRepository {


	@Autowired
	@Qualifier("inspectionRedisTemplate")
	private StringRedisTemplate inspectionRedisTemplate;
	  
	public void insertItem(String key, String value, Long timeout, TimeUnit timeUnit) {
		inspectionRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
	}

	public Set<String> getKeys(String pattern) {
		return inspectionRedisTemplate.keys(pattern);
	}

	public Boolean deleteItem(String key) {
		return inspectionRedisTemplate.delete(key);
	}

	public Long getExpire(String key) {
		return inspectionRedisTemplate.getExpire(key);
	}

}
