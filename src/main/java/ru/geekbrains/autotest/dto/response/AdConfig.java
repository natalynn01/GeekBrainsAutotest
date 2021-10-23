package ru.geekbrains.autotest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "safeFlags",
        "highRiskFlags",
        "unsafeFlags",
        "wallUnsafeFlags",
        "showsAds"
})
@Data
public class AdConfig {
    @JsonProperty("safeFlags")
    private List<String> safeFlags = null;
    @JsonProperty("highRiskFlags")
    private List<Object> highRiskFlags = null;
    @JsonProperty("unsafeFlags")
    private List<String> unsafeFlags = null;
    @JsonProperty("wallUnsafeFlags")
    private List<Object> wallUnsafeFlags = null;
    @JsonProperty("showsAds")
    private Boolean showsAds;
}
