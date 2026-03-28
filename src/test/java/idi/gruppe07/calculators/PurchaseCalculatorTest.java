package idi.gruppe07.calculators;

import idi.gruppe07.entities.Share;
import idi.gruppe07.entities.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseCalculatorTest {

  PurchaseCalculator purchaseCalculator;
  Share share;
  Stock stock;

  @BeforeEach
  void setUp() {
    stock = new Stock("APPL", "Apple Inc", new BigDecimal("150"));
    share = new Share(stock, BigDecimal.valueOf(10), BigDecimal.valueOf(100));
    purchaseCalculator = new PurchaseCalculator(share);
  }

  @Test
  void checkThatCalculateGrossReturnsCorrectValue() {
    BigDecimal expectedGross = BigDecimal.valueOf(1000);
    assertEquals(expectedGross, purchaseCalculator.calculateGross());
  }

  @Test
  void checkThatCommissionIsCalculatedCorrectly() {
    BigDecimal expectedCommission = new BigDecimal("5");
    assertEquals(expectedCommission, purchaseCalculator.calculateCommission());
  }

  @Test
  void checkThatTaxNoTaxIsAddedForPurchase() {
    BigDecimal expectedTax = BigDecimal.ZERO;
    assertEquals(expectedTax, purchaseCalculator.calculateTax());
  }

  @Test
  void checkThatTheTotalPriceIsAddedCorrectly() {
    BigDecimal expectedTotal = BigDecimal.valueOf(1005);
    assertEquals(expectedTotal, purchaseCalculator.calculateTotal());
  }
}