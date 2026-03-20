package idi.gruppe07.calculators;

import idi.gruppe07.entities.Share;
import java.math.BigDecimal;

/**
 * Calculator for calculating purchases.*/
public class PurchaseCalculator implements TransactionCalculator {
  private final BigDecimal purchasePrice;
  private final BigDecimal quantity;
  private final BigDecimal commission = BigDecimal.valueOf(0.005);

  /**
   * Constructs a PurchaseCalculator for the given share.
   *
   * @param share The share to calculate purchases for.*/
  public PurchaseCalculator(Share share) {
    this.purchasePrice = share.getPurchasePrice();
    this.quantity = share.getQuantity();
  }

  /**
   * Calculates gross value for given purchase.
   *
   * @return gross price*/
  @Override
  public BigDecimal calculateGross() {
    return purchasePrice.multiply(quantity);
  }

  /**
   * Calculates commission for given purchase.
   *
   * @return commission*/
  @Override
  public BigDecimal calculateCommission() {
    return calculateGross().multiply(commission).stripTrailingZeros();
  }

  /**
   * Calculates tax for given purchase.
   *
   * @return tax*/
  @Override
  public BigDecimal calculateTax() {
    return new BigDecimal("0");
  }

  /**
   * Calculates total value for given purchase.
   *
   * @return gross + commission + tax*/
  @Override
  public BigDecimal calculateTotal() {
    return calculateGross().add(calculateCommission()).add(calculateTax());
  }
}
