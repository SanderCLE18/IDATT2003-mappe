package idi.gruppe07.news;

/**News articles are instrumental in allowing players to make a good decision when buying stocks.
 * Articles reflect the expected growth of a stock, but are not 100% reliable as the expected growth reliability can be low*/
public class NewsArticle {

 private final String headline;
 private final String article;

 /**Constructor, creates a new news article based on a stock's expected development.
   *
   * @param headline the headline in question
   * @param article the actual article*/

  public NewsArticle(String headline, String article) {

    this.headline = headline;
    this.article  = article;

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
