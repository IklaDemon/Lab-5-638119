package ru.itmo.Lab5.commands;

import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Clear command
 */
public class Clear implements Command {
  private CollectionManager collection;

  public Clear(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  @Override
  public String exec(String args) {
    collection.clear();
    return "Collection cleared\n";
  }

  @Override
  public String usage() {
    String res = "";
    res += " - add {element}:\n";
    res += "Adds a new element to the collection\n";
    res += "Each attribute will be typed on a new line";
    return res;
  }

  @Override
  public int numberOfArgs() {
    return 0;
  }

  @Override
  public boolean requiresDragon() {
    return false;
  }
}
