package ru.itmo.Lab5.commands;

import java.util.Date;
import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.dragon.DragonMaker;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Update command
 */
public class Update implements Command {
  private CollectionManager collection;

  public Update(CollectionManager collection) {
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
    long id;
    try {
      id = Long.parseLong(args[8]);
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
      newDragon = dragonMaker.make(args[0], id, creationDate);
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

  @Override
  public String usage() {
    String res = "";
    res += " - update id {element}:\n";
    res += "Updates the element with the specified id\n";
    res += "Each attribute will be typed on a new line";
    return res;
  }

  @Override
  public int numberOfArgs() {
    return 2;
  }

  @Override
  public boolean requiresDragon() {
    return false;
  }
}
