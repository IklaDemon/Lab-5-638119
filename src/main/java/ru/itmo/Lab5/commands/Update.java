package ru.itmo.Lab5.commands;

import java.util.Date;
import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.dragon.DragonMaker;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Command that updates a dragon with the specified identifier.
 */
public class Update implements Command {
  private CollectionManager collection;

  /**
   * Creates the command with the specified collection manager.
   *
   * @param collection collection manager used to access the collection
   * @throws NullPointerException if {@code collection} is null
   */
  public Update(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  /**
   * Executes the update command.
   *
   * <p>
   * Finds the dragon with the specified id, creates a new dragon
   * from the provided data, and replaces the old element.
   *
   * @param arg command arguments containing the id and dragon data
   * @return result message of the execution
   */
  @Override
  public String exec(String arg) {
    String args[] = arg.split("\\s+");
    if (args.length != this.numberOfArgs()) {
      return "Wrong number of argument/s. " + this.numberOfArgs() + " argument/s are needed\n";
    }
    long id;
    try {
      id = Long.parseLong(args[0]);
    } catch (Exception e) {
      return "Wrong ID format\n";
    }
    Date creationDate;
    try {
      creationDate = collection.findById(id).getCreationDate();
    } catch (NullPointerException e) {
      return "No dragon with given ID";
    }
    DragonMaker dragonMaker = new DragonMaker();
    Dragon newDragon;

    try {
      newDragon = dragonMaker.make(args[1], id, creationDate);
    } catch (IllegalArgumentException e) {
      return dragonMaker.getErrors();
    }

    try {
      collection.update(id, newDragon);
    } catch (Exception e) {
      return "Not possible to update: " + e.getMessage() + "\n";
    }

    return "Dragon updated" + "\n";
  }

  /**
   * Returns the usage description of the command.
   *
   * @return usage string
   */
  @Override
  public String usage() {
    String res = "";
    res += " - update id {element}:\n";
    res += "Updates the element with the specified id\n";
    res += "Each attribute will be typed on a new line";
    return res;
  }

  /**
   * Returns the number of arguments required by this command.
   *
   * @return required number of arguments
   */
  @Override
  public int numberOfArgs() {
    return 2;
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
