package ru.geekbrains.autotest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ErrorResponse extends CommonResponse<ErrorResponse.ErrorData>{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "error",
            "request",
            "method"
    })
    @Data
    public static class ErrorData {
        @JsonProperty("error")
        private String error;
        @JsonProperty("request")
        private String request;
        @JsonProperty("method")
        private String method;
    }
}
