package dev.skaringa.avalab

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureEmbeddedDatabase
abstract class SpecBaseIT extends Specification {
    private static final def RANDOM = new Random()

    protected static def randomId() {
        return RANDOM.nextLong()
    }

    protected static def randomString() {
        return UUID.randomUUID().toString()
    }
}
