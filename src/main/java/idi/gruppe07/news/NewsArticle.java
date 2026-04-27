package idi.gruppe07.news;

import idi.gruppe07.entities.Stock;
import idi.gruppe07.utils.JsonParser;
import org.json.JSONObject;

/**News articles are instrumental in allowing players to make a good decision when buying stocks.
 * Articles reflect the expected growth of a stock, but are not 100% reliable as the expected growth reliability can be low*/
public class NewsArticle {

 private final String headline;
 private final String article;

 /**Constructor, creates a new news article based on a stock's expected development.
   *
   * @param stock the stock in question*/
  public NewsArticle(JsonParser parser, Stock stock) {

    double stockChange = stock.getPredictedGrowth().getExpectedReturn();

    if (stockChange >= 0.05) {
      JSONObject obj = parser.getRandomArticle("good_news");
      headline = obj.getString("headline");
      article = obj.getString("body");
    }
    else if (stockChange <= -0.05) {
      JSONObject obj = parser.getRandomArticle("bad_news");
      headline = obj.getString("headline");
      article = obj.getString("body");
    }
    else {
      JSONObject obj = parser.getRandomArticle("neutral_news");
      headline = obj.getString("headline");
      article = obj.getString("body");
    }

  }

  /**
   * Gets the headline from the news article object
   *
   * @return the headline*/
  public String getHeadline() {
    return headline;
  }

  /**
   * Gets the article from the news article object
   *
   * @return the article.*/
  public String getArticle() {
    return article;
  }
}
