package com.yanvelasco.imgeliteapi.domain.imageDomain.enums;

import lombok.Getter;

@Getter
public enum ImageExtensionEnum {
    JPEG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif"),
    BMP("image/bmp"),
    TIFF("image/tiff"),
    WEBP("image/webp");

    private final String contentType;

    ImageExtensionEnum(String contentType) {
        this.contentType = contentType;
    }

    public static ImageExtensionEnum fromValue(String contentType) {
        for (ImageExtensionEnum extension : values()) {
            if (extension.getContentType().equalsIgnoreCase(contentType)) {
                return extension;
            }
        }
        return null;
    }
}