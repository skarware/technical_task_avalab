package dev.skaringa.avalab.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.skaringa.avalab.annotation.validation.constraints.IsAfter;
import dev.skaringa.avalab.configuration.jackson.CustomDateTimeDeserializer;
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
    @JsonAlias("foreign_id")
    @ApiModelProperty(value = "Foreign ID", required = true, example = "123")
    Long foreignId;

    @NotBlank
    @ApiModelProperty(value = "Word", required = true, example = "ABC123")
    String word;

    @NotNull
    @IsAfter(value = "2015-01-01")
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    //    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss") // this works too
    @ApiModelProperty(value = "Created", required = true, example = "2021-06-26 19:05:41")
    LocalDateTime created;
}
