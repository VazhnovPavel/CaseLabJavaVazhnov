package com.greenatom.domain.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the Product.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о продукте")
public class ProductResponseDTO {

    @Schema(description = "Id продукта", example = "1")
    private Long id;

    @Schema(description = "Имя продукта", example = "Гвоздь")
    private String productName;

    @Schema(description = "Единица измерения продукта", example = "штука")
    private String unit;

    @Schema(description = "Общее количество продукта", example = "1000")
    private Long storageAmount;

    @Schema(description = "Цена за единицу продукта", example = "3")
    private Long cost;
}
