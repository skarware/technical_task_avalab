package dev.skaringa.avalab.repository

import dev.skaringa.avalab.SpecBaseIT
import dev.skaringa.avalab.entity.OcrData
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

class OcrDataRepositorySpec extends SpecBaseIT {
    @Autowired
    private OcrDataRepository ocrDataRepository

    def "should save entity to database and return"() {
        given: "ocr data entry"
        def ocrData = new OcrData(1, 2, "word", LocalDateTime.now())

        when: "save is invoked"
        def result = ocrDataRepository.save(ocrData)

        then: "ocr data entry is returned"
        result.id == ocrData.id
        result.externalId == ocrData.externalId
        result.word == ocrData.word
        result.createdAt == ocrData.createdAt
    }
}