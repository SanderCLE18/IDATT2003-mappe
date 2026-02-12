package idi.gruppe07.transactions;

import idi.gruppe07.calculators.PurchaseCalculator;
import idi.gruppe07.entities.Player;
import idi.gruppe07.entities.Share;

public class Purchase extends Transaction {

  public Purchase(Share share, int week) {
    PurchaseCalculator purchaseCalculator = new PurchaseCalculator();
    super(share, week, purchaseCalculator);
  }

  @Override
  public void commit(Player player){
    super.commit(player);
  }
}
