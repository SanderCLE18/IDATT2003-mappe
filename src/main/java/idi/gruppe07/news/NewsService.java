package idi.gruppe07.news;

import idi.gruppe07.entities.Stock;
import idi.gruppe07.utils.JsonParser;
import org.json.JSONObject;

import java.util.HashMap;

/**Factory class for creating NewsArticles*/
public class NewsService {

  private final JsonParser parser;

  public NewsService(JsonParser parser) {
    this.parser = parser;
  }

  public NewsArticle generateArticle(Stock stock) {
    double stockChange = stock.getPredictedGrowth().getExpectedReturn();
    JSONObject obj;

    if (stockChange >= 0.05) obj = parser.getRandomArticle("good_news");
    else if (stockChange <= -0.05) obj = parser.getRandomArticle("bad_news");
    else obj = parser.getRandomArticle("neutral_news");

    String headline = String.format(obj.getString("headline"), stock.getCompany());
    String body = String.format(obj.getString("body"), stock.getCompany());

    return new NewsArticle(headline, body);
  }
}
