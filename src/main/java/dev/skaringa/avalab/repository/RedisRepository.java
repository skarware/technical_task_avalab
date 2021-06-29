package dev.skaringa.avalab.repository;

import dev.skaringa.avalab.entity.OcrData;
import dev.skaringa.avalab.exception.SystemException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private static final String OCR_DATA_CACHE = "ocrDataCache";

    private final HashOperations<String, Long, OcrData> hashOperations;

    public List<OcrData> findAll() {
        return List.copyOf(hashOperations.values(OCR_DATA_CACHE));
    }

    public Optional<OcrData> findByForeignId(Long id) {
        return Optional.ofNullable(hashOperations.get(OCR_DATA_CACHE, id));
    }

    public OcrData save(OcrData ocrData) {
        hashOperations.put(OCR_DATA_CACHE, ocrData.getExternalId(), ocrData);
        return ocrData;
    }

    public void flush() {
        log.info("/api/cached/flush was called, flushing the cache.");
        List<OcrData> ocrDataList = findAll();
        if (ocrDataList.isEmpty()) {
            throw SystemException.notFound("Nothing to flush, no entries in redis repository found");
        }

        Set<Long> externalIds = extractExternalIds(ocrDataList);
        hashOperations.delete(OCR_DATA_CACHE, externalIds.toArray());
    }

    private Set<Long> extractExternalIds(List<OcrData> ocrDataList) {
        return ocrDataList.stream()
                .map(OcrData::getExternalId)
                .collect(Collectors.toUnmodifiableSet());
    }
}
