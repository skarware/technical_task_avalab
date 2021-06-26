package dev.skaringa.avalab.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import spock.mock.DetachedMockFactory

@Configuration
@Profile("test")
class TestConfiguration {
    private static final def DETACHED_MOCK_FACTORY = new DetachedMockFactory()
}
