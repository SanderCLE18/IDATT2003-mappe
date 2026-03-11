package idi.gruppe07.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioTest {


  Portfolio portfolio;
  Stock stock;
  Share share;

  @BeforeEach
  void setUp() {
    portfolio = new Portfolio();
    List<BigDecimal> salesPrice = new ArrayList<>();
    salesPrice.add(new BigDecimal("150.00"));
    stock = new Stock("AAPL", "Apple Inc.", salesPrice);
    share = new Share(stock, BigDecimal.valueOf(10), BigDecimal.valueOf(150.00));
  }

  @Test
  void checkThatShareIsAddedToShares() {
    assertTrue(portfolio.addShare(share));
  }

  @Test
  void checkThatShareIsSuccesfullyRemoved() {
    portfolio.addShare(share);
    assertTrue(portfolio.removeShare(share));
  }

  @Test
  void checkThatShareIsNotRemovedIfNotInPortfolio() {
    assertFalse(portfolio.removeShare(share));
  }

  @Test
  void checkThatObjectContainsAddedShare() {
    portfolio.addShare(share);
    assertTrue(portfolio.contains(share));
  }
}