package ru.itmo.Lab5.commands;

import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Command that saves the collection to the external data source.
 */
public class Save implements Command {
  private CollectionManager collection;

  /**
   * Creates the command with the specified collection manager.
   *
   * @param collection collection manager used to access the collection
   * @throws NullPointerException if {@code collection} is null
   */
  public Save(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  /**
   * Executes the save command.
   *
   * <p>
   * Writes the current collection to the external source.
   *
   * @param arg command arguments
   * @return result message of the execution
   */
  @Override
  public String exec(String arg) {
    String res = "";
    boolean status = collection.exportToExternalSource();
    if (status) {
      res = "File created succesfully\n";
    } else {
      res = "Problem while writing\n";
    }
    return res;
  }

  /**
   * Returns the usage description of the command.
   *
   * @return usage string
   */
  @Override
  public String usage() {
    String res = "";
    res += " - save:\n";
    res += "save the collection to a file";
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
