package com.mandacarubroker.domain.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Stock entities in the database.
 */
@Repository
public interface StockRepository  extends JpaRepository<Stock, String> {
}
