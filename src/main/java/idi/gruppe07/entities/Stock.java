package idi.gruppe07.entities;

import idi.gruppe07.news.NewsArticle;
import idi.gruppe07.utils.NormalDistribution;
import idi.gruppe07.utils.Validate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a stock.
 * */
public class Stock {
  private final String symbol;
  private final String company;
  private final List<BigDecimal> prices;
  private NewsArticle article;

  private PredictedGrowth predictedGrowth;

  /**
   * Constructs a stock with the given symbol, company, and sales price.
   *
   * @param symbol The symbol of the stock.
   * @param company The company name of the stock.
   * @param salesPrice The list of sales prices of the stock.
   * */
  public Stock(String symbol, String company, BigDecimal salesPrice) {
    Validate.that(symbol).isNotNullOrEmpty();
    Validate.that(company).isNotNullOrEmpty();

    this.symbol = symbol;
    this.company = company;
    this.prices = new ArrayList<>();
    prices.add(salesPrice);
  }

  /**
   * Returns the symbol of the stock.
   *
   * @return symbol The symbol of the stock.*/
  public String getSymbol() {
    return symbol;
  }

  /**
   * Returns the company name of the stock.
   *
   * @return company The company name of the stock.*/
  public String getCompany() {
    return company;
  }

  /**
   * Returns the last sales price of the stock.
   *
   * @return price The last sales price of the stock.*/
  public BigDecimal getPrice() {
    return prices.getLast();
  }

  /**
   * Adds a new sales price to the stock.
   *
   * @param price The new sales price to add.*/
  public void addNewSalesPrice(BigDecimal price) {
    this.prices.add(price);
  }


  /**
   * Returns all historical sales prices of the stock.
   *
   * @return the whole list of prices
   */
  public List<BigDecimal> getHistoricalPrices() {return prices; }


  /**
   * Returns the highest price the stock has had
   *
   * @return the highest price the stock has had
   */
  public BigDecimal getHighestPrice() {
    return Collections.max(prices);
  }

  /**
   * Returns the lowest price the stock has had
   *
   * @return the lowest price the stock has had
   */
  public BigDecimal getLowestPrice() {
    return Collections.min(prices);
  }

  /**
   * Return the difference between the newest price, and the price before it.
   * If there has only been 1 price, it returns 0.
   *
   * @return 0 or the price change from the newest price and the price before it
   */
  public BigDecimal getLatestPriceChange() {
    if(prices.size() == 1) return BigDecimal.ZERO;
    return getPrice().subtract(prices.get(prices.size() - 2));
  }

  /**
   * Creates a new predicted growth object
   *
   * @param normalDistribution a normal distribution.*/
  public void addPredictedGrowth(NormalDistribution normalDistribution) {
    this.predictedGrowth = new PredictedGrowth(normalDistribution);
  }

  /**
   * Returns the predicted growth object associated with the stock.*/
  public PredictedGrowth getPredictedGrowth() {
    return predictedGrowth;
  }

  /**
   * Returns the associated news
   *
   * @return a news article object*/
  public NewsArticle getNewsArticle() {
    return article;
  }

  /**
   * Sets the news article for the stock
   *
   * @param article the article*/
  public void setNewsArticle(NewsArticle article) {
    this.article = article;
  }

}
