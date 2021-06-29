package dev.skaringa.avalab.configuration

import dev.skaringa.avalab.entity.OcrData
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.core.HashOperations
import spock.mock.DetachedMockFactory

@Configuration
@Profile("test")
class TestConfiguration {
    private static final def DETACHED_MOCK_FACTORY = new DetachedMockFactory()

    @Bean
    @Primary
    HashOperations<String, Long, OcrData> hashOperationsSpy(HashOperations hashOperations) {
        return DETACHED_MOCK_FACTORY.Spy(hashOperations)
    }
}
