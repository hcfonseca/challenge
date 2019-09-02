package com.volanty.challenge.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
@RedisHash("AvailableDays")
public class AvailableDays implements Serializable {

    private String key;
    private String value;

}
