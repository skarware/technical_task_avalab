package dev.skaringa.avalab.configuration.redis;

import dev.skaringa.avalab.entity.OcrData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Getter
@Setter
@EnableCaching
@Configuration
@EnableRedisRepositories
public class RedisConfig {
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues();
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        return new LettuceConnectionFactory(
                redisProperties.getHost(),
                redisProperties.getPort());
    }

    @Bean
    public RedisTemplate<String, OcrData> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, OcrData> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public HashOperations<String, Long, OcrData> hashOperations(RedisTemplate<String, OcrData> redisTemplate) {
        return redisTemplate.opsForHash();
    }
}
