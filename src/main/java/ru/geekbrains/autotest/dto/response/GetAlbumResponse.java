package ru.geekbrains.autotest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GetAlbumResponse extends CommonResponse<GetAlbumResponse.GetAlbumInfoData>{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "title",
            "description",
            "datetime",
            "cover",
            "cover_edited",
            "cover_width",
            "cover_height",
            "account_url",
            "account_id",
            "privacy",
            "layout",
            "views",
            "link",
            "favorite",
            "nsfw",
            "section",
            "images_count",
            "in_gallery",
            "is_ad",
            "include_album_ads",
            "is_album",
            "deletehash",
            "images",
            "ad_config"
    })
    @Data
    public static class GetAlbumInfoData {
        @JsonProperty("id")
        private String id;
        @JsonProperty("title")
        private Object title;
        @JsonProperty("description")
        private Object description;
        @JsonProperty("datetime")
        private Integer datetime;
        @JsonProperty("cover")
        private Object cover;
        @JsonProperty("cover_edited")
        private Object coverEdited;
        @JsonProperty("cover_width")
        private Object coverWidth;
        @JsonProperty("cover_height")
        private Object coverHeight;
        @JsonProperty("account_url")
        private String accountUrl;
        @JsonProperty("account_id")
        private Integer accountId;
        @JsonProperty("privacy")
        private String privacy;
        @JsonProperty("layout")
        private String layout;
        @JsonProperty("views")
        private Integer views;
        @JsonProperty("link")
        private String link;
        @JsonProperty("favorite")
        private Boolean favorite;
        @JsonProperty("nsfw")
        private Boolean nsfw;
        @JsonProperty("section")
        private Object section;
        @JsonProperty("images_count")
        private Integer imagesCount;
        @JsonProperty("in_gallery")
        private Boolean inGallery;
        @JsonProperty("is_ad")
        private Boolean isAd;
        @JsonProperty("include_album_ads")
        private Boolean includeAlbumAds;
        @JsonProperty("is_album")
        private Boolean isAlbum;
        @JsonProperty("deletehash")
        private String deletehash;
        @JsonProperty("images")
        private List<Object> images = null;
        @JsonProperty("ad_config")
        private AdConfig adConfig;
    }
}
