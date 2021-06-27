package dev.skaringa.avalab.provider

import dev.skaringa.avalab.dto.CreatableOcrData
import dev.skaringa.avalab.entity.OcrData

import java.time.LocalDateTime

class OcrDataProvider {
    private static final def FOREIGN_ID = 1
    private static final def WORD = "word"
    private static final def CREATED_AT = LocalDateTime.of(2021, 6, 26, 21, 58, 30, 123456000)
    private static final Map SAMPLE = [foreign_id: FOREIGN_ID, word: WORD, created: CREATED_AT]

    static def creatableDto(Map<String, Object> map = [:]) {
        def props = SAMPLE + map
        return new CreatableOcrData(props.foreign_id as Long, props.word as String, props.created as LocalDateTime)
    }

    static def entity(Map<String, Object> map = [:]) {
        def props = SAMPLE + map
        return new OcrData(
                props.id as Long,
                props.foreign_id as Long,
                props.word as String,
                props.created as LocalDateTime)
    }
}
