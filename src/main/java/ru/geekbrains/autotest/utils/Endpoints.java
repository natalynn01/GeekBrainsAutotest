package ru.geekbrains.autotest.utils;

public class Endpoints {
    public static String URI = "https://api.imgur.com/3";
    public static String UPLOAD_IMAGE = "/upload";
    public static String IMAGE = "/image/{myImageId}";
    public static String ALBUM = "/album";
    public static String ALBUM_INFO = "/album/{myAlbumId}";
    public static String ALBUM_ADD_NEW_IMAGE = "/album/{myAlbumId}/add";
    public static String IMAGE_FROM_ALBUM = "/album/{myAlbumId}/image/{imageId}";
}
