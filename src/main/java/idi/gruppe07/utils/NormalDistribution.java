package idi.gruppe07.utils;

import java.util.Random;
/**
 * Represents a normal distribution.
 * */
public class NormalDistribution {
  Random r = new Random();
  //Mu
  double mean;
  //Sigma
  double stdDev;
  /**
   * Constructs a normal distribution with the given mean and standard deviation.
   * @param mean The mean of the distribution.
   * @param stdDev The standard deviation of the distribution.
   * @throws IllegalArgumentException if stdDev is less than or equal to 0.
   * @throws NullPointerException if mean is null.*/
  public NormalDistribution(double mean, double stdDev) {
    this.mean = mean;
    this.stdDev = stdDev;
  }
  /**
   * Generates a random number from the normal distribution.
   * @return A random number from the normal distribution.*/
  public double nextGaussian() {
    return r.nextGaussian() * stdDev + mean;
  }
}
