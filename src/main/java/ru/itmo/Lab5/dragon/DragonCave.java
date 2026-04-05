package ru.itmo.Lab5.dragon;

/**
 * class DragonCave, as per exercise
 */
public class DragonCave {
  private Double numberOfTreasures; // The field cannot be null, the field value must be greater than 0

  public DragonCave(Double numberOfTreasures) {
    setNumberOfTreasures(numberOfTreasures);
  }

  public DragonCave(String numberOfTreasures) {
    setNumberOfTreasures(numberOfTreasures);
  }

  public void setNumberOfTreasures(Double numberOfTreasures) {
    if (numberOfTreasures == null) {
      throw new NullPointerException("numberOfTreasures = null. Should not be null");
    }
    if (numberOfTreasures.doubleValue() <= 0) {
      throw new IllegalArgumentException("numberOfTreasures = " + numberOfTreasures + ". Should be > 0");
    }
    this.numberOfTreasures = numberOfTreasures;
  }

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
