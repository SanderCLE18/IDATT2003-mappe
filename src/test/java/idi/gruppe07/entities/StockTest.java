package idi.gruppe07.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

  @Test
  void checkThatNewSalesPriceIsAdded() {
    Stock stock = new Stock("AAPL", "Apple Inc.", new BigDecimal("150.00"));
    stock.addNewSalesPrice(new BigDecimal("155.00"));
    assertEquals(new BigDecimal("155.00"), stock.getPrice());
  }
}