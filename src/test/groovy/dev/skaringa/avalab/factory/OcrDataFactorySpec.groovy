package dev.skaringa.avalab.factory

import dev.skaringa.avalab.provider.OcrDataProvider
import spock.lang.Specification

class OcrDataFactorySpec extends Specification {
    private def factory = new OcrDataFactory()

    def "should create entity correctly"() {
        given: "valid dto"
        def dto = OcrDataProvider.creatableDto()

        when: "toEntity is invoked"
        def entity = factory.toEntity(dto)

        then: "entity has correct values"
        entity.externalId == dto.foreignId
        entity.word == dto.word
        entity.createdAt == dto.created
    }
}
