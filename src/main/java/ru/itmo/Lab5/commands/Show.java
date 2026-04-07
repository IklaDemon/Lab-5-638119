package ru.itmo.Lab5.commands;

import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Command that displays all elements of the collection.
 */
public class Show implements Command {
  private CollectionManager collection;

  /**
   * Creates the command with the specified collection manager.
   *
   * @param collection collection manager used to access the collection
   * @throws NullPointerException if {@code collection} is null
   */
  public Show(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collectin manager is null");
    }
    this.collection = collection;
  }

  /**
   * Executes the show command.
   *
   * <p>
   * Returns the string representation of all elements in the collection.
   *
   * @param arg command arguments
   * @return collection content
   */
  @Override
  public String exec(String arg) {
    String show = this.collection.toString();
    return show;
  }

  /**
   * Returns the usage description of the command.
   *
   * @return usage string
   */
  @Override
  public String usage() {
    String res = "";
    res += " - show:\n";
    res += "Shows all elements from collection:";
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
