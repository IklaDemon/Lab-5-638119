package ru.itmo.Lab5.commands;

import java.util.Date;

import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.dragon.DragonMaker;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Command that adds a dragon
 */
public class Add implements Command {
  private CollectionManager collection;

  public Add(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  @Override
  public int numberOfArgs() {
    return 1;
  }

  @Override
  public boolean requiresDragon() {
    return true;
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

  @Override
  public String usage() {
    String res = "";
    res += " - add {element}:\n";
    res += "Adds a new element to the collection\n";
    res += "Each attribute will be typed on a new line";
    return res;
  }
}
