package ru.geekbrains.autotest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CreateAlbumResponse extends CommonResponse<CreateAlbumResponse.CreateAlbumData>{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "deletehash"
    })
    @Data
    public static class CreateAlbumData {
        @JsonProperty("id")
        private String id;
        @JsonProperty("deletehash")
        private String deletehash;
    }
}
