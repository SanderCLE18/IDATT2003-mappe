package idi.gruppe07.entities;

import idi.gruppe07.utils.NormalDistribution;
import java.util.Random;

public class PredictedGrowth {

  private int weekCountdown;
  private final double expectedReturn;
  private final double reliability;

  public PredictedGrowth(NormalDistribution normalDistribution) {
    Random rand = new Random();
    this.weekCountdown = rand.nextInt(6) + 1;
    this.expectedReturn = normalDistribution.nextGaussian();
    this.reliability = rand.nextDouble();
  }

  public boolean tick(){
    return --weekCountdown <= 0;
  }

  public  double getExpectedReturn() {
    return expectedReturn;
  }

  public double getReliability() {
    return reliability;
  }
}
