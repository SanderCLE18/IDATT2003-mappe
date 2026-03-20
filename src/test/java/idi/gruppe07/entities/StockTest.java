package idi.gruppe07.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

  @Test
  void checkThatNewSalesPriceIsAdded() {
    Stock stock = new Stock("AAPL", "Apple Inc.", new BigDecimal("150.00"));
    stock.addNewSalesPrice(new BigDecimal("155.00"));
    assertEquals(new BigDecimal("155.00"), stock.getPrice());
  }

  @Nested
  class testingMethodsForPrices{
    Stock stock;

    @BeforeEach
    void setUp() {
      stock = new Stock("AAPL", "Apple Inc.", new BigDecimal("150.00"));
      stock.addNewSalesPrice(new BigDecimal("155.00"));
      stock.addNewSalesPrice(new BigDecimal("165.00"));
    }

    @Test
    void checkThatGetHistoricalPricesWork() {
      BigDecimal bd1 = new BigDecimal("150.00");
      BigDecimal bd2 = new BigDecimal("155.00");
      BigDecimal bd3 = new BigDecimal("165.00");
      ArrayList<BigDecimal> expectedPrices = new ArrayList<>(List.of(bd1, bd2, bd3));
      assertEquals(expectedPrices, stock.getHistoricalPrices());
    }

    @Test
    void checkThatGetHighestPricesWork() {
      BigDecimal expectedHighestPrice = new BigDecimal("165.00");
      assertEquals(expectedHighestPrice, stock.getHighestPrice());
      stock.addNewSalesPrice(new BigDecimal("200.00"));
      BigDecimal newExpectedHighestPrice = new BigDecimal("200.00");
      assertEquals(newExpectedHighestPrice, stock.getHighestPrice());
    }

    @Test
    void checkThatGetLowestPricesWork() {
      BigDecimal expectedLowestPrice = new BigDecimal("150.00");
      assertEquals(expectedLowestPrice, stock.getLowestPrice());
      stock.addNewSalesPrice(new BigDecimal("100.00"));
      BigDecimal newExpectedLowestPrice = new BigDecimal("100.00");
      assertEquals(newExpectedLowestPrice, stock.getLowestPrice());
    }

    @Test
    void checkThatGetLatestPriceChangeWork() {
      BigDecimal expectedPriceChange = new BigDecimal("10.00");
      assertEquals(expectedPriceChange, stock.getLatestPriceChange());
      stock.addNewSalesPrice(new BigDecimal("150.00"));
      BigDecimal newExpectedPriceChange = new BigDecimal("-15.00");
      assertEquals(newExpectedPriceChange, stock.getLatestPriceChange());
    }
  }
}