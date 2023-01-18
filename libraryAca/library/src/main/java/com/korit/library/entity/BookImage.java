package com.korit.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookImage {
    private int imageId;
    private String bookCode;
    private String saveName;
    private String originName;
}
