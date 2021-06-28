package dev.skaringa.avalab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import dev.skaringa.avalab.annotation.validation.constraints.IsAfter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Creatable Ocr Data")
public class CreatableOcrData {
    @NotNull
    @JsonSetter
    @ApiModelProperty(value = "Foreign ID", required = true, example = "123")
    Long foreign_id;

    @NotBlank
    @ApiModelProperty(value = "Word", required = true, example = "ABC123")
    String word;

    @NotNull
    @IsAfter(value = "2015-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Created", required = true, example = "2021-06-26 19:05:41")
    LocalDateTime created;
}
