package idi.gruppe07.transactions;

import idi.gruppe07.calculators.SaleCalculator;
import idi.gruppe07.entities.Player;
import idi.gruppe07.entities.Share;


public class Sale extends Transaction {

  public Sale(Share share, int week) {
    SaleCalculator saleCalculator = new SaleCalculator(share);
    super(share, week, saleCalculator);
  }

  @Override
  public void commit(Player player){
    super.commit(player);
  }
}
