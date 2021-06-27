package dev.skaringa.avalab.controller;

import dev.skaringa.avalab.dto.CreatableOcrData;
import dev.skaringa.avalab.entity.OcrData;
import dev.skaringa.avalab.service.OcrDataService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@Api(tags = "Ocr Data")
@RequiredArgsConstructor
@RequestMapping("api")
public class OcrDataController {
    private final OcrDataService ocrDataService;

    @PostMapping("migration/ocr")
    public OcrData create(@Valid @RequestBody CreatableOcrData creatableOcrData) {
        return ocrDataService.create(creatableOcrData);
    }
}
