package com.cdpr.gamestore_test.model.dto;

import com.cdpr.gamestore_test.persistence.entity.Game;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Incoming DTO to represent {@link Game}.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {
    @NotBlank(message = "Name cannot be blank")
    String name;
    @PositiveOrZero(message = "Price must be a positive number or zero")
    double price;
    @NotBlank(message = "Description cannot be blank")
    String description;
    @NotNull(message = "Release date cannot be null")
    LocalDate releaseDate;
    @NotBlank(message = "Company cannot be blank")
    String company;
}
