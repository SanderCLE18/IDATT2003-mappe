package idi.gruppe07.entities;

import idi.gruppe07.utils.NormalDistribution;
import java.util.Random;

/**
 * Instrumental value for adding a certain predictability to the stock market.
 * Class influences the news, and the growth of the market.*/
public class PredictedGrowth {

  private int initialPeriod;
  private int weekCountdown;
  private final double expectedReturn;
  private final double reliability;

  /**Constructor, creates the given values for the object.
   *
   * @param normalDistribution a reference to a normalDistribution object. */
  public PredictedGrowth(NormalDistribution normalDistribution) {
    Random rand = new Random();
    this.initialPeriod = rand.nextInt(6) + 1;
    this.weekCountdown =  initialPeriod;
    this.expectedReturn = normalDistribution.nextGaussian();
    this.reliability = rand.nextDouble();
  }

  /**returns a boolean value of if the period has ended or not
   *
   * @return boolean value of the given period*/
  public boolean tick(){
    return --weekCountdown <= 0;
  }

  /**Returns the expected return of a given stock.
   *
   * @return the expected return*/
  public  double getExpectedReturn() {
    return expectedReturn;
  }

  /**
   * Returns the reliability of the growth, low reliability means an uncertain stock.
   *
   * @return the reliability*/
  public double getReliability() {
    return reliability;
  }

  /**Returns the amount of weeks left for the current growth period
   *
   * @return the weeks left.*/
  public int getWeekCountdown() {
    return weekCountdown;
  }

  /**Returns the initial period, keeps track of how long it's supposed to be going for.
   *
   * @return the initial period*/
  public int getInitialPeriod() {
    return initialPeriod;
  }
}
