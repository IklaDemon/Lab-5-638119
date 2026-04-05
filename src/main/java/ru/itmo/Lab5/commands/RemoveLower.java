package ru.itmo.Lab5.commands;

import java.util.Date;
import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.dragon.DragonMaker;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * RemoveLower command
 */
public class RemoveLower implements Command {
  private CollectionManager collection;

  public RemoveLower(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

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

  @Override
  public String usage() {
    String res = "";
    res += " - remove_lower {element}:\n";
    res += "Removes the elements smaller than the given one";
    return res;
  }

  @Override
  public int numberOfArgs() {
    return 1;
  }

  @Override
  public boolean requiresDragon() {
    return true;
  }

}
