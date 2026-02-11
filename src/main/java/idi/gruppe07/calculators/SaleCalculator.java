package idi.gruppe07.calculators;

import idi.gruppe07.entities.Share;
import java.math.BigDecimal;

public class SaleCalculator implements TransactionCalculator{
  private BigDecimal purchasePrice;
  private final BigDecimal COMMISSION_CONST = BigDecimal.valueOf(0.01);
  private BigDecimal salesPrice;
  private BigDecimal quantity;
  public SaleCalculator(Share share){
    this.purchasePrice = share.getPurchasePrice();
    this.salesPrice = share.getStock().getPrice();
    this.quantity = share.getQuantity();
  }

  @Override
  public BigDecimal calculateGross() {
    return salesPrice.multiply(quantity);
  }
  @Override
  public BigDecimal calculateCommission(){
    return calculateGross().multiply(COMMISSION_CONST);
  }
  //TODO:This
  @Override
  public BigDecimal calculateTax() {
    return null;
  }
  //TODO:And this
  @Override
  public BigDecimal calculateTotal() {
    return null;
  }
}
