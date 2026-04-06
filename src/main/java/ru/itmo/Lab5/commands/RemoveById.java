package ru.itmo.Lab5.commands;

import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * RemoveById command
 */
public class RemoveById implements Command {
  private CollectionManager collection;

  public RemoveById(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

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

  @Override
  public String usage() {
    String res = "";
    res += " - remove_by_id id:\n";
    res += "Removes the element with the specified id";
    return res;
  }

  @Override
  public int numberOfArgs() {
    return 1;
  }

  @Override
  public boolean requiresDragon() {
    return false;
  }
}
