package idi.gruppe07.entities;

import java.math.BigDecimal;

/**
 * Represents a share.
 * 1 share = 1 unit of a stock.
 * */
public class Share {
  private final Stock stock;
  private BigDecimal quantity;
  private BigDecimal purchasePrice;

  /**
   * Constructs a share with the given stock, quantity, and purchase price.
   *
   * @param stock The stock of the share.
   * @param quantity The quantity of the share.
   * @param purchasePrice The purchase price of the share.
   * */
  public Share(Stock stock, BigDecimal quantity, BigDecimal purchasePrice) {
    this.stock = stock;
    this.quantity = quantity;
    this.purchasePrice = purchasePrice;
  }

  /**
   * Gets the stock of the share.
   *
   * @return stock The stock of the share.*/
  public Stock getStock() {
    return stock;
  }

  /**
   * Gets the quantity of the share.
   *
   * @return quantity The quantity of the share.*/
  public BigDecimal getQuantity() {
    return quantity;
  }

  /**
   * Gets the purchase price of the share.
   *
   * @return purchasePrice The purchase price of the share.*/
  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

}
