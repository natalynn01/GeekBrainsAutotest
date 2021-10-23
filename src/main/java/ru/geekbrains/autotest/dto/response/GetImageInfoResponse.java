package ru.geekbrains.autotest.dto.response;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GetImageInfoResponse extends CommonResponse<GetImageInfoResponse.ImageInfoGetData>{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "title",
            "description",
            "datetime",
            "type",
            "animated",
            "width",
            "height",
            "size",
            "views",
            "bandwidth",
            "vote",
            "favorite",
            "nsfw",
            "section",
            "account_url",
            "account_id",
            "is_ad",
            "in_most_viral",
            "has_sound",
            "tags",
            "ad_type",
            "ad_url",
            "edited",
            "in_gallery",
            "deletehash",
            "name",
            "link",
            "ad_config"
    })
    @Data
    public static class ImageInfoGetData {
        @JsonProperty("id")
        private String id;
        @JsonProperty("title")
        private String title;
        @JsonProperty("description")
        private String description;
        @JsonProperty("datetime")
        private Integer datetime;
        @JsonProperty("type")
        private String type;
        @JsonProperty("animated")
        private Boolean animated;
        @JsonProperty("width")
        private Integer width;
        @JsonProperty("height")
        private Integer height;
        @JsonProperty("size")
        private Integer size;
        @JsonProperty("views")
        private Integer views;
        @JsonProperty("bandwidth")
        private Integer bandwidth;
        @JsonProperty("vote")
        private Object vote;
        @JsonProperty("favorite")
        private Boolean favorite;
        @JsonProperty("nsfw")
        private Boolean nsfw;
        @JsonProperty("section")
        private Object section;
        @JsonProperty("account_url")
        private Object accountUrl;
        @JsonProperty("account_id")
        private Integer accountId;
        @JsonProperty("is_ad")
        private Boolean isAd;
        @JsonProperty("in_most_viral")
        private Boolean inMostViral;
        @JsonProperty("has_sound")
        private Boolean hasSound;
        @JsonProperty("tags")
        private List<Object> tags = null;
        @JsonProperty("ad_type")
        private Integer adType;
        @JsonProperty("ad_url")
        private String adUrl;
        @JsonProperty("edited")
        private String edited;
        @JsonProperty("in_gallery")
        private Boolean inGallery;
        @JsonProperty("deletehash")
        private String deletehash;
        @JsonProperty("name")
        private String name;
        @JsonProperty("link")
        private String link;
        @JsonProperty("ad_config")
        private AdConfig adConfig;
    }

}
