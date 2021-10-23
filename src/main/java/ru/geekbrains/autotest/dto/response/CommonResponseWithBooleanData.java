package ru.geekbrains.autotest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CommonResponseWithBooleanData extends CommonResponse<Boolean>{
}
