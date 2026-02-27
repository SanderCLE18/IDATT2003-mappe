package idi.gruppe07.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a stock.
 * */
public class Stock {
  private String symbol;
  private String company;
  private List<BigDecimal> prices;

  /**
   * Constructs a stock with the given symbol, company, and sales price.
   * @param symbol The symbol of the stock.
   * @param company The company name of the stock.
   * @param salesPrice The list of sales prices of the stock.
   * */
  public Stock(String symbol, String company, BigDecimal salesPrice) {
    this.symbol = symbol;
    this.company = company;
    this.prices = new ArrayList<>();
    prices.add(salesPrice);
  }

  /**
   * Returns the symbol of the stock.
   * @return symbol The symbol of the stock.*/
  public String getSymbol() {
    return symbol;
  }

  /**
   * Returns the company name of the stock.
   * @return company The company name of the stock.*/
  public String getCompany() {
    return company;
  }

  /**
   * Returns the last sales price of the stock.
   * @return price The last sales price of the stock.*/
  public BigDecimal getPrice() {
    return prices.getLast();
  }

  /**
   * Adds a new sales price to the stock.
   * @param price The new sales price to add.*/
  public void addNewSalesPrice(BigDecimal price) {
    this.prices.add(price);
  }

}
