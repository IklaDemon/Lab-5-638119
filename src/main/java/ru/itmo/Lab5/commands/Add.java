package ru.itmo.Lab5.commands;

import java.util.Date;
import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.dragon.DragonMaker;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Command that adds a new dragon to the collection.
 */
public class Add implements Command {
  private CollectionManager collection;

  /**
   * Creates the command with the specified collection manager.
   *
   * @param collection collection manager used to store dragons
   * @throws NullPointerException if {@code collection} is null
   */
  public Add(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
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

  /**
   * Executes the add command.
   *
   * <p>
   * Creates a new dragon from the provided argument and adds it to the
   * collection.
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
    Dragon dragon;

    try {
      dragon = dragonMaker.make(args[0], id, creationDate);
    } catch (IllegalArgumentException e) {
      return dragonMaker.getErrors();
    }

    try {
      collection.addDragon(dragon);
      return "Created new Dragon: " + dragon.getName() + "\n";
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    } catch (IndexOutOfBoundsException e) {
      return e.getMessage();
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
    res += " - add {element}:\n";
    res += "Adds a new element to the collection\n";
    res += "Each attribute will be typed on a new line";
    return res;
  }
}
