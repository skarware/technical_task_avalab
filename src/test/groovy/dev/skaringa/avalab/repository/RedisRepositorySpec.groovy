package dev.skaringa.avalab.repository

import dev.skaringa.avalab.SpecBaseIT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

class RedisRepositorySpec extends SpecBaseIT {
    @Autowired
    RedisRepository redisRepository

    @Autowired
    private RedisTemplate redisTemplate

    def "should set and get value from redis template"() {
        given: "redis with set entry"
        def key = "key"
        def value = "value"
        redisTemplate.opsForValue().set(key, value)

        when: "redis operation get for value invoked"
        def result = redisTemplate.opsForValue().get(key)

        then: "correct value returned"
        result == value
    }
}
