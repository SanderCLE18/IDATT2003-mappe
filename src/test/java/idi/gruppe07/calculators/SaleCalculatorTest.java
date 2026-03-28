package idi.gruppe07.calculators;

import idi.gruppe07.entities.Share;
import idi.gruppe07.entities.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SaleCalculatorTest {

  SaleCalculator saleCalculator;
  Share share;
  Stock stock;

  @BeforeEach
  void setUp() {
    stock = new Stock("APPL", "Apple Inc", new BigDecimal("150"));
    share = new Share(stock, BigDecimal.valueOf(10), BigDecimal.valueOf(100));
    saleCalculator = new SaleCalculator(share);
  }

  @Test
  void checkThatGrossIsCalculatedCorrectly() {
    BigDecimal expectedGross = BigDecimal.valueOf(1500);
    assertEquals(expectedGross, saleCalculator.calculateGross());
  }

  @Test
  void checkThatComissionIsCalculatedCorrectly() {
    BigDecimal expectedCommission = new BigDecimal("15");
    assertEquals(expectedCommission, saleCalculator.calculateCommission());
  }

  @Test
  void checkThatTaxIsCalculatedCorrectly() {
    BigDecimal expectedTax = new BigDecimal("145.5");
    assertEquals(expectedTax, saleCalculator.calculateTax());
  }

  @Test
  void checkThatTotalIsCalculatedCorrectly() {
    BigDecimal expectedTotal = BigDecimal.valueOf(1339.5);
    assertEquals(expectedTotal, saleCalculator.calculateTotal());
  }
}