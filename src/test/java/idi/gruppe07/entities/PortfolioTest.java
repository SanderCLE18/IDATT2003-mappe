package idi.gruppe07.entities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioTest {

  private Portfolio portfolio;
  private Stock appleStock;
  private Stock googleStock;
  private Share appleShare1;
  private Share appleShare2;
  private Share googleShare;

  @BeforeEach
  void setUp() {
    portfolio = new Portfolio();
    appleStock  = new Stock("AAPL", "Apple Inc.",  new BigDecimal("150.00"));
    googleStock = new Stock("GOOG", "Google LLC",  new BigDecimal("2800.00"));
    appleShare1 = new Share(appleStock,  new BigDecimal("10"), new BigDecimal("140.00"));
    appleShare2 = new Share(appleStock,  new BigDecimal("5"),  new BigDecimal("145.00"));
    googleShare = new Share(googleStock, new BigDecimal("2"),  new BigDecimal("2750.00"));
  }

  @Nested
  class AddShare {

    @Test
    void validShare_returnsTrue() {
      assertTrue(portfolio.addShare(appleShare1));
    }

    @Test
    void validShare_isStoredInPortfolio() {
      portfolio.addShare(appleShare1);
      assertTrue(portfolio.getShares().contains(appleShare1));
    }

    @Test
    void multipleShares_allStored() {
      portfolio.addShare(appleShare1);
      portfolio.addShare(appleShare2);
      portfolio.addShare(googleShare);
      assertEquals(3, portfolio.getShares().size());
    }

    @Test
    void nullShare_throwsNullPointerException() {
      assertThrows(NullPointerException.class, () -> portfolio.addShare(null));
    }
  }

  @Nested
  class RemoveShare {

    @Test
    void existingShare_returnsTrue() {
      portfolio.addShare(appleShare1);
      assertTrue(portfolio.removeShare(appleShare1));
    }

    @Test
    void existingShare_isNoLongerInPortfolio() {
      portfolio.addShare(appleShare1);
      portfolio.removeShare(appleShare1);
      assertFalse(portfolio.getShares().contains(appleShare1));
    }

    @Test
    void nonExistingShare_returnsFalse() {
      assertFalse(portfolio.removeShare(appleShare1));
    }

    @Test
    void nullShare_throwsNullPointerException() {
      assertThrows(NullPointerException.class, () -> portfolio.removeShare(null));
    }
  }

  @Nested
  class GetShares {

    @Test
    void emptyPortfolio_returnsEmptyList() {
      assertTrue(portfolio.getShares().isEmpty());
    }

    @Test
    void afterAdding_returnsCorrectSize() {
      portfolio.addShare(appleShare1);
      portfolio.addShare(googleShare);
      assertEquals(2, portfolio.getShares().size());
    }

    @Nested
    class BySymbol {

      @Test
      void matchingSymbol_returnsCorrectShares() {
        portfolio.addShare(appleShare1);
        portfolio.addShare(appleShare2);
        portfolio.addShare(googleShare);

        List<Share> result = portfolio.getShares("Apple Inc.");
        assertEquals(2, result.size());
        assertTrue(result.contains(appleShare1));
        assertTrue(result.contains(appleShare2));
      }

      @Test
      void noMatch_returnsEmptyList() {
        portfolio.addShare(appleShare1);

        List<Share> result = portfolio.getShares("Nonexistent Corp.");
        assertTrue(result.isEmpty());
      }

      @Test
      void emptySymbol_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> portfolio.getShares(""));
      }

      @Test
      void doesNotReturnSharesOfOtherCompanies() {
        portfolio.addShare(appleShare1);
        portfolio.addShare(googleShare);

        List<Share> result = portfolio.getShares("Apple Inc.");
        assertFalse(result.contains(googleShare));
      }
    }
  }

  @Nested
  class Contains {

    @Test
    void shareInPortfolio_returnsTrue() {
      portfolio.addShare(appleShare1);
      assertTrue(portfolio.contains(appleShare1));
    }

    @Test
    void shareNotInPortfolio_returnsFalse() {
      assertFalse(portfolio.contains(appleShare1));
    }

    @Test
    void afterRemoval_returnsFalse() {
      portfolio.addShare(appleShare1);
      portfolio.removeShare(appleShare1);
      assertFalse(portfolio.contains(appleShare1));
    }

    @Test
    void nullShare_throwsNullPointerException() {
      assertThrows(NullPointerException.class, () -> portfolio.contains(null));
    }
  }

  @Nested
  class GetNetWorth {

    @Test
    void emptyPortfolio_returnsZero() {
      assertEquals(BigDecimal.ZERO, portfolio.getNetWorth());
    }

    @Test
    void withShares_returnsPositiveValue() {
      portfolio.addShare(appleShare1);
      portfolio.addShare(googleShare);
      assertTrue(portfolio.getNetWorth().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void increasesWhenShareIsAdded() {
      portfolio.addShare(appleShare1);
      BigDecimal before = portfolio.getNetWorth();

      portfolio.addShare(googleShare);
      BigDecimal after = portfolio.getNetWorth();

      assertTrue(after.compareTo(before) > 0);
    }

    @Test
    void decreasesWhenShareIsRemoved() {
      portfolio.addShare(appleShare1);
      portfolio.addShare(googleShare);
      BigDecimal before = portfolio.getNetWorth();

      portfolio.removeShare(googleShare);
      BigDecimal after = portfolio.getNetWorth();

      assertTrue(after.compareTo(before) < 0);
    }
  }
}
