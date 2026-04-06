package ru.itmo.Lab5.commands;

import java.util.Date;
import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.dragon.DragonMaker;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Command that AddIfMin a dragon
 */
public class AddIfMin implements Command {
  private CollectionManager collection;

  public AddIfMin(CollectionManager collection) {
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

  @Override
  public String usage() {
    String res = "";
    res += " - add_if_min {element}:\n";
    res += "Adds the element only if it is smaller than the current smallest present";
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
