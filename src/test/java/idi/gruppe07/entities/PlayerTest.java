package idi.gruppe07.entities;

import idi.gruppe07.calculators.SaleCalculator;
import idi.gruppe07.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class PlayerTest {
  Player player;

  @BeforeEach
  void setUp() {
    player = new Player("Test", new BigDecimal("1000"));
  }

  @Test
  void checkThatTotalBalanceIsAddedCorrectly() {
    player.addMoney(new BigDecimal("500"));
    assertEquals(new BigDecimal("1500"), player.getMoney());
  }

  @Test
  void checkThatTotalBalanceIsSubtractedCorrectly() {
    player.withdrawMoney(new BigDecimal("500"));
    assertEquals(new BigDecimal("500"), player.getMoney());
  }

  @Test
  void checkThatPortfolioIsInitialized() {
    assertNotNull(player.getPortfolio());
  }

  @Test
  void checkThatTransactionArchiveIsInitialized() {
    assertNotNull(player.getTransactionArchive());
  }

  @Nested
  class getNetWorth {
    private Stock appleStock;
    private Stock googleStock;
    private Stock amazonStock;

    private Share appleShare;
    private Share amazonShare;
    private Share googleShare;

    @BeforeEach
    void setUp() {
      appleStock  = new Stock("AAPL", "Apple Inc.",  new BigDecimal("150.00"));
      googleStock = new Stock("GOOG", "Google LLC",  new BigDecimal("2800.00"));
      amazonStock = new Stock("AMZN", "Amazon.com, Inc.",  new BigDecimal("1000.00"));

      appleShare = new Share(appleStock,  new BigDecimal("10"), new BigDecimal("100"));
      amazonShare = new Share(amazonStock,  new BigDecimal("5"),  new BigDecimal("145.00"));
      googleShare = new Share(googleStock, new BigDecimal("2"),  new BigDecimal("2750.00"));


    }

    @Test
    void checkThatNetWorthIsCorrect() {
      player.getPortfolio().addShare(appleShare);
      ArrayList<Share> shares = (ArrayList<Share>) player.getPortfolio().getShares();
      BigDecimal total = shares.stream()
          .map(e -> new SaleCalculator(e).calculateTotal())
          .reduce(BigDecimal.ZERO, BigDecimal::add).add(player.getStartingMoney());
      assertEquals(total, player.getNetWorth());
    }

    @Test
    void CheckThatNetWorthIsCorrectWhenNoSharesArePresent() {
      assertEquals(player.getStartingMoney(), player.getNetWorth());
    }

    @Test void checkThatNetWorthUpdatesWhenSharesAreAdded() {
      player.getPortfolio().addShare(appleShare);
      ArrayList<Share> shares = (ArrayList<Share>) player.getPortfolio().getShares();
      BigDecimal total = shares.stream()
          .map(e -> new SaleCalculator(e).calculateTotal())
          .reduce(BigDecimal.ZERO, BigDecimal::add).add(player.getStartingMoney());
      assertEquals(total, player.getNetWorth());

      player.getPortfolio().addShare(googleShare);
      total = shares.stream()
          .map(e -> new SaleCalculator(e).calculateTotal())
          .reduce(BigDecimal.ZERO, BigDecimal::add).add(player.getStartingMoney());
      assertEquals(total, player.getNetWorth());

    }
  }
}