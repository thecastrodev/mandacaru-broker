package com.mandacarubroker.domain.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a stock item in the system.
 * Includes a builder that allows you to create a Stock and additionally
 * provides a method that dynamically adjusts the stock price.
 */
@Table(name = "stock")
@Entity(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Stock {
  @Id @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String symbol;
  private String companyName;
  private double price;


  /**
   * Constructs a new Stock object based on the provided RequestStockDTO.
   *
   * @param requestStockDTO The data object containing information to initialize the Stock.
   */
  public Stock(final RequestStockDTO requestStockDTO) {
    this.symbol = requestStockDTO.symbol();
    this.companyName = requestStockDTO.companyName();
    this.price = requestStockDTO.price();
  }

  /**
   * Adjusts the price of the stock based on the specified amount and direction.
   *
   * @param amount   The amount by which to adjust the stock price.
   * @param increase A boolean indicating whether to increase or decrease the stock price.
   * @return The adjusted stock price after the operation.
   */
  public double changePrice(final double amount, final boolean increase) {
    double newPrice;
    if (increase) {
      newPrice = increasePrice(amount);
    } else {
      newPrice = decreasePrice(amount);
    }
    return newPrice;
  }

  public double increasePrice(final double amount) {
    return this.price + amount;
  }

  /**
   * Decrease the stock price.
   *
   * @param amount Value to be subtracted from the current stock price.
   * @return The new stock price after the decrease.
   */
  public double decreasePrice(final double amount) {
    double newPrice = this.price - amount;
    if (newPrice < 0) {
      throw new IllegalStateException("Error! Price cannot be negative");
    }
    return newPrice;
  }

}