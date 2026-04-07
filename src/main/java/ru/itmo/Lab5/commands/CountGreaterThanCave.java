package ru.itmo.Lab5.commands;

import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Command that counts dragons whose cave value is greater than
 * the specified number of treasures.
 */
public class CountGreaterThanCave implements Command {
  private CollectionManager collection;

  /**
   * Creates the command with the specified collection manager.
   *
   * @param collection collection manager used to access the collection
   * @throws NullPointerException if {@code collection} is null
   */
  public CountGreaterThanCave(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  /**
   * Executes the count_greater_than_cave command.
   *
   * <p>
   * Parses the specified cave value and returns the number of dragons
   * whose cave contains more treasures than that value.
   *
   * @param arg command argument containing the number of treasures
   * @return result message of the execution
   */
  @Override
  public String exec(String arg) {
    String args[] = arg.split("\\s+");

    if (args.length != this.numberOfArgs()) {
      return "Wrong number of argument/s. " + this.numberOfArgs() + " argument/s are needed\n";
    }

    double treasures = 0;
    try {
      treasures = Double.parseDouble(args[0]);
      if (treasures <= 0)
        return "Cave number should be > 0";
      int n = collection.nGreaterThanCave(treasures);
      return n + "\n";
    } catch (Exception e) {
      return "Invalid argument: " + e.getMessage() + "\n";
    }
  }

  /**
   * Returns the usage description of the command.
   *
   * @return usage string
   */
  @Override
  public String usage() {
    String res = "";
    res += " - count_greater_than_cave cave:\n";
    res += "display the number of elements whose cave field value is greater than the specified value";
    return res;
  }

  /**
   * Returns the number of arguments required by this command.
   *
   * @return required number of arguments
   */
  @Override
  public int numberOfArgs() {
    return 1;
  }

  /**
   * Indicates that this command does not require dragon data.
   *
   * @return {@code false}
   */
  @Override
  public boolean requiresDragon() {
    return false;
  }

}
