package com.korit.library.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookImageDto {
    private int imageId;
    private String bookCode;
    private String saveName;
    private String originName;
}
