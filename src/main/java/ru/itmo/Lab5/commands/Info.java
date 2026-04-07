package ru.itmo.Lab5.commands;

import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Command that displays information about the collection.
 */
public class Info implements Command {
  private CollectionManager collection;

  /**
   * Creates the command with the specified collection manager.
   *
   * @param collection collection manager used to access collection information
   * @throws NullPointerException if {@code collection} is null
   */
  public Info(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  /**
   * Executes the info command.
   *
   * <p>
   * Returns general information about the collection.
   *
   * @param args command arguments
   * @return collection information
   */
  @Override
  public String exec(String args) {
    String info = this.collection.info();
    return info;
  }

  /**
   * Returns the usage description of the command.
   *
   * @return usage string
   */
  @Override
  public String usage() {
    String res = "";
    res += " - info:\n";
    res += "Prints information about the collection:";
    return res;
  }

  /**
   * Returns the number of arguments required by this command.
   *
   * @return {@code 0}
   */
  @Override
  public int numberOfArgs() {
    return 0;
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
