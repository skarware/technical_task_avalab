package dev.skaringa.avalab.service;

import dev.skaringa.avalab.dto.CreatableOcrData;
import dev.skaringa.avalab.entity.OcrData;
import dev.skaringa.avalab.exception.SystemException;
import dev.skaringa.avalab.factory.OcrDataFactory;
import dev.skaringa.avalab.repository.OcrDataRepository;
import dev.skaringa.avalab.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OcrDataService {
    private final OcrDataFactory ocrDataFactory;
    private final OcrDataRepository ocrDataRepository;
    private final RedisRepository redisRepository;

    public List<OcrData> getAll() {
        return ocrDataRepository.findAll();
    }

    //    @Cacheable(value = "ocrDataCache")
    public List<OcrData> getAllCached() {
        return redisRepository.findAll();
    }

    public OcrData getByForeignIdCached(Long id) {
        return redisRepository.findByForeignId(id)
                .orElseThrow(() -> SystemException.notFound("Orc data entry not found by foreign id " + id));
    }

    //    @CachePut(value = "ocrDataCache", key = "#result.externalId")
    @Transactional(propagation = Propagation.REQUIRED)
    public OcrData create(CreatableOcrData creatableOcrData) {
        OcrData ocrData = ocrDataFactory.toEntity(creatableOcrData);
        OcrData ocrDataSaved = ocrDataRepository.save(ocrData);
        return redisRepository.save(ocrDataSaved);
    }

    //    @CacheEvict(value = "ocrDataCache", allEntries = true)
    public void flushCached() {
        redisRepository.flush();
    }
}
