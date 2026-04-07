package ru.itmo.Lab5.commands;

import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Command that removes a dragon from the collection by its identifier.
 */
public class RemoveById implements Command {
  private CollectionManager collection;

  /**
   * Creates the command with the specified collection manager.
   *
   * @param collection collection manager used to access the collection
   * @throws NullPointerException if {@code collection} is null
   */
  public RemoveById(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  /**
   * Executes the remove_by_id command.
   *
   * <p>
   * Parses the specified id and removes the corresponding dragon
   * from the collection.
   *
   * @param arg command argument containing the dragon id
   * @return result message of the execution
   */
  @Override
  public String exec(String arg) {
    String args[] = arg.split("\\s+");

    if (args.length != this.numberOfArgs()) {
      return "Wrong number of argument/s. " + this.numberOfArgs() + " argument/s are needed\n";
    }

    try {
      long id = Long.parseLong(args[0]);
      collection.remove(id);
    } catch (Exception e) {
      return "Problem with the id: " + e.getMessage() + "\n";
    }

    return "Dragon removed\n";
  }

  /**
   * Returns the usage description of the command.
   *
   * @return usage string
   */
  @Override
  public String usage() {
    String res = "";
    res += " - remove_by_id id:\n";
    res += "Removes the element with the specified id";
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
