package idi.gruppe07.calculators;

import java.math.BigDecimal;

/**
 * Interface for calculating transactions.*/
public interface TransactionCalculator {

  /**
   * Calculates gross value for given share.*/
  public BigDecimal calculateGross();

  /**
   * Calculates commission for given share.*/
  public BigDecimal calculateCommission();

  /**
   * Calculates tax for given share.*/
  public BigDecimal calculateTax();

  /**
   * Calculates total value for given share.*/
  public BigDecimal calculateTotal();
}
