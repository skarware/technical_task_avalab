package dev.skaringa.avalab.factory;

import dev.skaringa.avalab.dto.CreatableOcrData;
import dev.skaringa.avalab.entity.OcrData;
import org.springframework.stereotype.Component;

@Component
public class OcrDataFactory {
    public OcrData toEntity(CreatableOcrData source) {
        return OcrData.builder()
                .externalId(source.getForeign_id())
                .word(source.getWord())
                .createdAt(source.getCreated())
                .build();
    }
}
