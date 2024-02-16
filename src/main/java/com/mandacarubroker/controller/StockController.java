package com.mandacarubroker.controller;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.service.StockService;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contains a CRUD using HTTP methods.
 */
@RestController
@RequestMapping("/stocks")
public class StockController {

  private final StockService stockService;

  public StockController(final StockService receivedStockService) {
    this.stockService = receivedStockService;
  }

  @GetMapping(produces = "application/json")
  public List<Stock> getAllStocks() {
    return stockService.getAllStocks();
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<?> getStockById(@PathVariable final String id) {
    return stockService.getStockById(id)
        .map(stock -> ResponseEntity.ok().body(stock))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Stock> createStock(@RequestBody final RequestStockDTO data) {
    if (data.price() <= 0) {
      return ResponseEntity.badRequest().build();
    }
    Stock createdStock = stockService.createStock(data);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
  }

  @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Stock> updateStock(@PathVariable final String id, @RequestBody final Stock updatedStock) {
    Stock newStock = stockService.updateStock(id, updatedStock);
    return ResponseEntity.status(HttpStatus.CREATED).body(newStock);
  }

  @DeleteMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<Void> deleteStockById(@PathVariable final String id) {
    stockService.deleteStock(id);
    return ResponseEntity.ok().build();
  }
}
