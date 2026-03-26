package idi.gruppe07.transactions;

import idi.gruppe07.calculators.PurchaseCalculator;
import idi.gruppe07.player.Player;
import idi.gruppe07.entities.Share;

/**
 * Represents a purchase.
 * */
public class Purchase extends Transaction {

  /**
   * Creates a new purchase.
   *
   * @param share The share that was purchased.
   * @param week The week of the transaction.*/
  public Purchase(Share share, int week) {
    PurchaseCalculator purchaseCalculator = new PurchaseCalculator(share);
    super(share, week, purchaseCalculator);
  }

  /**
   * Commits the purchase.
   *
   * @param player The player who made the purchase.
   * @throws NullPointerException if player is null.*/
  @Override
  public void commit(Player player) throws NullPointerException {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }

    player.withdrawMoney(this.getCalculator().calculateTotal());
    player.getPortfolio().addShare(this.getShare());
    player.getTransactionArchive().add(this);
    this.commited = true;
  }
}
