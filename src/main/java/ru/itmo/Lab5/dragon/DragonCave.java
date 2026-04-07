package ru.itmo.Lab5.dragon;

/**
 * Represents a dragon cave.
 *
 * <p>
 * A cave stores the number of treasures, which must be greater than 0.
 */
public class DragonCave {
  private Double numberOfTreasures; // The field cannot be null, the field value must be greater than 0

  /**
   * Creates a cave with the specified number of treasures.
   *
   * @param numberOfTreasures number of treasures, must not be null and must be
   *                          greater than 0
   * @throws NullPointerException     if {@code numberOfTreasures} is null
   * @throws IllegalArgumentException if {@code numberOfTreasures <= 0}
   */
  public DragonCave(Double numberOfTreasures) {
    setNumberOfTreasures(numberOfTreasures);
  }

  /**
   * Creates a cave from a string representation of the number of treasures.
   *
   * @param numberOfTreasures string representation of the number of treasures
   * @throws IllegalArgumentException if the value cannot be parsed or is not
   *                                  greater than 0
   */
  public DragonCave(String numberOfTreasures) {
    setNumberOfTreasures(numberOfTreasures);
  }

  /**
   * Sets the number of treasures in the cave.
   *
   * @param numberOfTreasures number of treasures, must not be null and must be
   *                          greater than 0
   * @throws NullPointerException     if {@code numberOfTreasures} is null
   * @throws IllegalArgumentException if {@code numberOfTreasures <= 0}
   */
  public void setNumberOfTreasures(Double numberOfTreasures) {
    if (numberOfTreasures == null) {
      throw new NullPointerException("numberOfTreasures = null. Should not be null");
    }
    if (numberOfTreasures.doubleValue() <= 0) {
      throw new IllegalArgumentException("numberOfTreasures = " + numberOfTreasures + ". Should be > 0");
    }
    this.numberOfTreasures = numberOfTreasures;
  }

  /**
   * Parses and sets the number of treasures from a string.
   *
   * @param numberOfTreasures string representation of the number of treasures
   * @throws IllegalArgumentException if the value cannot be parsed or is not
   *                                  greater than 0
   */
  public void setNumberOfTreasures(String numberOfTreasures) {
    try {
      Double doubleNumberOfTreasures = Double.parseDouble(numberOfTreasures);
      this.setNumberOfTreasures(doubleNumberOfTreasures);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid Double number for number of treasures");
    }
  }

  public Double getNumberOfTreasures() {
    return numberOfTreasures;
  }

  @Override
  public String toString() {
    return String.format("Number of treasures: %s", numberOfTreasures.toString());
  }
}
