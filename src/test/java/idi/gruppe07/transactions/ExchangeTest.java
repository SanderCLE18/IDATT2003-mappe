package idi.gruppe07.transactions;

import idi.gruppe07.entities.*;
import idi.gruppe07.transactions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class  ExchangeTest {

  private Exchange exchange;
  private Stock appleStock;
  private Stock googleStock;
  private Player player;

  @BeforeEach
  void setUp() {
    // Initialize sample stocks
    appleStock = new Stock("AAPL", "Apple Inc", new BigDecimal("150.00"));

    googleStock = new Stock("GOOGL", "Alphabet Inc", new BigDecimal("2800.00"));

    List<Stock> stocks = List.of(appleStock, googleStock);

    // Initialize Exchange and Player
    exchange = new Exchange("Nasdaq", stocks);
    player = new Player("Test Player", new BigDecimal("10000.00"));
  }

  @Test
  void testHasStock() {
    assertTrue(exchange.hasStock("AAPL"), "Exchange should contain AAPL");
    assertTrue(exchange.hasStock("GOOGL"), "Exchange should contain GOOGL");
    assertFalse(exchange.hasStock("MSFT"), "Exchange should not contain MSFT");
  }

  @Test
  void testFindStocks() {
    // Search for "Apple" (case insensitive)
    List<Stock> results = exchange.findStocks("apple");
    assertEquals(1, results.size());
    assertEquals("AAPL", results.get(0).getSymbol());

    // Search for partial name
    List<Stock> alphaResults = exchange.findStocks("Alpha");
    assertEquals(1, alphaResults.size());
    assertEquals("GOOGL", alphaResults.get(0).getSymbol());
  }

  @Test
  void testBuy() {
    BigDecimal quantity = new BigDecimal("10");
    Transaction transaction = exchange.buy("AAPL", quantity, player);

    assertNotNull(transaction, "Transaction should not be null for valid symbol");
    assertTrue(transaction instanceof Purchase, "Transaction should be a Purchase type");

    // Verify portfolio update
    assertEquals(1, player.getPortfolio().getShares().size());
    Share share = player.getPortfolio().getShares().get(0);
    assertEquals(appleStock, share.getStock());
    assertEquals(quantity, share.getQuantity());

    // Test buying non-existent stock
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      exchange.buy("MSFT", quantity, player);
    });
    assertEquals("Invalid stock", exception.getMessage());
  }

  @Test
  void testSell() {
    // Manually add a share to sell
    BigDecimal quantity = new BigDecimal("5");
    Share shareToSell = new Share(appleStock, quantity, appleStock.getPrice());
    player.getPortfolio().addShare(shareToSell);

    BigDecimal initialMoney = player.getMoney();
    BigDecimal expectedSaleValue = appleStock.getPrice().multiply(quantity);

    Transaction transaction = exchange.sell(shareToSell, player);

    assertNotNull(transaction);
    // Verify money was added to player
    assertEquals(initialMoney.add(expectedSaleValue), player.getMoney());
    // Verify share was removed from portfolio
    assertFalse(player.getPortfolio().contains(shareToSell));
  }

  @RepeatedTest(10)
  void testAdvance() {
    int initialWeek = exchange.getWeek();
    BigDecimal initialPrice = appleStock.getPrice();

    exchange.advance();

    // Check week incremented
    assertEquals(initialWeek + 1, exchange.getWeek());

    // Check price updated (statistically unlikely to remain exactly the same)
    assertNotEquals(initialPrice, appleStock.getPrice(),
        "Stock price should have changed after advance()");
  }
}