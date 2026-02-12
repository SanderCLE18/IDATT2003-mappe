package idi.gruppe07.calculators;

import idi.gruppe07.entities.Share;
import java.math.BigDecimal;

public class SaleCalculator implements TransactionCalculator {
  private BigDecimal purchasePrice;
  private final BigDecimal commission = BigDecimal.valueOf(0.01);
  private final BigDecimal taxRate = BigDecimal.valueOf(0.3);
  private BigDecimal salesPrice;
  private BigDecimal quantity;

  public SaleCalculator(Share share) {
    this.purchasePrice = share.getPurchasePrice();
    this.salesPrice = share.getStock().getPrice();
    this.quantity = share.getQuantity();
  }

  @Override
  public BigDecimal calculateGross() {
    return salesPrice.multiply(quantity);
  }

  @Override
  public BigDecimal calculateCommission() {
    return calculateGross().multiply(commission);
  }


  @Override
  public BigDecimal calculateTax() {
    BigDecimal purchaseCost = purchasePrice.multiply(quantity);
    BigDecimal gain = calculateCommission().subtract(purchaseCost).subtract(calculateCommission());
    return gain.multiply(taxRate);
  }

  @Override
  public BigDecimal calculateTotal() {
    return calculateGross().subtract(calculateCommission()).subtract(calculateTax());
  }
}
