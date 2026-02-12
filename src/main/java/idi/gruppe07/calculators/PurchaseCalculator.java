package idi.gruppe07.calculators;

import idi.gruppe07.entities.Share;
import java.math.BigDecimal;

public class PurchaseCalculator implements TransactionCalculator {
  private final BigDecimal purchasePrice;
  private final BigDecimal quantity;
  private final BigDecimal commission = BigDecimal.valueOf(0.005);

  public PurchaseCalculator(Share share) {
    this.purchasePrice = share.getPurchasePrice();
    this.quantity = share.getQuantity();
  }

  @Override
  public BigDecimal calculateGross() {
    return purchasePrice.multiply(quantity);
  }

  @Override
  public BigDecimal calculateCommission() {
    return calculateGross().multiply(commission);
  }

  @Override
  public BigDecimal calculateTax() {
    return null;
  }

  @Override
  public BigDecimal calculateTotal() {
    return calculateGross().add(calculateCommission()).add(calculateTax());
  }
}
