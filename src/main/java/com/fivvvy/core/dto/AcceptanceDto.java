package com.fivvvy.core.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AcceptanceDto {
    private DisclaimerDto disclaimer;
    private UserDto user;
    private LocalDateTime createdAt;
}
