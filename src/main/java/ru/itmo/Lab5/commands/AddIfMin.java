package ru.itmo.Lab5.commands;

import java.util.Date;
import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.dragon.DragonMaker;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Command that adds a dragon to the collection only if it is smaller
 * than the current minimum element.
 */
public class AddIfMin implements Command {
  private CollectionManager collection;

  /**
   * Creates the command with the specified collection manager.
   *
   * @param collection collection manager used to access the collection
   * @throws NullPointerException if {@code collection} is null
   */
  public AddIfMin(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  /**
   * Executes the add_if_min command.
   *
   * <p>
   * Creates a new dragon and adds it to the collection only if it is
   * smaller than the current minimum dragon.
   *
   * @param arg command argument containing dragon data
   * @return result message of the execution
   */
  @Override
  public String exec(String arg) {
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
    if (minDragon == null) {
      collection.addDragon(newDragon);
      return "Added new Dragon: " + newDragon.getName() + "\n";
    } else {
      if (newDragon.compareTo(minDragon) < 0) {
        collection.addDragon(newDragon);
        return "Added new Dragon: " + newDragon.getName() + "\n";
      } else {
        return newDragon.getName() + " is bigger than " + minDragon.getName() +
            "\n";
      }
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
    res += " - add_if_min {element}:\n";
    res += "Adds the element only if it is smaller than the current smallest present";
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
