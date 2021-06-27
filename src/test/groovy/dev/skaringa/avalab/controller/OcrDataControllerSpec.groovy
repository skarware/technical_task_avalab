package dev.skaringa.avalab.controller

import dev.skaringa.avalab.SpecBaseIT
import dev.skaringa.avalab.repository.OcrDataRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class OcrDataControllerSpec extends SpecBaseIT {
    @Autowired
    private OcrDataRepository ocrDataRepository

    def "POST api/migration/ocr with valid dto should return new object"() {
        given: "valid dto"
        def dto = [
                foreign_id: "123",
                word      : "123ABC",
                created   : "2021-06-26 19:05:41"]

        when: "POST api/migration/ocr is called"
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
                        .value(LocalDateTime.from(JSON_DATE_TIME_FORMATTER.parse(dto.created)).toString()))
    }

    def "POST api/migration/ocr should return 400 when incomplete request is sent"() {
        given: "incomplete dto"
        def dto = [
                foreign_id: "123",
                created   : "2021-06-26 19:05:41"]

        when: "POST api/migration/ocr is called"
        def response = mockMvc.perform(
                post("/api/migration/ocr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_GENERATOR.toJson(dto)))

        then: "repository is not invoked"
        0 * ocrDataRepository.save(_)

        and: "response status code is 400"
        response.andExpect(status().isBadRequest())
    }

    def "POST api/migration/ocr should return 400 when invalid foreign id is sent"() {
        given: "invalid dto"
        def dto = [
                foreign_id: "123ABC",
                word      : "123ABC",
                created   : "2021-06-26 19:05:41"]

        when: "POST api/migration/ocr is called"
        def response = mockMvc.perform(
                post("/api/migration/ocr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_GENERATOR.toJson(dto)))

        then: "repository is not invoked"
        0 * ocrDataRepository.save(_)

        and: "response status code is 400"
        response.andExpect(status().isBadRequest())
    }

    def "POST api/migration/ocr should return 400 when invalid created date time is sent"() {
        given: "invalid dto"
        def dto = [
                foreign_id: "123",
                word      : "123ABC",
                created   : "2021-06-26"]

        when: "POST api/migration/ocr is called"
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
