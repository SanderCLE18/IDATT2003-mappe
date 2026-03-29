package idi.gruppe07.utils;

import java.math.BigDecimal;

/**
 * Guardclass to validate different values.
 */
public class Validate {

  /**
   * The object to validate
   *
   * @see Object
   */
  private final Object object;

  /**
   * Private constructor
   * To disallow creation of this class through uncontrolled means.
   *
   * @param object The object to validate
   */
  private Validate(Object object) {
    this.object = object;
  }

  /**
   * Static factory method to create a new Validate instance.
   *
   */
  public static Validate that(Object object) {
    return new Validate(object);
  }

  /**
   * Validates that the object is not null.
   *
   * @return this
   * @throws NullPointerException if the object is null
   */
  public Validate isNull() throws NullPointerException {
    if (object == null) {
      throw new NullPointerException("Object cannot be null!");
    }
    return this;
  }

  /**
   * Validates that the object is not null or empty.
   * To be used with Strings.
   *
   * @return this
   * @throws IllegalArgumentException if the object is null or empty
   */
  public Validate isNullOrEmpty() throws IllegalArgumentException {
    if (object instanceof String str && str.isEmpty()) {
      throw new IllegalArgumentException("String cannot be empty!");
    }
    return this;
  }

  /**
   * Validates that the object is not negative. Works with BigDecimal and primitive numbers.
   *
   * @return this
   * @throws IllegalArgumentException if the object is negative
   */
  public Validate isNegative() throws IllegalArgumentException {
    if (object instanceof BigDecimal bd) {
      if (bd.compareTo(BigDecimal.ZERO) < 0) {
        throw new IllegalArgumentException("Number cannot be negative!");
      }
    } else if (!(object instanceof Number)) {
      return this;
    }
    double num = (double) object;
    if (num < 0) {
      throw new IllegalArgumentException("Number cannot be negative!");
    }
    return this;

  }


}

