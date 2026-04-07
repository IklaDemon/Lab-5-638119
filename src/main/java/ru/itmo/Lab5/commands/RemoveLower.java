package ru.itmo.Lab5.commands;

import java.util.Date;
import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.dragon.DragonMaker;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Command that removes all dragons smaller than the specified dragon.
 */
public class RemoveLower implements Command {
  private CollectionManager collection;

  /**
   * Creates the command with the specified collection manager.
   *
   * @param collection collection manager used to access the collection
   * @throws NullPointerException if {@code collection} is null
   */
  public RemoveLower(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  /**
   * Executes the remove_lower command.
   *
   * <p>
   * Creates a reference dragon from the provided argument and removes
   * all collection elements that are smaller than it.
   *
   * @param arg command argument containing dragon data
   * @return result message of the execution
   */
  @Override
  public String exec(String arg) {
    String res = "";
    String args[] = arg.split("\\s+");
    if (args.length != this.numberOfArgs()) {
      return "Wrong number of argument/s. " + this.numberOfArgs() + " argument/s are needed\n";
    }

    long id = collection.genNewID();
    Date creationDate = collection.genNewDate();
    DragonMaker dragonMaker = new DragonMaker();
    Dragon newDragon;

    try {
      newDragon = dragonMaker.make(args[0], id, creationDate);
    } catch (IllegalArgumentException e) {
      return dragonMaker.getErrors();
    }

    Dragon minDragon = collection.getMin();

    while (minDragon != null && minDragon.compareTo(newDragon) < 0) {
      collection.remove(minDragon.getId());
      res += "Removed dragon: " + minDragon.getName() + ", ID: " +
          minDragon.getId() + "\n";
      minDragon = collection.getMin();
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
    res += " - remove_lower {element}:\n";
    res += "Removes the elements smaller than the given one";
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
   * Indicates that this command requires dragon data.
   *
   * @return {@code true}
   */
  @Override
  public boolean requiresDragon() {
    return true;
  }

}
