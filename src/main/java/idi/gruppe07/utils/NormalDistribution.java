package idi.gruppe07.utils;

import java.util.Random;

public class NormalDistribution {
  Random r = new Random();
  //Mu
  double mean;
  //Sigma
  double stdDev;

  public NormalDistribution(double mean, double stdDev) {
    this.mean = mean;
    this.stdDev = stdDev;
  }
  public double nextGaussian() {
    return r.nextGaussian() * stdDev + mean;
  }
}
