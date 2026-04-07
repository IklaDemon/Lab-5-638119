package ru.itmo.Lab5.dragon;

/**
 * Represents the coordinates of a dragon.
 *
 * <p>
 * Contains two coordinate values, where {@code y} must not be null.
 */
public class Coordinates {
  private long x;
  private Long y; // The field cannot be null

  /**
   * Creates coordinates from numeric values.
   *
   * @param x x coordinate
   * @param y y coordinate, must not be null
   * @throws NullPointerException if {@code y} is null
   */
  public Coordinates(long x, Long y) {
    setX(x);
    setY(y);
  }

  /**
   * Creates coordinates from string values.
   *
   * @param x string representation of x coordinate
   * @param y string representation of y coordinate
   * @throws IllegalArgumentException if one of the values cannot be parsed
   */
  public Coordinates(String x, String y) {
    setX(x);
    setY(y);
  }

  /**
   * Sets the x coordinate.
   *
   * @param x x coordinate value
   */
  public void setX(long x) {
    this.x = x;
  }

  /**
   * Sets the y coordinate.
   *
   * @param y y coordinate value, must not be null
   * @throws NullPointerException if {@code y} is null
   */
  public void setY(Long y) {
    if (y == null) {
      throw new NullPointerException("y = null. Should not be null");
    }
    this.y = y;
  }

  /**
   * Parses and sets the x coordinate from a string.
   *
   * @param x string representation of x coordinate
   * @throws IllegalArgumentException if the value cannot be parsed as
   *                                  {@code long}
   */
  public void setX(String x) {
    try {
      this.x = Long.parseLong(x);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid long number for x");
    }
  }

  /**
   * Parses and sets the y coordinate from a string.
   *
   * @param y string representation of y coordinate
   * @throws IllegalArgumentException if {@code y} is null or cannot be parsed as
   *                                  {@code long}
   */
  public void setY(String y) {
    if (y == null) {
      throw new IllegalArgumentException("y should not be null");
    }
    try {
      this.y = Long.parseLong(y);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid long number for y");
    }
  }

  public long getX() {
    return x;
  }

  public Long getY() {
    return y;
  }

  @Override
  public String toString() {
    return String.format("x = %,d, y = %s", x, y.toString());
  }
}
