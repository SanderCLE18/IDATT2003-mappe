package idi.gruppe07.entities;


/**
 * Simple enum for providing the trend of a given {@link Stock}*/
public enum Trend {
  BULLISH, BEARISH, NEUTRAL;

  /**
   * Returns the {@link Trend} of the provided growth
   *
   * @return the trend*/
  public static Trend getTrend(double growth){
    if (growth >= 0.025) {
      return BULLISH;
    }
    if (growth <= -0.025) {
      return BEARISH;
    }
    return NEUTRAL;
  }

}


