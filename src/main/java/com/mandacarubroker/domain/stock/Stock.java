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
  public Stock(RequestStockDTO requestStockDTO) {
    this.symbol = requestStockDTO.symbol();
    this.companyName = requestStockDTO.companyName();
    this.price = changePrice(requestStockDTO.price(), true);
  }

  /**
   * Adjusts the price of the stock based on the specified amount and direction.
   *
   * @param amount   The amount by which to adjust the stock price.
   * @param increase A boolean indicating whether to increase or decrease the stock price.
   * @return The adjusted stock price after the operation.
   */
  public double changePrice(double amount, boolean increase) {
    if (increase) {
      if (amount < this.price) {
        return increasePrice(amount);
      } else {
        return decreasePrice(amount);
      }
    } else {
      if (amount > this.price) {
        return increasePrice(amount);
      } else {
        return this.decreasePrice(amount);
      }
    }
  }

  public double increasePrice(double amount) {
    return this.price + amount;
  }

  public double decreasePrice(double amount) {
    return this.price - amount;
  }

}