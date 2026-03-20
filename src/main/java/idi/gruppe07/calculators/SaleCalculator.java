package idi.gruppe07.calculators;

import idi.gruppe07.entities.Share;
import java.math.BigDecimal;

/**
 * Calculator for calculating sales. */
public class SaleCalculator implements TransactionCalculator {
  private BigDecimal purchasePrice;
  private final BigDecimal commission = BigDecimal.valueOf(0.01);
  private final BigDecimal taxRate = BigDecimal.valueOf(0.3);
  private BigDecimal salesPrice;
  private BigDecimal quantity;

  /**
   * Constructs a SaleCalculator for the given share.
   *
   * @param share The share to calculate sales for.
   * @throws NullPointerException if share is null.*/
  public SaleCalculator(Share share) {

    if (share == null) {
      throw new NullPointerException("Share cannot be null");
    }

    this.purchasePrice = share.getPurchasePrice();
    this.salesPrice = share.getStock().getPrice();
    this.quantity = share.getQuantity();
  }

  /**
   * Calculates gross sales price for given share.
   *
   * @return gross sales price.
   */
  @Override
  public BigDecimal calculateGross() {
    return salesPrice.multiply(quantity);
  }

  /**
   * Calculates commission for given share.
   *
   * @return commission.*/
  @Override
  public BigDecimal calculateCommission() {
    return calculateGross().multiply(commission).stripTrailingZeros();
  }

  /**
   * Calculates tax for given share.
   *
   * @return tax.*/
  @Override
  public BigDecimal calculateTax() {
    BigDecimal purchaseCost = purchasePrice.multiply(quantity);
    BigDecimal gain = calculateGross().subtract(purchaseCost).subtract(calculateCommission());
    return gain.multiply(taxRate);
  }

  /**
   * Calculates total sales price for given share.
   *
   * @return total sales price.*/
  @Override
  public BigDecimal calculateTotal() {
    return calculateGross().subtract(calculateCommission()).subtract(calculateTax());
  }
}
