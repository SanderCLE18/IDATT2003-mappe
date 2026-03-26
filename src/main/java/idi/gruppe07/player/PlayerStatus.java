package idi.gruppe07.player;

import java.math.BigDecimal;

/**
 * Represents the various progression tiers for a player, each associated with a display name and a
 * numerical multiplier.
 */
public enum PlayerStatus {
  /** Status for new or inexperienced players. */
  NOVICE("Novice", new BigDecimal("1")),

  /** Status for intermediate players. */
  INVESTOR("Investor", new BigDecimal("1.2")),
  /** Status for the best of the best. */
  SPECULATOR("Speculator", new BigDecimal("2.0"));

  private final String name;
  private final BigDecimal constant;

  /**
   * Internal constructor for PlayerStatus constants.
   *
   * @param name The human-readable label for the status.
   * @param constant The {@link BigDecimal} value used for business logic calculations.
   */
  PlayerStatus(String name, BigDecimal constant) {
    this.name = name;
    this.constant = constant;
  }

  /**
   * Returns the display name of the status.
   *
   * @return A String representing the player's status name.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the mathematical constant associated with this status.
   *
   * @return A {@link BigDecimal} used for status-based multipliers.
   */
  public BigDecimal getConstant() {
    return constant;
  }
}
