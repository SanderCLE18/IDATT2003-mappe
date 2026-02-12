package idi.gruppe07.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Stock {
  private String symbol;
  private String company;
  private List<BigDecimal> prices;

  public Stock(String symbol, String company, List<BigDecimal> salesPrice) {
    this.symbol = symbol;
    this.company = company;
    this.prices = new ArrayList<BigDecimal>(salesPrice);
  }

  public String getSymbol() {
    return symbol;
  }

  public String getCompany() {
    return company;
  }

  public BigDecimal getPrice() {
    return prices.getLast();
  }

  public void addNewSalesPrice(BigDecimal price) {
    this.prices.add(price);
  }

}
