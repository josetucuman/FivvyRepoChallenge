package com.fivvvy.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DisclaimerDto {

    private String name;
    private String text;
    private String version;

    public DisclaimerDto(String name, String text, String version) {
        this.name = name;
        this.text = text;
        this.version = version;
    }
}
