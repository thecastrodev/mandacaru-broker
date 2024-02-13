package com.mandacarubroker.domain.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * A Data Transfer Object (DTO) representing the request for creating or updating a stock item.
 * The class enforce constraints on its fields:
 *
 * @param symbol       The stock symbol consisting of two letters followed by a single number.
 * @param companyName  The name of the company associated with the stock item.
 * @param price        The price of the stock item.
 */
public record RequestStockDTO(
        @Pattern(regexp = "[A-Za-z]{2}[0-9]{1}",
            message = "Symbol must be 3 letters followed by 1 number")
        String symbol,
        @NotBlank(message = "Company name cannot be blank")
        String companyName,
        @NotNull(message = "Price cannot be null")
        double price
) {
}
