package idi.gruppe07.entities;

import idi.gruppe07.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
}