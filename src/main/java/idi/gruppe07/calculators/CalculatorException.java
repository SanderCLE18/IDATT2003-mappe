package idi.gruppe07.calculators;

/**
 * Represents an exception that occurs during the construction of a transaction.*/
public class CalculatorException extends Exception {
  private final int code;

  /**
   * Constructs a new {@code CalculatorException} with the given message and code.
   * @param message The message of the exception.
   * @param code The code of the exception.
   * */
  public CalculatorException(String message, int code)
  {
    super(message);
    this.code = code;
  }
  /**
   * Gets the code of the exception.
   * @return code The code of the exception.*/
  public int getCode() {
    return code;
  }
}
