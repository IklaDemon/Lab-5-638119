package ru.itmo.Lab5.dragon;

/**
 * Class Coordinates
 * per exercise
 */
public class Coordinates {
  private long x;
  private Long y; // The field cannot be null

  public Coordinates(long x, Long y) {
    setX(x);
    setY(y);
  }

  public void setX(long x) {
    this.x = x;
  }

  public void setY(Long y) {
    if (y == null) {
      throw new NullPointerException("y = null. Should not be null");
    }
    this.y = y;
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
