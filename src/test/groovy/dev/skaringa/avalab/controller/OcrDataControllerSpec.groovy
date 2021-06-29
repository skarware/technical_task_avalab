package dev.skaringa.avalab.controller

import dev.skaringa.avalab.SpecBaseIT
import dev.skaringa.avalab.repository.OcrDataRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.jdbc.JdbcTestUtils

import java.time.LocalDateTime

import static java.time.format.DateTimeFormatter.ofPattern
import static org.hamcrest.Matchers.containsInAnyOrder
import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class OcrDataControllerSpec extends SpecBaseIT {
    @Autowired
    private OcrDataRepository ocrDataRepository

    @Autowired
    private JdbcTemplate jdbcTemplate

    def setup() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "ava_lab.ocr_data")
    }

    def "GET /api/all should return all ocr data entries from database"() {
        given: "multiple ocr data entries in database exist"
        def ocrData = createOcrData()
        createOcrData()
        createOcrData()

        when: "GET /api/all is called"
        def response = mockMvc.perform(get("/api/all"))

        then: "ocr data is returned"
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(3)))
                .andExpect(jsonPath('$.[0].id').value(ocrData.id))
                .andExpect(jsonPath('$.[0].externalId').value(ocrData.externalId))
                .andExpect(jsonPath('$.[0].word').value(ocrData.word))
                .andExpect(jsonPath('$.[0].createdAt').value(ocrData.createdAt.toString()))
    }

    def "GET /api/cached/all should return all ocr data entries from redis"() {
        given: "multiple ocr data entries in redis exist"
        def entries = [createRedisOcrData(), createRedisOcrData(), createRedisOcrData()]

        when: "GET /api/cached/all is called"
        def response = mockMvc.perform(get("/api/cached/all"))

        then: "ocr data entries is returned"
        def ids = entries.collect({ it.id.toLong() }).toArray()
        def externalIds = entries.collect({ it.externalId.toLong() }).toArray()
        def words = entries.collect({ it.word }).toArray()
        def createdAtDates = entries.collect({ it.createdAt.toString() }).toArray()
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(3)))
                .andExpect(jsonPath('$.[*].id', containsInAnyOrder(ids)))
                .andExpect(jsonPath('$.[*].externalId', containsInAnyOrder(externalIds)))
                .andExpect(jsonPath('$.[*].word', containsInAnyOrder(words)))
                .andExpect(jsonPath('$.[*].createdAt', containsInAnyOrder(createdAtDates)))
    }

    def "GET /api/cached/details/{foreignId} should return ocr data entry from redis"() {
        given: "ocr data in redis exist"
        def ocrData = createRedisOcrData()

        when: "GET /api/cached/details/{foreignId} is called"
        def response = mockMvc.perform(get("/api/cached/details/{foreignId}", ocrData.externalId))

        then: "ocr data is returned"
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id').value(ocrData.id))
                .andExpect(jsonPath('$.externalId').value(ocrData.externalId))
                .andExpect(jsonPath('$.word').value(ocrData.word))
                .andExpect(jsonPath('$.createdAt').value(ocrData.createdAt.toString()))
    }

    def "GET /api/cached/flush should delete ocr data entries from redis"() {
        given: "multiple ocr data entries in redis exist"
        createRedisOcrData()
        createRedisOcrData()
        createRedisOcrData()

        when: "GET /api/cached/flush is called"
        def response = mockMvc.perform(get("/api/cached/flush"))

        then: "status 200 is returned"
        response
                .andExpect(status().isOk())
    }

    def "POST /api/migration/ocr with valid dto should return new object"() {
        given: "valid dto"
        def dto = [
                foreign_id: "123",
                word      : "123ABC",
                created   : "2021-06-26 19:05:41"]

        when: "POST /api/migration/ocr is called"
        def response = mockMvc.perform(
                post("/api/migration/ocr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_GENERATOR.toJson(dto)))

        then: "ocr data is returned"
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.externalId').value(dto.foreign_id))
                .andExpect(jsonPath('$.word').value(dto.word))
                .andExpect(jsonPath('$.createdAt')
                        .value(LocalDateTime.parse(dto.created, ofPattern("yyyy-MM-dd HH:mm:ss")).toString()))
    }

    def "POST /api/migration/ocr should return 400 when incomplete request is sent"() {
        given: "incomplete dto"
        def dto = [
                foreign_id: "123",
                created   : "2021-06-26 19:05:41"]

        when: "POST /api/migration/ocr is called"
        def response = mockMvc.perform(
                post("/api/migration/ocr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_GENERATOR.toJson(dto)))

        then: "repository is not invoked"
        0 * ocrDataRepository.save(_)

        and: "response status code is 400"
        response.andExpect(status().isBadRequest())
    }

    def "POST /api/migration/ocr should return 400 when invalid foreign id is sent"() {
        given: "invalid dto"
        def dto = [
                foreign_id: "123ABC",
                word      : "123ABC",
                created   : "2021-06-26 19:05:41"]

        when: "POST /api/migration/ocr is called"
        def response = mockMvc.perform(
                post("/api/migration/ocr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_GENERATOR.toJson(dto)))

        then: "repository is not invoked"
        0 * ocrDataRepository.save(_)

        and: "response status code is 400"
        response.andExpect(status().isBadRequest())
    }

    def "POST /api/migration/ocr should return 400 when invalid created date time is sent"() {
        given: "invalid dto"
        def dto = [
                foreign_id: "123",
                word      : "123ABC",
                created   : "2021-06-26"]

        when: "POST /api/migration/ocr is called"
        def response = mockMvc.perform(
                post("/api/migration/ocr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_GENERATOR.toJson(dto)))

        then: "repository is not invoked"
        0 * ocrDataRepository.save(_)

        and: "response status code is 400"
        response.andExpect(status().isBadRequest())
    }
}
