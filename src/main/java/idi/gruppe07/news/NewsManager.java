package idi.gruppe07.news;

import idi.gruppe07.entities.Stock;

import java.util.Map;

/**Class for updating the news articles. */
public class NewsManager {
  private final NewsService newsService;

  /**Constructor for creating a newsManager class
   *
   * @param newsService a reference to a newsService object. */
  public NewsManager(NewsService newsService) {
    this.newsService = newsService;
  }

  /**Updates the stock articles for the given stocks
   *
   * @param stockMap a map containing stocks*/
  public void updateStockArticles(Map<String, Stock> stockMap){
    for (var stock : stockMap.values()){
      if (stock.getPredictedGrowth().getWeekCountdown() == stock.getPredictedGrowth().getInitialPeriod()){
        NewsArticle article = newsService.generateArticle(stock);
        stock.setNewsArticle(article);
      }
    }
  }
}
