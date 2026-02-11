package idi.gruppe07.calculators;

import java.math.BigDecimal;

public interface TransactionCalculator {

  public BigDecimal calculateGross();

  public BigDecimal calculateCommission();

  public BigDecimal calculateTax();

  public BigDecimal calculateTotal();
}
