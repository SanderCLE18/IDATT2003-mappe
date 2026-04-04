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
  public Validate isNotNull() throws NullPointerException {
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
  public Validate isNotNullOrEmpty() throws IllegalArgumentException {
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
  public Validate isNotNegative() throws IllegalArgumentException {
    if (object instanceof BigDecimal bd) {
      if (bd.compareTo(BigDecimal.ZERO) < 0) {
        throw new IllegalArgumentException("Number cannot be negative!");
      }
      return this;
    } else if (!(object instanceof Number)) {
      return this;
    }
    double num = Double.parseDouble(object.toString());
    if (num < 0) {
      throw new IllegalArgumentException("Number cannot be negative!");
    }
    return this;

  }

  /**
   * Validates that a given object is not Zero. Compatible with BigDecimal and primitives.
   *
   * @return this
   * @throws IllegalArgumentException if the object is equal to Zero
   */
  public Validate isNotZero() throws IllegalArgumentException {
    if (object instanceof BigDecimal bd) {
      if (bd.compareTo(BigDecimal.ZERO) == 0) {
        throw new IllegalArgumentException("Number cannot be zero!");
      }
      return this;
    } else if (!(object instanceof Number)) {
      return this;
    }
    double num = Double.parseDouble(object.toString());
    if (num == 0) {
      throw new IllegalArgumentException("Number cannot be zero!");
    }
    return this;
  }

  /**
   * Validates that the object is a valid number.
   *
   * @return this
   * @throws IllegalArgumentException if the object is not a valid number
   * @see BigDecimal*/
  public Validate isValidNumber() throws IllegalArgumentException {
    if (object == null) {
      throw new IllegalArgumentException("Object cannot be null!");
    }

    try{
      new BigDecimal(object.toString());
    }catch(NumberFormatException e){
      throw new IllegalArgumentException("Object is not a valid number!");
    }
    return this;
  }

}

