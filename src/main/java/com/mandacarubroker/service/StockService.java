package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * Contains the services for CRUD.
 */
@Service
public class StockService {

  private final StockRepository stockRepository;

  public StockService(final StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  public List<Stock> getAllStocks() {
    return stockRepository.findAll();
  }

  public Optional<Stock> getStockById(final String id) {
    return stockRepository.findById(id);
  }

  /**
  * Creates a new stock item based on the provided data and saves it to the repository.
  *
  * @param data The data from the RequestStockDTO used to create the new stock item.
  * @return A Stock object representing the newly created stock item, after being saved.
  * @throws IllegalArgumentException If the validation of the RequestStockDTO fails.
  */
  public Stock createStock(final RequestStockDTO data) {
    Stock newAction = new Stock(data);
    validateRequestStockDTO(data);
    return stockRepository.save(newAction);
  }

  /**
   * Updates an existing stock item identified by its unique ID with the provided information.
   *
   * @param id The unique identifier of the stock item to be updated.
   * @param updatedStock A Stock object containing the updated information for the stock item.
   * @return An Optional containing the updated Stock object if the stock item was found
   *     and updated, or an empty Optional if the stock item with the given ID was not found.
   */
  public Optional<Stock> updateStock(final String id, final Stock updatedStock) {
    return stockRepository.findById(id)
      .map(stock -> {
        stock.setSymbol(updatedStock.getSymbol());
        stock.setCompanyName(updatedStock.getCompanyName());
        double newPrice = stock.changePrice(updatedStock.getPrice(), true);
        stock.setPrice(newPrice);

        return stockRepository.save(stock);
      });
  }

  public void deleteStock(final String id) {
    stockRepository.deleteById(id);
  }

  /**
   * Validates the provided RequestStockDTO using Java Bean Validation (JSR 380) annotations.
   *
   * @param data The RequestStockDTO object to be validated.
   * @throws ConstraintViolationException If validation fails.
   */
  public static void validateRequestStockDTO(final RequestStockDTO data) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(data);

    if (!violations.isEmpty()) {
      StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");

      for (ConstraintViolation<RequestStockDTO> violation : violations) {
        errorMessage.append(String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage()));
      }

      errorMessage.delete(errorMessage.length() - 2, errorMessage.length());

      throw new ConstraintViolationException(errorMessage.toString(), violations);
    }
  }
}
