package dev.skaringa.avalab.service;

import dev.skaringa.avalab.dto.CreatableOcrData;
import dev.skaringa.avalab.entity.OcrData;
import dev.skaringa.avalab.factory.OcrDataFactory;
import dev.skaringa.avalab.repository.OcrDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OcrDataService {
    private final OcrDataFactory ocrDataFactory;
    private final OcrDataRepository ocrDataRepository;

    public List<OcrData> getAll() {
        return ocrDataRepository.findAll();
    }

    public OcrData create(CreatableOcrData creatableOcrData) {
        OcrData toSave = ocrDataFactory.toEntity(creatableOcrData);
        return ocrDataRepository.save(toSave);
    }
}
