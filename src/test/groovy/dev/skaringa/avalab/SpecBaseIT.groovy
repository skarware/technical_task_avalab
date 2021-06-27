package dev.skaringa.avalab

import groovy.json.JsonGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

import static java.time.format.DateTimeFormatter.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
abstract class SpecBaseIT extends Specification {
    private static final def RANDOM = new Random()

    protected static final def JSON_GENERATOR = new JsonGenerator.Options()
            .addConverter(LocalDateTime) { it.format(ISO_LOCAL_DATE_TIME) }
            .addConverter(LocalDate) { it.format(ISO_LOCAL_DATE) }
            .addConverter(LocalTime) { it.format(ISO_LOCAL_TIME) }
            .build()
    protected static final DateTimeFormatter JSON_DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .append(ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(ISO_LOCAL_TIME)
            .toFormatter()

    @Autowired
    protected MockMvc mockMvc

    protected static def randomId() {
        return RANDOM.nextLong()
    }

    protected static def randomString() {
        return UUID.randomUUID().toString()
    }
}
